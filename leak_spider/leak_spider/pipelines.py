# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html
import logging
import pymysql
from scrapy import settings

# useful for handling different item types with a single interface
from itemadapter import ItemAdapter

# 同步插入数据库(MongoDB)
class LeakSpiderPipeline:

    # collection_name = 'nvd'
    #
    # def __init__(self, mongo_uri, mongo_db):
    #     self.mongo_uri = mongo_uri
    #     self.mongo_db = mongo_db
    #
    # @classmethod
    # def from_crawler(cls, crawler):
    #     return cls(
    #         mongo_uri=crawler.settings.get('MONGO_URI'),
    #         mongo_db=crawler.settings.get('MONGO_DATABASE', 'items')
    #     )
    #
    # def open_spider(self, spider):
    #     self.client = pymongo.MongoClient(self.mongo_uri)
    #     self.db = self.client[self.mongo_db]
    #
    # def close_spider(self, spider):
    #     self.client.close()
    #
    # def process_item(self, item, spider):
    #     self.db[self.collection_name].insert_one(ItemAdapter(item).asdict())
    #     return item


    def __init__(self):
        self.connect = pymysql.connect(
            host = '127.0.0.1',
            db = 'leak',
            user = 'root',
            password = '123456',
            port = 3306
        )
        self.cursor = self.connect.cursor()
        logging.info('数据库连接成功!')


    # @classmethod
    # def from_crawler(cls, crawler):
    #     db_name = crawler.settings.get('DB_SETTINGS')
    #     db_params = db_name.get('db1')
    #     return cls(
    #         host=db_params.get('host'),
    #         db=db_params.get('db'),
    #         user=db_params.get('user'),
    #         password=db_params.get('password'),
    #         port=db_params.get('port'),
    #     )

    def process_item(self, item, spider):
        name = item['name']
        url = item['url']
        description = item['description']
        cvss2_nvd_base_score = item['cvss2_nvd_base_score']
        cvss2_nvd_vector = item['cvss2_nvd_vector']
        cvss3_nvd_base_score = item['cvss3_nvd_base_score']
        cvss3_nvd_vector = item['cvss3_nvd_vector']
        references = item['references']
        cwe_id = item['cwe_id']
        cwe_name = item['cwe_name']
        cpe = item['cpe']
        # sql语句
        sql = """insert into nvd (name, url, description, cvss2_nvd_base_score, cvss2_nvd_vector, \
                     cvss3_nvd_base_score, cvss3_nvd_vector, refer, cwe_id, cwe_name, cpe, tech_level, comp_level) \
                     values('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}','{9}','{10}','','')"""\
                    .format(name, url, description, cvss2_nvd_base_score, cvss2_nvd_vector, \
                     cvss3_nvd_base_score, cvss3_nvd_vector, references, cwe_id, cwe_name, cpe)

        try:
            self.cursor.execute(sql)
            logging.info("数据插入成功 => " + '1')
        except Exception as e:
            logging.error("执行sql异常 => " + str(e))
            pass
        finally:
            self.connect.commit()
        return item

    def close_spider(self, spider):
        self.connect.close()
        self.cursor.close()