<template>
  <div :data="websitedataform">
    <div>
      ip: <span>{{websitedataform.ip}}</span>
      <el-divider direction="vertical"></el-divider>
      pv: <span>{{websitedataform.pv}}</span>
      <el-divider direction="vertical"></el-divider>
      uv: <span>{{websitedataform.uv}}</span>
    </div>
    <router-link to="/websitedatadetail">查询以往网站访问数据</router-link>
  </div>
</template>

<script>

import global from "@/global";

export default {
  name: "websitedata",
  data() {
    return {
      websitedataform: {
        date:'',
        ip: '',
        uv: '',
        pv: '',
      }
    }
  },
  created() {
    // console.log(sessionStorage.getItem("websiteData"))
    this.websitedataform = JSON.parse(sessionStorage.getItem("websiteData"))
  }
}
</script>

<style scoped>

</style>