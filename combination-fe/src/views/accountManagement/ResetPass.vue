<template>
  <div style="width: 500px;position: fixed; left: 30%; top: 30%;">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" size="medium" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="账号">
        <el-input type="text" v-model="ruleForm.accountName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPass">
        <el-input type="password" v-model="ruleForm.newPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认新密码" prop="checkPass">
        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import App from '../../App'
  import commonUtils from "../../common/commonUtils";
  import utils from '../../common/util'
  export default {
    data() {
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
          newPass: [
            {validator: validatePass, trigger: 'blur'}
          ],
          checkPass: [
            {validator: validatePass2, trigger: 'blur'}
          ],
        }
      };
    },
    methods: {
      submitForm(formName) {
        const self = this;
        self.$refs["ruleForm"].validate(function(valid) {
          if (valid) {
            var param = {
              accountName: self.ruleForm.accountName,
              oldPassword: self.ruleForm.oldPass,
              newPassword: self.ruleForm.newPass
            };
            self.$http.post('user/updatePassword', param)
              .then((result) => {
                if (result.code === 200) {
                  self.$confirm('密码修改成功，请重新登录', '提示', {
                    confirmButtonText: '确定',
                    type: 'warning'
                  }).then(() => {
                    utils.$emit("clearLoginSession");
                    App.methods.clearLoginSession();
                    self.$router.replace("/")
                  })
                } else if (result.code === 300) {
                  self.$alert("密码不能与前三次相同！", '消息提醒', {
                    confirmButtonText: '确定',
                  });
                } else {
                  self.$alert("旧密码输入错误！", '消息提醒', {
                    confirmButtonText: '确定',
                  });
                }
              })
              .catch(function (error) {
                commonUtils.Log("user/updatePwd:" + error);
                self.$message.error("修改密码错误");
              });
          }else {
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

<style scoped>

</style>
