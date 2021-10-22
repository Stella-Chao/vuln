import re
import requests
import random
from utils.mongoUtils import connect_iot
from bs4 import BeautifulSoup
from spider_utils import user_agent_pc, get_proxy, random_sleep

# 通过 CVE-ID 获取漏洞细节(漏洞类型)
def get_detail_by_id():
    base_url = "https://www.cvedetails.com/cve/"
    count = 0
    headers = {
        'User-Agent': random.choice(user_agent_pc)
    }
    proxy = get_proxy().get("proxy")
    # df = pd.read_csv("../data/cveid.csv")
    # cve_num = len(df)
    conn = connect_iot()
    # conn2 = connect_data01()
    for vuln in conn.find():
        flag = False
        if vuln["Type01"] == "":
            cve_id = vuln["CVE-ID"]
            print("正在更新第{}条：{}...".format(count, cve_id))
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
            count += 1
            # random_sleep()
            conn.update_one({"CVE-ID":cve_id},{"$set":{"Type01":type}})

# 获取漏洞类型
def get_all_type():
    conn = connect_iot()
    type = []
    for item in conn.find():
        for t in item["Type01"]:
            if t not in type:
                type.append(t)

    print(type)


if __name__ == '__main__':
    get_detail_by_id()
    # get_all_type()
    # iot_num()