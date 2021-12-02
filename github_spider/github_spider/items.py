# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class GithubSpiderItem(scrapy.Item):

    # CVE-ID
    cve = scrapy.Field()

    # POC链接 [,,,]
    url = scrapy.Field()
