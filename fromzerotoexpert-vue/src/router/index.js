import Vue from 'vue'
import VueRouter from 'vue-router'
import Register from "@/views/user/register";
import Login from "@/views/user/login";
import Fromzerotoexpert from "@/views/fromzerotoexpert";
import Websitedatadetail from "@/views/websitedatadetail";
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    redirect: {name: 'fromzerotoexpert'}
  },
  {
    path: '/fromzerotoexpert',
    name: 'fromzerotoexpert',
    component: Fromzerotoexpert,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/register',
    name: 'register',
    component: Register,
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/websitedatadetail',
    name: 'websitedatadetail',
    component: Websitedatadetail,
    meta: {
      requireAuth: true
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
