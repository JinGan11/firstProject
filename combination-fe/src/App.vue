<template>
  <div>
    <el-container ref="container" class="view-container" v-if="loginIn">
      <aside-menu :isCollapse="isCollapse" :data="data"></aside-menu>
      <div class="contentBox" :class="{'content-collapse':isCollapse}">
        <el-container>
          <el-header style="background-color: #1E90FF;">
            <el-row style="line-height: 60px;">
              <el-col :span="2">
                <el-button type="text" style="color: white;" icon="el-icon-menu" @click="btnClick"></el-button>
              </el-col>
              <el-col :span="22" align="right">
                <span class="displaySwitch" style="color: white">日志：</span>
                <el-switch
                  v-model="isLog"
                  class="displaySwitch"
                  @change="switchLog"
                  active-color="#13ce66">
                </el-switch>

                <el-dropdown @command="handleCommand">
  <span class="el-dropdown-link home_userinfo" style="display: flex;align-items: center;font-size: 23px;color: #FFF;">
    <span style="margin-bottom: 7px;">{{loginUserName}}</span>
    <i><img src="http://bpic.588ku.com/element_pic/01/40/00/64573ce2edc0728.jpg"
            style="width: 35px;height: 35px;margin-right: 5px;margin-left: 5px;border-radius: 40px;margin-top:13px "/></i>
  </span>
                  <el-dropdown-menu :size="medium" slot="dropdown" style="position: relative;margin-top: -10px">
                    <el-dropdown-item command="getUserInfo">个人信息</el-dropdown-item>
                    <el-dropdown-item command="resetPassword">修改密码</el-dropdown-item>
                    <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
                <!--<el-dropdown @command="handleCommand">-->
                  <!--<span class="el-dropdown-link" style="font-size: 22px">-->
                    <!--{{loginUserName}}<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
                  <!--</span>-->
                  <!--<el-dropdown-menu slot="dropdown">-->
                    <!--<el-dropdown-item style="font-size: 18px" command="getUserInfo">个人信息</el-dropdown-item>-->
                    <!--<el-dropdown-item style="font-size: 18px" command="resetPassword">修改密码</el-dropdown-item>-->
                    <!--<el-dropdown-item style="font-size: 18px" command="logout">注销登陆</el-dropdown-item>-->
                  <!--</el-dropdown-menu>-->
                <!--</el-dropdown>-->
              </el-col>
            </el-row>
          </el-header>
          <el-main>
            <el-breadcrumb separator-class="el-icon-arrow-right">
              <el-breadcrumb-item replace
                                  v-for='(item,index) in levelList'
                                  :key='item.id'
                                  separator="/"
                                  :to="{ path: item.path }">
                {{item.name}}
              </el-breadcrumb-item>
            </el-breadcrumb>
            <el-scrollbar class="scrollbar">
              <keep-alive>
                <router-view v-if="$route.meta.keepAlive"></router-view>
              </keep-alive>
              <router-view v-if="!$route.meta.keepAlive"></router-view>
            </el-scrollbar>
          </el-main>
        </el-container>
      </div>
      <el-dialog title="基本信息查看" :visible.sync="accountInfoFlag" :close-on-click-modal="false" width="700px">
        <div class="dialog-main">
          <el-form :inline="true" :model="StaffInfo" class="demo-form-inline" label-width="100px">
            <el-form-item label="员工编号">
              <el-input v-model="StaffInfo.staffNum" readonly="true" placeholder="无"></el-input>
            </el-form-item>
            <el-form-item label="员工姓名">
              <el-input v-model="StaffInfo.staffName" readonly="true" placeholder="无"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-input v-model="sex" readonly="true" placeholder="无" ></el-input>
            </el-form-item>
            <el-form-item label="员工手机">
              <el-input v-model="StaffInfo.staffTelephone" readonly="true" placeholder="无"></el-input>
            </el-form-item>
            <el-form-item label="员工邮箱">
              <el-input v-model="StaffInfo.staffEmail" readonly="true" placeholder="无"></el-input>
            </el-form-item>
            <el-form-item label="归属部门">
              <el-input v-model="StaffInfo.departmentName" readonly="true" placeholder="无"></el-input>
            </el-form-item>
            <el-form-item label="是否离职">
              <el-input v-model="isDimission" readonly="true" placeholder="无"></el-input>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <el-dialog title="基本信息查看" :visible.sync="resetPasswordFlag" :close-on-click-modal="false" width="700px">
        <div class="dialog-main">
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" size="medium" label-width="100px"
                   class="demo-ruleForm" >
            <el-form-item label="旧密码">
              <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPass">
              <el-input type="password" v-model="ruleForm.newPass" autocomplete="off" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item label="确认新密码" prop="checkPass">
              <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item style="text-align: center">
              <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
              <el-button style="margin-bottom: 20px;margin-left: 20px" @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
    </el-container>

    <div id="abc" v-else>
      <login-page @loginSuccess="loginSuccess"></login-page>
    </div>
  </div>
</template>

<script>
  import MenuItem from './components/MenuItem'
  import AsideMenu from './components/AsideMenu'
  import LoginPage from './views/Login'
  import utils from './common/util'

  export default {
    data() {
      let validatePass = (rule, value, callback) => {
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
      let validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.newPass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        loginIn: false,
        isLog: false,
        isCollapse: false,
        data: [],
        levelList: null,
        name: 'app',
        loginUserName: null,
        accountInfoFlag: false,
        resetPasswordFlag: false,
        StaffInfo: {},
        sex: null,
        isDimission: null,
        ruleForm: {
          oldPass: '',
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
    created() {
      this.getCookie();
      const self = this;
      var log = localStorage.getItem("switchLog");
      // self.loginIn = localStorage.getItem('isLogin');
      if (log) {
        self.isLog = true;
      }
      self.$http.get('sys/menu/list.do_').then((result) => {
        self.data = result.menuList;
      });
      self.getBreadcrumb(true);
    },
    mounted() {
      utils.$on('loginSuccess', (loginFlag, username) => {
        this.loginSuccess(loginFlag, username);
      });
      utils.$on('clearLoginSession', () => {
        this.clearLoginSession();
      });
      if (window.sessionStorage.getItem("loginUsername") === null) {
        this.loginIn = false
      } else {
        this.loginIn = true
      }
    },
    components: {MenuItem, AsideMenu, LoginPage},
    methods: {
      loginSuccess(isSuccess, username) {
        const self = this;
        self.loginIn = isSuccess;
        window.sessionStorage.setItem("loginUsername", username);
        self.loginUserName = window.sessionStorage.getItem("loginUsername")
      },
      switchLog() {
        if (this.isLog) {
          localStorage.setItem("switchLog", true);
        } else {
          localStorage.removeItem("switchLog");
        }
      },
      btnClick() {
        const self = this;
        self.isCollapse = !self.isCollapse;
        if (self.isCollapse) {
          self.sysTitleShow = "";
        } else {
          self.sysTitleShow = self.sysTitle;
        }
      },
      getBreadcrumb(isReload) {
        var breadNumber = typeof (this.$route.meta.breadNumber) != "undefined" ? this.$route.meta.breadNumber : 1;//url变量breadNum记录层级，默认为1，如果大于1，要添加上变量；
        var breadLength = this.$store.state.menuListState != null ? this.$store.state.menuListState.length : 0;//目前breadlist集合数组个数
        var curName = this.$route.name;
        var curPath = this.$route.fullPath;
        var newBread = {name: curName, path: curPath};
        var ishome = curName == '首页';
        if (breadNumber === 0 || breadNumber === 1) {//点击首页或者一级
          this.$store.commit('menuListStateRemove', 1);//初始化，只有首页面包屑按钮
          if (breadNumber === 1) {//点击一级菜单
            this.$store.commit('menuListStateAdd', newBread);//当前页面添加到breadlist集合
          }
        } else {
          if (!isReload) {
            if (breadLength <= breadNumber) {//breadlist集合个数等于或者小于目前层级breadNumber
              this.$store.commit('menuListStateAdd', newBread);//要把当前路由添加到breadlist集合
            } else {
              this.$store.commit('menuListStateRemove', parseInt(breadNumber) + 1);//如果往回点面包屑导航，截取；
            }
          } else {//刷新，state.menuListState被初始化，从缓存取值；
            this.$store.state.menuListState = JSON.parse(localStorage.getItem('menuListStorage'));
          }
        }
        this.levelList = this.$store.state.menuListState;
        localStorage.setItem("menuListStorage", JSON.stringify(this.$store.state.menuListState));
      },
      handleCommand(cmd) {
        var self = this;
        if (cmd == 'log') {
          localStorage.setItem("log", true);
        } else if (cmd == 'logout') {
          self.$confirm('注销登录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            self.$http.get('login/logout.do_')
              .then(result => {
                self.loginIn = false;
                window.sessionStorage.removeItem("loginUsername");
                self.loginUserName = window.sessionStorage.getItem("loginUsername");
                self.$router.replace("/");
                self.$message({
                  type: "success",
                  message: "退出成功"
                });
              })
              .catch(function (error) {
                commonUtils.Log("user/updatePwd:" + error);
                self.$message.error("系统故障，请联系管理员！");
              });
          }).catch(() => {
            self.$message({
              type: 'info',
              message: '取消'
            });
          });
        } else if(cmd == "getUserInfo") {
          var param = {
            accountName: window.sessionStorage.getItem("loginUsername")
          }
          self.$http.post('user/getEmpInfo', param)
            .then(result => {
              if (result.code === 200) {
                console.log(result);
                self.StaffInfo = result.list[0];
                self.sex = result.sex;
                self.isDimission = result.isDimission;
              } else {
                self.$alert("查询失败，请稍后再试！", '消息提醒', {
                  confirmButtonText: '确定',
                });
              }
            })
            .catch(function (error) {
              commonUtils.Log("user/updatePwd:" + error);
              self.$message.error("系统故障，请联系管理员！");
            });
          self.accountInfoFlag = true;
        } else if (cmd == "resetPassword") {
          self.resetPasswordFlag = true;
        }
      },
      clearLoginSession: function () {
        const self = this;
        self.loginIn = false;
        window.sessionStorage.removeItem("loginUsername");
        self.loginUserName = window.sessionStorage.getItem("loginUsername");
      },
      getCookie: function () {
        if (document.cookie.length > 0) {
          var arr = document.cookie.split('; '); //这里显示的格式需要切割一下自己可输出看下
          for (var i = 0; i < arr.length; i++) {
            var arr2 = arr[i].split('='); //再次切割
            //判断查找相对应的值
            if (arr2[0] == 'userName') {
              this.loginUserName = arr2[1]; //保存到保存数据的地方
            }
          }
        }
      },
      submitForm(formName) {
        const self = this;
        self.$refs["ruleForm"].validate(function(valid) {
          if (valid) {
            var param = {
              accountName: window.sessionStorage.getItem("loginUsername"),
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
      },
    },
    watch: {
      $route() {
        this.getBreadcrumb();
      }
    }
  }
</script>

<style>
  @import "./css/combination.css";

  .displaySwitch {
    display: none;
  }

  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
