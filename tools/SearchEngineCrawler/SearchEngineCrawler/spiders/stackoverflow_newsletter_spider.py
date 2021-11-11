import scrapy
from SearchEngineCrawler.items import StackoverflowNewsletterItem
import time
import html2text
from scrapy.selector import Selector

class StackoverflowSpider(scrapy.Spider):

    name = "stackoverflownewsletter"
    start_urls = ["https://stackoverflow.blog/company/"]
    count = 0

    def parse(self, response):
        for news in response.css("#content > div.grid.mxn16.ff-row-wrap.md\:fd-column > div"):
            url= news.css("a::attr(href)").extract_first()
            yield scrapy.Request(url, callback=self.parse_stackoverflow_newsletter_item)
        
        next_page = response.css("#content > div.grid.sm\:fd-column.ai-center.pt32.pb64.sm\:pb0 > div.s-pagination > nav > div > a.next.s-pagination--item.fs-body2.py4::attr(href)").extract_first()
        if next_page:
            time.sleep(1)
            yield scrapy.Request(next_page, callback=self.parse)

    def parse_stackoverflow_newsletter_item(self, response):
        item = StackoverflowNewsletterItem()

        converter = html2text.HTML2Text()
        converter.ignore_links = True

        item["title"] = response.css("div.bb.bc-black-1.pb64.sm\:pb32.mb64.sm\:mb32 > div > header > h1::text").extract_first()
        item["content"]  = "".join(response.css("div.p-article.wmx9.fc-black-700.mb96 ::text").extract()).strip().rstrip().lstrip()
        # item["content"] = Selector(response=response).xpath("/html/body/div/div[1]/section/article/div[2]/text()").getall()
        item["url"] = response.url
        yield item


# /html/body/div/div[1]/section/article/div[2]
