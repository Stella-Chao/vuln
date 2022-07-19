from scrapy.item import Item, Field

class LeakSpiderItem(Item):
    # define the fields for your item here like:
    name = Field()
    url = Field()
    description = Field()
    cvss2_nvd_base_score = Field()
    cvss2_nvd_vector = Field()
    cvss3_nvd_base_score = Field()
    cvss3_nvd_vector = Field()
    references = Field()
    cwe_id = Field()
    cwe_name = Field()
    cpe = Field()
