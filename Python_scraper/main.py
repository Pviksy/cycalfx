from bs4 import BeautifulSoup
import requests
import db

#  race_tuple = (None, category_id, name, start_date, end_date, logo, flag, results)
# stage_tuple = (None, race_id, date, stage_number, results)

year = 2023

url_template = "https://firstcycling.com/race.php?y=" + str(year) + "&t=2&m="

categories = db.select_all_categories()  # list of all categories

for month in range(1, 13):
    url = url_template + str(month)

