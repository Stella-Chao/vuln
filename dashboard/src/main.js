import Vue from 'vue'
import App from './App.vue'
import './assets/common.less'
import dataV from '@jiaminghi/data-view'
import Router from 'vue-router'

Vue.config.productionTip = false

Vue.use(dataV)
Vue.use(Router)

new Vue({
  render: h => h(App)
}).$mount('#app')
