import pymysql
import logging


def connect():
    connect = pymysql.connect(
                    host = '127.0.0.1',
                    db = 'leak',
                    user = 'root',
                    password = '123456',
                    port = 3306
                )
    cursor = connect.cursor()
    logging.info('数据库连接成功!')
    return cursor
