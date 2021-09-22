import time
import requests
from mongoUtils import connect_nvd

def get_all_cve():
    collection = connect_nvd()
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


# 统计CVE漏洞库中硬件平台上的漏洞数目
def hardware_num():
    collection = connect_nvd()
    h_count = 0
    print("漏洞总数: ", collection.count_documents({}))
    for item in collection.find():
        flag = 0
        for node in item['configurations']['nodes']:
            for cpe in node['cpe_match']:
                if cpe['cpe23Uri'].find(':h:') != -1:  # 漏洞发生在硬件平台上
                    print(cpe['cpe23Uri'])
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


