import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import Demo from '@/views/Demo'
import Login from '@/views/Login'
import EmployeeManagement from '@/views/employeeManagement/EmployeeList'
import DepartmentManagement from '@/views/departmentManagement/DepartmentList'
import RoleApplyManage from '@/views/roleManagement/RoleApplyManage'
import RoleApplyApproval from '@/views/roleManagement/RoleApplyApproval'

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
  }, {
    path: '/departmentManagement',
    name: '部门管理',
    component: DepartmentManagement
  },{
    path:'/roleManagement/apply',
    name:'角色申请',
    component:RoleApplyManage
  },{
    path:'/roleManagement/approval',
    name:'角色申请审核',
    component:RoleApplyApproval
  }
  ]
})
