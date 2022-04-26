import re

from utils.message import send
import datetime
from utils.sql import connect
from spider.cve import hardware_num, get_cpe_by_cveId, iot_num
from spider.qly_spider import spider
from spider.exploit_spider import exp_spider
from utils.mongoUtils import get_train_data

def test_message():
    '''情报订阅用户列表从数据库中获取'''
    receivers = []
    sql = 'select email from sub_user'
    cursor = connect()
    cursor.execute(sql)
    data = cursor.fetchall()
    for item in data:
        receivers.append(item[0])
    print(receivers, 'hello world!')
    send(receivers)

def test_cve1():
    hardware_num()

def test_cve2():
    get_cpe_by_cveId('CVE-2020-20211')

def test_cve3():
    iot_num()

def test_qly_spider():
    spider()

def test_time():
    today = datetime.date.today()
    oneday = datetime.timedelta(days=1)
    yesterday = today - oneday
    print(str(yesterday))

def test_exp_spider():
    exp_spider()

def test_get_train_data():
    get_train_data()
    re.sub()