<template>
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
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: "register",
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
            if (res.data.code == 0) {
              alert("注册成功")
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