<template>
  <div class="bar">
    <h4>{{title}}</h4>
    <div class="chart">
      <v-chart :force-fit="true" height="312" :data="data" :padding="[24, 0, 0, 0]">
        <v-tooltip />
        <v-axis />
        <v-bar position="x*y"/>
      </v-chart>
    </div>
  </div>
</template>

<script>

const data = []
import axios from 'axios'
const base_url = process.env.VUE_APP_API_BASE_URL


const tooltip = [
  'x*y',
  (x, y) => ({
    name: x,
    value: y
  })
]

const scale = [{
  dataKey: 'x',
  min: 2
}, {
  dataKey: 'y',
  title: '时间',
  min: 1,
  max: 22
}]
export default {
  name: 'Bar',
  props: ['title'],
  data () {
    return {
      data,
      scale,
      tooltip
    }
  },
  created(){
    this.getRecent();
  },
  methods: {
    getRecent() {
      axios.get(base_url + '/tf/get/month').then(res=>{
        var newData = Object.keys(res.data).sort();
        console.log('12月：',newData)
        for (let key in newData) {
          let time = newData[key]
          console.log(res.data[time])
          this.data.push({
            x: `${time}`,
            y: res.data[time]
          })
        }
      })
    }
  }
}
</script>

<style scoped lang="less">
  .bar{
    position: relative;
    .chart{
    }
  }
</style>
