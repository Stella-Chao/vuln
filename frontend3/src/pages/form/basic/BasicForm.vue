<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false" >
    <a-form-model :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-model-item label="TF-ID*">
        <a-input v-model="form.tfid" placeholder="漏洞编号" />
      </a-form-model-item>
      <a-form-model-item label="漏洞名称*">
        <a-input v-model="form.title" placeholder="简要概述一下漏洞信息" />
      </a-form-model-item>
      <a-form-model-item label="发现时间">
        <a-date-picker v-model="form.date" style="width: 100%" />
      </a-form-model-item>
      <a-form-model-item label="详细描述*">
        <a-textarea v-model="form.description" rows="4" placeholder="请详细描述一下漏洞相关信息"/>
      </a-form-model-item>
      <a-form-model-item label="漏洞类型*">
        <a-select v-model="form.type" placeholder="请选择漏洞的类型">
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
      </a-form-model-item>
      <a-form-model-item label="受影响产品">
        <a-input v-model="form.product" placeholder="受影响的产品(型号)"/>
      </a-form-model-item>
      <a-form-model-item label="产品厂商">
        <a-input v-model="form.vendor" placeholder="受影响的产品厂商"/>
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
        <a-button style="margin-left: 50px" type="primary" @click="submit"> 提交 </a-button>
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
        title: undefined,
        date: '',
        tfid: '',
        type: '',
        vendor: '',
        product: '',
        description: '',
        attacker: '',
        address: '',
        rules: {
          title: [
            {
              required: true,
              message: "请输入标题",
              trigger: ["change", "blur"]
            }
          ],
          description: [
            {
              required: true,
              message: "请输入漏洞描述信息",
              trigger: ["change", "blur"]
            }
          ]
        }
      },
      
    }
  },
  methods: {
    submit() {
      console.log(this.form)
      let data = {}
      data['tfid'] = this.form['tfid']
      data['title'] = this.form['title']
      data['date'] = this.form['date'] 
      data['type'] = this.form['type']
      data['product'] = this.form['product']
      data['vendor'] = this.form['vendor']
      data['description'] = this.form['description']
      data['attacker'] = this.form['attacker']
      data['address'] = this.form['address']
      axios.post(base_url + '/submit/add', data)
        .then(res=>{
          if(res.data === "success") {
            this.$message.success("提交成功，天防安全欢迎你！", 3)
          } else {
            this.$message.error("提交失败");
          }
        })
    }
  }
}
</script>

<style scoped>

</style>
