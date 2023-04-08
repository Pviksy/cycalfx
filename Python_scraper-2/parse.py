import re


def normalize_url(url, end_sequence):
    # Check if the URL ends with the desired end_sequence
    if url.endswith(end_sequence):
        return url
    else:
        # If not, remove the unwanted part using regex and return the normalized URL
        pattern = re.compile(f'{re.escape(end_sequence)}.*$')
        return re.sub(pattern, end_sequence, url)