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

sau đó cài các lib như scrapy, pysolr

# structure

cấu trúc thư mục như sau

`resource/` chứa các file dữ liệu, config các thứ, không được commit lên github 

`tools/` chứa các tool như crawl dữ liệu, post data tới solr

`src/main/resources/static/` code web frontend html, css, js