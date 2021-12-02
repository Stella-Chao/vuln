import gc
import re
import time
import requests
from utils.mongoUtils import connect_nvd,connect_iot

def get_all_cve():
    collection = connect_nvd()
    base_url = 'https://services.nvd.nist.gov/rest/json/cves/1.0?'
    index = 0
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


# 统计CVE漏洞库中硬件平台上的漏洞数目
def hardware_num():
    collection = connect_nvd()
    h_count = 0
    print("漏洞总数: ", collection.count_documents({}))
    for item in collection.find():
        flag = 0
        description = item['cve']['description']['description_data']
        for node in item['configurations']['nodes']:
            for cpe in node['cpe_match']:
                # if (cpe['cpe23Uri'].find(':h:') != -1) & (description[0]['value'].find('device') != -1 or description[0]['value'].find('Device') != -1):  # 漏洞发生在硬件平台上
                #     print(cpe['cpe23Uri'])
                    h_count += 1
                    flag = 1
                    break
            if flag == 1:
                break
    print("硬件平台漏洞总数: ", h_count)


def get_cpe_by_cveId(cve_id):
    collection = connect_nvd()
    query = {"cve": {"CVE_data_meta": {"ID": cve_id}}}
    result = collection.find_one(query)
    print(result)

def get_by_cveID(cve_id):
    collection = connect_nvd()
    for item in collection.find():
        if item["cve"]["CVE_data_meta"]["ID"] == cve_id:
            print(item)
            break

# 统计描述信息中含有iot/device的漏洞数
def iot_num():
    collection = connect_nvd()
    print(collection)
    i_num = 0
    print("漏洞总数：", collection.count_documents({}))
    for item in collection.find():
        for description in item['cve']['description']['description_data']:
            if description['value'].find('device') != -1 \
                    or description['value'].find('Device') != -1:
                i_num += 1
                break
    print(i_num)

# 从 nvd(collection) 中抽取出 IOT(collection)
def nvd2iot():
    conn1 = connect_nvd()
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

# 按行业分类（暂时先筛选出视频监控类漏洞）
def update_type02():
    connection = connect_iot()
    for item in connection.find():
        description = item["description"]
        if len(re.findall(r'camera|vedio|hikvision|dahua',description,re.I)) > 0:
            # 更新类型2
            connection.update_one({"CVE-ID": item["CVE-ID"]}, {"$set": {"Type02": "vedio"}})

# 统计缺少CPE的漏洞数目
if __name__ == '__main__':
    # get_all_cve()
    # nvd2iot()
    # get_by_cveID("CVE-2021-34947")
    # update_type02()
    hardware_num()
    # iot_num()