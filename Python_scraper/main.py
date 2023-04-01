import db
import scrape
import service

year = 2023

url_template = f"https://firstcycling.com/race.php?y={year}&t=2&m="


def get_race_urls(starting_month, ending_month):
    categories = db.select_all_categories()

    months = {i: [] for i in range(starting_month, ending_month + 1)}

    race_urls = {"one_day_races": [], "stage_races": []}

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
                    if cat_col[0] == '2':  # if first character of category is 2
                        # stage race
                        race_urls["stage_races"].append(f'https://firstcycling.com/{race_col}')
                    else:
                        # one day race
                        race_urls["one_day_races"].append(f'https://firstcycling.com/{race_col}')
    return race_urls


race_urls = get_race_urls(1, 1)


def get_race(url):
    page = scrape.get_soup(url)
    print(page.prettify())


def insert_stage_race(url):
    race_id = service.extract_id(url)


    print(url)


def insert_one_day_race(url):
    page         = scrape.get_soup(url)
    sideboxes    = page.find_all('div', class_='sideBox')

    race_id      = service.extract_id(url)
    category     = scrape.category(sideboxes[0])
    name         = scrape.name(sideboxes[0])
    date         = scrape.date(sideboxes[0])
    logo         = scrape.logo(page)
    flag         = scrape.flag(sideboxes[0])
    distance     = scrape.distance(sideboxes[0])
    profile_icon = scrape.profile_icon(sideboxes[0])
    profile      = scrape.profile(sideboxes)

    race = (race_id, category, name, date, date, logo, flag, distance, profile_icon, profile)

    db.insert_race(race)


def insert_races(dictionary):
    for key, url_list in dictionary.items():
        print(f"Inserted races with key '{key}'")
        for url in url_list:
            if key == 'stage_races':
                insert_stage_race(url)
            elif key == 'one_day_races':
                #insert_one_day_race(url)
                print("one day races?")




insert_races(race_urls)