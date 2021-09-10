from message import send
from sql import connect
if __name__ == '__main__':

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