import pymongo
from jvn_spider.settings import MONGODB_PORT, MONGODB_HOST, MONGODB_DBNAME, MONGODB_COLLECTION, MONGO_URI

class JvnSpiderPipeline:

    def __init__(self):
        host = MONGODB_HOST
        port = MONGODB_PORT
        database = MONGODB_DBNAME
        collection = MONGODB_COLLECTION
        # 创建 mongos数据库连接
        client = pymongo.MongoClient(MONGO_URI)
        # 指定数据库
        mydb = client[database]
        # 集合
        self.post = mydb[collection]

    def process_item(self, item, spider):
        data = dict(item)
        self.post.insert_one(data)
        return item
