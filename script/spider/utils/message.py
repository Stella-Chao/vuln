import smtplib
from email.mime.text import MIMEText

def send(receivers, news):
    # 发送方邮箱
    sender = 'tfsecurity@163.com'
    # 发送方授权码
    sender_pass = 'DVUWTHMLEHSUTWPQ'
    # email 内容设置
    message = MIMEText('content', 'plain', 'utf-8')
    # 邮件主题
    message['Subject'] = '威胁情报速递'
    message['From'] = '鲲鹏·天眼'

    '''当日新增漏洞信息'''
    message['news'] = news
    message['情报内容'] = 'test'

    # 接收方信息
    message['To'] = '愿天下无漏洞!'

    #登录并发送邮件
    try:
        smtpObj = smtplib.SMTP()
        # 连接到服务器
        smtpObj.connect(sender, 25)
        # 登录到服务器
        smtpObj.login(sender, sender_pass)
        # 发送
        smtpObj.sendmail(
            sender, receivers, message.as_string())
        # 退出
        smtpObj.quit()
        print('success')
    except smtplib.SMTPException as e:
        print('error', e)


if __name__ == '__main__':
    receiver = ['zhanghe0309@qq.com']
    news = "test"
    send(receiver, news)