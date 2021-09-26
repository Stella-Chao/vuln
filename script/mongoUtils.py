import pymongo
from config import MONGO_HOST,MONGO_USER,MONGO_PASSWORD

def connect_vuln():
    client = pymongo.MongoClient(MONGO_HOST, 27017)
    # 连接vuln数据库，账号密码认证
    db = client.vuln
    db.authenticate(MONGO_USER, MONGO_PASSWORD)
    return db

# 连接 nvd (collection)
def connect_nvd():
    collection = connect_vuln().nvd
    return collection

# 连接 cnvd (collection)
def connect_cnvd():
    collection = connect_vuln().cnvd
    return collection

# 连接 cnnvd (collection)
def connect_cnnvd():
    collection = connect_vuln().cnnvd
    return collection

# 连接 device (collection)
def connect_device():
    collection = connect_vuln().device
    return collection

# 连接 poc (collection)
def connect_poc():
    collection = connect_vuln().poc
    return collection

# 连接 iotvd (collection)
def connect_iotvd():
    collection = connect_vuln().iotvd
    return collection

# 连接 exploit (collection)
def connect_exploit():
    collection = connect_vuln().exploit
    return collection