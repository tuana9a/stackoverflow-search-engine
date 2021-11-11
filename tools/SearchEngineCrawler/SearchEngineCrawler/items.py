# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class DemoScrapyItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    product_name = scrapy.Field()
    price_sale = scrapy.Field()
    price = scrapy.Field()
    rate_average = scrapy.Field()

class QuoteItem(scrapy.Item):
    text = scrapy.Field()
    author = scrapy.Field()
    tags = scrapy.Field()

class StackoverflowItem(scrapy.Item):
    id = scrapy.Field()
    url = scrapy.Field()
    date_time = scrapy.Field()
    title = scrapy.Field()
    question = scrapy.Field()

class StackoverflowNewsletterItem(scrapy.Item):
    url = scrapy.Field()
    title = scrapy.Field()
    content = scrapy.Field()
    

