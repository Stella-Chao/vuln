<template>
    <a-card>
      <div :class="advanced ? 'search' : null">
        <a-form layout="horizontal">
          <div :class="advanced ? null: 'fold'">
            <a-row v-if="advanced">
            <a-col :md="16" :sm="24" >
              <a-form-item
                label="预警规则"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-input v-model="username" placeholder="请输入"/>
              </a-form-item>
            </a-col>
          </a-row>
           
          </div>
          <span style="float: right; margin-top: 5px;">
            <a-button type="primary" @click="search">查询</a-button>
          </span>
          <span style="float: center; margin-top: 5px;">
            <a-button type="primary" @click="reset">添加预警规则</a-button>
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
            
            <router-link :to="{name:'用户编辑',query:{user:record}}" >
              <a style="margin-right: 8px">
                <a-icon type="edit"/>编辑
              </a>
            </router-link>
            <a >
              <a-icon />查看
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

  const columns = [
    {
      title: '规则编号',
      dataIndex: 'id'
    },
    {
      title: '规则名称',
      dataIndex: 'phone',
      // width: 500
    },
    {
      title: '操作人',
      dataIndex: 'email',
    },
    {
      title: '添加日期',
      dataIndex: 'company',
      needTotal: true,
    },
    {
      title: '操作',
      scopedSlots: { customRender: 'action' }
    }
  ]
  
  const dataSource = []
  
  
  export default {
    name: 'UserList',
    components: {StandardTable},
    data () {
      return {
        advanced: true,
        columns: columns,
        dataSource: dataSource,
        username: "",
        phone: "",
        email: "",
        company: "",
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

  
    methods: {
      
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
  