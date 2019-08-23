// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import Home from './views/Home'
import 'element-ui/lib/theme-chalk/index.css';
import Http from './common/http'
import qs from "qs";
import msg from './common/msg'
// import DialogDrag from '../static/DialogDrag'
import utils from './common/util'
// import Vuex from 'vuex'
// import VueRouter from 'vue-router'

// import routes from './router/testindex'
// import { makeRoute } from './router/testindex'
//
// Vue.use(VueRouter)

// const router = new VueRouter({
//   routes
// })

// Vue.use(Vuex);
import VueAMap from 'vue-amap';
import global from './common/global.js'
import commonUtils from "./common/commonUtils";


window.addEventListener("visibilitychange",function(){ //这个方法是监测浏览器窗口发生变化的时候执行
  if (document.hidden == false && global.accountName != localStorage.getItem('accountName')) {
    global.accountName = localStorage.getItem('accountName') //只有当初始创建的aaa不等于localStorage里面的userId的时候去覆盖掉这个aaa
    // window.sessionStorage.removeItem("loginUsername");
    // window.sessionStorage.removeItem("powerList");
    router.push({path:'/'});
  }
  //不覆盖的话aaa永远都是我们设的初始值
});
Vue.use(VueAMap);
VueAMap.initAMapApiLoader({
  key: 'b2551e2e478785561d5d88081a58dfb3',
  plugin: ['AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PlaceSearch', 'AMap.Geolocation', 'AMap.Geocoder'],
  v: '1.4.4',
  uiVersion: '1.0'
});


Date.prototype.Format = function (fmt) { //日期格式化
  var o = {
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "h+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds(), //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    "S": this.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}

Vue.use(ElementUI,{size:'small'});
Vue.use(Http);

Vue.component("home",Home);

Vue.prototype.$qs = qs;
Vue.prototype.msg = msg;


Vue.config.productionTip = false


//路由守卫
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
    if(window.sessionStorage.getItem("loginUsername") != null ){ //判断本地是否存在access_token
      next();
    } else {
      utils.$emit("loginSuccess",false);
      next({
        path:'/'
      })
    }
  }
  else {
    if (to.path === "/resetPass") {
      utils.$emit("loginSuccess",false);
      // window.sessionStorage.removeItem("loginUsername");
      next()
    }else if (to.path === '/') {
        utils.$emit("loginSuccess",false);
        window.sessionStorage.removeItem("loginUsername");
        // Http.$http.post("login/logout.do_",{
        //   time: new Date().getTime()
        // })
        // .then(result => {
        //   self.loginIn = false;
        //   window.sessionStorage.removeItem("loginUsername");
        //   window.sessionStorage.removeItem("powerList");
        //   self.$store.state.loginUserName = '';
        //   self.$store.state.powerList = '';
        //   self.loginUserName = window.sessionStorage.getItem("loginUsername");
        // })
        // .catch(function (error) {
        //   commonUtils.Log("user/updatePwd:" + error);
        //   self.$message.error("系统故障，请联系管理员！");
        // });
        next()
    }else {
      next({
        path:'/'
      })
    }
  }
});

new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
});

