<template>
  <div :data="websitedataform">
    ip: <span>{{websitedataform.ip}}</span>
    <el-divider direction="vertical"></el-divider>
    pv: <span>{{websitedataform.pv}}</span>
    <el-divider direction="vertical"></el-divider>
    uv: <span>{{websitedataform.uv}}</span>
  </div>
</template>

<script>

export default {
  name: "websitedata",
  data() {
    return {
      websitedataform: {
        ip: this.$cookies.get("ip"),
        pv: this.$cookies.get("pv"),
        uv: this.$cookies.get("uv"),
      },
    }
  },
  created() {
    // const that = this
    // this.global.ws.onmessage = function(res) {
    //   res = JSON.parse(res.data)
    //   // console.log("收到服务器内容", res.ip)
    //   that.$cookies.set("ip", res.ip)
    //   that.$cookies.set("pv", res.pv)
    //   that.$cookies.set("uv", res.uv)
      // console.log(that.websitedataform)
    // };
  }
}
</script>

<style scoped>

</style>