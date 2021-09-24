import requests
import logging
import time
from mongoUtils import connect_iotvd

'''2021/09/24'''
'''青莲云 IOTVD (物联网安全漏洞库): 699个漏洞 70页'''

def spider():
    url = 'https://iotvd.qinglianyun.com/Front/Leak/getLeakList'
    page = 1
    collection = connect_iotvd()
    while page <= 70:
        print("正在抓取第{}页的内容".format(page))
        response = requests.post(url,{'page':page})
        for data in response.json()['list']:
            collection.insert_one(data)
        page += 1
        time.sleep(5)



