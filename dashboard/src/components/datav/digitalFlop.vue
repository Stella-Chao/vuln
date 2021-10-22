<template>
  <div id="digital-flop">
    <div
      class="digital-flop-item"
      v-for="item in digitalFlopData"
      :key="item.title"
    >
      <div class="digital-flop-title">{{ item.title }}</div>
      <div class="digital-flop">
        <dv-digital-flop
          :config="item.number"
          style="width:100px;height:50px;"
        />
          <div class="unit">{{ item.unit }}</div>
      </div>
    </div>

    <dv-decoration-10 />
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'DigitalFlop',
  data () {
    return {
      digitalFlopData: [],
      result: []
    }
  },
  methods: {
    createData () {
      const { randomExtend } = this
      axios.get('http://127.0.0.1:9090/dashboard/data01')
        .then(res=>{
          this.result = res.data
          this.digitalFlopData = [
            {
              title: '漏洞总数',
              number: {
                number: [this.result['漏洞总数量']],
                content: '{nt}',
                textAlign: 'right',
                style: {
                  fill: '#4d99fc',
                  fontWeight: 'bold'
                }
              },
              unit: '个'
            },
            {
              title: '高危',
              number: {
                number: [this.result['高危漏洞']],
                content: '{nt}',
                textAlign: 'right',
                style: {
                  fill: '#f46827',
                  fontWeight: 'bold'
                }
              },
              unit: '个'
            },
            {
              title: '中危',
              number: {
                number: [this.result['中危漏洞']],
                content: '{nt}',
                textAlign: 'right',
                style: {
                  fill: '#40faee',
                  fontWeight: 'bold'
                }
              },
              unit: '个'
            },
            {
              title: '低危',
              number: {
                number: [this.result['低危漏洞']],
                content: '{nt}',
                textAlign: 'right',
                style: {
                  fill: '#4d99fc',
                  fontWeight: 'bold'
                }
              },
              unit: '个'
              },
            {
              title: 'POC',
              number: {
                number: [this.result['POC数量']],
                content: '{nt}',
                textAlign: 'right',
                style: {
                  fill: '#f46827',
                  fontWeight: 'bold'
                }
              },
              unit: '个'
            },
            {
              title: '当日新增',
              number: {
                number: [this.result['当日新增']],
                content: '{nt}',
                textAlign: 'right',
                style: {
                  fill: '#40faee',
                  fontWeight: 'bold'
                }
              },
              unit: '个'
            },
            {
              title: '近一周',
              number: {
                number: [this.result['近一周新增数量']],
                content: '{nt}',
                textAlign: 'right',
                style: {
                  fill: '#4d99fc',
                  fontWeight: 'bold'
                }
              },
              unit: '个'
              },
            {
              title: '近一月',
              number: {
                number: [this.result['近一月新增数量']],
                content: '{nt}',
                textAlign: 'right',
                style: {
                  fill: '#f46827',
                  fontWeight: 'bold'
                }
              },
              unit: '个'
              },
            {
              title: '近一年',
              number: {
                number: [this.result['近一年新增数量']],
                content: '{nt}',
                textAlign: 'right',
                style: {
                  fill: '#40faee',
                  fontWeight: 'bold'
                }
              },
              unit: '个'
            }
        ]
        })
      
    },
    randomExtend (minNum, maxNum) {
      if (arguments.length === 1) {
        return parseInt(Math.random() * minNum + 1, 10)
      } else {
        return parseInt(Math.random() * (maxNum - minNum + 1) + minNum, 10)
      }
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
#digital-flop {
  position: relative;
  height: 15%;
  flex-shrink: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: rgba(6, 30, 93, 0.5);

  .dv-decoration-10 {
    position: absolute;
    width: 95%;
    left: 2.5%;
    height: 5px;
    bottom: 0px;
  }

  .digital-flop-item {
    width: 11%;
    height: 80%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border-left: 3px solid rgb(6, 30, 93);
    border-right: 3px solid rgb(6, 30, 93);
  }

  .digital-flop-title {
    font-size: 20px;
    margin-bottom: 20px;
  }

  .digital-flop {
    display: flex;
  }

  .unit {
    margin-left: 10px;
    display: flex;
    align-items: flex-end;
    box-sizing: border-box;
    padding-bottom: 13px;
  }
}
</style>
