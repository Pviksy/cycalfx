from datetime import datetime
import re
import requests
from bs4 import BeautifulSoup
import service


def get_soup(url):
    req = requests.get(url)
    html = req.content
    soup = BeautifulSoup(html, 'html5lib')
    return soup


def date(sidebox):
    date_string = sidebox.find_all('p')[3].text.strip()

    # Extract day, month, and year using regex
    match = re.match(r"(\d+)(?:st|nd|rd|th) (\w+) \| (\d+)", date_string)
    if match:
        day, month, year = match.groups()

        # Parse the date using the extracted day, month, and year
        parsed_date = datetime.strptime(f"{day} {month} {year}", "%d %B %Y")

        # Format the date as YYYY-MM-DD
        formatted_date = parsed_date.strftime("%Y-%m-%d")
        return formatted_date
    else:
        raise ValueError("Invalid date format")


def start_end_dates(sidebox):
    date_string = sidebox.find_all('p')[3].text.strip()

    # Check if date string follows the "30th January - 3rd February | 2023" format
    if re.match(r"\d+(?:st|nd|rd|th) \w+ - \d+(?:st|nd|rd|th) \w+ \| \d+", date_string):
        # Extract start and end days, months, and year using regex
        match = re.match(r"(\d+)(?:st|nd|rd|th) (\w+) - (\d+)(?:st|nd|rd|th) (\w+) \| (\d+)", date_string)
        start_day, start_month, end_day, end_month, year = match.groups()
    # Check if date string follows the "17th - 22nd January | 2023" format
    elif re.match(r"\d+(?:st|nd|rd|th) - \d+(?:st|nd|rd|th) \w+ \| \d+", date_string):
        # Extract start and end days, month, and year using regex
        match = re.match(r"(\d+)(?:st|nd|rd|th) - (\d+)(?:st|nd|rd|th) (\w+) \| (\d+)", date_string)
        start_day, end_day, month, year = match.groups()
        start_month = end_month = month
    else:
        raise ValueError("Invalid date format")

    # Parse the start and end dates using the extracted days, months, and years
    start_date = datetime.strptime(f"{start_day} {start_month} {year}", "%d %B %Y")
    end_date = datetime.strptime(f"{end_day} {end_month} {year}", "%d %B %Y")

    # Format the dates as YYYY-MM-DD
    formatted_start_date = start_date.strftime("%Y-%m-%d")
    formatted_end_date = end_date.strftime("%Y-%m-%d")

    return formatted_start_date, formatted_end_date


def profile_icon(sidebox):
    profile_icon = sidebox.find('img', {'width': '12'})
    if profile_icon:
        return profile_icon['src']


def profile(sideboxes):
    if len(sideboxes) > 1:
        identifier = sideboxes[1].select_one('img[src*="img/"]')['src']
        return f"https://firstcycling.com/{identifier}"


def category(sidebox):
    info_p = sidebox.find_all('p')[4].text.strip()
    category, _, distance = info_p.partition(' ')
    return category.split()[0]


def distance(sidebox):
    info_p = sidebox.find_all('p')[4].text.strip()
    category, _, distance = info_p.partition(' ')
    return service.fix_dist(distance)


def flag(sidebox):
    identifier = sidebox.select_one('img[src*="img/flag"]')['src']
    return f"https://firstcycling.com/{identifier}"


def logo(soup):
    logo = soup.find('img', {'title': 'Race logo'})
    if logo:
        identifier = logo['src']
        return f"https://firstcycling.com/{identifier}"


def name(sidebox):
    return sidebox.find('p').text.strip()


def stage_number(column):
    return column.get_text().strip()


def stage_profile_icon(column):
    stage_profile_icon = column.find('img')['src']
    if stage_profile_icon:
        return stage_profile_icon


def stage_distance(column):
    return column.get_text().strip()


def stage_date(column):
    return column.get_text().strip()


def stage_info_page(table):
    stage_info = {}

    for row in table.find_all('tr'):
        columns = row.find_all('td')
        if len(columns) > 2:
            number_col = stage_number(columns[0])
            if number_col == "Pl.":
                number_col = 00
            if number_col != "":
                profile_icon_col = stage_profile_icon(columns[1])
                date_col = stage_date(columns[2])
                distance_col = stage_distance(columns[3])
                if distance_col == "-":
                    distance_col = None
                stage_info[number_col] = {
                    "profile_icon": profile_icon_col,
                    "date": service.format_date_string(date_col),
                    "distance": distance_col,
                }

    return stage_info
