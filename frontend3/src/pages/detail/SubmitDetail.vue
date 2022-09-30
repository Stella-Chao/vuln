<template>
    <page-layout title="漏洞详情">
      <a-card :bordered="false">
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="TF-ID：">
          {{ vuln["tfid"] }}
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="漏洞名称：">
          {{ vuln["title"] }}
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="漏洞类型：">
          {{ vuln["type"] }}
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="攻击向量：">
          {{ vuln["attacker"] }}
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="描述信息：">
          <span >{{ vuln["description"] }}</span>
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="设备厂商：">
          <span >{{ vuln["vendor"] }}</span>
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="受影响设备：">
          <span >{{ vuln["product"] }}</span>
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="联系方式：">
          <span >{{ vuln["address"] }}</span>
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
        <detail-list title="提交日期：">
          {{ vuln["date"] }}
        </detail-list>
        <a-divider style="margin-bottom: 32px"/>
      </a-card>
    </page-layout>
</template>

<script>
import DetailList from '../../components/tool/DetailList'
import PageLayout from '../../layouts/PageLayout'
import axios from 'axios'
const base_url = process.env.VUE_APP_API_BASE_URL


const linkData = []

const vuln = {

}

export default {
  name: 'BasicDetail',
  components: {PageLayout, DetailList},
  data () {
    return {
      base_url: base_url,
      tfid: "",
      vuln,
      linkData,
      title: '',
      type: '',
      product: '',
      vendor: '/',
      description: '/',
      attacker: '/',
      address: '',
    }
  },
  methods: {
    getVulnDetail(id) {
      axios.get(this.base_url + '/submit/vuln?tfid=' + id)
        .then(res=>{
          this.vuln = res.data
          console.log(this.vuln)
          this.tfid = this.vuln["tfid"]
          this.title = this.vuln["title"]
          this.type = this.vuln["type"]
          this.product = this.vuln["product"]
          this.vendor = this.vuln["vendor"]
          this.description = this.vuln["description"]
          this.attacker = this.vuln["attacker"]
          this.address = this.vuln["address"]
        })
    },
  },
  created(){
    let id =  this.$route.query["id"]
    console.log("id:" + id)
    this.getVulnDetail(id)
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
