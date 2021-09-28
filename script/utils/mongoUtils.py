import pymongo
from config import MONGO_HOST,MONGO_USER,MONGO_PASSWORD

def connect_vuln():
    client = pymongo.MongoClient(MONGO_HOST, 27017)
    # 连接vuln数据库，账号密码认证
    db = client.vuln
    db.authenticate(MONGO_USER, MONGO_PASSWORD)
    return db

# 连接 nvd (collection)
def connect_nvd():
    collection = connect_vuln().nvd
    return collection

# 连接 cnvd (collection)
def connect_cnvd():
    collection = connect_vuln().cnvd
    return collection

# 连接 cnnvd (collection)
def connect_cnnvd():
    collection = connect_vuln().cnnvd
    return collection

# 连接 device (collection)
def connect_device():
    collection = connect_vuln().device
    return collection

# 连接 poc (collection)
def connect_poc():
    collection = connect_vuln().poc
    return collection

# 连接 iotvd (collection)
def connect_iotvd():
    collection = connect_vuln().iotvd
    return collection

# 连接 exploit (collection)
def connect_exploit():
    collection = connect_vuln().exploit
    return collection

# 连接 tf_iot (collection)
def connect_tfiot():
    collection = connect_vuln().tf_iot
    return collection

'''从cve中抽取出有用字段存入tfiot'''
def convert1():
    conn1 = connect_nvd()
    conn2 = connect_tfiot()
    count = 1
    for item in conn1.find():
        vuln = {}
        vuln['CVE-ID'] = item['cve']['CVE_data_meta']['ID']

        # CVE-ID可能对应不止一个CWE-ID
        cwe_list = []
        for cwe_item in item['cve']['problemtype']['problemtype_data']:
            for cwe_item_child in cwe_item['description']:
                cwe_list.append(cwe_item_child['value'])
        vuln['CWE-ID'] = cwe_list

        # 相关链接
        refer = []
        for refer_item in item['cve']['references']['reference_data']:
            refer.append(refer_item)
        vuln['refer'] = refer

        # 描述
        if item['cve']['description']['description_data'] != None:
            description = item['cve']['description']['description_data'][0]['value']
        vuln['description'] = description

        # CPE
        cpe = []
        for cpe_item in item['configurations']['nodes']:
            if cpe_item.get('cpe_match'):
                cpe.append(cpe_item['cpe_match'])
        vuln['cpe'] = cpe
        if item['impact'].get('baseMetricV3'):
            vuln['cvss3'] = item['impact']['baseMetricV3']
        else:
            vuln['cvss3'] = {}
        if item['impact'].get('baseMetricV2'):
            vuln['cvss2'] = item['impact']['baseMetricV2']
        else:
            vuln['cvss2'] = {}
        vuln['publishedDate'] = item['publishedDate']
        vuln['lastModifiedDate'] = item['lastModifiedDate']
        print(item)
        print(vuln)
        conn2.insert_one(vuln)
        print('正在转换第{}条记录'.format(str(count)))
        count += 1