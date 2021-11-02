# info

# prepare

dùng python thì nên cài môi trường ảo virtualenv để khởi tạo venv chạy lệnh sau

tạo một môi trường ảo thay giá trị ".venv" với giá trị khác bạn mong muốn

<code>python -m venv .venv</code>

lệnh trên sẽ tạo thêm một thư mục ".venv", sau đó kích hoạt môi trường ảo này

<code>.venv\Script\activate</code>

với linux lệnh sẽ khác xíu

<code>virtualenv .venv</code>

<code>source .venv/bin/activate</code>

sau đó cài các lib như scrapy, pysolr

# structure

cấu trúc thư mục như sau

resource/ chứa các file dữ liệu, config các thứ, không được commit lên github 

tools/ chứa các tool như crawl dữ liệu, post data tới solr

server/ chứa webserver nodejs, html, css, js