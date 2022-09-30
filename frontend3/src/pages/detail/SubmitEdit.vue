<template>
    <page-layout title="漏洞信息修改">
      <a-form-model :model="form" :label-col="labelCol" :wrapper-col="wrapperCol">
          <div>漏洞详情</div>
          <a-form-model-item label="TF-ID">
          <a-input v-model="form.tfid"/>
          </a-form-model-item>
          <a-form-model-item label="漏洞名称">
          <a-input v-model="form.title"/>
          </a-form-model-item>
          <a-form-model-item label="漏洞类型">
          <a-input v-model="form.type" />
          </a-form-model-item>
          <a-form-model-item label="设备厂商">
          <a-input v-model="form.vendor" />
          </a-form-model-item>
          <a-form-model-item label="受影响设备">
          <a-input v-model="form.product" />
          </a-form-model-item>
          <a-form-model-item label="描述信息">
          <a-textarea v-model="form.description" 
          :auto-size="{ minRows: 4, maxRows: 7}"/>
          </a-form-model-item>
          <a-form-model-item label="攻击向量">
          <a-input v-model="form.attacker"/>
          </a-form-model-item>
          <a-form-model-item label="联系方式">
          <a-input v-model="form.address"/>
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
          tfid: '',
          title: '',
          type: '',
          description: '',
          vendor: '',
          product: '',
          attacker: '',
          address: ''
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
          axios.get(this.base_url + '/submit/vuln?tfid=' + id)
            .then(res=>{
              let result = res.data
              console.log(result)
              this.form['tfid'] = result["tfid"]
              this.form['title'] = result["title"]
              this.form['type'] = result["type"]
              this.form['description'] = result["description"]
              this.form['vendor'] = result["vendor"]
              this.form['product'] = result["product"]
              this.form['attacker'] = result["attacker"]
              this.form['address'] = result["address"]
            })
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
        axios.post(base_url + '/submit/update', data)
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
  