"""
EXPLAIN: cái này để upload json vào server để index files
"""
import requests
import json

files = [
    "data/newsletter1.json",
    "data/newsletter2.json",
    "data/company.json",
]

url = "http://localhost/api/newsletter/index"

for filepath in files:
    f = open(filepath, encoding="UTF-8")
    records = json.load(f)
    requests.post(url=url, json=records)
