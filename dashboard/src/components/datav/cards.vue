<template>
  <div id="cards">
    <div
      class="card-item"
      v-for="(card, i) in cards"
      :key="card.title"
    >
      <div class="card-header">
        <div class="card-header-left">{{ card.title }}</div>
        <div class="card-header-right">{{ '0' + (i + 1) }}</div>
      </div>
      <dv-charts class="ring-charts" :option="card.ring" />
      <div class="card-footer">
        <div class="card-footer-item">
          <div class="footer-title">累计漏洞</div>
          <div class="footer-detail">
            <dv-digital-flop :config="card.total" style="width:70%;height:35px;" />个
          </div>
        </div>
        <div class="card-footer-item">
          <div class="footer-title">高危漏洞</div>
          <div class="footer-detail">
            <dv-digital-flop :config="card.num" style="width:70%;height:35px;" />个
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Cards',
  data () {
    return {
      cards: [],
      result: []
    }
  },
  methods: {
    createData () {
      const { randomExtend } = this
      axios.get('http://127.0.0.1:9090/dashboard/data04')
        .then(res=>{
          this.result = res.data
        })
      this.cards = []
      for (let i in this.result) {
        this.cards.push({
          title: this.result[i]['type'],
          total: {
            number: [this.result[i]['总数']],
            content: '{nt}',
            textAlign: 'right',
            style: {
              fill: '#ea6027',
              fontWeight: 'bold'
            }
          },
          num: {
            number: [this.result[i]['高危']],
            content: '{nt}',
            textAlign: 'right',
            style: {
              fill: '#26fcd8',
              fontWeight: 'bold'
            }
          },
          ring: {
            series: [
              {
                type: 'gauge',
                startAngle: -Math.PI / 2,
                endAngle: Math.PI * 1.5,
                arcLineWidth: 13,
                radius: '80%',
                data: [
                  { name: '资金占比', value: randomExtend(40, 60) }
                ],
                axisLabel: {
                  show: false
                },
                axisTick: {
                  show: false
                },
                pointer: {
                  show: false
                },
                backgroundArc: {
                  style: {
                    stroke: '#224590'
                  }
                },
                details: {
                  show: true,
                  formatter: '高危占比{value}%',
                  style: {
                    fill: '#1ed3e5',
                    fontSize: 20
                  }
                }
              }
            ],
            color: ['#03d3ec']
        }
      })
      }
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

    setInterval(this.createData, 3000)
  }
}
</script>

<style lang="less">
#cards {
  display: flex;
  justify-content: space-between;
  height: 45%;

  .card-item {
    background-color: rgba(6, 30, 93, 0.5);
    border-top: 2px solid rgba(1, 153, 209, .5);
    width: 19%;
    display: flex;
    flex-direction: column;
  }

  .card-header {
    display: flex;
    height: 20%;
    align-items: center;
    justify-content: space-between;

    .card-header-left {
      font-size: 18px;
      font-weight: bold;
      padding-left: 20px;
    }

    .card-header-right {
      padding-right: 20px;
      font-size: 40px;
      color: #03d3ec;
    }
  }

  .ring-charts {
    height: 55%;
  }

  .card-footer {
    height: 25%;
    display: flex;
    align-items: center;
    justify-content: space-around;
  }

  .card-footer-item {
    padding: 5px 10px 0px 10px;
    box-sizing: border-box;
    width: 40%;
    background-color: rgba(6, 30, 93, 0.7);
    border-radius: 3px;

    .footer-title {
      font-size: 15px;
      margin-bottom: 5px;
    }

    .footer-detail {
      font-size: 20px;
      color: #1294fb;
      display: flex;
      font-size: 18px;
      align-items: center;

      .dv-digital-flop {
        margin-right: 5px;
      }
    }
  }
}
</style>
