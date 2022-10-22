import Vue from 'vue'
import App from './App.vue'
import router from './router'
// import store from './store'

Vue.config.productionTip = false
// 引入element-ui依赖
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
Vue.use(Element)

//全局引入axios
import axios from 'axios'
Vue.prototype.$axios = axios

import VueCookies from 'vue-cookies'
import cookies from "vue-cookies";
Vue.use(VueCookies)

axios.defaults.baseURL="http://47.98.198.2:8060/fromzerotoexpert"
axios.defaults.withCredentials=true

import global from './global.js'
Vue.prototype.global = global

//后置拦截
axios.interceptors.response.use(response => {
       let res = response.data;
       // console.log("===========")
       // console.log(res)
       // console.log("===========")
       global.ws.onmessage = function(res) {
           // console.log(res)
           sessionStorage.setItem("websiteData", res.data)
           // console.log(sessionStorage.getItem("websiteData"))
           // res = JSON.parse(res.data)
           // this.$cookies.set("ip", res.ip)
           // this.$cookies.set("pv", res.pv)
           // this.$cookies.set("uv", res.uv)
           // console.log("===========")
           // console.log(this.$cookies.get("ip"))
           // console.log(this.$cookies.get("pv"))
           // console.log(this.$cookies.get("uv"))
           // console.log("===========")
       }



        if (res.code === 0) {
        return response
       }else {
        alert(res.msg)
        router.push("/login")
       }
    },
    error => {
        console.log("error")
        return Promise.reject(error)
    }
)



// 路由判断登录 根据路由配置文件的参数
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requireAuth)) { // 判断该路由是否需要登录权限
    // axios.get('/isLogin').then(res => {
    //   console.log(res.data)
    //   if (res.data.code === 0) {
    //     next()
    //   } else {
    //     alert(res.data.data)
    //     next({
    //       path: '/login'
    //     })
    //   }
    // }
    const username = cookies.get("username")
    if (typeof username == "undefined" || username == null || username == "") {
        // alert("请先登录")
        next({
            path: '/login'
        })
    } else {
        next()
    }
  } else {
    next()
  }
})


new Vue({
  router,
  // store,
  render: function (h) { return h(App) }
}).$mount('#app')
