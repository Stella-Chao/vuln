<template>
  <div class="hot-search">
    <a-row style="margin: 0 -34px">
      <a-col style="padding: 0 34px; margin-bottom: 24px" :sm="12" :xs="24">
        <div class="num-info">
          <span class="title">
            IoT漏洞总数
            <a-tooltip title="IoT漏洞总数">
            </a-tooltip>
          </span>
          <div class="value">
            <span class="total">{{ total }}</span>
          </div>
        </div>
        <mini-area style="height: 45px" />
      </a-col>
      <a-col style="padding: 0 34px; margin-bottom: 24px" :sm="12" :xs="24">
        <div class="num-info">
          <span class="title">
            PoC总数
            <a-tooltip title="PoC总数">
            </a-tooltip>
          </span>
          <div class="value">
            <span class="total">{{ poc }}</span>
          </div>
        </div>
        <mini-area style="height: 45px" />
      </a-col>
    </a-row>
    <a-table
      :dataSource="searchData"
      :columns="tableColumns"
      :pagination="{style: { marginBottom: 0 }, pageSize: 5}"
      size="small"
      rowKey="index"
    >
      <a href="#/" slot="keyword" slot-scope="text">{{text}}</a>
      <span slot="rang" slot-scope="text">{{text}} %<a-icon type="caret-up" /> </span>
    </a-table>
  </div>
</template>

<script>
import MiniArea from '../../../components/chart/MiniArea'

import axios from 'axios'
const base_url = process.env.VUE_APP_API_BASE_URL

const searchData = []
// for (let i = 0; i < 5; i++) {
//   searchData.push({
//     index: i + 1,
//     keyword: '关键词-' + i,
//     count: Math.floor(Math.random() * 1000)
//   })
// }

const columns = [
  {
    dataIndex: 'index',
    key: '编号'
  },
  {
    dataIndex: 'keyword',
    key: "漏洞类型"
  },
  {
    dataIndex: 'count',
    key: "数量"
  }
]

export default {
  name: 'HotSearch',
  components: {MiniArea},
  i18n: require('./i18n-search'),
  data () {
    return {
      searchData,
      columns,
      total: "",
      poc:""
    }
  },
  computed: {
    tableColumns() {
      let columns = this.columns
      return columns.map(item => {
       item.title = this.$t(item.key)
        return item
      })
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      axios.get(base_url + '/tf/get/count').then(res=>{
        console.log('漏洞总数量：',res.data)
        this.total = res.data
      })
      axios.get(base_url + '/poc/get/pocNum').then(res=>{
        console.log('Poc 总数量：',res.data)
        this.poc = res.data
      })
      axios.get(base_url + '/dashboard/data02')
      .then(res=>{
        console.log(res.data)
        this.result = JSON.parse(res.data['CVE类别'])
        console.log(this.result)
        let idx = 1
        for (let key in this.result) {
          searchData.push({
          index: idx ++,
          keyword: key,
          count: this.result[key]
        })
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
  .num-info{
    .title{
      color: @text-color-second;
      font-size: 14px;
      height: 22px;
      line-height: 22px;
      overflow: hidden;
      text-overflow: ellipsis;
      word-break: break-all;
      white-space: nowrap;
    }
    .value{
      .total{
        color: @title-color;
        display: inline-block;
        line-height: 32px;
        height: 32px;
        font-size: 24px;
        margin-right: 32px;
      }
      .subtotal{
        color: @text-color-second;
        font-size: 16px;
        vertical-align: top;
        margin-right: 0;
        i{
          font-size: 12px;
          color: red;
          transform: scale(.82);
          margin-left: 4px;
        }
      }
    }
  }
</style>
