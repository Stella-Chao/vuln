import scrapy
import logging
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
import ast
import scrapy_redis

logger = logging.getLogger(__name__)

class MySpider(scrapy.Spider):
    name = 'cnvd'
    allowed_domains = ['cnvd.org.cn']
    start_urls = ['https://www.cnvd.org.cn/flaw/list.htm']

    def __init__(self):
        self.headers = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36 Edg/93.0.961.52"}
        # 如果从某处断线了，可以更改起始的url地址
        # self.start_url = "http://www.cnvd.org.cn/flaw/list.htm"
        self.count = 0

    def get_cookies(self):
        chrome_options = Options()
        chrome_options.add_argument('--no-sandbox')
        chrome_options.add_argument('--disable-dev-shm-usage')
        driver = webdriver.Chrome("C:\Program Files\Google\Chrome\Application\chromedriver.exe",chrome_options=chrome_options)
        driver.get(
            "https://www.cnvd.org.cn/flaw/list.htm?max=20&offset=20")  # 设置每次ChromeDriver访问的初始页面用来更新cookie，以绕过cnvd爬虫限制
        cj = driver.get_cookies()
        cookie = ''
        for c in cj:
            cookie += "'" + c['name'] + "':'" + c['value'] + "',"
        cookie = ast.literal_eval('{' + cookie + '}')
        driver.quit()
        print('hahaha~')
        print(cookie)
        return cookie

    def start_requests(self):
        logger.info("start spider")
        for url in self.start_urls:
            if 'cnvd.org' in url:
                # js渲染
                # yield SplashRequest(url, self.parse, args={'wait':0.5, 'images':0 ,'timeout': 50})
                response = scrapy.FormRequest(url=url, cookies=self.get_cookies() ,formdata={"typeId": "29"}, headers=self.headers, callback=self.cnvd_parse, dont_filter=True)
                print(response)
                yield scrapy.FormRequest(url=url, cookies=self.get_cookies() ,formdata={"typeId": "29"}, headers=self.headers, callback=self.cnvd_parse, dont_filter=True)

    def cnvd_parse(self, response):
        res = response.xpath("//table[@class='tlist']/tbody/tr")
        for link in res:
            url = "https://www.cnvd.org.cn" + link.xpath("./td/a/@href").extract_first()
            logger.info("spider " + url)
            yield scrapy.Request(url=url, callback=self.get_detail)

    def get_detail(self, response):
        sec_cnvd_id = response.xpath(
            "//div[@class='mw Main clearfix']/div[@class='blkContainer']/div[@class='blkContainerPblk']/div[@class='blkContainerSblk']/div[@class='blkContainerSblkCon clearfix']/div[@class='tableDiv']/table[@class='gg_detail']/tbody/tr[1]/td[2]//text()").extract_first().strip()
        sec_name = response.xpath(
            "//div[@class='mw Main clearfix']/div[@class='blkContainer']/div[@class='blkContainerPblk']/div[@class='blkContainerSblk']/h1/text()").extract_first().strip()
        sec_url = response.request.url
        sec_date = response.xpath(
            "//div[@class='mw Main clearfix']/div[@class='blkContainer']/div[@class='blkContainerPblk']/div[@class='blkContainerSblk']/div[@class='blkContainerSblkCon clearfix']/div[@class='tableDiv']/table[@class='gg_detail']/tbody/tr[2]/td[2]/text()").extract_first().strip()
        sec_from = "cnvd"
        if ("CVE-" in str(response.xpath(".").extract())):
            sec_cve_id = response.xpath(
                "/html/body/div[@class='mw Main clearfix']/div[@class='blkContainer']/div[@class='blkContainerPblk']/div[@class='blkContainerSblk']/div[@class='blkContainerSblkCon clearfix']/div[@class='tableDiv']/table[@class='gg_detail']/tbody/tr[5]/td[2]/a/text()").extract_first().strip()
            sec_type = response.xpath(
                "/html/body/div[@class='mw Main clearfix']/div[@class='blkContainer']/div[@class='blkContainerPblk']/div[@class='blkContainerSblk']/div[@class='blkContainerSblkCon clearfix']/div[@class='tableDiv']/table[@class='gg_detail']/tbody/tr[7]/td[2]/text()").extract_first().strip()
            sec_desc = "".join(response.xpath(
                "/html/body/div[@class='mw Main clearfix']/div[@class='blkContainer']/div[@class='blkContainerPblk']/div[@class='blkContainerSblk']/div[@class='blkContainerSblkCon clearfix']/div[@class='tableDiv']/table[@class='gg_detail']/tbody/tr[6]/td[2]/text()").extract()).strip().replace(
                "\r", "").replace("\n", "").replace("\t", "").replace(" ", "")
        else:
            sec_cve_id = "None"
            sec_type = response.xpath(
                "/html/body/div[@class='mw Main clearfix']/div[@class='blkContainer']/div[@class='blkContainerPblk']/div[@class='blkContainerSblk']/div[@class='blkContainerSblkCon clearfix']/div[@class='tableDiv']/table[@class='gg_detail']/tbody/tr[6]/td[2]/text()").extract_first().strip()
            sec_desc = "".join(response.xpath(
                "/html/body/div[@class='mw Main clearfix']/div[@class='blkContainer']/div[@class='blkContainerPblk']/div[@class='blkContainerSblk']/div[@class='blkContainerSblkCon clearfix']/div[@class='tableDiv']/table[@class='gg_detail']/tbody/tr[5]/td[2]/text()").extract()).strip().replace(
                "\r", "").replace("\n", "").replace("\t", "").replace(" ", "")
        sec_level = "".join(response.xpath(
            "/html/body/div[4]/div[1]/div[1]/div[1]/div[2]/div[1]/table/tbody/tr[3]/td[2]/text()").extract()).strip().replace(
            "\r", "").replace("\n", "").replace("\t", "").replace(" ", "").replace("(", "").replace(")", "")
