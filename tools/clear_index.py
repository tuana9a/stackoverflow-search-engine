import requests

url = "http://localhost/api/newsletter/index"

resp = requests.delete(url=url)
print(resp.json())
    