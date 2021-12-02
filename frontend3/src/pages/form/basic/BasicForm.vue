<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false" >
    <a-form-model :model="form" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-model-item label="漏洞名称">
        <a-input v-model="form.title" placeholder="简要概述一下漏洞信息" />
      </a-form-model-item>
      <a-form-model-item label="发现时间">
        <a-date-picker v-model="form.date" style="width: 100%" />
      </a-form-model-item>
      <a-form-model-item label="详细描述">
        <a-textarea v-model="form.description" rows="4" placeholder="请详细描述一下漏洞相关信息"/>
      </a-form-model-item>
      <a-form-model-item label="漏洞类型">
        <a-select v-model="form.type" placeholder="请选择漏洞的类型">
          <a-select-option value="DOS">
            DOS
          </a-select-option>
          <a-select-option value="Overflow">
            Overflow
          </a-select-option>
          <a-select-option value="SQL">
            SQL注入
          </a-select-option>
          <a-select-option value="XSS">
            XSS
          </a-select-option>
          <a-select-option value="Code">
            执行代码
          </a-select-option>
          <a-select-option value="Other">
            其他类型
          </a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="受影响产品">
        <a-input v-model="form.product" placeholder="受影响的产品(型号)"/>
      </a-form-model-item>
      <a-form-model-item label="产品厂商">
        <a-input v-model="form.ventor" placeholder="受影响的产品厂商"/>
      </a-form-model-item>
      <a-form-model-item label="攻击途径">
        <a-select v-model="form.attacker" placeholder="请选择攻击途径">
          <a-select-option value="远程">
              远程
          </a-select-option>
          <a-select-option value="邻接">
            邻接
          </a-select-option>
          <a-select-option value="本地">
            本地
          </a-select-option>
          <a-select-option value="物理">
            物理
          </a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="联系方式">
        <a-input v-model="form.address" placeholder="电话/邮箱"/>
      </a-form-model-item>
      <a-form-item style="margin-top: 24px" :wrapperCol="{span: 10, offset: 7}">
        <a-button style="margin-left: 50px" type="primary" @click="save"> 提交 </a-button>
        <a-button style="margin-left: 150px"> 保存 </a-button>
      </a-form-item>
    </a-form-model>
  </a-card>
</template>

<script>
import axios from 'axios'
const base_url = process.env.VUE_APP_API_BASE_URL
export default {
  name: 'BasicForm',
  data () {
    return {
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
      form: {
        title: '',
        date: undefined,
        type: undefined,
        ventor: '',
        product: '',
        description: '',
        attacker: undefined,
        address: '',
        desc: '',
      },
    }
  },
  computed: {
    desc() {
      return "漏洞上报用于向漏洞发现者收集或验证信息"
    }
  },
  methods: {
    save() {
      axios.post(base_url + 'tf/submit/{}')
        .then(res=>{
          console.log(res.data)
        })
      
    }
  }
}
</script>

<style scoped>

</style>
