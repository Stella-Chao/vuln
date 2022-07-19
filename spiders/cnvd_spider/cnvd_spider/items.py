import scrapy

class CnvdSpiderItem(scrapy.Item):

    # CNVD-ID
    cnvd_id = scrapy.Field()
    # 公开日期
    published_date = scrapy.Field()
    # 危害级别
    damage_level = scrapy.Field()
    # CVE-ID
    cve_id = scrapy.Field()
    # 漏洞描述
    description = scrapy.Field()
    # 漏洞类型
    type = scrapy.Field()
    # 参考链接
    refer = scrapy.Field()
    # 漏洞解决方案
    solution = scrapy.Field()
    # 厂商补丁
    vendor_patch = scrapy.Field()
    # 验证信息
    verification = scrapy.Field()
    # 报送时间
    report_time = scrapy.Field()
    # 收录时间
    ingest_time = scrapy.Field()
    # 更新时间
    update_time = scrapy.Field()
    # 漏洞附件
    attachment = scrapy.Field()
