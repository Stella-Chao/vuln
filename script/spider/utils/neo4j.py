from mongoUtils import connect_iot2
import pandas as pd
import uuid

# Cisco 相关漏洞
def cisco():
    connection = connect_iot2()
    count = 0
    vuln = pd.DataFrame(columns=["cve"])
    type = pd.DataFrame(columns=["cwe","type"])
    cvss = pd.DataFrame(columns=["cvssid","level","score","attack"])
    product = pd.DataFrame(columns=["product_name","vendor","type","version","vulns"])
    type_list = []
    product_list = []
    # vuln - type
    relation1 = pd.DataFrame(columns=["cve","cwe"])
    # vuln - cvss
    relation2 = pd.DataFrame(columns=["cve","cvssid"])
    # vuln - product
    relation3 = pd.DataFrame(columns=["cve","product_name"])

    for item in connection.find():
        if "cisco" in item["description"] or "Cisco" in item["description"] or "思科" in item["description"]:
            if "baseMetricV3" in item and len(item["CPE"]) > 0 and item["Type01"] != "其他":
                print(item["CPE"][0])
                vuln = vuln.append(pd.DataFrame({"cve":[item["CVE-ID"]]}),ignore_index=True)
                for t in item["CWE"]:
                    if t not in type_list:
                        type_list.append(t)
                        type = type.append(pd.DataFrame({"cwe":[t],"type":[item["Type01"]]}),ignore_index=True)
                cvssid = uuid.uuid1()
                product_name = item["CPE"][0]["cpe23Uri"].split(':')[4]
                if product_name not in product_list:
                    product_list.append(product_name)
                    product = product.append(pd.DataFrame(
                        {"product_name": [product_name], "vendor": ["Cisco"], "type": ["router"], "version": ["xx"],
                         "vulns": [1]}))
                level = item["baseMetricV3"]["cvssV3"]["baseSeverity"]
                score = item["baseMetricV3"]["cvssV3"]["baseScore"]
                attack = item["baseMetricV3"]["cvssV3"]["attackVector"]
                cvss = cvss.append(pd.DataFrame({"cvssid":[cvssid],"level":[level],"score":[score],"attack":[attack]}))
                for t in item["CWE"]:
                    relation1 = relation1.append(pd.DataFrame({"cve":[item["CVE-ID"]],"cwe":[t]}))
                relation2 = relation2.append(pd.DataFrame({"cve":[item["CVE-ID"]],"cvssid":[cvssid]}))
                relation3 = relation3.append(pd.DataFrame({"cve":[item["CVE-ID"]],"product_name":[product_name]}))
    vuln.to_csv("vuln.csv",sep = ",", index = False, encoding="utf8")
    type.to_csv("type.csv",sep = ",", index = False, encoding="utf8")
    cvss.to_csv("cvss.csv",sep = ",", index = False, encoding="utf8")
    product.to_csv("product.csv",sep = ",", index = False, encoding="utf8")
    relation1.to_csv("relation1.csv",sep = ",", index = False, encoding="utf8")
    relation2.to_csv("relation2.csv",sep = ",", index = False, encoding="utf8")
    relation3.to_csv("relation3.csv",sep = ",", index = False, encoding="utf8")
    print("Cisco 漏洞总数：", count)
    print(vuln)
    print(type)
if __name__ == '__main__':
    cisco()