import pymongo
import scrapy
from bs4 import BeautifulSoup

from github_spider.items import GithubSpiderItem


class ExploitSpider(scrapy.Spider):
    name = "github"

    def start_requests(self):
        urls = []
        for item in self.connect().find():
            cve = item["CVE-ID"]
            url = "https://api.github.com/search/repositories?q=" + cve
            urls.append(url)
        for url in urls:
            yield scrapy.Request(url=url, callback=self.parse)

    def parse(self, response):
        items = response.json()["items"]
        if len(items) > 0:
            print(items["html_url"])


    def connect(self):
        client = pymongo.MongoClient(host='localhost', port=27017)
        db = client.vuln
        return db.iot2


