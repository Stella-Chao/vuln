<template>
  <div style="">
    <v-chart :forceFit="true" :height="height" :data="data" :scale="scale">
      <v-tooltip :showTitle="false" dataKey="item*percent" />
      <v-axis />
      <v-legend dataKey="item" position="right" :offsetX="-140"/>
      <v-pie position="percent" color="item" :vStyle="pieStyle" :label="labelConfig" />
      <v-coord type="theta" :radius="0.75" :innerRadius="0.6" />
    </v-chart>
  </div>
</template>

<script>
import axios from 'axios'
const DataSet = require('@antv/data-set')
const base_url = process.env.VUE_APP_API_BASE_URL
const sourceData = [
  { item: '超危', count: 10 },
  { item: '高危', count: 21 },
  { item: '中危', count: 17 },
  { item: '低危', count: 13 }
]

const scale = [{
  dataKey: 'percent',
  min: 0,
  formatter: '.0%'
}]




export default {
  name: 'SalesData',
  data () {
    return {
      data: "",
      scale,
      height: 385,
      pieStyle: {
        stroke: '#fff',
        lineWidth: 1
      },
      labelConfig: ['percent', {
        formatter: (val, item) => {
          return item.point.item + ': ' + val
        }
      }]
    }
  },
  methods: {
    getSeverityDate() {
      axios.get(base_url + '/tf/severity').then(res=>{
        console.log(res.data)
        sourceData[0]["count"] = res.data["超危"]
        sourceData[1]["count"] = res.data["高危"]
        sourceData[2]["count"] = res.data["中危"]
        sourceData[3]["count"] = res.data["低危"]
        console.log(sourceData)
        const dv = new DataSet.View().source(sourceData)
        dv.transform({
          type: 'percent',
          field: 'count',
          dimension: 'item',
          as: 'percent'
        })
        this.data = dv.rows
      })
    }
  },
  created() {
    this.getSeverityDate()
  }
}
</script>

<style scoped>

</style>
