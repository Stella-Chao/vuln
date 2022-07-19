
BOT_NAME = 'leak_spider'

SPIDER_MODULES = ['leak_spider.spiders']
NEWSPIDER_MODULE = 'leak_spider.spiders'

SCHEDULER='scrapy_redis.scheduler.Scheduler'
DUPEFILTER_CLASS='scrapy_redis.dupefilter.RFPDupeFilter'
SCHEDULER_PERSIST=True

REDIS_HOST = '123.57.77.254'
REDIS_PORT = 6379
REDIS_ENCODING = 'utf-8'
REDIS_PARAMS = {
    'password': 'zhanghe',
}

# MYSQL
DB_SETTINGS = {
    'db1': {
        'host': 'localhost',
        'db': 'leak',
        'user': 'root',
        'password': '123456',
        'port': 3306
    },
}

MONGO_URI = "127.0.0.1:27017"  # 主机IP
MONGO_PORT = 27017  # 端口号
MONGO_DATABASE = "vuln"  # 库名
MONGO_COLL = "nvd"  # collection名
# MONGO_USER = "admin" #用户名
# MONGO_PSW = "zhanghe" #用户密码

# Obey robots.txt rules
ROBOTSTXT_OBEY = True


DEFAULT_REQUEST_HEADERS = {
  'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
  'Accept-Language': 'en',
}


ITEM_PIPELINES = {
    'leak_spider.pipelines.LeakSpiderPipeline': 300,
    'scrapy_redis.pipelines.RedisPipeline': 300
}

# SCHEDULER_FLUSH_ON_START = True

ROBOTSTXT_OBEY = False
