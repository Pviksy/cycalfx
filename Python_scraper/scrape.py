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