import time
import requests
import random
from translate import translate
import datetime
from mongoUtils import connect_jvndb2, connect_nvd01, connect_iot2, connect_cve2
from bs4 import BeautifulSoup
from spider_utils import user_agent_pc, random_sleep, get

def cve_daily():
    collection = connect_nvd01()
    iot2 = connect_iot2()
    cve2 = connect_cve2()
    iot_list = []
    cve_list = []
    iot_type = ['camera' , 'video' , 'vcr' , 'nvr', 'router' , 'televsion' , 'printer' , 'phone' , 'mobile' , 'watch' ,'light' , 'refrigerator', 'car', 'device', 'iot']
    for item in iot2.find():
        iot_list.append(item["CVE-ID"])
    for item in cve2.find():
        cve_list.append(item["CVE-ID"])
    # 请求接口示例：https://services.nvd.nist.gov/rest/json/cves/1.0/?pubStartDate=2021-08-04T13:00:00:000 UTC-05:00&pubEndDate=2021-10-22T13:36:00:000 UTC-05:00
    base_url = 'https://services.nvd.nist.gov/rest/json/cves/1.0?pubStartDate='
    today = (datetime.datetime.today() + datetime.timedelta(days=-1)).strftime("%Y-%m-%d")
    nextday = (datetime.datetime.now()+datetime.timedelta(days=1)).strftime("%Y-%m-%d")
    from_day = today + "T00:00:00:000 UTC-05:00"
    to_day = nextday + "T00:00:00:000 UTC-05:00"
    url = base_url + str(from_day) + '&pubEndDate=' + str(to_day) + "&resultsPerPage=500"
    print(url)
    results = requests.get(url).json()
    total = results['totalResults']
    print('Total = ', total)
    print('返回的结果数: ', len(results['result']['CVE_Items']))
    for vuln in results['result']['CVE_Items']:
        # print(vuln)
        description = vuln["cve"]["description"]["description_data"][0]["value"]
        # 精简格式
        cve = {}
        cve["description"] = description
        cve["CVE-ID"] = vuln["cve"]["CVE_data_meta"]["ID"]
        if cve["CVE-ID"] not in cve_list:
            collection.insert_one(vuln)
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

            print(cve)

            flag = False
            vuln_type = ''
            for type in iot_type:
                if description.find(type) != -1:
                    flag = True
                    vuln_type = type
                    break
            if flag:
                cveid =  cve["CVE-ID"]
                if cveid not in iot_list:
                    cve["Type02"] = vuln_type
                    print(cve)
                    cve["description"] = translate(cve["description"])
                    iot2.insert_one(cve)
            cve["description"] = translate(cve["description"])
            cve2.insert_one(cve)
def jvn_daily():
    # 获取首页新增的漏洞，通过日期判断是否为当天的漏洞
    # https://jvndb.jvn.jp/search/index.php?mode=_vulnerability_search_IA_VulnSearch&lang=ja&keyword=&vendor=&product=&datePublicFromYear=2022&datePublicFromMonth=07&datePublicToYear=&datePublicToMonth=&dateLastPublishedFromYear=&dateLastPublishedFromMonth=&dateLastPublishedToYear=&dateLastPublishedToMonth=&cwe=&searchProductId=
    url = 'https://jvndb.jvn.jp/en/'
    results = requests.get(url)
    soup = BeautifulSoup(results.text, features="lxml")
    # 获取URL列表
    urls = []
    for li in soup.find('ul', class_='news-list bg').find_all('li'):
        # if it has <span> New </span>
        text = str(li.div)
        if text.find("New") != -1:
            urls.append("https://jvndb.jvn.jp/ja" + li.a["href"][3:])
    parse(urls)

def parse(urls):
    headers = {
        'User-Agent': random.choice(user_agent_pc),
        'Connection': 'close'
    }
    for url in urls:
        print(url)
        jvn = {}
        jvn['url'] = url
        requests.adapters.DEFAULT_RETRIES = 5
        res = requests.get(url, headers)
        # 指定编码方式(日文),否则会出现乱码
        res.encoding = 'SHIFT_JIS'
        soup = BeautifulSoup(res.text, features="lxml")
        table = soup.find('table', class_='vuln_table_clase')
        rows = table.find_all('tr')
        '''注意：不同页面按行取值的方式可不可行？'''
        if rows[0].text.replace('\n','') == '[English]':
            rows = rows[1:]
        jvn['jvn_id'] = rows[0].text.replace('\n','')
        jvn['title'] = rows[1].text.replace('\n','').replace('\xa0',' ')
        jvn['abstract'] = rows[3].text.replace('\n','')
        cvss = rows[5].find_all('div',class_='float_left')
        # 判断是否存在 cvss2
        if len(cvss) > 0:
            jvn['cvss2'] = cvss[0].text
        # 判断是否存在 cvss3
        if len(cvss) > 1:
            jvn['cvss3'] = cvss[1].text
        # 受影响的系统
        jvn['affected'] = rows[8].text.replace('\n','')
        # 预期影响
        jvn['expected_impact'] = rows[11].text.replace('\n','')
        # 措施
        jvn['measure'] = rows[13].text.replace('\n','')
        # 供应商
        jvn['vendor'] = rows[15].text.replace('\n','')
        # CWE
        jvn['cwe'] = rows[17].text.replace('\n','')
        # CVE
        jvn['cve'] = rows[19].text.replace('\n','')
        # 相关链接
        jvn['refer'] = rows[21].text.replace('\n','')

        footer = soup.find('table', class_='vuln_table_clase_footer')
        rows2 = footer.find_all('tr')
        # 发布日期
        jvn['pulished_date'] = rows2[1].find_all('td')[1].text
        # 注册日期
        jvn['register_date'] = rows2[2].find_all('td')[1].text
        # 上次更新
        jvn['updated_date'] = rows2[3].find_all('td')[1].text
        # 插入vuln.jvndb集合中
        coll = connect_jvndb2()
        # coll.insert_one(jvn)
        print(jvn)
        # time.sleep(1)
        random_sleep()

def fusion_daily():
    #获取 iot2 中的 cve-id 列表，用于去重
    cve_list = []
    jvn_list = []
    iot_list = []
    nvd = connect_nvd01()
    jvn = connect_jvndb2()
    iot = connect_iot2()
    for item in nvd.find():
        cve_list.append(item["cve"]["CVE_data_meta"]["ID"])
    for item in jvn.find():
        jvn_list.append(item["cve"])
    for item in iot.find():
        iot_list.append(item["CVE-ID"])
    #获取当日新增的 nvd
    new_nvd = nvd.find()
    #获取当日新增的 jvn
    today = datetime.datetime.today().strftime("%Y/%m/%d")
    for vuln in jvn.find({"published_date":today}):
        print(vuln)
        cveid = vuln["cve"]
        # if cveid in iot_list:
        #     print(cveid, "test...")
            # 更新 title
            # title = translate(vuln["title"])
            # iot.update_one({"CVE-ID": cveid}, {"$set": {"title": title}})

if __name__ == '__main__':
    cve_daily()
    # jvn_daily()
    # fusion_daily()