import json
from os import popen
import pysolr

"""

*** command on server ***

/opt/solr-8.10.0/bin/solr start
/opt/solr-8.10.0/bin/solr stop
/opt/solr-8.10.0/bin/solr create -c test
/opt/solr-8.10.0/bin/solr delete -c test


"""

"""
ví dụ url để connect tới solr

phần /solr/test trong đó

    /solr/  là bắt buộc
    /test   là core của solr (core giống database trong mysql cần được phải tạo trước khi sử dụng)
            để test thì nghịch gì cũng được
            khi demo thì mình sẽ sử dụng /stackoverflow cho chuyên nghiệp
"""
url = "http://vm1.aws.tuana9a.tech:8983/solr/test"
url = "http://192.168.200.11:8983/solr/test"

"""
khởi tạo connection tới solr với url ở trên
"""
solr = pysolr.Solr(url, timeout=10)
print(solr.url)

"""
ví dụ load data từ file test2.json và insert chúng vào solr bằng connection đã kết nối ở trên
"""
filepath = "test2.json"
items = json.load(open(filepath, encoding="UTF-8"))

"""
cụ thể lệnh add dữ liệu vào solr như sau
"""
response = solr.add(items, commit=True)
print(response)

"""
cách search toàn bộ, ngoài ra các cách search khác cần phải nghiên cứu thêm
"""
response = solr.search('*:*')
for record in response:
    print(record)

"""
cách xóa dữ liệu khỏi solr, xóa cũng có query
"""
response = solr.delete(q="*:*", commit=True)
print(response)
