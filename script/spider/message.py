import datetime
import smtplib
from email.message import EmailMessage
from mongoUtils import connect_iot2, connect_subscribe
import sys, codecs
sys.stdout = codecs.getwriter("utf-8")(sys.stdout.detach())


def send(receivers, news):
    smtpserver = 'smtp.163.com'
    # 发送方邮箱
    user = 'tfsecurity@163.com'
    # 发送方授权码
    password = 'DVUWTHMLEHSUTWPQ'
    # email 内容设置
    em = EmailMessage()
    # 邮件主题
    em['Subject'] = "情报速递"

    em['From'] = '鲲鹏·天眼'

    '''当日新增漏洞信息'''
    em.set_content(news)

    # 接收方信息
    em['To'] = '订阅用户'
    print(em)

    #登录并发送邮件
    try:
        smtpObj = smtplib.SMTP()
        # 连接到服务器
        smtpObj.connect(smtpserver)
        # 登录到服务器
        smtpObj.login(user, password)
        # 发送
        smtpObj.sendmail(user, receivers, em.as_string())
        # 退出
        smtpObj.quit()
        print('success')
    except smtplib.SMTPException as e:
        print('error', e)

def get_users():
    collection = connect_subscribe()
    list = []
    for user in collection.find():
        if len(user["email"]) > 0:
            list.append(user["email"])
    return list

def get_news():
    collection = connect_iot2()
    list = []
    today = datetime.date.today()
    print(today)
    for vuln in collection.find():
        if vuln["publishedDate"][:10] == today:
            list.append(vuln)
    return list

if __name__ == '__main__':
    receivers = get_users()
    for receiver in receivers:
        print(receiver)
    news = get_news()
    # send(get_users(), get_news())