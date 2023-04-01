import re
from datetime import datetime
import requests
from bs4 import BeautifulSoup


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