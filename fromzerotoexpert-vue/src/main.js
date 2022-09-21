import Vue from 'vue'
import App from './App.vue'
import router from './router'

Vue.config.productionTip = false
// 引入element-ui依赖
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
Vue.use(Element)

//全局引入axios
import axios from 'axios'
Vue.prototype.$axios = axios

import './axios.js'

new Vue({
  router,
  render: function (h) { return h(App) }
}).$mount('#app')
