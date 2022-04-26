import pymongo
import pandas as pd
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

# 连接 nvd01 (collection)
def connect_nvd01():
    collection = connect_vuln().nvd01
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

# 连接 jvn_db (collection)
def connect_jvndb():
    collection = connect_vuln().jvndb
    return collection

# 连接 cwe (collection)
def connect_cwe():
    collection = connect_vuln().cwe
    return collection

# 连接 data01 (collection)
def connect_data01():
    collection = connect_vuln().data01
    return collection

# 连接 iot (collection)
def connect_iot():
    connection = connect_vuln().iot
    return connection

# 连接 iot2 (collection)
def connect_iot2():
    connection = connect_vuln().iot2
    return connection

# 连接 jvndb2 (collection)
def connect_jvndb2():
    connection = connect_vuln().jvndb2
    return connection

# 连接 device
def connect_device():
    connection = connect_vuln().device
    return connection


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

'''训练数据准备：CVE-ID  Description  CVSS3_Score(等级)
    评级        Base Score        label
    Low         0.1 - 3.9           1
    Medium      4.0 - 6.9           2
    High        7.0 - 8.9           3
    Critical    9.0 - 10.0          4
'''
def get_train_data():
    df = pd.DataFrame(columns=['CVE-ID','Description','Label'])
    conn = connect_tfiot()
    count = 1
    for item in conn.find():
        if ("cvss3" in item) & ("cvssV3" in item['cvss3']):
            print('test...')
            cve_id = item['CVE-ID']
            description = item['description']
            baseScore = item['cvss3']['cvssV3']['baseScore']
            if (baseScore >= 0.1) & (baseScore <= 3.9):
                label = 1
            elif (baseScore >= 4.0) & (baseScore <= 6.9):
                label = 2
            elif (baseScore >= 7.0) & (baseScore <= 8.9):
                label = 3
            else:
                label = 4
            df0 = pd.DataFrame({'CVE-ID':cve_id, 'Description':description, 'Label':label}, index=["0"])
            df = df.append(df0,ignore_index=True)
            print('抽取第{}个漏洞...'.format(count))
            count += 1
        # if count > 10:
        #     print(df)
        #     break
    df.to_csv("../data/data01.csv")

# 查找没有 cvssV3_Score的 CVE-ID
def get_no_cvss3():
    conn = connect_tfiot()
    for item in conn.find():
        if "cvssV3" not in item["cvss3"]:
            print(item["CVE-ID"])

# vuln.data01 中一个漏洞可能对应多个 type, 拆分开存入 csv
def get_train_data_02():
    type_dict = {"None":0, "":1, "":2, "":3, "":4, "":5, "":6, "":7, "":8, "":9, "":10, "":11, "":12, "":13}
    df = pd.DataFrame(columns=['CVE-ID', 'Description', 'Label'])
    conn = connect_data01()
    for item in conn.find():
        cve_id = item["CVE-ID"]
        description = item["description"]
        for type in item["type"]:
            label = type_dict[type]
            df0 = pd.DataFrame({'CVE-ID': cve_id, 'Description': description, 'Label': label}, index=["0"])
            df = df.append(df0, ignore_index=True)

def get_train_data_03():
    pass

# MongoDB 去重
def distinct():
    con = connect_iot()
    # 选中所有不重复的 CVE-ID
    for item in con.distinct('CVE-ID'):
        # 复制第一条 CVE-ID相同的数据
        repeating = con.find_one({'CVE-ID' : item})
        # 删除所有 CVE-ID相同的数据
        result = con.delete_many({'CVE-ID' : item})
        con.insert_one(repeating)

if __name__ == '__main__':
    # get_train_data()
    # get_no_cvss3()
    distinct()