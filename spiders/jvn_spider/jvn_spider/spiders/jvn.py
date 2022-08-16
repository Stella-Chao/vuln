import re
import time

import scrapy
import requests
import scrapy_redis.spiders

from jvn_spider.items import JvnSpiderItem
from bs4 import BeautifulSoup
import urllib3

urllib3.disable_warnings()

# class JVNSpider(scrapy.Spider):
class JVNSpider(scrapy_redis.spiders.RedisSpider):
    name = "jvn"
    global count
    count = 0
    def start_requests(self):
        base = 'https://jvndb.jvn.jp'
        search_base = 'https://jvndb.jvn.jp/search/index.php?mode=_vulnerability_search_IA_VulnSearch&lang=ja&keyword=&useSynonym=1&vendor=&product=&datePublicFromYear={}&datePublicFromMonth={}&datePublicToYear={}&datePublicToMonth={}&dateLastPublishedFromMonth=&dateLastPublishedFromYear=&dateLastPublishedToMonth=&dateLastPublishedToYear=&cwe=&searchProductId=&pageNo={}'
        years = ["1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011"
                 ,"2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022"]
        urls = []
        months = ["01","02","03","04","05","06","07","08","09","10","11","12"]
        for year in years:
            for month in months:
                page = 1
                url = search_base.format(year,month,year,month,page)
                res = requests.get(url, verify=False)
                res.encoding = 'SHIFT_JIS'
                soup = BeautifulSoup(res.content, "html.parser")
                print(soup.title.string)
                try:
                    all_text = soup.select(".pager_class .pager_count_class")[0].text
                    total = re.findall(r'^\D*(\d+)', all_text)
                    total = (int)(total[0])
                except:
                    continue
                print('total:', total)
                # 获取总页数
                if total % 100 == 0:
                    pages = total / 100
                else:
                    pages = total / 100 + 1
                print(total)
                while page <= pages:
                    # proxy = self.get_proxy()
                    url = search_base.format(year, month, year, month, page)
                    print(url)
                    print("--------------------正在爬取第{}页-------------------".format(page))
                    time.sleep(5)
                    # res = requests.get(url,verify = False, proxies={"http": "http://{}".format(proxy)})
                    res = requests.get(url, verify=False)
                    # 指定编码方式(日文),否则会出现乱码
                    res.encoding = 'SHIFT_JIS'
                    soup = BeautifulSoup(res.content, "html.parser")
                    tr_list = soup.select('table.result_class tr')
                    for tr in tr_list:
                        td = tr.find_all('td')
                        if len(td) > 0:
                            url = base + td[0].a['href']
                            print(url)
                            urls.append(url)
                    page += 1
        for link in urls:
            yield scrapy.Request(url=link, encoding='SHIFT_JIS', callback=self.parse2, dont_filter=True)

    def parse2(self, res):
        global count
        count += 1
        print('正在抓取第{}条数据...'.format(count))
        jvn = JvnSpiderItem()
        soup = BeautifulSoup(res.text, features="lxml")
        table = soup.find('table', class_='vuln_table_clase')
        rows = table.find_all('tr')
        '''注意：不同页面按行取值的方式可不可行？'''
        if rows[0].text.replace('\n', '') == '[English]':
            rows = rows[1:]
        jvn['jvn_id'] = rows[0].text.replace('\n', '')
        jvn['title'] = rows[1].text.replace('\n', '').replace('\xa0', ' ')
        jvn['abstract'] = rows[3].text.replace('\n', '')
        cvss = rows[5].find_all('div', class_='float_left')
        # 判断是否存在 cvss2
        if len(cvss) > 0:
            jvn['cvss2'] = cvss[0].text
        # 判断是否存在 cvss3
        if len(cvss) > 1:
            jvn['cvss3'] = cvss[1].text
        # 受影响的系统
        jvn['affected'] = rows[8].text.replace('\n', '')
        # 预期影响
        jvn['expected_impact'] = rows[11].text.replace('\n', '')
        # 措施
        jvn['measure'] = rows[13].text.replace('\n', '')
        # 供应商
        jvn['vendor'] = rows[15].text.replace('\n', '')
        # CWE
        jvn['cwe'] = rows[17].text.replace('\n', '')
        # CVE
        jvn['cve'] = rows[19].text.replace('\n', '')
        # 相关链接
        jvn['refer'] = rows[21].text.replace('\n', '')

        footer = soup.find('table', class_='vuln_table_clase_footer')
        rows2 = footer.find_all('tr')
        # 发布日期
        jvn['published_date'] = rows2[1].find_all('td')[1].text
        # 注册日期
        jvn['register_date'] = rows2[2].find_all('td')[1].text
        # 上次更新
        jvn['updated_date'] = rows2[3].find_all('td')[1].text
        # time.sleep(1)
        return jvn

    def get_proxy(self):
        return requests.get("http://127.0.0.1:5010/get/").json()