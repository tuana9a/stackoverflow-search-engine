import scrapy
from SearchEngineCrawler.items import StackoverflowItem
import time
import html2text

class StackoverflowSpider(scrapy.Spider):

    name = "stackoverflow"
    # start_urls = ["https://stackoverflow.com/questions?tab=Newest"]
    start_urls = ["https://stackoverflow.com/questions?tab=Votes"]
    count = 0

    def parse(self, response):
        for question in response.css("div.question-summary"):
            # datestr = question.css("div.summary > div.d-flex.ai-start.fw-wrap > div.flex--item.ml-auto.fl-shrink0.started.mt0 > div > div.user-action-time > span::attr(title)").extract_first()
            # datetime_object = datetime.fromisoformat(datestr[0:19]).date()
            # if(self.begin_date > datetime_object and datetime_object > self.end_date):
            time.sleep(0.5)
            yield scrapy.Request(response.urljoin(question.css("div.summary > h3 a::attr(href)").extract_first()), callback=self.parse_stackoverflow_item)

        
        self.count += 1
        next_page = response.css("#mainbar > div.s-pagination.site1.themed.pager.fl > a:last-child::attr(href)").extract_first()
        if next_page and self.count <= 10:
            time.sleep(1)
            yield scrapy.Request(response.urljoin(next_page), callback=self.parse)
        
        # if next_page:
        #     print("done "+response.urljoin(next_page))
        #     yield scrapy.Request(response.urljoin(next_page), callback=self.parse)
        

    def parse_stackoverflow_item(self, response):
        item = StackoverflowItem()
        item['id'] = response.css("#question::attr(data-questionid)").extract_first()
        item['url'] = response.url
        item['title'] = response.css("div#question-header > h1 a::text").extract_first()
        item['question'] = "".join(response.css("div#question > div.post-layout > div.postcell.post-layout--right > div.s-prose.js-post-body ::text").extract()).strip().rstrip().lstrip()
        item['date_time'] = response.css("#content > div > div.inner-content.clearfix > div.d-flex.fw-wrap.pb8.mb16.bb.bc-black-075 > div:nth-child(1) > time::attr(datetime)").extract_first()
        yield item
        

