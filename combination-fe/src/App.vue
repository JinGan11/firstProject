<template>
  <div>
    <div id="resetPass" v-if="$route.path ==='/resetPass'">
      <reset-pass></reset-pass>
    </div>

    <div id="abc" v-else-if="$route.path.slice(-6)==='/'">
      <login-page @loginSuccess="loginSuccess"></login-page>
    </div>

    <el-container ref="container" class="view-container" v-else-if="loginIn">
    <!--<el-container ref="container" class="view-container" v-if="$route.path.slice(-6)!=='/index'">-->
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
                  <el-dropdown-menu slot="dropdown" style="position: relative;margin-top: -10px">
                    <el-dropdown-item command="getUserInfo">个人信息</el-dropdown-item>
                    <el-dropdown-item command="resetPassword">修改密码</el-dropdown-item>
                    <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
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
              <el-input v-model="sex" readonly="true" placeholder="无"></el-input>
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
      <el-dialog title="密码重置" :visible.sync="resetPasswordFlag" :close-on-click-modal="false" width="700px" @close="cencelDialog">
        <div class="dialog-main">
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" size="medium" label-width="100px"
                   class="demo-ruleForm">
            <el-form-item label="旧密码" prop="oldPass">
              <el-input type="password" v-model.trim="ruleForm.oldPass" autocomplete="off" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPass">
              <el-input type="password" v-model.trim="ruleForm.newPass" autocomplete="off" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item label="确认新密码" prop="checkPass">
              <el-input type="password" v-model.trim="ruleForm.checkPass" autocomplete="off" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item style="text-align: center">
              <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
              <el-button style="margin-bottom: 20px;margin-left: 20px" @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <el-dialog title="密码重置" :show-close="showClose" :visible.sync="resetPasswordStaticFlag" :close-on-click-modal="false" width="700px">
        <div class="dialog-main">
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" size="medium" label-width="100px"
                   class="demo-ruleForm">
            <el-form-item label="旧密码" prop="oldPass">
              <el-input type="password" v-model.trim="ruleForm.oldPass" autocomplete="off" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPass">
              <el-input type="password" v-model.trim="ruleForm.newPass" autocomplete="off" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item label="确认新密码" prop="checkPass">
              <el-input type="password" v-model.trim="ruleForm.checkPass" autocomplete="off" style="width: 70%"></el-input>
            </el-form-item>
            <el-form-item style="text-align: center">
              <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
              <el-button style="margin-bottom: 20px;margin-left: 20px" @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
    </el-container>

  </div>
</template>

<script>
  import MenuItem from './components/MenuItem'
  import AsideMenu from './components/AsideMenu'
  import LoginPage from './views/Login'
  import utils from './common/util'
  import ResetPass from './views/accountManagement/ResetPass'
  import global from './common/global.js'
  export default {
    data() {
      let validateOldPass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else if (!(/((^(?=.*[a-z])(?=.*[A-Z])(?=.*\W)[\da-zA-Z\W]{8,16}$)|(^(?=.*\d)(?=.*[A-Z])(?=.*\W)[\da-zA-Z\W]{8,16}$)|(^(?=.*\d)(?=.*[a-z])(?=.*\W)[\da-zA-Z\W]{8,16}$)|(^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[\da-zA-Z\W]{8,16}$))/.test(value))) {
          callback(new Error('旧密码不符合密码命名规则'));
        } else {
          if (this.ruleForm.newPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      let validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else if (!(/((^(?=.*[a-z])(?=.*[A-Z])(?=.*\W)[\da-zA-Z\W]{8,16}$)|(^(?=.*\d)(?=.*[A-Z])(?=.*\W)[\da-zA-Z\W]{8,16}$)|(^(?=.*\d)(?=.*[a-z])(?=.*\W)[\da-zA-Z\W]{8,16}$)|(^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[\da-zA-Z\W]{8,16}$))/.test(value))) {
          callback(new Error('新密码不符合密码命名规则'));
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
          callback(new Error('确认密码与新密码不一致，请重新定义'));
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
        resetPasswordStaticFlag: false,
        showClose:false,
        StaffInfo: {
          staffNum:'',
          staffName:'',
          staffTelephone:'',
          staffEmail:'',
          departmentName:''
        },
        sex: null,
        isDimission: null,
        ruleForm: {
          oldPass: '',
          newPass: '',
          checkPass: '',
        },
        rules: {
          oldPass: [
            {validator: validateOldPass, required: true, trigger: 'blur'}
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
    watch: {
      'global.accountName' : 'refresh'
    },
    created() {
      const self = this;
      // console.log($route.path.slice(-6));
      var log = localStorage.getItem("switchLog");
      // self.loginIn = localStorage.getItem('isLogin');
      if (log) {
        self.isLog = true;
      }
      // 在页面加载时读取sessionStorage里的状态信息
      if (sessionStorage.getItem('store')) {
        self.$store.replaceState(Object.assign({}, self.$store.state, JSON.parse(sessionStorage.getItem('store'))))
        self.data = self.$store.state.menuList;
        self.loginUserName = self.$store.state.loginUserName;
      }
      // 在页面刷新时将vuex里的信息保存到sessionStorage里
      window.addEventListener('beforeunload', () => {
        sessionStorage.setItem('store', JSON.stringify(self.$store.state));
      })
    },
    mounted() {
      this.timer = setInterval(this.get, 1000);
      utils.$on('isFirstLogin', () => {
        this.resetPasswordStaticFlag = true;
      });
      utils.$on('loginSuccess', (loginFlag, username, powerList) => {
        this.loginSuccess(loginFlag, username, powerList);
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
    components: {MenuItem, AsideMenu, LoginPage, ResetPass},
    methods: {
      refresh() {
        this.loginIn = false;
        this.$nextTick(() => {
          this.loginIn = true
        })
      },
      loginSuccess(isSuccess, username, powerList) {
        const self = this;
        self.loginIn = isSuccess;
        self.$store.state.loginUserName = username;
        self.$store.state.powerList = powerList;
        self.loginUserName = username;
        window.sessionStorage.setItem("loginUsername", username);
        self.$http.post('sys/menu/list.do_').then((result) => {
          self.data = result.menuList;
          self.$store.state.menuList = result.menuList;
        });
        self.getBreadcrumb(true);
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
          window.localStorage.setItem("log", true);
        } else if (cmd == 'logout') {
          self.$confirm('注销登录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            self.$http.post('login/logout.do_')
              .then(result => {
                self.loginIn = false;
                window.sessionStorage.removeItem("loginUsername");
                window.sessionStorage.removeItem("powerList");
                self.$store.state.loginUserName = '';
                self.$store.state.powerList = '';
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
        } else if (cmd == "getUserInfo") {
          var param = {
            accountName: window.sessionStorage.getItem("loginUsername")
          }
          self.$http.post('user/getEmpInfo', param)
            .then(result => {
              if (result.code === 200) {
                if (result.list[0]) {
                  self.StaffInfo = result.list[0];
                }else {
                  self.StaffInfo.staffNum = '';
                  self.StaffInfo.staffName = '';
                  self.StaffInfo.staffTelephone = '';
                  self.StaffInfo.staffEmail = '';
                  self.StaffInfo.departmentName = '';
                }
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
        self.$store.state.loginUserName = null;
        self.loginUserName = window.sessionStorage.getItem("loginUsername");
      },
      submitForm(formName) {
        const self = this;
        self.$refs["ruleForm"].validate(function (valid) {
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
                    showCancelButton:false,
                    showClose:false,
                    confirmButtonText: '确定',
                    type: 'warning'
                  }).then(() => {
                    utils.$emit("clearLoginSession");
                    self.ruleForm.newPass='';
                    self.ruleForm.oldPass='';
                    self.ruleForm.checkPass='';
                    self.resetPasswordFlag = false;
                    self.$http.post('login/logout.do_')
                      .then(result => {
                        self.loginIn = false;
                        window.sessionStorage.removeItem("loginUsername");
                        window.sessionStorage.removeItem("powerList");
                        self.$store.state.loginUserName = '';
                        self.$store.state.powerList = '';
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
          } else {
            console.log('error submit!!');
            return false;
          }
        })
        self.resetPasswordStaticFlag = false;
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.ruleForm.newPass='';
        this.ruleForm.oldPass='';
        this.ruleForm.checkPass='';
      },
      cencelDialog() {
        this.$refs["ruleForm"].resetFields();
        this.ruleForm.newPass='';
        this.ruleForm.oldPass='';
        this.ruleForm.checkPass='';
      }
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
