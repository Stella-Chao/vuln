<template>
  <a-card>
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <div :class="advanced ? null: 'fold'">
          <a-row v-if="advanced">
          <a-col :md="16" :sm="24" >
            <a-form-item
              label="厂商"
              :labelCol="{span: 5}"
              :wrapperCol="{span: 18, offset: 1}"
            >
              <a-input v-model="brand" placeholder="请输入设备厂商"/>
            </a-form-item>
          </a-col>
        </a-row>
          <a-row v-if="advanced">
          <a-col :md="16" :sm="24" >
            <a-form-item
              label="设备名"
              :labelCol="{span: 5}"
              :wrapperCol="{span: 18, offset: 1}"
            >
              <a-input v-model="product" placeholder="请输入设备型号"/>
            </a-form-item>
          </a-col>
        </a-row>
        </div>
        <span style="float: right; margin-top: 5px;">
          <a-button type="primary" @click="search">查询</a-button>
          <a-button style="margin-left: 8px" @click="reset">重置</a-button>
        </span>
      </a-form>
    </div>
    <div>
      <standard-table
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pagination"
      >
        <div slot="description" slot-scope="{text}">
          {{text}}
        </div>
        <div slot="action" slot-scope="{record}">
          
          <router-link :to="{name:'设备信息编辑',query:{device:record}}" >
            <a style="margin-right: 8px">
              <a-icon type="edit"/>编辑
            </a>
          </router-link>
          <!-- <a @click="deleteRecord(record.product)" v-auth="`delete`"> -->
          <a @click="deleteRecord(record.product)">
            <a-icon type="delete" />删除
          </a>
          <!-- <router-link :to="`/list/query/detail/${record.key}`" >详情</router-link> -->
          <!-- <router-link :to="{name:'基础详情页',query:{id:record.cveID}}" >详情</router-link> -->
        </div>
        <template slot="statusTitle">
          <a-icon @click.native="onStatusTitleClick" type="info-circle" />
        </template>
      </standard-table>
    </div>
  </a-card>
</template>

<script>
import StandardTable from '@/components/table/StandardTable'
// import getDate from 'date-fns/getDate/index.js'
// import {dataSource as ds} from '@/services'
import axios from 'axios'
const base_url = process.env.VUE_APP_API_BASE_URL
const columns = [
  {
    title: '厂商',
    dataIndex: 'brand'
  },
  {
    title: '设备',
    dataIndex: 'product',
    // width: 500
  },
    {
    title: '设备类型',
    dataIndex: 'type1',
  },
  {
    title: '别名',
    dataIndex: 'brandAlias',
    // scopedSlots: { customRender: 'description' }
  },
  {
    title: '是否已确认',
    dataIndex: 'verify',
    needTotal: true,
    // slots: {title: 'statusTitle'},
    // scopedSlots: { customRender: 'tags' }
  },
  {
    title: '操作',
    scopedSlots: { customRender: 'action' }
  }
]

const dataSource = []


export default {
  name: 'DeviceList',
  components: {StandardTable},
  data () {
    return {
      advanced: true,
      columns: columns,
      dataSource: dataSource,
      product: "",
      brand: "",
      url:"",
      pagination:{
        defaultPageSize:20,
        showTotal: total => `共${total}条记录`,
        showSizeChanger:true,
        pageSizeOptions: ['10', '20', '50', '100'],
        onShowSizeChange:(current, pageSize)=>this.pageSize = pageSize,
        onChange: (current, pageSize) => {
          console.log('当前页: ', current)
          console.log('页面大小：', pageSize)
          this.getPageDate(pageSize, current)
        },
        total:0 //总条数
      },
    }
  },
  // authorize: {
  //   deleteRecord: 'delete'
  // },
  created() {
    console.log(base_url)
    this.getData()
    
  },

  methods: {
    getData() {
        axios.get(base_url + '/device/get/count').then(res=>{
          console.log('设备数量：',res.data)
          this.pagination.total = res.data
        })
        this.url = base_url + '/device/list/'
        axios.get(this.url + this.pagination.defaultPageSize +'/1').then(res => {
          console.log(res.data)
          this.dataSource = res.data
        });
    },
    getPageDate(pageSize, current) {
        // 分页时判断是哪种情况下的查询结果
        let request = ""
        if (this.url.search("search") != -1) {
          request = this.url + '&size='+pageSize+'&page='+current 
          axios.get(request).then(res => {
            console.log(res.data)
            this.dataSource = res.data.result
          });
        } else {
          request = this.url + pageSize +'/' + current
          axios.get(request).then(res => {
            console.log(res.data)
            this.dataSource = res.data
          });
        }

    },
    search() {
        this.url = base_url +'/device/search?brand=' + this.brand + '&product=' + this.product
        axios.get(this.url + '&size='+this.pagination.defaultPageSize+'&page=1').then(res=>{
          console.log('查询结果...')
          console.log(res.data.result)
          console.log(this.pagination)
          this.pagination.current = this.pagination.defaultCurrent
          this.dataSource = res.data.result
          this.pagination.total = res.data.total
          
      })
    },
    reset() {
      this.brand = ""
      this.product = ""
      this.getData()
    },
    deleteRecord(key) {
      console.log(key)
      let param = {}
      param["product"] = key
      axios.post(base_url + '/device/delete', param).then(res => {
        console.log(res.data);
        this.getData()
        this.$message.success("删除成功！", 2);
        this.created()
      })
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    remove () {
      this.dataSource = this.dataSource.filter(item => this.selectedRows.findIndex(row => row.key === item.key) === -1)
      this.selectedRows = []
    },
    handleMenuClick (e) {
      if (e.key === 'delete') {
        this.remove()
      }
    }
  }
}
</script>

<style lang="less" scoped>
  .search{
    margin-bottom: 54px;
  }
  .fold{
    width: calc(100% - 216px);
    display: inline-block
  }
  .operator{
    margin-bottom: 18px;
  }
  @media screen and (max-width: 900px) {
    .fold {
      width: 100%;
    }
  }
</style>
