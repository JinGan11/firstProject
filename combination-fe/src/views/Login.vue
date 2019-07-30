<template>
  <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" class="login-container" label-position="left"
           label-width="0px" v-loading="loading">
    <h3 class="login_title">系统登录</h3>
    <el-popover
      placement="top"
      width="260"
      v-model="visible">
      <div style="text-align: center; margin: 0">
        <h3 style="padding-bottom: 10px">用户名或密码错误！</h3>
        <el-button size="mini" type="text" @click="visible = false" style="margin-right: 25px;font-size: 18px">取消</el-button>
        <el-button type="primary" size="mini" @click="visible = false" style="font-size: 18px">确定</el-button>
      </div>
    </el-popover>
    <el-form-item prop="username">
      <el-input type="text" v-model="loginForm.username"
                autocomplete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="loginForm.password"
                autocomplete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-checkbox class="login_remember" v-model="checked"
                 label-position="left">记住密码</el-checkbox>
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
          checked: ''
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
      this.getCookie()
    },
    components: {App},
    methods: {
      submitForm () {
        const self = this
        self.$refs["loginForm"].validate(function(valid) {
          if (self.checked === true) {
            console.log("checked == true");
            //传入账号名，密码，和保存天数3个参数
            self.setCookie(self.loginForm.username, self.loginForm.password, 7);
          }else {
            console.log("清空Cookie");
            //清空Cookie
            self.clearCookie();
          }
          if (valid) {
            var param = {
              accountName: self.loginForm.username,
              accountPassword: self.loginForm.password
            }
            self.$http.post('login/login', param)
              .then(result => {
                console.log(result.code);
                if (result.code === 300) {
                  // self.loginMsg = result.msg;
                  self.visible = true
                } else {
                  // this.$emit("loginSuccess", true);
                  utils.$emit("loginSuccess",true)
                  App.methods.loginSuccess(true)
                  console.log(11);
                  self.$router.replace("/index")
                }
              })
              .catch(result => {
                alert("请联系管理员！")
              })
          } else {
            console.log('error submit!!');
            return false;
          }
        })
      },
      //设置cookie
      setCookie(c_name, c_pwd, exdays) {
        var exdate = new Date(); //获取时间
        exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * exdays); //保存的天数
        //字符串拼接cookie
        window.document.cookie = "userName" + "=" + c_name + ";path=/;expires=" + exdate.toGMTString();
        window.document.cookie = "userPwd" + "=" + c_pwd + ";path=/;expires=" + exdate.toGMTString();
      },
      //读取cookie
      getCookie: function() {
        if (document.cookie.length > 0) {
          var arr = document.cookie.split('; '); //这里显示的格式需要切割一下自己可输出看下
          for (var i = 0; i < arr.length; i++) {
            var arr2 = arr[i].split('='); //再次切割
            //判断查找相对应的值
            if (arr2[0] == 'userName') {
              this.loginForm.username = arr2[1]; //保存到保存数据的地方
            } else if (arr2[0] == 'userPwd') {
              this.loginForm.password = arr2[1];
            }
          }
        }
      },
      //清除cookie
      clearCookie: function() {
        this.setCookie("", "", -1); //修改2值都为空，天数为负1天就好了
      }
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
