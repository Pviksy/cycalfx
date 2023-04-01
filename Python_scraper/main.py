from bs4 import BeautifulSoup
import requests
import db

#  race_tuple = (None, category_id, name, start_date, end_date, logo, flag, results)
# stage_tuple = (None, race_id, date, stage_number, results)
import service

year = 2023

url_template = "https://firstcycling.com/race.php?y=" + str(year) + "&t=2&m="


def get_race_page_urls():
    categories = db.select_all_categories()

    months = {i: [] for i in range(1, 13)}

    for month in months:
        url = url_template + str(month)

        page = service.get_soup(url)

        tables = page.find_all('table', class_='sortTabell tablesorter')

        if len(tables) > 0:
            table = tables[-1]
        else:
            table = None

        race_urls = []

        if table is not None:
            for row in table.find_all('tr'):
                columns = row.find_all('td')

                cat_col, race_col = None, None

                if len(columns) > 2:
                    cat_col = columns[1].get_text().strip()
                    race_col = columns[2].find('a')['href']

                if cat_col in categories:
                    race_urls.append('https://firstcycling.com/' + race_col)

        months[month] = race_urls
    return months


