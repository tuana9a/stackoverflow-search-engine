import scrapy
from SearchEngineCrawler.items import QuoteItem


class QuotesSpider(scrapy.Spider):

    name = "quotes"
    start_urls = ['http://quotes.toscrape.com']

    def parse(self, response):
        for quote in response.css('div.quote'):
            yield self.parse_quote_item(quote)

        next_page = response.css("li.next > a ::attr(href)").extract_first()
        if next_page:
            yield scrapy.Request(response.urljoin(next_page), callback=self.parse)

    def parse_quote_item(self, quote):
        item = QuoteItem()
        item['text'] = quote.css('span.text::text').get()
        item['author'] = quote.css('small.author::text').get()
        item['tags'] = quote.css('div.tags a.tag::text').getall()
        return item
