import re
import pandas as pd
import requests
import time
import random
from utils.mongoUtils import connect_nvd,connect_data01
from bs4 import BeautifulSoup
from cve import iot_num

# PC端的 User-Agent
user_agent_pc = [
    # 谷歌
    'Mozilla/5.0.html (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.html.2171.71 Safari/537.36',
    'Mozilla/5.0.html (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.html.1271.64 Safari/537.11',
    'Mozilla/5.0.html (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.html.648.133 Safari/534.16',
    # 火狐
    'Mozilla/5.0.html (Windows NT 6.1; WOW64; rv:34.0.html) Gecko/20100101 Firefox/34.0.html',
    'Mozilla/5.0.html (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10',
    # opera
    'Mozilla/5.0.html (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.html.2171.95 Safari/537.36 OPR/26.0.html.1656.60',
    # qq浏览器
    'Mozilla/5.0.html (compatible; MSIE 9.0.html; Windows NT 6.1; WOW64; Trident/5.0.html; SLCC2; .NET CLR 2.0.html.50727; .NET CLR 3.5.30729; .NET CLR 3.0.html.30729; Media Center PC 6.0.html; .NET4.0C; .NET4.0E; QQBrowser/7.0.html.3698.400)',
    # 搜狗浏览器
    'Mozilla/5.0.html (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.html.963.84 Safari/535.11 SE 2.X MetaSr 1.0.html',
    # 360浏览器
    'Mozilla/5.0.html (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.html.1599.101 Safari/537.36',
    'Mozilla/5.0.html (Windows NT 6.1; WOW64; Trident/7.0.html; rv:11.0.html) like Gecko',
    # uc浏览器
    'Mozilla/5.0.html (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.html.2125.122 UBrowser/4.0.html.3214.0.html Safari/537.36',
]

def get_proxy():
    return requests.get("http://127.0.0.1:5010/get/").json()


# 随机睡眠
def random_sleep(mu=1, sigma=0.4):
    '''正态分布随机睡眠
    :param mu: 平均值
    :param sigma: 标准差，决定波动范围
    '''
    secs = random.normalvariate(mu, sigma)
    if secs <= 0:
        secs = mu  # 太小则重置为平均值
    time.sleep(secs)

# 通过 CVE-ID 获取漏洞细节(漏洞类型)
def get_detail_by_id():
    base_url = "https://www.cvedetails.com/cve/"
    count = 0
    headers = {
        'User-Agent': random.choice(user_agent_pc)
    }
    proxy = get_proxy().get("proxy")
    df = pd.read_csv("../data/cveid.csv")
    cve_num = len(df)
    conn = connect_nvd()
    conn2 = connect_data01()
    for vuln in conn.find():
        flag = False
        cve_id = vuln["cve"]["CVE_data_meta"]["ID"]
        data = {}
        data["CVE-ID"] = cve_id
        data["description"]=vuln["cve"]["description"]["description_data"][0]["value"]
        url = base_url + cve_id
        # 访问被禁止后，更换代理
        try:
            res = requests.get(url,headers=headers,proxies={"http": "http://{}".format(proxy)})
        except Exception as e:
            print(e)
            print("第{}次更换代理...".format(count))
            count += 1
            proxy = get_proxy().get("proxy")
            res = requests.get(url, headers=headers, proxies={"http": "http://{}".format(proxy)})

        soup = BeautifulSoup(res.text, features="lxml")
        type = []
        for item in soup.findAll("span",attrs={"class":re.compile("^vt_.*")}):
            type.append(item.string)
        if len(type) == 0:
            type.append("None")
        data["type"] = type
        count += 1
        random_sleep()
        conn2.insert_one(data)

# 获取漏洞类型
def get_all_type():
    conn = connect_data01()
    type = []
    for item in conn.find():
        for t in item["type"]:
            if t not in type:
                type.append(t)

    print(type)


if __name__ == '__main__':
    get_detail_by_id()
    # get_all_type()
    # iot_num()