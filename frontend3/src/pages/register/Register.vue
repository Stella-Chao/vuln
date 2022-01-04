<template>
  <common-layout>
    <div class="top">
      <div class="header">
        <img alt="logo" class="logo" src="@/assets/img/logo2.png" />
      </div>
      <br/>
      <br/>
    </div>
    <div class="login">
      <a-form :form="form" @submit="handleSubmit" :label-col="{ span: 5 }" :wrapper-col="{ span: 20 }">
        <a-tabs size="large" :tabBarStyle="{textAlign: 'center'}" style="padding: 0 2px;">
          <a-tab-pane tab="账户注册" key="1">
            <a-alert type="error" :closable="true" v-show="error" :message="error" showIcon style="margin-bottom: 24px;" />
            <a-form-item >
              <a-input
                placeholder="用户名"
                v-decorator="[
                  'username',
                  {
                    rules: [{ required: true, message: '请输入您的用户名!', whitespace: true }],
                  },
                ]"
              />
            </a-form-item>
            <a-popover
              placement="rightTop"
              :trigger="['focus']"
              :getPopupContainer="(trigger) => trigger.parentElement"
              v-model="state.passwordLevelChecked">
              <template slot="content">
                <div :style="{ width: '240px' }" >
                  <div :class="['user-register', passwordLevelClass]">强度：<span>{{ passwordLevelName }}</span></div>
                  <a-progress :percent="state.percent" :showInfo="false" :strokeColor=" passwordLevelColor " />
                  <div style="margin-top: 10px;">
                    <span>请至少输入 6 个字符。请不要使用容易被猜到的密码。</span>
                  </div>
                </div>
              </template>
              <a-form-item>
                <a-input
                  placeholder="密码长度至少6位,区分大小写"
                  v-decorator="[
                    'password',
                    {
                      rules: [
                        {
                          required: true,
                          message: '密码长度至少6位,区分大小写',
                        },
                        {
                          validator: this.handlePasswordLevel,
                        },
                      ],
                      validateTrigger: ['change', 'blur']
                    },
                  ]"
                  type="password"
                />
              </a-form-item>
            </a-popover>
            <a-form-item has-feedback>
              <a-input
                placeholder="确认密码"
                v-decorator="[
                  'confirm',
                  {
                    rules: [
                      {
                        required: true,
                        message: '密码长度至少6位,区分大小写',
                      },
                      {
                        validator: this.handlePasswordCheck,
                      },
                    ],
                    validateTrigger: ['change', 'blur']
                  },
                ]"
                type="password"
              />
            </a-form-item>
            <a-form-item>
              <a-input
                placeholder="电子邮箱（非必填）"
                v-decorator="[
                  'email',
                  {
                    rules: [
                      {
                        type: 'email',
                        message: 'The input is not valid E-mail!',
                      },
                      {
                        required: false,
                        message: 'Please input your E-mail!',
                      },
                    ],
                  },
                ]"
              />
            </a-form-item>
            <a-form-item>
              <a-input placeholder="公司名称（非必填）"/>
            </a-form-item>
            <a-form-item>
                <a-input placeholder="从事行业（非必填）"/>
            </a-form-item>
            <a-form-item>
              <a-input
                placeholder="电话号码（必填）"
                v-decorator="[
                  'phone',
                  {
                    rules: [{ required: true, message: '请输入您的手机号!' }],
                  },
                ]"
                style="width: 100%"
              >
                <a-select
                  slot="addonBefore"
                  v-decorator="['prefix', { initialValue: '86' }]"
                  style="width: 70px"
                >
                  <a-select-option value="86">
                    +86
                  </a-select-option>
                  <a-select-option value="87">
                    +87
                  </a-select-option>
                </a-select>
              </a-input>
            </a-form-item>
            <a-form-item
          >
            <a-row :gutter="8">
              <a-col :span="12">
                <a-input :disabled="true"
                  v-decorator="[
                    'captcha',
                    { rules: [{ required: false, message: 'Please input the captcha you got!' }] },
                  ]"
                />
              </a-col>
              <a-col :span="12">
                <a-button>获取验证码</a-button>
              </a-col>
            </a-row>
          </a-form-item>
          </a-tab-pane>
        </a-tabs>
        <a-form-item>
        <a-button
          size="large"
          type="primary"
          style="width: 100%; margin-top: 24px"
          htmlType="submit"
          class="register-button"
          :loading="registerBtn"
          @click.stop.prevent="handleSubmit"
          :disabled="registerBtn">注册
        </a-button>
        <router-link style="float: right; line-height: 40px;" to="/login">使用已有账户登录</router-link>
        </a-form-item>
      </a-form>
    </div>
  </common-layout>
</template>

<script>
import CommonLayout from '@/layouts/CommonLayout'
import axios from 'axios'
const base_url = process.env.VUE_APP_API_BASE_URL

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
  name: 'Register',
  components: {CommonLayout},
  data () {
    return {
      logging: false,
      error: '',
      form: this.$form.createForm(this),
      state: {
        time: 60,
        smsSendBtn: false,
        passwordLevel: 0,
        passwordLevelChecked: false,
        percent: 10,
        progressColor: '#FF0000'
      },
      registerBtn: false,
      base_url: base_url,
    }
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

    handlePasswordCheck (rule, value, callback) {
      const password = this.form.getFieldValue('password')
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    },

    handlePhoneCheck (rule, value, callback) {
      console.log('handlePhoneCheck, rule:', rule)
      console.log('handlePhoneCheck, value', value)
      console.log('handlePhoneCheck, callback', callback)

      callback()
    },

    handleSubmit () {
      let data = {}
      data['username'] = this.form.getFieldValue('username')
      data['password'] = this.form.getFieldValue('password')
      data['email'] = this.form.getFieldValue('email')
      data['phone'] = this.form.getFieldValue('phone')
      console.log(JSON.stringify(data))
      axios.post(this.base_url + '/user/signup',data)
        .then(res => {
          console.log(res.data)
          this.$message.success("注册成功！", 3)
          this.$router.push("/login")
        })
    },
  }
}
</script>

<style lang="less" scoped>
  .common-layout{
    .top {
      text-align: center;
      .header {
        height: 44px;
        line-height: 44px;
        a {
          text-decoration: none;
        }
        .logo {
          height: 44px;
          vertical-align: top;
          margin-right: 16px;
        }
        .title {
          font-size: 33px;
          color: @title-color;
          font-family: 'Myriad Pro', 'Helvetica Neue', Arial, Helvetica, sans-serif;
          font-weight: 600;
          position: relative;
          top: 2px;
        }
      }
      .desc {
        font-size: 14px;
        color: @text-color-second;
        margin-top: 12px;
        margin-bottom: 40px;
      }
    }
    .login{
      width: 500px;
      margin: 0 auto;
      @media screen and (max-width: 576px) {
        width: 95%;
      }
      @media screen and (max-width: 320px) {
        .captcha-button{
          font-size: 14px;
        }
      }
      .icon {
        font-size: 24px;
        color: @text-color-second;
        margin-left: 16px;
        vertical-align: middle;
        cursor: pointer;
        transition: color 0.3s;

        &:hover {
          color: @primary-color;
        }
      }
    }
  }
</style>
