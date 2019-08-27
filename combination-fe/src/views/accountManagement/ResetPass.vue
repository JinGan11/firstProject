<template>
  <div class="bg">
    <div style="width: 450px;position: fixed; left: 30%; top: 30%; ">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" size="medium" label-width="100px"
               class="login-container">
        <h2 class="login_title" style="color: white">密码重置</h2>
        <el-form-item label="账号" prop="accountName" class="whiteFont" style="margin-top: -20px">
          <el-input type="text" v-model.trim="usernameComputed" autocomplete="off"></el-input>
        </el-form-item>
        <div style="position: absolute; width: 0px">
          <el-form-item label="">
            <el-input  autocomplete="off"></el-input>
          </el-form-item>
        </div>
        <div style="position: absolute; width: 0px">
          <el-form-item label="">
            <el-input  type="password"  autocomplete="off" ></el-input>
          </el-form-item>
        </div>
        <el-form-item label="新密码" prop="newPass" class="whiteFont">
          <el-input type="password" v-model.trim="ruleForm.newPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="checkPass" class="whiteFont">
          <el-input type="password" v-model.trim="ruleForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item style="text-align: center;margin-left: -50px">
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">清空</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import commonUtils from "../../common/commonUtils";
  import utils from '../../common/util'

  export default {
    data() {
      var validateName =  (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入用户名'));
        }else {
          callback();
        }
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else if (!(/((^(?=.*[a-z])(?=.*[A-Z])(?=.*\W)[\da-zA-Z\W]{8,16}$)|(^(?=.*\d)(?=.*[A-Z])(?=.*\W)[\da-zA-Z\W]{8,16}$)|(^(?=.*\d)(?=.*[a-z])(?=.*\W)[\da-zA-Z\W]{8,16}$)|(^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[\da-zA-Z\W]{8,16}$))/.test(value))) {
          callback(new Error('请输入8-16位字符，至少包含数字、大写字母、小写字母、特殊字符中的三种类型'));
        } else {
          if (this.ruleForm.newPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };

      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.newPass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        ruleForm: {
          accountName: '',
          newPass: '',
          checkPass: '',
        },
        rules: {
          accountName: [
            {validator: validateName, required: true, trigger: 'blur'}
          ],
          newPass: [
            {validator: validatePass, required: true, trigger: 'blur'}
          ],
          checkPass: [
            {validator: validatePass2, required: true, trigger: 'blur'}
          ],
        }
      };
    },
    computed : {
      usernameComputed : {
        get: function(){
          return this.ruleForm.accountName;
        },
        set : function(val){
          this.ruleForm.accountName = val.toLowerCase();
        }
      }
    },
    methods: {
      submitForm(formName) {
        const self = this;
        self.$refs["ruleForm"].validate(function (valid) {
          if (valid) {
            var param = {
              accountName: self.ruleForm.accountName,
              newPassword: self.ruleForm.newPass
            };
            self.$http.post('user/resetPassword', param)
              .then((result) => {
                if (result.code === 200) {
                  self.$confirm('密码修改成功，请重新登录', '提示', {
                    confirmButtonText: '确定',
                    type: 'warning'
                  }).then(() => {
                    utils.$emit("clearLoginSession");
                    self.$router.replace("/")
                  })
                } else if (result.code === 300) {
                  self.$alert("密码不能与前三次相同！", '消息提醒', {
                    confirmButtonText: '确定',
                  });
                } else if (result.code === 212) {
                  self.$alert("账号不存在，请重新输入！", '消息提醒', {
                    confirmButtonText: '确定',
                  });
                } else {
                  self.$alert("旧密码输入错误！!", '消息提醒', {
                    confirmButtonText: '确定',
                  });
                }
              })
              .catch(function (error) {
                commonUtils.Log("user/updatePwd:" + error);
                self.$message.error("修改密码错误");
              });
          } else {
            console.log('error submit!!');
            return false;
          }
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
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
    background-image: url("../../../static/img/bg.jpg");
    background-position: center 0;
  }
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    width: 450px;
    height: 280px;
    padding: 15px 55px 5px 25px;
    background: #fff;
    border: 1px solid #eaeaea;
    z-index: 3;
    box-shadow: 0 0 25px #cac6c6;
    background: rgba(150, 130, 120, 0.5);
  }
  .whiteFont .el-form-item__label {
    color: white;
  }

  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
</style>
