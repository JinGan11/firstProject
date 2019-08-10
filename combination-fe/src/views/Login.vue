<template>
  <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" class="login-container" label-position="left"
           label-width="0px" v-loading="loading">
    <h3 class="login_title">系统登录</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="loginForm.username"
                autocomplete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="loginForm.password"
                autocomplete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%" @click="submitForm()">登录</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
  import App from '../App'
  import utils from '../common/util'
  export default{
    data(){
      return {
        rules: {
          username: [{required: true, message: '用户名不能为空', trigger: ['blur','change']}],
          password: [{required: true, message: '密码不能为空', trigger: ['blur','change']}]
        },
        checked: true,
        loginForm: {
          username: '',
          password: '',
        },
        loginMsg: '',
        visible: false,
        loading: false
      }
    },
    created() {
      localStorage.setItem('isLogin', 'false');
    },
    mounted() {
    },
    components: {App},
    methods: {
      submitForm () {
        const self = this
        self.$refs["loginForm"].validate(function(valid) {
          if (valid) {
            var param = {
              accountName: self.loginForm.username,
              accountPassword: self.loginForm.password
            }
            self.$http.post('login/login.do_', param)
              .then(result => {
                if (result.code === 200) {
                  utils.$emit("loginSuccess",true,self.loginForm.username, result.powerList);
                  self.$router.replace("/index");
                  // self.visible = true
                } else {
                  self.$alert("账号或者密码错误", '消息提醒', {
                    confirmButtonText: '确定',
                  });
                }
              })
              .catch(function (error) {
                commonUtils.Log("user/updatePwd:" + error);
                self.$message.error("登陆错误，请联系管理员！");
              });
          } else {
            console.log('error submit!!');
            return false;
          }
        })
      },
    }
  }
</script>
<style>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
  .login_remember {
    margin: 0px 0px 35px 0px;
    text-align: left;
  }
</style>
