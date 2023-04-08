import requests
from bs4 import BeautifulSoup


def get_soup(url):
    req = requests.get(url)
    html = req.content
    soup = BeautifulSoup(html, 'html5lib')
    return soup
