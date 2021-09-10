import time

def get_time():
    # 格式化成2021-09-10 11:45:39形式
    cur_time = time.strftime("%Y-%m-%d", time.localtime())
    print(cur_time)

if __name__ == '__main__':
    get_time()