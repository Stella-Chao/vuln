import pymongo
from settings import MONGODB_PORT,MONGODB_HOST,MONGODB_DBNAME,MONGODB_COLLECTION


class GithubSpiderPipeline:

    def __init__(self):
        host = MONGODB_HOST
        port = MONGODB_PORT
        database = MONGODB_DBNAME
        collection = MONGODB_COLLECTION
        # 创建 mongos数据库连接
        client = pymongo.MongoClient(host=host, port=port)
        # 指定数据库
        mydb = client[database]
        # 集合
        self.post = mydb[collection]

    def process_item(self, item, spider):
        data = dict(item)
        self.post.insert(data)
        return item
