<template>
  <a-card>
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <div :class="advanced ? null: 'fold'">
          <a-row v-if="advanced">
          <a-col :md="16" :sm="24" >
            <a-form-item
              label="内容/标题"
              :labelCol="{span: 5}"
              :wrapperCol="{span: 18, offset: 1}"
            >
              <a-input v-model="descri" placeholder="请输入"/>
            </a-form-item>
          </a-col>
        </a-row>
          <a-row >
          <a-col :md="16" :sm="24" >
            <a-form-item
              label="漏洞编号"
              :labelCol="{span: 5}"
              :wrapperCol="{span: 18, offset: 1}"
            >
              <a-input v-model="cveid" placeholder="请输入" />
            </a-form-item>
          </a-col>
        </a-row>
          <a-row v-if="advanced">
          <a-col :md="16" :sm="24" >
            <a-form-item
              label="危险级别"
              :labelCol="{span: 5}"
              :wrapperCol="{span: 18, offset: 1}"
            >
              <a-select v-model="severity" placeholder="请选择">
                <a-select-option value="超危">超危</a-select-option>
                <a-select-option value="高危">高危</a-select-option>
                <a-select-option value="中危">中危</a-select-option>
                <a-select-option value="低危">低危</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          </a-row>
          <a-row v-if="advanced">
          <a-col :md="16" :sm="24" >
            <a-form-item
              label="攻击向量"
              :labelCol="{span: 5}"
              :wrapperCol="{span: 18, offset: 1}"
            >
              <a-select v-model="attack" placeholder="请选择">
                <a-select-option value="网络">网络</a-select-option>
                <a-select-option value="邻接">邻接</a-select-option>
                <a-select-option value="本地">本地</a-select-option>
                <a-select-option value="物理">物理</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row v-if="advanced">
          <a-col :md="16" :sm="24" >
            <a-form-item
              label="漏洞类型"
              :labelCol="{span: 5}"
              :wrapperCol="{span: 18, offset: 1}"
            >
              <a-select v-model="type" placeholder="请选择">
                <a-select-option value="拒绝服务">拒绝服务</a-select-option>
                <a-select-option value="执行代码">执行代码</a-select-option>
                <a-select-option value="绕过">绕过</a-select-option>
                <a-select-option value="获取权限">获取权限</a-select-option>
                <a-select-option value="目录遍历">目录遍历</a-select-option>
                <a-select-option value="跨站脚本">跨站脚本</a-select-option>
                <a-select-option value="获取信息">获取信息</a-select-option>
                <a-select-option value="溢出">溢出</a-select-option>
                <a-select-option value="SQL注入">SQL注入</a-select-option>
                <a-select-option value="内存错误">内存错误</a-select-option>
                <a-select-option value="文件包含">文件包含</a-select-option>
                <a-select-option value="跨站请求伪造">跨站请求伪造</a-select-option>
                <a-select-option value="HTTP响应拆分">HTTP响应拆分</a-select-option>
                <a-select-option value="其他">其他</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row v-if="advanced">
          <a-col :md="16" :sm="24" >
            <a-form-item
              label="产品厂商"
              :labelCol="{span: 5}"
              :wrapperCol="{span: 18, offset: 1}"
            >
              <a-select v-model="ventor" placeholder="请选择">
                <a-select-option value="Cisco">思科</a-select-option>
                <a-select-option value="Hikvision">海康威视</a-select-option>
                <a-select-option value="Dahua">大华</a-select-option>
              </a-select>
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
          
          <router-link :to="{name:'编辑页',query:{id:record.cveID}}" >
            <a style="margin-right: 8px">
              <a-icon type="edit"/>编辑
            </a>
          </router-link>
          <!-- <a @click="deleteRecord(record.key)" v-auth="`delete`">
            <a-icon type="delete" />删除
          </a> -->
          <!-- <router-link :to="`/list/query/detail/${record.key}`" >详情</router-link> -->
          <router-link :to="{name:'基础详情页',query:{id:record.cveID}}" >详情</router-link>
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
    title: 'CVE-ID',
    dataIndex: 'cveID'
  },
  {
    title: '漏洞名称',
    dataIndex: 'title',
    width: 500
  },
  {
    title: '漏洞类型',
    dataIndex: 'type01[0]',
    // scopedSlots: { customRender: 'description' }
  },
  {
    title: '危险级别',
    dataIndex: "cvssV3['cvssV3']['baseSeverity']",
    needTotal: true,
    // slots: {title: 'statusTitle'},
    scopedSlots: { customRender: 'tags' }
  },
  {
    title: '更新时间',
    dataIndex: 'lastModifiedDate',
    sorter: true
  },
  {
    title: '操作',
    scopedSlots: { customRender: 'action' }
  }
]

const dataSource = []


export default {
  name: 'QueryList',
  components: {StandardTable},
  data () {
    return {
      advanced: true,
      columns: columns,
      dataSource: dataSource,
      cveid: "",
      severity: undefined,
      descri: "",
      ventor: undefined,
      attack: undefined,
      type: undefined,
      url: "",
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
  authorize: {
    deleteRecord: 'delete'
  },
  created() {
    console.log(base_url)
    this.getData()
    
  },

  methods: {
    getData() {
        axios.get(base_url + 'tf/get/count').then(res=>{
          console.log('漏洞总数量：',res.data)
          this.pagination.total = res.data
        })
        this.url = base_url + 'tf/list/'
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
        this.url = base_url +'tf/search2?cveID='+this.cveid+'&severity='+this.severity+'&attack='+this.attack+'&type='+this.type+'&description='+this.descri
        axios.get(this.url +'&size='+this.pagination.defaultPageSize+'&page=1').then(res=>{
        console.log('查询结果...')
        console.log(res.data.result)
        console.log(this.pagination)
        this.pagination.current = this.pagination.defaultCurrent
        this.dataSource = res.data.result
        this.pagination.total = res.data.total
      })
    },
    reset() {
      this.cveid = ""
      this.severity = undefined
      this.descri = ""
      this.attack = undefined
      this.ventor = undefined
      this.type = undefined
      this.getData()
    },
    deleteRecord(key) {
      this.dataSource = this.dataSource.filter(item => item.key !== key)
      this.selectedRows = this.selectedRows.filter(item => item.key !== key)
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
