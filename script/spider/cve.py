import gc
import re
import time
import requests
from utils.mongoUtils import connect_nvd, connect_nvd01, connect_iot

def get_all_cve():
    collection = connect_nvd01()
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
        time.sleep(20)


# 统计CVE漏洞库中硬件平台上的漏洞数目
def test_hardware_num():
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
                if (cpe['cpe23Uri'].find(':h:') != -1):
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


# 按行业分类（暂时先筛选出视频监控类漏洞）
def update_type02():
    connection = connect_iot()
    for item in connection.find():
        description = item["description"]
        if len(re.findall(r'camera|vedio|hikvision|dahua',description,re.I)) > 0:
            # 更新类型2
            connection.update_one({"CVE-ID": item["CVE-ID"]}, {"$set": {"Type02": "vedio"}})


# 统计描述信息中含有含有Cisco 但不含iot/device的漏洞数
def test_Cisco():
    collection = connect_nvd()
    print(collection)
    i_num = 0
    print("漏洞总数：", collection.count_documents({}))
    for item in collection.find():
        for description in item['cve']['description']['description_data']:
            if description['value'].find('Cisco') != -1:
                if check(description['value']) == False:
                    i_num += 1
                    print(description['value'])
                    if len(item["configurations"]["nodes"]) > 0:
                        nodes = item["configurations"]["nodes"][0]["cpe_match"]
                    print(nodes)
                    break
    print(i_num)

def check(des):
    if (des.find('Device') == -1) & (des.find('device') == -1) & (des.find('IoT') == -1) & (des.find('iot') == -1)\
            & (des.find('router') == -1) & (des.find('Router') == -1):
        return False
    else:
        return True


# 统计CVE漏洞库中有CPE的漏洞数量
def test_cpe_num():
    collection = connect_nvd01()
    cpe_count = 0
    print("漏洞总数: ", collection.count_documents({}))
    for item in collection.find():
        nodes = item['configurations']['nodes']
        if len(nodes) > 0:
            cpe = nodes[0]["cpe_match"]
            if len(cpe) > 0:
                cpe_count += 1
    print("有CPE信息的漏洞数: ", cpe_count)

def test_cve():
    get_all_cve()

if __name__ == '__main__':
    get_all_cve()