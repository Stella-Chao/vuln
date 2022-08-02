# -*- coding: utf-8 -*-

import requests
import random
import json
from hashlib import md5

# 每月可免费翻译200w字符
appid = '20200202000379572'
appkey = 'pjNWyL2apr4DOb1qeaPh'

endpoint = 'http://api.fanyi.baidu.com'
path = '/api/trans/vip/translate'
url = endpoint + path

# query = '发现部分科达设备存在0Day漏洞，可以绕过认证直接获取系统登录账号信息，建议尽快联系厂商更新设备软件。'

# Generate salt and sign
def make_md5(s, encoding='utf-8'):
    return md5(s.encode(encoding)).hexdigest()

# 翻译模块 参数 (文本，源语言，目标语言)
def translate(query, from_lang, to_lang):
    salt = random.randint(32768, 65536)
    sign = make_md5(appid + query + str(salt) + appkey)

    # Build request
    headers = {'Content-Type': 'application/x-www-form-urlencoded'}
    payload = {'appid': appid, 'q': query, 'from': from_lang, 'to': to_lang, 'salt': salt, 'sign': sign}

    # Send request
    r = requests.post(url, params=payload, headers=headers)
    result = r.json()
    # Show response
    print(json.dumps(result, indent=4, ensure_ascii=False))
    return result['trans_result'][0]['dst']
