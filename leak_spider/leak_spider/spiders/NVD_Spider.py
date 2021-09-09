import scrapy
import scrapy_redis.spiders

from leak_spider.items import LeakSpiderItem

class NVD_Spider(scrapy_redis.spiders.RedisSpider):
# class NVD_Spider(scrapy.Spider):
    name = "nvd"
    allowd_domain = ['nvd.nist.gov']
    #start_urls = ['https://nvd.nist.gov/vuln/full-listing']
    redis_key = 'nvd:start_url'
    def parse(self, response):  # 获取月份链接
        urls = response.css('#body-section > div:nth-child(2) a::attr(href)').getall()
        print("----------月份个数：------",len(urls),"----------")
        for url in urls:
            yield scrapy.Request(url='https://nvd.nist.gov'+url, callback=self.parse2)

    def parse2(self, response): # 获取漏洞 URL(按月)
        urls = response.css('#body-section > div:nth-child(2) a::attr(href)').getall()
        for url in urls:
            yield scrapy.Request(url='https://nvd.nist.gov'+url, callback=self.detail)

    def detail(self, response):
        leak = LeakSpiderItem()
        leak['name'] = "".join(response.xpath('//*[@data-testid="page-header-vuln-id"]/text()').get())
        leak['description'] = "".join(response.xpath('//p[@data-testid="vuln-description"]/text()').get())
        leak['url'] = response.url
        print(response.url)
        cvss2_nvd_vector = response.xpath('//div[@id="Vuln2CvssPanel"]//span[@class="tooltipCvss2NistMetrics"]/text()').get()
        cvss2_nvd_base_score = response.xpath('//div[@id="Vuln2CvssPanel"]//a[@id="Cvss2CalculatorAnchor"]/text()').get()
        cvss3_nvd_vector = response.xpath('//div[@id="Vuln3CvssPanel"]//span[@class="tooltipCvss3NistMetrics"]/text()').get()
        cvss3_nvd_base_score = response.xpath('//div[@id="Vuln3CvssPanel"]//a[@id="Cvss3CalculatorAnchor"]/text()').get()
        references = response.xpath('//*[@data-testid="vuln-hyperlinks-link-0"]/a/text()').get()
        if cvss3_nvd_vector == None:
            cvss3_nvd_vector = ''

        if cvss2_nvd_vector == None:
            cvss2_nvd_vector = ''

        if cvss3_nvd_base_score == None:
            cvss3_nvd_base_score = response.xpath('//div[@id="Vuln3CvssPanel"]//a[@id="Cvss3NistCalculatorAnchor"]/text()').get()
            if cvss3_nvd_base_score == None:
                cvss3_nvd_base_score = 'N/A'

        if references == None:
            references = response.xpath('//*[@data-testid="vuln-hyperlinks-link-2"]/a/text()').get()
            if references == None:
                references = ''
        leak['cvss2_nvd_vector'] = "".join(cvss2_nvd_vector)
        leak['cvss2_nvd_base_score'] = "".join(cvss2_nvd_base_score)
        print(type(cvss3_nvd_base_score))
        print(cvss3_nvd_base_score)
        leak['cvss3_nvd_vector'] = "".join(cvss3_nvd_vector)
        leak['cvss3_nvd_base_score'] = "".join(cvss3_nvd_base_score)
        leak['references'] = "".join(references)
        # cwe_id 是 NVD-CWE-Other 时，<a> => <span>
        cwe_id = response.xpath('//*[@data-testid="vuln-CWEs-link-0"]/a/text()').get()
        if cwe_id == None:
            cwe_id = response.xpath('//*[@data-testid="vuln-CWEs-link-0"]/span/text()').get()
        leak['cwe_id'] = "".join(cwe_id)
        leak['cwe_name'] = "".join(response.xpath('//*[@data-testid="vuln-CWEs-link-0"]/a/text()').get())
        leak['cpe'] = ""   # *************有问题**************

        return leak
