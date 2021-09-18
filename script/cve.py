import time
import requests
import pymongo
from config import MONGO_HOST,MONGO_USER,MONGO_PASSWORD

'''获取所有CVE数据'''
client = pymongo.MongoClient(MONGO_HOST, 27017)
# 连接vuln数据库，账号密码认证
db = client.vuln
db.authenticate(MONGO_USER, MONGO_PASSWORD)
# 连接集合
collection = db.nvd

base_url = 'https://services.nvd.nist.gov/rest/json/cves/1.0?'
index = 120000
total = 99999999
while index <= total:
    # 每次请求 2000个记录
    url = base_url + 'startIndex=' + str(index) + '&resultsPerPage=2000'
    print(url)
    results = requests.get(url).json()
    total = results['totalResults']
    print('----------------第{}次请求------------------'.format(index / 2000 + 1))
    print('Index = ', index)
    print('Total = ', total)
    print('返回的结果数: ', len(results['result']['CVE_Items']))
    for item in results['result']['CVE_Items']:
        collection.insert_one(item)
    index += 2000
    time.sleep(10)

