<template>
  <div class="bg">
    <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" class="login-container" label-position="left"
             label-width="0px" v-loading="loading">
      <h3 class="login_title" style="color: white">系统登录</h3>
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
  </div>
</template>
<script>
  import App from '../App'
  import utils from '../common/util'
  import commonUtils from "../common/commonUtils";
  import global from '../common/global'

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
      const self = this
      self.$http.get('login/isLogin')
        .then(result => {
          if (result.code === 202) {
            utils.$emit("loginSuccess",true,result.accountName, result.powerList);
            self.$router.replace("/index");
          }
        })
        .catch(function (error) {
          commonUtils.Log("user/updatePwd:" + error);
          self.$message.error("登陆错误，请联系管理员！");
        });
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
                  localStorage.setItem("isLogin",1);
                  global.accountName = result.accountName;
                  localStorage.setItem("accountName",result.accountName);
                  utils.$emit("loginSuccess",true,result.accountName, result.powerList);
                  self.$router.replace("/index");
                  // self.visible = true
                } else if (result.code === 300) {
                  self.$alert("没有该用户", '消息提醒', {
                    confirmButtonText: '确定',
                  });
                }else if (result.code === 303) {
                  self.$alert(result.Msg, '消息提醒', {
                    confirmButtonText: '确定',
                  });
                }else if (result.code === 202) {
                  utils.$emit("loginSuccess",true,result.accountName, result.powerList);
                  self.$confirm('已有账号登陆，自动为您跳转！如需登陆其它账号请先退出！', '提示', {
                    showCancelButton:false,
                    showClose:false,
                    confirmButtonText: '确定',
                    type: 'warning'
                  });
                  self.$router.replace("/index");
                } else if (result.code === 506){
                  self.$confirm('首次登陆系统，请先修改密码', '提示', {
                    showCancelButton:false,
                    showClose:false,
                    confirmButtonText: '确定',
                    cancelButtonText: '',
                    type: 'warning'
                  }).then(() => {
                    utils.$emit("loginSuccess",true,self.loginForm.username, result.powerList);
                    utils.$emit("isFirstLogin");
                    self.$router.replace("/index");
                  }).catch(() => {
                    self.$message({
                      type: 'info',
                      message: '取消'
                    });
                  });
                } else if (result.code === 507){
                  self.$confirm('已超过60天，请重置密码', '提示', {
                    showCancelButton:false,
                    showClose:false,
                    confirmButtonText: '确定',
                    cancelButtonText: '',
                    type: 'warning'
                  }).then(() => {
                    utils.$emit("loginSuccess",true,self.loginForm.username, result.powerList);
                    utils.$emit("isFirstLogin");
                    self.$router.replace("/index");
                  }).catch(() => {
                    self.$message({
                      type: 'info',
                      message: '取消'
                    });
                  });
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
  .bg {
    position: fixed;
    overflow: hidden;
    height: 100%;
    width: 100%;
    display: flex;
    background-size: cover;
    justify-content: center;
    align-items: center;
    background-repeat: no-repeat;
    background-image: url("../../static/img/bg.jpg");
    background-position: center 0;
  }
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    z-index: 5;
    box-shadow: 0 0 25px #cac6c6;
    background: rgba(150, 130, 120, 0.5);
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
