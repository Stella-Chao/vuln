from message import send
import datetime
from sql import connect
from cve import hardware_num, get_cpe_by_cveId, iot_num
from qly_spider import spider
from exploit_spider import exp_spider

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