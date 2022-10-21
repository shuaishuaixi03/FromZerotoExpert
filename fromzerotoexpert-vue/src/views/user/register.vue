<template>
  <div>
    <h1>注册页面</h1>
    <ol style="text-align: left"><span style="color: cornflowerblue">长度限制: </span>昵称:2-10位，账号、密码:6-16位</ol>
    <ol style="text-align: left"><span style="color: cornflowerblue">格式限制: </span>账号:纯数字，密码:必须包含数字和字母，邮箱:正确的邮箱格式</ol>
    <ol style="text-align: left"><span style="color: chartreuse">账号注册成功会有提示框, 一直注册不成功可能是账号或昵称已被使用</span></ol>
    <el-form :model="registerFrom" :rules="rules" ref="registerFrom" label-width="100px" class="demo-ruleForm">
      <el-form-item label="用户昵称" prop="userName">
        <el-input v-model="registerFrom.userName" label-width="auto"></el-input>
      </el-form-item>
      <el-form-item label="用户账号" prop="userAccount">
        <el-input v-model="registerFrom.userAccount" label-width="auto"></el-input>
      </el-form-item>
      <el-form-item label="用户密码" prop="userPassword">
        <el-input v-model="registerFrom.userPassword" label-width="auto" show-password></el-input>
      </el-form-item>
      <el-form-item label="用户邮箱" prop="userEmail">
        <el-input v-model="registerFrom.userEmail" label-width="auto"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('registerFrom')">注册</el-button>
        <el-button @click="resetForm('registerFrom')">重置</el-button>
        <el-button @click="gotoLogin()" type="info">去登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
// import Websitedata from "@/components/websitedata";

export default {
  name: "register",
  // components: {Websitedata},
  data() {
    return {
      registerFrom: {
        userName: '',
        userAccount: '',
        userPassword: '',
        userEmail: '',
      },
      rules: {
        userName: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        userAccount: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        userPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        userEmail: [
          { required: true, message: '请输入邮箱'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("/register", this.registerFrom).then(res => {
              alert("注册成功")
          })
        } else {
          console.log('表单验证不通过！');
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    gotoLogin() {
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>

</style>