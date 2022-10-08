<template>
  <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="用户账号" prop="loginAccount">
      <el-input v-model="loginForm.loginAccount" label-width="auto"></el-input>
    </el-form-item>
    <el-form-item label="用户密码" prop="loginAccount">
      <el-input v-model="loginForm.loginPassword" label-width="auto" show-password></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
      <el-button @click="resetForm('loginForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      loginForm: {
        loginAccount: '',
        loginPassword: ''
      },
      rules: {
        loginAccount: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        loginPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("/login", this.loginForm).then(res => {
            if (res.data.code == 0) {
              alert(res.data.data)
            }
          })
        } else {
          console.log('表单验证不通过！');
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>

</style>