import requests
import re
import time
import random
from utils.mongoUtils import connect_jvndb
from bs4 import BeautifulSoup
from spider_utils import user_agent_pc, get_proxy

urls = []
headers = {
    'User-Agent': random.choice(user_agent_pc)
}

def get_urls():
    proxy = get_proxy().get("proxy")
    base = 'https://jvndb.jvn.jp/'
    search_base = 'https://jvndb.jvn.jp/search/index.php?mode=_vulnerability_search_IA_VulnSearch&lang=ja&keyword=&useSynonym=1&vendor=&product=&datePublicFromMonth=01&datePublicFromYear={}&datePublicToMonth={}&datePublicToYear={}&datePublicToMonth={}&dateLastPublishedFromMonth=&dateLastPublishedFromYear=&dateLastPublishedToMonth=&dateLastPublishedToYear=&cwe=&searchProductId=&pageNo={}'
    # 起止日期, 每次的搜索结果不能超过10000条
    from_year = "2021"
    from_month = "01"
    to_year = "2021"
    to_month = "10"
    page = 1
    count = 0
    url = search_base.format(from_year,from_month,to_year,to_month,page)
    # 访问被禁止后，更换代理
    try:
        res = requests.get(url, headers=headers, proxies={"http": "http://{}".format(proxy)}, timeout = 3)
    except Exception as e:
        print(e)
        print("第{}次更换代理...".format(count))
        count += 1
        proxy = get_proxy().get("proxy")
        res = requests.get(url, headers=headers, proxies={"http": "http://{}".format(proxy)},timeout=3)
    # 指定编码方式(日文),否则会出现乱码
    res.encoding = 'SHIFT_JIS'
    soup = BeautifulSoup(res.text, features="lxml")
    print(soup.title.string)
    # 获取记录总数
    str = soup.find_all('td', class_="pager_count_class")[0].get_text().strip()
    total = int(re.match('.+?(?=\件)',str).group(0))
    print('total:',total)
    # 获取总页数
    if total % 100 == 0:
        pages = total / 100
    else:
        pages = total / 100 + 1
    print(total)

    while page <= pages:
        url = search_base.format(from_year, from_month, to_year, to_month, page)
        print(url)
        try:
            res = requests.get(url, headers=headers, proxies={"http": "http://{}".format(proxy)}, timeout=3)
        except Exception as e:
            print(e)
            print("第{}次更换代理...".format(count))
            count += 1
            proxy = get_proxy().get("proxy")
            res = requests.get(url, headers=headers, proxies={"http": "http://{}".format(proxy)}, timeout=3)
        # 指定编码方式(日文),否则会出现乱码
        res.encoding = 'SHIFT_JIS'
        soup = BeautifulSoup(res.text, features="lxml")

        # 获取URL列表
        for tr in soup.find('table', class_='result_class').find_all('tr'):
            td = tr.find_all('td')
            if len(td) > 0:
                url = base + td[0].a['href']
                urls.append(url)
                jvn_id = td[0].a.string
                print(url,jvn_id)
        page += 1
    print(urls)
# 遍历所有的 URL

def parse(urls):
    count = 1
    for url in urls:
        print('正在抓取第{}个页面...'.format(count))
        jvn = {}
        jvn['url'] = url
        try:
            res = requests.get(url, headers=headers, proxies={"http": "http://{}".format(proxy)}, timeout=3)
        except Exception as e:
            print(e)
            print("第{}次更换代理...".format(count))
            count += 1
            proxy = get_proxy().get("proxy")
            res = requests.get(url, headers=headers, proxies={"http": "http://{}".format(proxy)},timeout=3)
        # 指定编码方式(日文),否则会出现乱码
        res.encoding = 'SHIFT_JIS'
        soup = BeautifulSoup(res.text, features="lxml")
        table = soup.find('table', class_='vuln_table_clase')
        rows = table.find_all('tr')
        '''注意：不同页面按行取值的方式可不可行？'''
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
        print(jvn)
        count += 1
        # 插入vuln.jvndb集合中
        coll = connect_jvndb()
        coll.insert_one(jvn)
        time.sleep(1)


if __name__ == '__main__':

    get_urls()
    parse(urls)