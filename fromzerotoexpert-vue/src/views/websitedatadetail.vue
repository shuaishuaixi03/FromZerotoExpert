<template>
  <el-container>
<!--    <el-header>-->

<!--    </el-header>-->
    <el-main>
      <el-table
          :data="websitedataform"
          style="width: 100%">
        <el-table-column
            prop="date"
            label="日期"
            width="180">
        </el-table-column>
        <el-table-column
            prop="ip"
            label="ip"
            width="180">
        </el-table-column>
        <el-table-column
            prop="uv"
            label="uv"
            width="180">
        </el-table-column>
        <el-table-column
            prop="pv"
            label="pv">
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer>
      <router-link to="/fromzerotoexpert">返回首页</router-link>
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name: "websitedatadetail",
  data() {
    return {
      websitedataform:[{

      }]
    }
  },
  created() {
    this.$axios.get("/websitedata/getall").then(res => {
      // console.log(res)
      this.websitedataform = res.data.data
    })
  }
}
</script>

<style scoped>

</style>