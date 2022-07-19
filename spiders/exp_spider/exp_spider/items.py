import scrapy

class ExpSpiderItem(scrapy.Item):

    # EDB-ID
    edb_id = scrapy.Field()
    # Title
    title = scrapy.Field()
    # exploit URL
    url = scrapy.Field()
    # 漏洞类型
    type = scrapy.Field()
    # 漏洞所在平台
    plat = scrapy.Field()
    # 作者
    author = scrapy.Field()
    # 日期
    date = scrapy.Field()
    # 是否核实
    verified = scrapy.Field()
    # CVE-ID
    cve = scrapy.Field()
    # 受攻击的应用程序
    app = scrapy.Field()
    # poc下载链接
    code_url = scrapy.Field()
    # code
    code = scrapy.Field()
