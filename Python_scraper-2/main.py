import db
import parse
import scrape

year = 2023

url_template = f"https://firstcycling.com/race.php?y={year}&t=2&m="


def get_race_urls(starting_month, ending_month):
    race_urls = {"one_day_races": set(), "stage_races": set()}
    categories = db.select_all_categories()

    for month in range(starting_month, ending_month + 1):
        month_url = f"{url_template}{month}"
        month_page = scrape.get_soup(month_url)

        tables = month_page.find_all('table', class_='sortTabell tablesorter')
        table = tables[-1] if tables else None

        if table:
            for row in table.find_all('tr'):
                columns = row.find_all('td')

                if len(columns) > 2:
                    cat_col = columns[1].get_text().strip()
                    race_col = columns[2].find('a')['href']

                    if cat_col in categories:
                        race_url = parse.normalize_url(f'https://firstcycling.com/{race_col}', str(year))

                        if cat_col[0] == '2':  # if first character of category is 2
                            # stage race
                            race_urls["stage_races"].add(race_url)
                        else:
                            # one day race
                            race_urls["one_day_races"].add(race_url)

    return race_urls


print(get_race_urls(1, 1))
