import requests
from spider_utils import random_sleep
from utils.mongoUtils import connect_iot2

def start():
    count = 0
    iot = connect_iot2()
    for item in iot.find():
        random_sleep()
        url = "https://api.github.com/search/repositories?q=" + item["CVE-ID"]
        try:
            result = requests.get(url).json()["items"]
        except:
            continue
        iot.update_one({"CVE-ID":item["CVE-ID"]})
        if len(result) > 0:
            count += 1
            print("找到第{}个POC:".format(count),result[0]["html_url"])

if __name__ == '__main__':
    start()