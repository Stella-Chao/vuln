<template>
  <div id="ranking-board">
    <div class="ranking-board-title">漏洞上报记录数量</div>
    <dv-scroll-ranking-board :config="config" />
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'RankingBoard',
  data () {
    return {
      config: {},
      result: {}
    }
  },
  methods: {
    createData() {
      axios.get('http://api.eye.tf.lab/eye/dashboard/data02')
        .then(res=>{
          console.log(res.data)
          this.result = JSON.parse(res.data['CVE类别'])
          console.log(typeof this.result)
          this.config = {
            data: [
              {
                name: 'SQL注入',
                value: this.result['SQL注入']
              },
              {
                name: '执行代码',
                value: this.result['Execute Code']
              },
              {
                name: '目录遍历',
                value: this.result['目录遍历']
              },
              {
                name: '获取信息',
                value: this.result['获取信息']
              },
              {
                name: '获取权限',
                value: this.result['获取权限']
              },
              {
                name: '溢出',
                value: this.result['Overflow']
              },
              {
                name: '跨站脚本',
                value: this.result['XSS']
              },
              {
                name: '绕过',
                value: this.result['绕过']
              },
              {
                name: '拒绝服务',
                value: this.result['DoS']
              }
            ],
            rowNum: 9
          }
        })
    }
  },
  mounted () {
    const { createData } = this

    createData()

    setInterval(createData, 60000)
  }
}
</script>

<style lang="less">
#ranking-board {
  width: 20%;
  box-shadow: 0 0 3px blue;
  display: flex;
  flex-direction: column;
  background-color: rgba(6, 30, 93, 0.5);
  border-top: 2px solid rgba(1, 153, 209, .5);
  box-sizing: border-box;
  padding: 0px 30px;

  .ranking-board-title {
    font-weight: bold;
    height: 50px;
    display: flex;
    align-items: center;
    font-size: 20px;
  }

  .dv-scroll-ranking-board {
    flex: 1;
  }
}
</style>
