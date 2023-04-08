import db
import scrape
import service

year = 2023

url_template = f"https://firstcycling.com/race.php?y={year}&t=2&m="


def get_race_urls(starting_month, ending_month):
    categories = db.select_all_categories()

    months = {i: [] for i in range(starting_month, ending_month + 1)}

    race_urls = {"one_day_races": set(), "stage_races": set()}

    for month in months:
        url = url_template + str(month)

        page = scrape.get_soup(url)

        tables = page.find_all('table', class_='sortTabell tablesorter')

        if len(tables) > 0:
            table = tables[-1]
        else:
            table = None

        if table is not None:
            for row in table.find_all('tr'):
                columns = row.find_all('td')

                cat_col, race_col = None, None

                if len(columns) > 2:
                    cat_col = columns[1].get_text().strip()
                    race_col = columns[2].find('a')['href']

                if cat_col in categories:
                    race_url = service.normalize_url(f'https://firstcycling.com/{race_col}', str(year))

                    if cat_col[0] == '2':  # if first character of category is 2
                        # stage race
                        race_urls["stage_races"].add(race_url)
                    else:
                        # one day race
                        race_urls["one_day_races"].add(race_url)
    return race_urls


race_urls = get_race_urls(5, 5)



races = []
stages = []


def insert_stage_race(url):
    print(url)
    info_url      = f"{url}&k=2"
    profiles_url  = f"{url}&e=all"

    info_page     = scrape.get_soup(info_url)
    profiles_page = scrape.get_soup(profiles_url)
    main_page     = scrape.get_soup(url)
    sidebox       = main_page.find('div', class_='sideBox')

    # set dates before, incase table is None

    table = info_page.find('table', class_='tablesorter')

    stage_info = {}

    if table is not None:
        stage_info = scrape.stage_info_page(table)
        print(f"Stage info for {url}:")
        for stage_number, info in stage_info.items():
            print(f"Stage {stage_number}:")
            print(f"  Profile Icon: {info['profile_icon']}")
            print(f"  Date: {info['date']}")
            print(f"  Distance: {info['distance']}")
    else:
        number_col, profile_icon_col, date_col, distance_col = None, None, None, None

    race_id       = service.extract_id(url)
    category      = scrape.category(sidebox)
    name          = scrape.name(sidebox)
    start_date    = '1970-01-01'  # reassign dates after if table is not None
    end_date      = '1970-01-01'  # reassign dates after if table is not None
    logo          = scrape.logo(main_page)
    flag          = scrape.flag(sidebox)

    race = (race_id, category, name, start_date, end_date, logo, flag, None, None, None)

    races.append(race)

    for s in race:
        stage_id     = None
        # race_id    = /\
        number       = None
        date         = None
        distance     = None
        profile_icon = None
        profile      = None

        stage = (stage_id, race_id, number, date, distance, profile_icon, profile)
        stages.append(stage)


def insert_one_day_race(url):
    main_page    = scrape.get_soup(url)
    sideboxes    = main_page.find_all('div', class_='sideBox')

    race_id      = service.extract_id(url)
    category     = scrape.category(sideboxes[0])
    name         = scrape.name(sideboxes[0])
    date         = scrape.date(sideboxes[0])
    logo         = scrape.logo(main_page)
    flag         = scrape.flag(sideboxes[0])
    distance     = scrape.distance(sideboxes[0])
    profile_icon = scrape.profile_icon(sideboxes[0])
    profile      = scrape.profile(sideboxes)

    race = (race_id, category, name, date, date, logo, flag, distance, profile_icon, profile)

    races.append(race)


def insert_races(dictionary):
    for key, url_list in dictionary.items():
        print(f"Inserted races with key '{key}'")
        for url in url_list:
            if key == 'stage_races':
                insert_stage_race(url)
            elif key == 'one_day_races':
                insert_one_day_race(url)
    #db.insert_races(db.conn, races)
    #db.insert_stages(db.conn, stages)
    # print(stages)


insert_races(race_urls)
