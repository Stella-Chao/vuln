'''文档之间的转换'''
import time
import sys
import re
import json
sys.path.append("..")
from mongoUtils import connect_nvd01,connect_cve2
# from translate import translate

# 将 nvd01 转换格式存入 cve2
def nvd2cve2():
    conn1 = connect_nvd01()
    conn2 = connect_cve2()
    for vuln in conn1.find():
        description = vuln["cve"]["description"]["description_data"][0]["value"]
        cve = {}
        cve["description"] = description
        cve["CVE-ID"] = vuln["cve"]["CVE_data_meta"]["ID"]
        if "baseMetricV3" in vuln["impact"]:
            cve["baseMetricV3"] = vuln["impact"]["baseMetricV3"]
        if "baseMetricV2" in vuln["impact"]:
            cve["baseMetricV2"] = vuln["impact"]["baseMetricV2"]
        cwe = []
        cpe = []
        for cwe_item in vuln["cve"]["problemtype"]["problemtype_data"]:
            for item_child in cwe_item["description"]:
                cwe.append(item_child["value"])
        cve["CWE"] = cwe
        for node in vuln["configurations"]["nodes"]:
            for children in node["children"]:
                for cpe_item in children["cpe_match"]:
                    cpe.append(cpe_item)
        cve["CPE"] = cpe
        cve["references"] = vuln["cve"]["references"]["reference_data"]
        cve["publishedDate"] = vuln["publishedDate"].split('T')[0]
        cve["lastModifiedDate"] = vuln["lastModifiedDate"].split('T')[0]
        # 预留分类字段
        cve["title"] = description.split(".")[0]
        cve["Type01"] = ""
        cve["Type02"] = ""
        cve["Type03"] = ""
        cve["Type04"] = ""
        cve["POC"] = ""
        conn2.insert_one(cve)


'''将cvssV3中的危险级别改为中文'''
def convert_severity():
    cve = connect_cve2()
    for item in cve.find():
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
            cve.update_one({"CVE-ID": id}, {"$set": {"baseMetricV3.cvssV3.baseSeverity": severity}})

'''cvssV3的攻击向量转化为中文'''
def convert_cvssv3_attacker():
    cve = connect_cve2()
    count = 0
    for item in cve.find():
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
            cve.update_one({"CVE-ID": id}, {"$set": {"baseMetricV3.cvssV3.attackVector": new}})
            count += 1
    print(count)


def add_type01():
    cve = connect_cve2()
    for item in cve.find():
        id = item["CVE-ID"]
        type = ""
        description = item["description"]
        if description.find("拒绝服务") != -1 or description.find("Denial Of Service") != -1:
            type = "拒绝服务"
        elif description.find("执行代码") != -1 or description.find("Execute Code") != -1:
            type = "执行代码"
        elif description.find("溢出") != -1 or description.find("Overflow") != -1:
            type = "溢出"
        elif description.find("跨站脚本") != -1 or description.find("Cross Site Scripting") != -1:
            type = "跨站脚本"
        elif description.find("目录遍历") != -1 or description.find("Directory traversal") != -1:
            type = "目录遍历"
        elif description.find("绕过") != -1 or description.find("Bypass a restriction or similar") != -1:
            type = "绕过"
        elif description.find("获取信息") != -1 or description.find("Obtain Information") != -1:
            type = "获取信息"
        elif description.find("获取权限") != -1 or description.find("Gain privileges") != -1:
            type = "获取权限"
        elif description.find("注入") != -1 or description.find("Sql Injection") != -1:
            type = "SQL注入"
        elif description.find("文件包含") != -1 or description.find("File Inclusion") != -1:
            type = "文件包含"
        elif description.find("内存错误") != -1 or description.find("Memory corruption") != -1:
            type = "内存错误"
        elif description.find("请求伪造") != -1 or description.find("CSRF") != -1:
            type = "跨站请求伪造"
        elif description.find("响应拆分") != -1 or description.find("Http response splitting") != -1:
            type = "HTTP响应拆分"
        print(type)
        cve.update_one({"CVE-ID": id}, {"$set": {"Type01": type}})
if __name__ == '__main__':

    # nvd2cve2()
    # add_description_title()
    # 将 cvssV3 的攻击向量转化为中文
    convert_cvssv3_attacker()

    # # 将 cvssV3 中的危险级别翻译为中文
    convert_severity()

    add_type01()
