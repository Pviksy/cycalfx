import re
from datetime import datetime, timedelta
import requests
from bs4 import BeautifulSoup

year = 2023

def extract_id(url):
    # Search for the pattern 'r=' followed by a number
    match = re.search(r"r=(\d+)", url)

    if match:
        # Extract the number (ID) from the match object
        race_id = match.group(1)
        return int(race_id)  # Convert the ID to an integer and return it
    else:
        raise ValueError("Invalid URL format")


def fix_dist(distance):
    if "km" in distance and not distance.strip() == "km":
        return distance.split()[0]


def normalize_url(url, end_sequence):
    # Check if the URL ends with the desired end_sequence
    if url.endswith(end_sequence):
        return url
    else:
        # If not, remove the unwanted part using regex and return the normalized URL
        pattern = re.compile(f'{re.escape(end_sequence)}.*$')
        return re.sub(pattern, end_sequence, url)


# think this is superfluous
def start_end_dates_from_stage_info_dict(stage_info):
    keys = list(stage_info.keys())

    if 'Pl.' in stage_info:
        min_key = 'Pl.'
        max_index = keys.index('Pl.') - 1
    else:
        min_key = min(keys)
        max_index = len(keys) - 1

    max_key = keys[max_index]

    start_date = format_date_string(stage_info[min_key]['date'])
    end_date = format_date_string(stage_info[max_key]['date'])

    return start_date, end_date


def format_date_string(date_string):
    # Get the current year
    current_year = year

    # Add the current year to the date string
    date_string_with_year = f"{date_string} {current_year}"

    # Parse the date string using datetime.strptime
    parsed_date = datetime.strptime(date_string_with_year, "%d.%b %Y")

    # Format the date as YYYY-MM-DD
    formatted_date = parsed_date.strftime("%Y-%m-%d")

    return formatted_date


def table_is_valid(input_string):
    # checking if the table on the info page is the correct type.
    # https://firstcycling.com/race.php?r=258&y=2023 had a team table for some reason
    substrings = ["Team", "Division", "Average age"]
    for substring in substrings:
        if substring.lower() in input_string.lower() or input_string == "No data":
            return False
    return True


def map_dates(start_date, end_date):

    # Convert start_date and end_date to datetime objects
    start_date_obj = datetime.strptime(start_date, "%Y-%m-%d")
    end_date_obj = datetime.strptime(end_date, "%Y-%m-%d")

    # Calculate the number of days between start_date and end_date
    num_days = (end_date_obj - start_date_obj).days + 1

    # Iterate through the range of numbers (1 to num_days) and assign the corresponding date
    date_mapping = {}
    for number in range(1, num_days + 1):
        current_date = start_date_obj + timedelta(days=(number - 1))
        date_mapping[number] = current_date.strftime("%Y-%m-%d")

    print(date_mapping)

    return date_mapping
