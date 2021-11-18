# 13/11/2021: @tuana9a đã update lại các api url

GET http://localhost/api/newsletter/search?field=content&limit=1&q=code

cái này để search, gồm các tham số

- field: trường muốn search VD: content, url, tittle
- limit: giới hạn số bản ghi trả về (top K trong lý thuyết)
- q: là câu truy vấn

POST http://localhost/api/newsletter/index

cái url này để POST dữ liệu để tạo index (xem tools/create_index.py)