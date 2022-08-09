'''文档之间的转换'''
import time
import sys
import re
sys.path.append("..")
from spider_utils import random_sleep
from mongoUtils import connect_iot2,connect_iot,connect_jvndb2,connect_exploit,connect_nvd01
from translate import translate

# 从 nvd(collection) 中抽取出 IOT(collection)
def nvd2iot():
    conn1 = connect_nvd01()
    conn2 = connect_iot()
    for vuln in conn1.find():
        description = vuln["cve"]["description"]["description_data"][0]["value"]
        # 关键字匹配，re.I 忽略大小写
        if len(re.findall(r'device|iot|router',description,re.I)) > 0:
            iot = {}
            iot["description"] = description
            iot["CVE-ID"] = vuln["cve"]["CVE_data_meta"]["ID"]
            if "baseMetricV3" in vuln["impact"]:
                iot["baseMetricV3"] = vuln["impact"]["baseMetricV3"]
            if "baseMetricV2" in vuln["impact"]:
                iot["baseMetricV2"] = vuln["impact"]["baseMetricV2"]
            cwe = []
            cpe = []
            for cwe_item in vuln["cve"]["problemtype"]["problemtype_data"]:
                for item_child in cwe_item["description"]:
                    cwe.append(item_child["value"])
            iot["CWE"] = cwe
            for node in vuln["configurations"]["nodes"]:
                for children in node["children"]:
                    for cpe_item in children["cpe_match"]:
                        cpe.append(cpe_item)
            iot["CPE"] = cpe
            iot["references"] = vuln["cve"]["references"]["reference_data"]
            iot["publishedDate"] = vuln["publishedDate"]
            iot["lastModifiedDate"] = vuln["lastModifiedDate"]
            # 预留分类字段
            iot["Type01"] = ""
            iot["Type02"] = ""
            iot["Type03"] = ""
            iot["Type04"] = ""
            iot["POC"] = ""
            conn2.insert_one(iot)

# iot => iot2 增加 title 字段
def convert():
    iot = connect_iot()
    iot2 = connect_iot2()
    for item in iot.find():
        vuln = item
        vuln["title"] = "请查看详情"
        iot2.insert_one(vuln)

def translate_title():
    iot = connect_iot2()
    jvn = connect_jvndb2()
    for item in iot.find():
        id = item["CVE-ID"]
        if item["title"] == "请查看详情":
            jvn_vuln = list(jvn.find({"cve":id}))
            if len(jvn_vuln) > 0:
                title = jvn_vuln[0]["title"]
                time.sleep(1)
                random_sleep()
                try:
                    new_title = translate(title, 'jp', 'zh')
                except:
                    continue
                print(id, " => ", new_title)
            else:
                new_title = "请查看详情"
            iot.update_one({"CVE-ID": id}, {"$set": {"title": new_title}})

def translate_title2():
    iot = connect_iot2()
    for item in iot.find():
        id = item["CVE-ID"]
        title = item["title"]
        if title != "请查看详情":
            time.sleep(1)
            random_sleep()
            new_title = translate(title, 'en', 'zh')
            print(id, " => ", new_title)
            iot.update_one({"CVE-ID": id}, {"$set": {"title": new_title}})


'''
使用 references中的 name作为 title
更改日期格式: 2021-10-06T20:15Z = > 2021-10-06
'''
def title2():
    iot = connect_iot2()
    for item in iot.find():
        id = item["CVE-ID"]
        title = "请查看详情"
        published = item["publishedDate"].split('T')[0]
        modified = item["lastModifiedDate"].split('T')[0]
        print(published,modified)
        if (item["title"] == "请查看详情") & (len(item["references"])) > 0:
            name = item["references"][0]["name"]
            # 判断名字是不是链接
            if "://" not in name:
                # 去掉前面的日期
                if published[:4] == name[:4]:
                    title = " ".join(name.split()[1:])
                    print(title)
        iot.update_one({"CVE-ID": id}, {"$set": {"title": title, "publishedDate": published, "lastModifiedDate": modified}})

def tranlate_type01():
    iot1 = connect_iot()
    iot2 = connect_iot2()
    for item in iot1.find():
        id = item["CVE-ID"]
        type = item["Type01"][0]
        if (type == "Denial Of Service"):
            new = "拒绝服务"
        elif (type == "Execute Code"):
            new = "执行代码"
        elif (type == "Overflow"):
            new = "溢出"
        elif (type == "Cross Site Scripting"):
            new = "跨站脚本"
        elif (type == "Directory traversal"):
            new = "目录遍历"
        elif (type == "Bypass a restriction or similar"):
            new = "绕过"
        elif (type == "Obtain Information"):
            new = "获取信息"
        elif (type == "Gain privileges"):
            new = "获取权限"
        elif (type == "Sql Injection"):
            new = "SQL注入"
        elif (type == "File Inclusion"):
            new = "文件包含"
        elif (type == "Memory corruption"):
            new = "内存错误"
        elif (type == "CSRF"):
            new = "跨站请求伪造"
        elif (type == "Http response splitting"):
            new = "HTTP响应拆分"
        else:
            new = "其他"
        iot2.update_one({"CVE-ID": id}, {"$set": {"Type01": new}})

'''将cvssV3中的危险级别改为中文'''
def convert_severity():
    iot = connect_iot2()
    for item in iot.find():
        id = item["CVE-ID"]
        if "baseMetricV3" in item:
            severity = item["baseMetricV3"]["cvssV3"]["baseSeverity"]
            if severity == "CRITICAL":
                severity = "超危"
            if severity == "HIGH":
                severity = "高危"
            if severity == "MEDIUM":
                severity = "中危"
            if severity == "LOW":
                severity = "低危"
            iot.update_one({"CVE-ID": id}, {"$set": {"baseMetricV3.cvssV3.baseSeverity": severity}})

'''cvssV3的攻击向量转化为中文'''
def convert_cvssv3_attacker():
    iot = connect_iot2()
    count = 0
    for item in iot.find():
        if "baseMetricV3" in item:
            id = item["CVE-ID"]
            vector = item["baseMetricV3"]["cvssV3"]["attackVector"]
            if vector == "NETWORK":
                new = "网络"
            elif vector == "LOCAL":
                new = "本地"
            elif vector == "PHYSICAL":
                new = "物理"
            else:
                new = "邻接"
            iot.update_one({"CVE-ID": id}, {"$set": {"baseMetricV3.cvssV3.attackVector": new}})
            count += 1
    print(count)

def translate_description():
    iot = connect_iot2()
    for item in iot.find():
        id = item["CVE-ID"]
        description = item["description"]
        flag = item["Type04"]
        if flag == "1":
            continue    # 表示已翻译过
        time.sleep(1)
        random_sleep()
        try:
            new_desc = translate(description, 'en', 'zh')
        except:
            continue
        print(id, " => ", new_desc)
        iot.update_one({"CVE-ID": id}, {"$set": {"description": new_desc, "Type04": "1"}})

def getPocNum():
    iot = connect_iot2()
    poc = connect_exploit()
    count = 0
    for p in poc.find():
        cve = p["cve"]
        cveid = "CVE-" + cve
        result = iot.find_one({"CVE-ID": cveid})
        if result != None:
            print(cveid)
            count += 1
    print("IOT漏洞共有POC {}个...".format(count))

def fun():
    poc = connect_exploit()
    for item in poc.find():
        id = item["edb_id"]
        url = item["code_url"]
        new = url.replace("/exploits//","/")
        poc.update_one({"edb_id": id}, {"$set": {"code_url": new}})

if __name__ == '__main__':
    # 从 nvd01 中提取出 iot 数据存入 iot
    nvd2iot()
    # iot => iot2 添加 title 字段
    convert()
    # 翻译 title
    translate_title()
    # 将 references 中的 name 作为 title; 更改日期格式: 2021-10-06T20:15Z = > 2021-10-06
    title2()
    # 将 cvssV3 的攻击向量转化为中文
    convert_cvssv3_attacker()
    translate_title2()
    # 将漏洞类型转化为中文
    tranlate_type01()
    # 将 cvssV3 中的危险级别翻译为中文
    convert_severity()
    # 翻译漏洞描述信息
    translate_description()