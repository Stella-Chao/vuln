<template>
  <div id="scroll-board">
    <dv-scroll-board :config="config"/>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'ScrollBoard',
  data () {
    return {
      base_url: 'http://127.0.0.1:9090/dashboard/data03',
      config: {
        header: ['CVE-ID', '漏洞名称', '危险级别', '攻击类型', '发布日期'],
        data: [],
        index: false,
        columnWidth: [50],
        align: ['center'],
        rowNum: 7,
        headerBGC: '#1981f6',
        headerHeight: 45,
        waitTime: 1000,
        oddRowBGC: 'rgba(0, 44, 81, 0.8)',
        evenRowBGC: 'rgba(10, 29, 50, 0.8)'
      }
    }
  },
  methods: {
    getLeakList() {
      axios.get(this.base_url)
        .then(res=>{
          console.log(res.data)
          let result = res.data["近一年新增数量"]
          console.log(typeof result)
          let leakresult = []
          for (let i in result) {
            let arrlist = []
            let security = ""
            let cve_id = result[i]["cveID"]
            let title = result[i]["title"]
            if (result[i]["cvssV2"] != undefined) {
              security = result[i]["cvssV2"]["severity"]
              if (security == "HIGH") {
                security = "高危"
              } else if (security == "MEDIUM") {
                security = "中危"
              } else {
                security = "低危"
              }
            } else {
              security = "未知"
            }
            let type = result[i]["type01"][0]
            if (type == "Denial Of Service") {
              type = "拒绝服务"
            } else if (type == "Execute Code") {
              type = "执行代码"
            } else if (type == "Overflow") {
              type = "溢出"
            } else if (type == "Cross Site Scripting") {
              type = "跨站脚本"
            } else if (type == "Directory traversal") {
              type = "目录遍历"
            } else if (type == "Bypass a restriction or similar") {
              type = "绕过"
            } else if (type == "Obtain Information") {
              type = "获取信息"
            } else if (type == "Gain privileges") {
              type = "获取权限"
            } else if (type == "Sql Injection") {
              type = "SQL注入"
            } else if (type == "File Inclusion") {
              type = "文件包含"
            } else if (type == "Memory corruption") {
              type = "内存错误"
            } else if (type == "CSRF") {
              type = "跨站请求伪造"
            } else if (type == "Http response splitting") {
              type = "HTTP响应拆分"
            } else if (type == "None") {
              type = "未知"
            }
            if (title == "请查看详情") {
              continue
            }
            let published = result[i]["publishedDate"]
            arrlist.push(cve_id)
            arrlist.push(title)
            arrlist.push(security)
            arrlist.push(type)
            arrlist.push(published)
            leakresult.push(arrlist)
          }
          // res.data.map(item =>{
          //   let arrlist = [item.CVE-ID,item.name,item.refer,item.url]
          //   leakresult.push(arrlist)
          // })
          // 注意此处赋值方式，this.config.data = leakresult 无效
          this.config = {
            header: ['CVE-ID','漏洞名称', '危险级别', '攻击类型', '发布日期'],
            data: leakresult,
            index: false,
            columnWidth: [200,500], //100,100,200,100,100
            align: ['center'],
            rowNum: 7,
            headerBGC: '#1981f6',
            headerHeight: 45,
            waitTime: 1000,
            oddRowBGC: 'rgba(0, 44, 81, 0.8)',
            evenRowBGC: 'rgba(10, 29, 50, 0.8)'
          }
        });
    }
  },
  mounted () {
    this.getLeakList()
  }
}
</script>

<style lang="less">
#scroll-board {
  width: 55%;
  box-sizing: border-box;
  margin-left: 20px;
  height: 100%;
  width: 100%;
  padding-right: 20px;
  overflow: hidden;
}
</style>
