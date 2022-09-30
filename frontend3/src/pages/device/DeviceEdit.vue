<template>
    <page-layout title="设备信息修改">
      <a-form-model :model="form" :label-col="labelCol" :wrapper-col="wrapperCol">
          <div>设备信息</div>
          <a-form-model-item label="厂商">
          <a-input v-model="form.brand"/>
          </a-form-model-item>
          <a-form-model-item label="设备">
          <a-input v-model="form.product"/>
          </a-form-model-item>
          <a-form-model-item label="影响范围">
            <a-select v-model="form.range" placeholder="请选择">
              <a-select-option value="high">极广</a-select-option>
              <a-select-option value="medium">中度</a-select-option>
              <a-select-option value="low">少量</a-select-option>
            </a-select>
          </a-form-model-item>
          <a-form-model-item label="是否已确认">
            <a-select v-model="form.verify" placeholder="请选择">
              <a-select-option value="Yes">已确认</a-select-option>
              <a-select-option value="No">未确认</a-select-option>
            </a-select>
          </a-form-model-item>
          <a-form-model-item label="设备类型">
          <a-input v-model="form.type1" />
          </a-form-model-item>
          <a-form-model-item label="行业类型">
          <a-input v-model="form.type2"/>
          </a-form-model-item>
          <a-form-model-item label="相关漏洞">
          <a-input v-model="form.vulns"/>
          </a-form-model-item>
          <a-form-model-item label="IoT厂商关联度">
          <a-input v-model="form.iotBrandRel"/>
          </a-form-model-item>
          <a-form-model-item label="厂商别名">
          <a-input v-model="form.brandAlias"/>
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
    name: 'deviceEdit',
    data() {
      return {
        base_url: base_url,
        labelCol: { span: 4 },
        wrapperCol: { span: 14 },
        form: {
          brand: '',
          product: '',
          type1: '',
          verify: '',
          version: '',
          type2: '',
          vulns: '',
          iotBrandRel: '',
          brandAlias: '',
          range: '',
          brandCN: '',
        },
      };
    },
    created() {
        let device =  this.$route.query["device"]
        console.log(device)
        this.form.brand = device["brand"],
        this.form.product = device["product"]
        this.form.type1 = device["type1"]
        this.form.verify = device["verify"],
        this.form.version = device["version"],
        this.form.type2 = device["type2"]
        this.form.vulns = device["vulns"]
        this.form.iotBrandRel = device["iotBrandRel"]
        this.form.brandAlias = device["brandAlias"]
        this.form.range = device["range"]
        this.form.brandCN = device["brandCN"]
    },
    methods: {
      onSubmit() {
        axios.post(base_url + '/device/modify', this.form) // body
        console.log('submit!', this.form);
        this.$message.success("修改成功！", 2);
      },
      update() {
          console.log("修改成功!")
      }
    },
  };
  </script>
  