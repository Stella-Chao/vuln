<template>
  <a-form @submit="onSubmit" :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
    <a-form-item label="用户名">
      <a-input v-model="form.username" :disabled="true" />
    </a-form-item>
    <a-form-item label="手机号">
      <a-input v-model="form.phone"/>
    </a-form-item>
    <a-form-item label="邮箱">
      <a-input v-model="form.email"/>
    </a-form-item>
    <a-form-item label="公司">
      <a-input v-model="form.company"/>
    </a-form-item>
    <a-form-item label="职位">
      <a-input v-model="form.profession"/>
    </a-form-item>
    <a-form-item label="性别">
      <a-select
        placeholder="选择您的性别"
        v-model="form.gender"
      >
        <a-select-option value="男">
          男
        </a-select-option>
        <a-select-option value="女">
          女
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 12, offset: 5 }">
      <a-button type="primary" html-type="submit">
        修改
      </a-button>
    </a-form-item>
  </a-form>
</template>

<script>
import axios from 'axios'
const baseUrl = process.env.VUE_APP_API_BASE_URL

export default {
  name: 'Basic',
  data() {
    return {
      form: {
        username: '',
        phone: '',
        company: '',
        profession: '',
        emial: '',
        gender: '',
      },
      baseUrl: baseUrl
    };
  },
  created() {
      let user = JSON.parse(localStorage.getItem("admin.user"))
      this.form.username = user["username"]
      this.form.phone = user["phone"]
      this.form.email = user["email"]
      this.form.company = user["company"]
      this.form.profession = user["profession"] 
      console.log(this.form)
  },
  methods: {
    onSubmit() {
      let data = {}
      data["username"] = this.form.username
      data["phone"] = this.form.phone
      data["email"] = this.form.email
      data["company"] = this.form.company
      data["profession"] = this.form.profession
      data["gender"] = this.form.gender
      console.log(this.baseUrl)
      axios.post(this.baseUrl + '/user/modify', data)
        .then(res => {
          console.log(res.data)
        })
      console.log('submit!', this.form);
      this.$message.success("修改成功!", 2)
    },
  },
};
</script>