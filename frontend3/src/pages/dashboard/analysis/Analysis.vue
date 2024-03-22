<template>
  <div class="analysis">
    <a-row style="margin-top: 0" :gutter="[24, 24]">
      <a-col :sm="24" :md="12" :xl="6">
        <chart-card :loading="loading" title="IOT漏洞总数" :total= "total">
          <a-tooltip :title="$t('introduce')" slot="action">
            <a-icon type="info-circle-o"/>
          </a-tooltip>
          <div>
            <trend style="margin-right: 16px" :term="$t('wow')" :percent="12" :is-increase="true" :scale="0" />
            <trend :term="$t('dod')" :target="100" :value="89" :scale="0" />
          </div>
          <div slot="footer">日均新增漏洞数量 <span>1</span></div>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6">
        <chart-card :loading="loading" title="漏洞提交总数" :total="submit">
          <a-tooltip :title="$t('introduce')" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <trend style="margin-right: 16px" :term="$t('wow')" :percent="12" :is-increase="true" :scale="0" />
            <trend :term="$t('dod')" :target="100" :value="89" :scale="0" />
          </div>
          <div slot="footer">日均提交漏洞数 <span>1</span></div>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6">
        <chart-card :loading="loading" title="POC总数" :total="poc">
          <a-tooltip :title="$t('introduce')" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <trend style="margin-right: 16px" :term="$t('wow')" :percent="12" :is-increase="true" :scale="0" />
            <trend :term="$t('dod')" :target="100" :value="89" :scale="0" />
          </div>
          <div slot="footer">日均新增POC总数 <span>1</span></div>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6">
        <chart-card :loading="loading" title="高危漏洞" :total="highNum">
          <a-tooltip :title="$t('introduce')" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <mini-progress target="90" percent="78" color="#13C2C2" height="8px"/>
          </div>
          <div slot="footer" style="white-space: nowrap;overflow: hidden">
            <trend style="margin-right: 16px" :term="$t('wow')" :percent="12" :is-increase="true" :scale="0" />
            <trend :term="$t('dod')" :target="100" :value="89" :scale="0" />
          </div>
        </chart-card>
      </a-col>
    </a-row>
    <a-card :loading="loading" style="margin-top: 24px" :bordered="false" :body-style="{padding: '24px'}">
      <div class="salesCard">
        <a-tabs default-active-key="1" size="large" :tab-bar-style="{marginBottom: '24px', paddingLeft: '16px'}">
          <div class="extra-wrap" slot="tabBarExtraContent">
            <div class="extra-item">
              <a>{{$t('day')}}</a>
              <a>{{$t('week')}}</a>
              <a>{{$t('month')}}</a>
              <a>{{$t('year')}}</a>
            </div>
            <a-range-picker :style="{width: '256px'}"></a-range-picker>
          </div>
          <a-tab-pane loading="true" tab="漏洞统计" key="1">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
                <bar title="漏洞趋势分析" />
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                <ranking-list title="厂商漏洞数量排行榜" :list="rankList"/>
              </a-col>
            </a-row>
          </a-tab-pane>
          <!-- <a-tab-pane :tab="$t('visits')" key="2"><a-row>
            <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
              <bar :title="$ta('visits|trend', 'p')" />
            </a-col>
            <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
              <ranking-list :title="$ta('stores|visits|ranking', 'p')" :list="rankList"/>
            </a-col>
          </a-row></a-tab-pane> -->
        </a-tabs>
      </div>
    </a-card>
    <a-row style="margin: 0 -12px">
      <a-col style="padding: 0 12px" :xl="12" :lg="24" :md="24" :sm="24" :xs="24">
        <a-card :loading="loading" :bordered="false" style="margin-top: 24px" :title="$t('search')">
          <hot-search />
        </a-card>
      </a-col>
      <a-col style="padding: 0 12px" :xl="12" :lg="24" :md="24" :sm="24" :xs="24">
        <a-card :loading="loading" :bordered="false" style="margin-top: 24px;" title="危险级别占比">
          <sales-data />
          <a-radio-group slot="extra" style="margin: -12px 0">
            <a-radio-button value="a"> 类别1</a-radio-button>
            <a-radio-button value="b"> 类别2</a-radio-button>
            <a-radio-button value="c"> 类别3</a-radio-button>
          </a-radio-group>
        </a-card>
      </a-col>
    </a-row>
    <!-- <a-card :loading="loading" title="最新动态" :bordered="false">
      <a-list>
        <a-list-item :key="index" v-for="(item, index) in activities">
          <a-list-item-meta>
            <a-avatar slot="avatar" :src="item.user.avatar" />
            <div slot="title" v-html="item.template" />
            <div slot="description">9小时前</div>
          </a-list-item-meta>
        </a-list-item>
      </a-list>
    </a-card> -->
    <a-card :loading="loading" title="情报订阅" :bordered="false">
      <div>
        <div :style="{ borderBottom: '1px solid #E9E9E9' }">
          <a-checkbox :indeterminate="indeterminate" :checked="checkAll" @change="onCheckAllChange">
            Check all
          </a-checkbox>
          <br/>
          <br/>
        </div>
        <a-checkbox-group v-model="checkedList" :options="plainOptions" @change="onChange" />
      </div>
      <br/>
      <a-form :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }" :style="{ borderBottom: '1px solid #E9E9E9' }">
          <a-form-item label="Email">
            <a-input
              v-model="email"
              v-decorator="['Email', { rules: [{ required: true, message: '请输入邮箱地址' }] }]"
            />
          </a-form-item>
          <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
          <a-button type="primary" html-type="submit" @click="submitEmail">
            订阅
          </a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </div>
</template>

<script>
import ChartCard from '../../../components/card/ChartCard'
// import MiniArea from '../../../components/chart/MiniArea'
// import MiniBar from '../../../components/chart/MiniBar'
// import MiniProgress from '../../../components/chart/MiniProgress'
import Bar from '../../../components/chart/Bar'
import RankingList from '../../../components/chart/RankingList'
import HotSearch from './HotSearch'
import SalesData from './SalesData'
import {request, METHOD} from '@/utils/request'
// import Trend from '../../../components/chart/Trend'
import axios from 'axios'

const rankList = []

const plainOptions = ['视频监控类', '智能家居类', '工业控制类', '移动设备类'];
const defaultCheckedList = [];
const base_url = process.env.VUE_APP_API_BASE_URL
export default {
  name: 'Analysis',
  i18n: require('./i18n'),
  data () {
    return {
      rankList,
      loading: true,
      activities: [],
      checkedList: defaultCheckedList,
      indeterminate: true,
      checkAll: false,
      plainOptions,
      total: "",
      poc: "",
      submit: "",
      highNum: "",
      email: ""
    }
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
        axios.get(base_url + '/tf/get/submitNum').then(res=>{
          console.log('漏洞提交总数量：',res.data)
          this.submit = res.data
        })
        axios.get(base_url + '/tf/highnum').then(res=>{
          console.log('高危漏洞：',res.data)
          this.highNum = res.data
        })
        axios.get(base_url + '/tf/get/vendor').then(res=>{
          console.log(res.data)
          let rankList0 = []
          let rank = res.data
          for (let key in rank) {
            let dict = {}
            dict["name"] = key
            dict["total"] = rank[key]
            rankList0.push(dict)
          }
          let tmp = rankList0.sort((a, b) => b.total - a.total);
          for (let i = 0; i < tmp.length; i++) {
            rankList.push(tmp[i])
          }
        })
    },
    onChange(checkedList) {
      this.indeterminate = !!checkedList.length && checkedList.length < plainOptions.length;
      this.checkAll = checkedList.length === plainOptions.length;
    },
    onCheckAllChange(e) {
      Object.assign(this, {
        checkedList: e.target.checked ? plainOptions : [],
        indeterminate: false,
        checkAll: e.target.checked,
      });
    },
    submitEmail() {
      let data = {}
      data['type'] = 0
      data['email'] = this.email
      axios.post(base_url + '/subscribe/submit', data)
        .then(res=>{
          if (res.data === "success") {
            this.$message.success("订阅成功!",3)
          } else {
            this.$message.error("订阅失败！")
          }
        })
    }
  },
  created() {
    setTimeout(() => this.loading = !this.loading, 1000)
    request('/work/activity', METHOD.GET).then(res => this.activities = res.data)
    this.getData()
  },
  // components: { SalesData, HotSearch, RankingList, Bar, ChartCard, MiniProgress, Trend}
  components: { SalesData, HotSearch, RankingList, Bar, ChartCard }
}
</script>

<style lang="less" scoped>
  .extra-wrap{
    .extra-item{
      display: inline-block;
      margin-right: 24px;
      a:not(:first-child){
        margin-left: 24px;
      }
    }
  }
  @media screen and (max-width: 992px){
    .extra-wrap .extra-item{
      display: none;
    }
  }
  @media screen and (max-width: 576px){
    .extra-wrap{
      display: none;
    }
  }

</style>
