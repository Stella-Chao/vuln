from bs4 import BeautifulSoup
import requests
from utils.mongoUtils import connect_nvd01

def cve_daily():
    collection = connect_nvd01()
    # 请求接口示例：https://services.nvd.nist.gov/rest/json/cves/1.0/?pubStartDate=2021-08-04T13:00:00:000 UTC-05:00&pubEndDate=2021-10-22T13:36:00:000 UTC-05:00
    base_url = 'https://services.nvd.nist.gov/rest/json/cves/1.0?pubStartDate='
    begin_time = ''
    current_time = ''
    url = base_url + str(begin_time) + '&pubEndDate=' + str(current_time)
    print(url)
    results = requests.get(url).json()
    total = results['totalResults']
    print('Total = ', total)
    print('返回的结果数: ', len(results['result']['CVE_Items']))
    for item in results['result']['CVE_Items']:
        # 是否需要去重逻辑
        collection.insert_one(item)

def jvn_daily():
    # 获取首页新增的漏洞，通过日期判断是否为当天的漏洞
    # https://jvndb.jvn.jp/search/index.php?mode=_vulnerability_search_IA_VulnSearch&lang=ja&keyword=&vendor=&product=&datePublicFromYear=2022&datePublicFromMonth=07&datePublicToYear=&datePublicToMonth=&dateLastPublishedFromYear=&dateLastPublishedFromMonth=&dateLastPublishedToYear=&dateLastPublishedToMonth=&cwe=&searchProductId=
    url = 'https://jvndb.jvn.jp/en/'
    results = requests.get(url)
    soup = BeautifulSoup(results.text, features="lxml")

    # 获取URL列表
    for li in soup.find('ul', class_='news-list bg').find_all('li'):
        # if it has <span> New </span>
        text = str(li.div)
        if text.find("New") != -1:
            print(li.string)
            print(li.text)
            print(text)
def fusion_daily():
    pass


if __name__ == '__main__':
    # cve_daily()
    jvn_daily()
    # fusion_daily()