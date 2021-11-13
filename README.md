# info

sử dụng thư viện mã nguồn mở lucence đánh chỉ mục tìm kiếm các blog, newsletter trên trang web [stackverflow](https://stackoverflow.blog/newsletter/)

# prepare

dùng python thì nên cài môi trường ảo virtualenv để khởi tạo venv chạy lệnh sau

tạo một môi trường ảo thay giá trị ".venv" với giá trị khác bạn mong muốn

`python -m venv .venv`

lệnh trên sẽ tạo thêm một thư mục ".venv", sau đó kích hoạt môi trường ảo này

`.venv\Script\activate`

với linux lệnh sẽ khác xíu

`virtualenv .venv`

`source .venv/bin/activate`

sau đó cài các lib như scrapy

# structure

cấu trúc thư mục như sau

`resource/` chứa các file dữ liệu, config các thứ, không được commit lên github

`tools/` chứa các tool như crawl dữ liệu, post data tới solr

`src/main/resources/static/` code web frontend html, css, js

`index` là thư mục chứa index được sinh ra bởi lucene

# changelog + notes

13/11/2021: @tuana9a đã update lại các api url với dạng như sau

GET http://localhost/api/newsletter/search?field=content&limit=1&q=code

cái này để search, gồm các tham số

-   field: trường muốn search VD: content, url, tittle
-   limit: giới hạn số bản ghi trả về (top K trong lý thuyết)
-   q: là câu truy vấn

POST http://localhost/api/newsletter/index

cái url này để POST dữ liệu để tạo index (xem tools/create_index.py)

# note

Link Tham Khảo

<a href="https://alibaba-cloud.medium.com/analysis-of-lucene-basic-concepts-5ff5d8b90a53">https://alibaba-cloud.medium.com/analysis-of-lucene-basic-concepts-5ff5d8b90a53</a>

<a href="https://stackoverflow.com/a/43203339/10459230">https://stackoverflow.com/a/43203339/10459230</a>

<a href="https://lucene.apache.org/core/7_7_3/index.html">https://lucene.apache.org/core/7_7_3/index.html</a>

<a href="https://lucene.apache.org/core/7_7_3/core/org/apache/lucene/analysis/package-summary.html#package.description"> analysis </a>

<a href="https://en.wikipedia.org/wiki/Skip_list#Indexable_skiplist">Skip_list#Indexable_skiplist</a>

Using Finite State Transducers in Lucene

<a href="https://blog.mikemccandless.com/2010/12/using-finite-state-transducers-in.html">https://blog.mikemccandless.com/2010/12/using-finite-state-transducers-in.html</a>

Lucene File Format

<a href="https://lucene.apache.org/core/8_10_1/core/org/apache/lucene/codecs/lucene87/package-summary.html#package.description">file format 8.10.1</a>

<a href="https://lucene.apache.org/core/7_7_3/core/org/apache/lucene/codecs/lucene70/package-summary.html#package.description">file format 7.7.3</a>

<h2>note tay</h2>

// tìm kiếm thông tin

index writer có nhiệm vụ ghi index (chính)

directory là một abstract lưu index, index writer sẽ ghi index vào directory

directory lưu index thành các sub-indexs, tạo thành segments

một term là một cặp: field_name + token (token có thể là từ)


