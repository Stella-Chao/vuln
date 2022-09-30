<template>
  <page-layout title="漏洞信息修改">
    <a-form-model :model="form" :label-col="labelCol" :wrapper-col="wrapperCol">
        <div>漏洞详情</div>
        <a-form-model-item label="CVE-ID">
        <a-input v-model="form.cveId"/>
        </a-form-model-item>
        <a-form-model-item label="漏洞名称">
        <a-input v-model="form.name"/>
        </a-form-model-item>
        <a-form-model-item label="CWE-ID">
        <a-input v-model="form.cweID"/>
        </a-form-model-item>
        <a-form-model-item label="漏洞类型">
        <a-input v-model="form.type" />
        </a-form-model-item>
        <a-form-model-item label="描述信息">
        <a-textarea v-model="form.description" 
        :auto-size="{ minRows: 4, maxRows: 7}"/>
        </a-form-model-item>
        <div>攻击详情</div>
        <a-form-model-item label="危险评分">
        <a-input v-model="form.score"/>
        </a-form-model-item>
        <a-form-model-item label="危险级别">
        <a-input v-model="form.severity"/>
        </a-form-model-item>
        <a-form-model-item label="攻击复杂度">
        <a-input v-model="form.complexity"/>
        </a-form-model-item>
        <a-form-model-item label="攻击方式">
        <a-input v-model="form.attackVector"/>
        </a-form-model-item>
        <a-form-model-item label="攻击向量">
        <a-input v-model="form.vector"/>
        </a-form-model-item>
        <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
        <a-button type="primary" @click="onSubmit">
            修改
        </a-button>
        <a-button style="margin-left: 10px;">
            取消
        </a-button>
        </a-form-model-item>
    </a-form-model>
  </page-layout>
</template>
<script>
import axios from 'axios'
import PageLayout from '../../layouts/PageLayout.vue';

const base_url = process.env.VUE_APP_API_BASE_URL
export default {
  components: { PageLayout },
  name: 'Edit',
  data() {
    return {
      base_url: base_url,
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
      form: {
        cveId: '',
        name: '',
        cweId: '',
        type: '',
        description: '',
        score: '',
        severity: '',
        complexity: '',
        attackVector: '',
        vector: ''
      },
    };
  },
  created() {
      let id =  this.$route.query["id"]
      this.getVulnData(id)
  },
  methods: {
    onSubmit() {
      console.log('submit!', this.form);
      this.update()
    },
    getVulnData(id) {
        axios.get(this.base_url + '/tf/cve?cveID=' + id)
          .then(res=>{
            let result = res.data
            console.log(result)
            this.form['cveId'] = result["cveID"]
            this.form['name'] = result["title"]
            this.form['type'] = result["type01"]
            this.form['cweId'] = result["cweID"][0]
            this.form['description'] = result["description"]
            this.form['score'] = result["cvssV3"]["cvssV3"]["baseScore"]
            this.form['severity'] = result["cvssV3"]["cvssV3"]["baseSeverity"]
            this.form['complexity'] = result["cvssV3"]["cvssV3"]["attackComplexity"]
            this.form['attackVector'] = result["cvssV3"]["cvssV3"]["attackVector"]
            this.form['vector'] = result["cvssV3"]["cvssV3"]["vectorString"]
          })
        this.form['name'] = "Cisco Web Security Appliance代理服务拒绝服务漏洞"
        console.log(this.form)
    },
    update() {
      let data = {}
      data['cveid'] = this.form['cveId']
      data['title'] = this.form['name']
      data['cweId'] = this.form['cweId']
      data['type'] = this.form['type']
      data['description'] = this.form['description']
      data['score'] = this.form['score']
      data['severity'] = this.form['severity']
      data['complexity'] = this.form['complexity']
      data['attackVector'] = this.form['attackVector']
      data['vector'] = this.form['vector']
      axios.post(base_url + '/tf/update', data)
      .then(res=>{
        console.log(data)
        if(res.data === "success") {
          this.$message.success("修改成功", 3)
        } else {
          this.$message.info("修改失败", 3)
        }
      })
    }
  },
};
</script>
