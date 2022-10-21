<template>
<div>
  <el-container>
    <el-header style="background-color: darkgrey">
      <div>
      <span>今日网站访问数据</span>
      <websitedata style="margin: auto"></websitedata>
      <span>尊敬的:&nbsp;</span>
      <span style="color: orange">{{this.$cookies.get("username")}}</span>
      <span>&nbsp;&nbsp;用户</span>
      <span>欢迎来到from zero to expert</span>
      <el-link type="primary" style="float: right" @click="logout()">退出登录</el-link>
      </div>
    </el-header>
  </el-container>
</div>
</template>

<script>
import Websitedata from "@/components/websitedata";
export default {
  name: "fromzerotoexpert",
  components: {Websitedata},
  data() {
    return {
    }
  },
  methods: {
    logout() {
      this.$axios.get('/logout').then(res => {
        // console.log(res)
        if (res.data.code === 0) {
          this.$cookies.remove("username")
          this.$router.push('/login')
          alert("退出成功")
        } else {
          alert(res.data.msg)
          this.$cookies.remove("username")
          this.$router.push('/login')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>