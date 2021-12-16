<template>
  <a-form @submit="onSubmit" :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
    <a-form-item label="当前密码">
      <a-input v-model="form.oldPass" type="password"/>
    </a-form-item>
    <a-form-item label="新密码">
      <a-input v-model="form.newPass" type="password"
        placeholder="密码长度至少6位,区分大小写"
      />
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

const levelNames = {
  0: '低',
  1: '低',
  2: '中',
  3: '强'
}
const levelClass = {
  0: 'error',
  1: 'error',
  2: 'warning',
  3: 'success'
}
const levelColor = {
  0: '#ff0000',
  1: '#ff0000',
  2: '#ff7e05',
  3: '#52c41a'
}

export default {
  name: 'Password',
  data() {
    return {
      passwordLevel: 0, 
      percent: 10,
      form: {
        oldPass: '',
        newPass: '',
      },
      baseUrl: baseUrl
    };
  },
  computed: {
    passwordLevelClass () {
      return levelClass[this.state.passwordLevel]
    },
    passwordLevelName () {
      return levelNames[this.state.passwordLevel]
    },
    passwordLevelColor () {
      return levelColor[this.state.passwordLevel]
    }
  },
  methods: {
    handlePasswordLevel (rule, value, callback) {
      let level = 0
      // 判断字符串的长度
      if (value.length < 6) {
        callback(new Error('密码长度不够'))
      }
      // 判断这个字符串中有没有数字
      if (/[0-9]/.test(value)) {
        level++
      }
      // 判断字符串中有没有字母
      if (/[a-zA-Z]/.test(value)) {
        level++
      }
      // 判断字符串中有没有特殊符号
      if (/[^0-9a-zA-Z_]/.test(value)) {
        level++
      }
      this.state.passwordLevel = level
      this.state.percent = level * 30
      if (level >= 2) {
        if (level >= 3) {
          this.state.percent = 100
        }
        callback()
      } else {
        if (level === 0) {
          this.state.percent = 10
        }
        callback(new Error('密码强度不够'))
      }
    },

    onSubmit() {
      let data = {}
      let user = JSON.parse(localStorage.getItem("admin.user"))
      data["username"] = user["username"]
      data["oldPass"] = this.form.oldPass
      data["newPass"] = this.form.newPass
      console.log(this.baseUrl + 'user/password')
      axios.post(this.baseUrl + '/user/password', data)
        .then(res => {
          if (res.data === "密码修改成功") {
            this.$message.success("修改成功!", 2)
          } else {
            this.$message.error("密码错误!", 2)
          }
        })
    },
  },
};
</script>