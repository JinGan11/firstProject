<template>
  <el-container ref="container" class="view-container">
    <aside-menu :sysTitleShow="sysTitleShow" :sysTitle="sysTitle" :isCollapse="isCollapse" :data="data"></aside-menu>
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
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-dropdown @command="handleCommand" style="margin-right: 10px;">
                <span class="el-dropdown-link" style="display: flex;align-items: center;color: white;line-height: 40px;">
                  guest
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="logout">注销</el-dropdown-item>
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
  </el-container>
</template>

<script>
  import MenuItem from './components/MenuItem'
  import AsideMenu from './components/AsideMenu'
  import img from './assets/logo.png'

  export default {
    data() {
      return {
        sysTitle: img,
        sysTitleShow: img,
        isLog:false,
        isCollapse: false,
        data: [],
        levelList: null
      }
    },
    created() {
      const self = this;
      var log = localStorage.getItem("switchLog");
      if(log){
        self.isLog = true;
      }
      self.$http.get('sys/menu/list.do_').then((result) => {
        self.data = result.menuList;
      });
      self.getBreadcrumb(true);
    },
    components: {MenuItem, AsideMenu},
    methods: {
      switchLog(){
        if(this.isLog){
          localStorage.setItem("switchLog",true);
        }else{
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
      handleCommand(cmd){
        var self = this;
        if(cmd == 'log'){
          localStorage.setItem("log",true);
        }else if (cmd == 'logout') {
          self.$confirm('注销登录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            self.$router.replace("/");
            self.$message({
              type:"success",
              message:"退出成功"
            });
          }).catch(() => {
            self.$message({
              type: 'info',
              message: '取消'
            });
          });
        }
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
</style>
