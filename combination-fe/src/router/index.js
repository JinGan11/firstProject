import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import Demo from '@/views/Demo'
import Login from '@/views/Login'
import EmployeeManagement from '@/views/employeeManagement/EmployeeList'


Vue.use(Router)

export default new Router({
  mode:'history',
  routes: [{
    path: '/index',
    name: '首页',
    component: Home,
    meta:{
      breadNumber:0
    }
  },{
    path: '/',
    name: '登录',
    component: Login
  }, {
    path: '/demo1',
    name: 'Demo',
    component: Demo
  },{
    path:'/employeeManagement',
    name:'员工管理',
    component:EmployeeManagement
  }
  ]
})
