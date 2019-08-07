import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import Demo from '@/views/Demo'
import Login from '@/views/Login'
import EmployeeManagement from '@/views/employeeManagement/EmployeeList'
import DepartmentManagement from '@/views/departmentManagement/DepartmentList'
import RoleApplyManage from '@/views/roleManagement/RoleApplyManage'
import RoleApplyApproval from '@/views/roleManagement/RoleApplyApproval'
import PwdReset from '@/views/personalAccountManagement/PwdReset'
import AccountInfo from '@/views/personalAccountManagement/AccountInfo'
import DepartmentSearch from '@/views/search/DepartmentSearch'
import AccountManagement from '@/views/accountManagement/AccountList'
import CreateEmployee from '@/views/employeeManagement/CreateEmployee'
import ModifyEmployee from '@/views/employeeManagement/ModifyEmployee'
import RoleManagement from '@/views/roleManagement/RoleManagement'
import DepartmentManagementTest from '@/views/departmentManagement/DepartmentTest'
import CompanyManagement from '@/views/companyManagement/CompanyList'
import CreateAccount from '@/views/accountManagement/CreateAccount'
import CreateRole from '@/views/roleManagement/CreateRole'
import CreateRoleApply from '@/views/roleManagement/CreateRoleApply'
import CreateCompany from'@/views/companyManagement/CreateCompany'
import DepartmentAdd from '@/views/departmentManagement/DepartmentAdd'
import RoleAccountDetail from'@/views/search/RoleAccountDetail'
import ModifyRole from "@/views/roleManagement/ModifyRole";
import AssignPermission from '@/views/accountManagement/AssignPermission'
import provinceSearch from "@/views/cityInfoManagement/provinceSearch";
import citySearch from "@/views/cityInfoManagement/citySearch";
import countySearch from "@/views/cityInfoManagement/countySearch";
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
    path: '/departmentManagement/showDepartment',
    name: '查看部门',
    component: DepartmentManagement
  },{
    path:'/roleManagement/apply',
    name:'角色申请',
    component:RoleApplyManage
  },{
    path:'/roleManagement/approval',
    name:'角色申请审核',
    component:RoleApplyApproval
  },{
    path:'/personalAccountManage/pwdReset',
    name:'密码重置',
    component:PwdReset
  },{
    path:'/personalAccountManage/AccountInfo',
    name:'个人账户信息',
    component:AccountInfo
  },{
    path:'/search/departSearch',
    name:'部门查询',
    component:DepartmentSearch,
  },{
    path:'/accountManagement',
    name:'账户查询',
    component:AccountManagement,
  },{
    path:'/CreateEmployee',
    name:'新建员工',
    component:CreateEmployee
  },{
    path:'/modifyEmployee',
    name:'修改员工信息',
    component:ModifyEmployee
  },{
    path:'/roleManagement/roleManagement',
    name:'角色管理',
    component:RoleManagement
  },{
    path:'/departmentManagement/test',
    name:'测试页面',
    component:DepartmentManagementTest
  },{
    path:'/companyManagement',
    name:'公司查询',
    component:CompanyManagement
  },{
    path:'/CreateRole',
    name:'新建角色',
    component:CreateRole
  },{
    path:'/createRoleApply',
    name:'新建角色申请',
    component:CreateRoleApply
  },{
    path:'/createCompany',
    name:'新建公司',
    component:CreateCompany
  },{
    path:'/departmentManagement/addDepartment',
    name:'添加部门',
    component:DepartmentAdd
  },{
      path:'/ModifyRole',
      name:'修改角色',
      component:ModifyRole
  },{
    path:'/createAccount',
    name:'新建账户',
    component:CreateAccount
  },{
    path:'/RoleAccountDetail',
    name:'角色账号明细',
    component:RoleAccountDetail
  },{
    path:'/cityInfoManagement/provinceSearch',
    name:'省/市查询',
    component:provinceSearch
  },{
    path:'/cityInfoManagement/citySearch',
    name:'城市查询',
    component:citySearch
  },{
    path:'/cityInfoManagement/countySearch',
    name:'区县查询',
    component:countySearch
  },{
    path:'/accountManagement/AccountPermission',
    name:'分配权限',
    component:AssignPermission
  }
  ]
})
