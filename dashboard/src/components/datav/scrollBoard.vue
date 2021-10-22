<template>
  <div id="scroll-board">
    <dv-scroll-board :config="config" />
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
        header: ['CVE-ID', '危险级别', '攻击类型', 'POC'],
        data: [],
        index: true,
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
          let result = res.data["近一月新增数量"]
          console.log(typeof result)
          let leakresult = []
          for (let i in result) {
            let arrlist = []
            let security = ""
            let cve_id = result[i]["cveID"]
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
            let poc = result[i]["poc"]
            console.log(poc)
            if (poc == "") {
              poc = "暂无POC"
            }
            arrlist.push(cve_id)
            arrlist.push(security)
            arrlist.push(type)
            arrlist.push(poc)
            leakresult.push(arrlist)
          }
          // res.data.map(item =>{
          //   let arrlist = [item.CVE-ID,item.name,item.refer,item.url]
          //   leakresult.push(arrlist)
          // })
          // 注意此处赋值方式，this.config.data = leakresult 无效
          this.config = {
            header: ['CVE-ID', '危险级别', '攻击类型', 'POC'],
            data: leakresult,
            index: true,
            columnWidth: [70,150,200,350,400],
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
