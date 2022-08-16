import pymongo
from jvn_spider.settings import MONGO_URI

class JvnSpiderPipeline:

    def __init__(self):
        # 创建 mongos数据库连接
        client = pymongo.MongoClient(MONGO_URI)

    def process_item(self, item, spider):
        data = dict(item)
        self.post.insert_one(data)
        return item
