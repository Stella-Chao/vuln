<template>
    <page-layout title="漏洞详情">
      <a-card :bordered="false">
        <detail-list title="漏洞详情">
          <detail-list-item term="CVE-ID"> {{ vuln["cveID"] }}</detail-list-item>
          <detail-list-item term="漏洞名称">
            <a :href="title_url">{{ vuln["title"] }}</a>
          </detail-list-item>
          <detail-list-item term="CWE-ID">
            <a :href="cwe_url"> {{ vuln["cweID"][0] }} </a>
          </detail-list-item>
          <detail-list-item term="漏洞类型"> {{ vuln["type01"][0] }} </detail-list-item>
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="描述信息">
          <!-- <detail-list-item term="">{{ vuln["description"] }}</detail-list-item> -->
          <span >{{ vuln["description"] }}</span>
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="攻击详情">
          <detail-list-item term="危险评分">{{ baseScore }}</detail-list-item>
          <detail-list-item term="危险级别">{{ baseSeverity }}</detail-list-item>
          <detail-list-item term="攻击复杂度">{{ attackComplexity }}</detail-list-item>
          <detail-list-item term="攻击方式">{{ attackVector }}</detail-list-item>
          <detail-list-item term="攻击向量">
            <a :href="cvss3_url">
              {{ cvss_vector }}
            </a>
          </detail-list-item>
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
        <div class="title">
          <b>受影响平台</b>
          </div>
        <a-table
          row-key="id"
          style="margin-bottom: 24px"
          :columns="cpeColumns"
          :dataSource="cpeData"
          :pagination="true"
        >
        </a-table>
        <a-card>
          <Graph :vuln="vuln"/>
        </a-card>
        <div class="title">
          <b>相关链接</b>
        </div>
        <a-table
          :columns="linkColumns"
          :dataSource="linkData"
          :pagination="false"
        >
          <a slot="url" slot-scope="text" :href="text">{{ text }}</a>
        </a-table>
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="POC">
          <detail-list-item term="点击下载">
            <a>{{ code_url }}</a>
            </detail-list-item>
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="更新日期">
          <detail-list-item term="漏洞发布时间">{{ vuln["publishedDate"] }}</detail-list-item>
          <detail-list-item term="最后修改时间">{{ vuln["lastModifiedDate"] }}</detail-list-item>
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
      </a-card>
    </page-layout>
</template>

<script>
import DetailList from '../../components/tool/DetailList'
import PageLayout from '../../layouts/PageLayout'
import Graph from '../kg/Graph'
import axios from 'axios'
const base_url = process.env.VUE_APP_API_BASE_URL
const DetailListItem = DetailList.Item

const cpeColumns = [
  {
    title: '平台类型',
    dataIndex: 'platform',
    key: 'platform'
  },
  {
    title: '受影响应用',
    dataIndex: 'application',
    key: 'application'
  },
  {
    title: '厂商',
    dataIndex: 'ventor',
    key: 'ventor'
  },
  {
    title: '名称',
    dataIndex: 'cpe_name',
    key: 'cpe_name'
  },
  {
    title: '受影响版本 ≥ ',
    dataIndex: 'versionStartIncluding',
    key: 'versionStartIncluding'
  },
  {
    title: '受影响版本 < ',
    dataIndex: 'versionEndExcluding',
    key: 'versionEndExcluding'
  }
]

const linkColumns = [
  {
    title: '名称',
    dataIndex: 'name',
    key: 'name'
  },
  {
    title: '厂商',
    dataIndex: 'refsource',
    key: 'refsource'
  },
  {
    title: '链接',
    dataIndex: 'url',
    key: 'url',
    scopedSlots: { customRender: 'url' },
  }
]

const linkData = []

const vuln = {

}

export default {
  name: 'BasicDetail',
  components: {PageLayout, DetailListItem, DetailList, Graph},
  data () {
    return {
      base_url: base_url,
      cveid: "",
      vuln,
      cpeColumns,
      cpeData: [],
      linkColumns,
      linkData,
      poc: '',
      code_url: '',
      poc_verified: '',
      cvss3_url: '/',
      cwe_url: '/',
      title_url: '/',
      baseScore: '',
      baseSeverity: '',
      attackComplexity: '',
      attackVector: '',
      cvss_vector: ''
    }
  },
  methods: {
    getVulnDetail(id) {
      axios.get(this.base_url + '/cve/cve?cveID=' + id)
        .then(res=>{
          this.vuln = res.data
          console.log(this.vuln)
          this.cveid = this.vuln["cveID"]
          this.linkData = this.vuln.refer
          console.log("cvssV2:", this.vuln["cvssV2"])
          console.log("cvssV3:", this.vuln["cvssV3"])
          this.baseScore = this.vuln["cvssV2"]["cvssV2"]["baseScore"]
          this.baseSeverity = this.vuln["cvssV2"]["severity"]
          this.attackComplexity = this.vuln["cvssV2"]["cvssV2"]["accessComplexity"]
          this.attackVector = this.vuln["cvssV2"]["cvssV2"]["accessVector"]
          this.cvss_vector = this.vuln["cvssV2"]["cvssV2"]["vectorString"]
          if ("cvssV3" in this.vuln && this.vuln["cvssV3"] !== null) {
            this.cvss3_url = "https://nvd.nist.gov/vuln-metrics/cvss/v3-calculator?vector="+this.vuln["cvssV3"]["cvssV3"]["vectorString"].substring(9)+"&version=3.1"
            this.baseScore = this.vuln["cvssV3"]["cvssV3"]["baseScore"]
            this.baseSeverity = this.vuln["cvssV3"]["cvssV3"]["baseSeverity"]
            this.attackComplexity = this.vuln["cvssV3"]["cvssV3"]["attackComplexity"]
            this.attackVector = this.vuln["cvssV3"]["cvssV3"]["attackVector"]
            this.cvss_vector = this.vuln["cvssV3"]["cvssV3"]["vectorString"].substring(9)
            console.log("进入test...")
          }
          console.log(this.baseScore)
          console.log(this.baseSeverity)
          console.log(this.attackComplexity)
          console.log(this.attackVector)
          this.cwe_url = ""
          if (this.vuln["cweID"].length > 0) {
            this.cwe_url = "https://cwe.mitre.org/data/definitions/" + this.vuln["cweID"][0].substring(4) + ".html"
          }
          for (let i in this.vuln.cpe) {
            let uri = this.vuln.cpe[i]["cpe23Uri"].split(":")
            console.log(uri)
            let platform = uri[2]
            let application = uri[4]
            let ventor = uri[3]
            if (platform == "o") {
              platform = "操作系统"
            } else if (platform == "a") {
              platform = "应用程序"
            } else {
              platform = "硬件平台"
            }
            this.cpeData.push({
              "platform":platform,
              "application":application,
              "ventor": ventor,
              "cpe_name": this.vuln.cpe[i]["cpe_name"],
              "versionStartIncluding": this.vuln.cpe[i]["versionStartIncluding"],
              "versionEndExcluding": this.vuln.cpe[i]["versionEndExcluding"]})
          }
        })
    },
    getPoc(id) {
      axios.get(this.base_url + 'poc/get?cveID=' + id.substring(4))
        .then(res=>{
          // console.log(res.data)
          // this.poc = res.data
          // if (res.data === "") {
          //   this.poc = '暂无POC'
          // }
          console.log('hello,world!')
          console.log(id.substring(4))
          this.code_url = res.data["codeUrl"]
          console.log(res.data["codeUrl"])
        })
    }
  },
  created(){
    let id =  this.$route.query["id"]
    this.getVulnDetail(id)
    this.getPoc(id)
  }
}
</script>

<style lang="less" scoped>
  .title {
    color: @title-color;
    font-size: 16px;
    font-weight: 500;
    margin-bottom: 16px;
  }
</style>
