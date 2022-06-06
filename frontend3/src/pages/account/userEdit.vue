<template>
  <page-layout title="用户信息修改">
    <a-form-model :model="form" :label-col="labelCol" :wrapper-col="wrapperCol">
        <div>用户信息</div>
        <a-form-model-item label="用户名">
        <a-input v-model="form.username"/>
        </a-form-model-item>
        <a-form-model-item label="密码">
        <a-input v-model="form.password"/>
        </a-form-model-item>
        <a-form-model-item label="角色">
        <a-input v-model="form.role"/>
        </a-form-model-item>
        <a-form-model-item label="性别">
        <a-input v-model="form.gender" />
        </a-form-model-item>
        <a-form-model-item label="手机号">
        <a-input v-model="form.phone"/>
        </a-form-model-item>
        <a-form-model-item label="邮箱">
        <a-input v-model="form.email"/>
        </a-form-model-item>
        <a-form-model-item label="公司">
        <a-input v-model="form.company"/>
        </a-form-model-item>
        <a-form-model-item label="职位">
        <a-input v-model="form.profession"/>
        </a-form-model-item>
        <a-form-model-item label="注册时间">
        <a-input v-model="form.created_date" :disabled="true"/>
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
  name: 'userEdit',
  data() {
    return {
      base_url: base_url,
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
      form: {
        username: '',
        password: '',
        role: '',
        gender: '',
        phone: '',
        email: '',
        company: '',
        profession: '',
        created_date: '',
      },
    };
  },
  created() {
      let user =  this.$route.query["user"]
      console.log(user)
      console.log(user["username"])
      this.form.username = user["username"],
      this.form.password = ""
      this.form.role = user["role"]
      this.form.gender = user["gender"],
      this.form.phone = user["phone"],
      this.form.email = user["email"]
      this.form.company = user["company"]
      this.form.profession = user["profession"]
      this.form.created_date = user["created_date"]
  },
  methods: {
    onSubmit() {
      axios.post(base_url + '/user/modify', this.form) // body
      console.log('submit!', this.form);
      this.$message.success("修改成功！", 2);
    },
    update() {
        console.log("修改成功!")
    }
  },
};
</script>
