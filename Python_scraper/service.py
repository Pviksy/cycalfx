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


def normalize_url(url, end_sequence):
    # Check if the URL ends with the desired end_sequence
    if url.endswith(end_sequence):
        return url
    else:
        # If not, remove the unwanted part using regex and return the normalized URL
        pattern = re.compile(f'{re.escape(end_sequence)}.*$')
        return re.sub(pattern, end_sequence, url)