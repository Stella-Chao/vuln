from utils.mongoUtils import connect_nvd, connect_jvndb, connect_total, connect_exploit, connect_github_poc, connect_poc
import datetime
from random import randint

# 定时任务
# 将每天新增的漏洞数据融合起来

def fusion():
    nvd = connect_nvd()
    jvn = connect_jvndb()
    total = connect_total()
    poc = connect_poc()
    exploit = connect_exploit()
    github_poc = connect_github_poc()
    total_list = []
    nvd_list = []
    cve_list = []
    # jvn_id 对应的 cve-id
    jvn_list = {}
    for item in total.find():
        total_list.append(item["cve"]["CVE_data_meta"])

    today = str(datetime.date.today())

    # 是否只需要 更新 updateDate 在当日的漏洞信息
    # create_nvd(nvd, today, total_list)
    update_nvd(nvd, today, nvd_list)

    # create_jvn(jvn, today, total_list)
    update_jvn(total, jvn, today, jvn_list)

    update_exploit(today, poc, exploit, cve_list)

    update_github_poc(today, poc, github_poc, cve_list)



def update_nvd(nvd, today, nvd_list):
    # 1.获取当日新增的所有漏洞（publishedDate）
    today += "T00:00Z"
    # 查找出当天更新的漏洞列表
    nvd_new = nvd.find({"publishedDate": {"$gte": today}})
    for item in nvd_new:
        cve_id = item["cve"]["CVE_data_meta"]["ID"]
        # 应区别插入和更新的情况 还是直接插入？
        if cve_id not in nvd_list:
            vuln = {}
            year = today.split('-')[0]
            month = today.split('-')[1]
            # 生成漏洞唯一编号：TF-年份月份-四位随机数
            vuln["id"] = "TF-" + year[2:] + month + "-" + ''.join(["%s" % randint(0, 9) for num in range(0, 4)])
            print(vuln["id"])

            # CVE-ID可能对应不止一个CWE-ID
            cwe_list = []
            for cwe_item in item['cve']['problemtype']['problemtype_data']:
                for cwe_item_child in cwe_item['description']:
                    cwe_list.append(cwe_item_child['value'])
            vuln['cwe'] = cwe_list

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
            # 时间保留 year-month-day
            vuln['publishedDate'] = item['publishedDate'][:10]
            vuln['lastModifiedDate'] = item['lastModifiedDate'][:10]
            # 漏洞 ID 列表
            vuln["other_id"] = [{"CVE-ID": cve_id}]
            # 漏洞标题（其他源头）
            vuln["title"] = ""
            vuln["poc"] = []
            # 漏洞补丁（其他源头）
            vuln["patch"] = ""
            # 漏洞类型
            vuln["type"] = ""
            # 攻击方式
            vuln["attack_type"] = ""
            # IoT 漏洞标识
            vuln["isIoT"] = False
            # 可采取的措施（其他源头）
            vuln["measure"] = ""


def update_jvn(total, jvn, today, jvn_list):
    # 转换日期格式：year-month-day => year/month/day
    today = '/'.join(today.split('-'))
    # 查找出当天更新的漏洞列表
    jvn_new = jvn.find({"updated_date": {"$gte": today}})
    for item in jvn_new:
        jvn_id = item["jvn_id"]
        # 找到 jvn_id 对应的漏洞
        vuln = total.find({"jvn_id": jvn_id})
        if vuln != []:
            # 查询结果是否是列表
            vuln = vuln[0]
            total.update_one({"cve_id": vuln["cve_id"]}, {"$set": {"title": item["title"], "measure": item["measure"]}})
        else:
            pass

def update_exploit(today, poc, exploit, cve_list):
    # 获取当日新增的exploit-poc
    exploit_new = exploit.find({"date": {"$gte": today}})
    for item in exploit_new:
        edb_id = item["1"]
        cve_id = "CVE-" + item["cve"]
        if cve_id not in cve_list:
            new_poc = {}
            new_poc["cve_id"] = cve_id
            new_poc["github_url"] = ""
            new_poc["exb_id"] = edb_id
            new_poc["verified"] = item["verified"]
            new_poc["author"] = item["author"]
            new_poc["type"] = item["remote"]
            new_poc["code_url"] = item["code_url"]
            new_poc["plat"] = item["plat"]
            poc.insert(new_poc)


def update_github_poc(today, poc, github_poc, cve_list):
    # 获取当日新增的 github_poc
    github_new = github_poc.find({"date": {"$gte": today}})
    for item in github_new:
        cve_id = item["cve_id"]
        if cve_id not in cve_list:
            new_poc = {}
            new_poc["cve_id"] = cve_id
            new_poc["github_url"] = item["url"]
            poc.insert(new_poc)
        else:
            pass

if __name__ == '__main__':
    fusion()