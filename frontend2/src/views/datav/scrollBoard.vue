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
      base_url: 'http://127.0.0.1:8000/api/data/',
      config: {
        header: ['cwe_id', 'name', 'refer', 'url'],
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
          // console.log(res.data)
          let leakresult = []
          res.data.map(item =>{
            let arrlist = [item.cwe_id,item.name,item.refer,item.url]
            leakresult.push(arrlist)
          })
          // 注意此处赋值方式，this.config.data = leakresult 无效
          this.config = {
            header: ['cwe_id', 'name', 'refer', 'url'],
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
