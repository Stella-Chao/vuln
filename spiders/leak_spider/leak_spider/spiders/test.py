import json
import requests
"""
NVD增量爬取方案：
1.爬取历史所有数据
2.https://nvd.nist.gov/vuln/full-listing/年/月
3.获取 CVE-ID
4.避免重爬: Redis自动去重(内存中数据保存多久?) / 查询MySQL
5.请求 https://services.nvd.nist.gov/rest/json/cve/1.0/CVE-ID  (每隔几秒请求一次,防止被墙)
  Response 格式如下：
  
  {
    "resultsPerPage":1,
    "startIndex":0,
    "totalResults":1,
    "result":{
        "CVE_data_type":"CVE",
        "CVE_data_format":"MITRE",
        "CVE_data_version":"4.0",
        "CVE_data_timestamp":"2021-09-14T09:09Z",
        "CVE_Items":[
            {
                "cve":{
                    "data_type":"CVE",
                    "data_format":"MITRE",
                    "data_version":"4.0",
                    "CVE_data_meta":{
                        "ID":"CVE-2015-5611",
                        "ASSIGNER":"cve@mitre.org"
                    },
                    "problemtype":{
                        "problemtype_data":[
                            {
                                "description":[
                                    {
                                        "lang":"en",
                                        "value":"NVD-CWE-noinfo"
                                    }
                                ]
                            }
                        ]
                    },
                    "references":{
                        "reference_data":[
                            {
                                "url":"http://www.wired.com/2015/07/hackers-remotely-kill-jeep-highway/",
                                "name":"http://www.wired.com/2015/07/hackers-remotely-kill-jeep-highway/",
                                "refsource":"MISC",
                                "tags":[

                                ]
                            },
                            ......
                        ]
                    },
                    "description":{
                        "description_data":[
                            {
                                "lang":"en",
                                "value":"Unspecified vulnerability in Uconnect before 15.26.1, as used in certain Fiat Chrysler Automobiles (FCA) from 2013 to 2015 models, allows remote attackers in the same cellular network to control vehicle movement, cause human harm or physical damage, or modify dashboard settings via vectors related to modification of entertainment-system firmware and access of the CAN bus due to insufficient \"Radio security protection,\" as demonstrated on a 2014 Jeep Cherokee Limited FWD."
                            }
                        ]
                    }
                },
                "configurations":{
                    "CVE_data_version":"4.0",
                    "nodes":[
                        {
                            "operator":"OR",
                            "children":[

                            ],
                            "cpe_match":[
                                {
                                    "vulnerable":true,
                                    "cpe23Uri":"cpe:2.3:a:fca:uconnect:*:*:*:*:*:*:*:*",
                                    "versionEndIncluding":"15.26.1",
                                    "cpe_name":[

                                    ]
                                }
                            ]
                        }
                    ]
                },
                "impact":{
                    "baseMetricV2":{
                        "cvssV2":{
                            "version":"2.0",
                            "vectorString":"AV:A/AC:L/Au:N/C:C/I:C/A:C",
                            "accessVector":"ADJACENT_NETWORK",
                            "accessComplexity":"LOW",
                            "authentication":"NONE",
                            "confidentialityImpact":"COMPLETE",
                            "integrityImpact":"COMPLETE",
                            "availabilityImpact":"COMPLETE",
                            "baseScore":8.3
                        },
                        "severity":"HIGH",
                        "exploitabilityScore":6.5,
                        "impactScore":10,
                        "obtainAllPrivilege":false,
                        "obtainUserPrivilege":false,
                        "obtainOtherPrivilege":false,
                        "userInteractionRequired":false
                    }
                },
                "publishedDate":"2015-07-21T21:05Z",
                "lastModifiedDate":"2016-12-24T02:59Z"
            }
        ]
    }
  }
"""

response = requests.get('https://services.nvd.nist.gov/rest/json/cve/1.0/CVE-2015-5611')
vuln = response.json()
print(type(vuln))
cve_items = vuln['result']['CVE_Items'][0]
print(type(cve_items))
# cwe = cve_items['cve']['problemtype']['problemtype_data']['description']['value']
# description = cve_items['cve']['description']['description_data']['value']
# refer = cve_items['references']['reference_data']
# publishedDate = cve_items['publishedDate']
# lastModifiedDate = cve_items['lastModifiedDate']

import pymongo
client = pymongo.MongoClient(host='localhost', port=27017)
collection = client.vuln.nvd
result = collection.insert_one(cve_items)
print(result)