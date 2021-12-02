import scrapy

class JvnSpiderItem(scrapy.Item):

    # JVNDB-ID
    jvn_id = scrapy.Field()
    # 标题
    title = scrapy.Field()
    # 受影响设备
    affected = scrapy.Field()
    # 摘要
    abstract = scrapy.Field()
    # CVSSV2
    cvss2 = scrapy.Field()
    # CVSS3
    cvss3 = scrapy.Field()
    #
    expected_impact = scrapy.Field()
    # 厂商
    vendor = scrapy.Field()
    # CWE
    cwe = scrapy.Field()
    # CVE
    cve = scrapy.Field()
    # 相关链接
    refer = scrapy.Field()
    # 发布日期
    published_date = scrapy.Field()
    # 注册日期
    register_date = scrapy.Field()
    # 更新时间
    updated_date = scrapy.Field()
    # 措施
    measure = scrapy.Field()