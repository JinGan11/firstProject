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
import RoleManagement from '@/views/roleManagement/RoleManagement'
import CompanyManagement from '@/views/companyManagement/CompanyList'
import CreateAccount from '@/views/accountManagement/CreateAccount'
import ModifyAccount from '@/views/accountManagement/modifyAccount'
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
import DepartmentChangeUpper from "@/views/departmentManagement/DepartmentChangeUpper"
import ModifyCompany from'@/views/companyManagement/ModifyCompany';
import modifyRoleApply from '@/views/roleManagement/modifyRoleApply';
import RoleInf from "../views/roleManagement/RoleInf";
import DepartmentUpdate from "@/views/departmentManagement/DepartmentUpdate";
import AccountPowerSearch from "@/views/search/AccountPowerSearch";
import RelationCompanyList from "@/views/companyManagement/RelationCompanyList";
import rolePower from "@/views/search/RolePowerSearch";
import ResetPass from "@/views/accountManagement/ResetPass";

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
    component: Login,
  }, {
    path: '/demo1',
    name: 'Demo',
    component: Demo
  },{
    path:'/employeeManagement',
    name:'员工管理',
    component:EmployeeManagement,
    meta:{requireAuth:true}
  }, {
    path: '/departmentManagement/showDepartment',
    name: '查看部门',
    component: DepartmentManagement,
    meta:{requireAuth:true}
  },{
    path:'/roleManagement/apply',
    name:'角色申请',
    component:RoleApplyManage,
    meta:{requireAuth:true}
  },{
    path:'/roleManagement/approval',
    name:'角色申请审核',
    component:RoleApplyApproval,
    meta:{requireAuth:true}
  },{
    path:'/personalAccountManage/pwdReset',
    name:'密码重置',
    component:PwdReset,
    meta:{requireAuth:true}
  },{
    path:'/personalAccountManage/AccountInfo',
    name:'个人账户信息',
    component:AccountInfo,
    meta:{requireAuth:true}
  },{
    path:'/search/departSearch',
    name:'部门查询',
    component:DepartmentSearch,
    meta:{requireAuth:true}
  },{
    path:'/accountManagement',
    name:'账户查询',
    component:AccountManagement,
    meta:{requireAuth:true}
  },{
    path:'/roleManagement/roleManagement',
    name:'角色管理',
    component:RoleManagement,
    meta:{requireAuth:true}
  },{
    path:'/companyManagement',
    name:'公司查询',
    component:CompanyManagement,
    meta:{requireAuth:true}
  },{
    path:'/CreateRole',
    name:'新建角色',
    component:CreateRole,
    meta:{requireAuth:true}
  },{
    path:'/createRoleApply',
    name:'新建角色申请',
    component:CreateRoleApply,
    meta:{requireAuth:true}
  },{
    path:'/createCompany',
    name:'新建公司',
    component:CreateCompany,
    meta:{requireAuth:true}
  },{
    path:'/departmentManagement/addDepartment',
    name:'添加部门',
    component:DepartmentAdd,
    meta:{requireAuth:true}
  },{
      path:'/ModifyRole',
      name:'修改角色',
      component:ModifyRole,
      meta:{requireAuth:true}
  },{
    path:'/createAccount',
    name:'新建账户',
    component:CreateAccount,
    meta:{requireAuth:true}
  },{
    path:'/modifyAccount',
    name:'新建账户',
    component:ModifyAccount,
    meta:{requireAuth:true}
  },{
    path:'/RoleAccountDetail',
    name:'角色账号明细',
    component:RoleAccountDetail,
    meta:{requireAuth:true}
  },{
    path:'/cityInfoManagement/provinceSearch',
    name:'省/市查询',
    component:provinceSearch,
    meta:{requireAuth:true}
  },{
    path:'/cityInfoManagement/citySearch',
    name:'城市查询',
    component:citySearch,
    meta:{requireAuth:true}
  },{
    path:'/cityInfoManagement/countySearch',
    name:'区县查询',
    component:countySearch,
    meta:{requireAuth:true}
  },{
    path:'/accountManagement/AssignPermission',
    name:'账户分配权限',
    component:AssignPermission,
    meta:{requireAuth:true}
  },{
    path:'/modifyCompany',
    name:'修改公司',
    component:ModifyCompany,
    meta:{requireAuth:true}
  },{
    path:'/departmentManagement/changeUpperDepartment',
    name:'修改上级部门',
    component:DepartmentChangeUpper,
    meta:{requireAuth:true}
  },{
    path:'/modifyRoleApply',
    name:'修改角色申请',
    component:modifyRoleApply,
    meta:{requireAuth:true}
  }, {
    path: '/RoleInf',
    name: '角色信息',
    component: RoleInf,
    meta: {requireAuth: true}
  },{
    path:'/search/roleSearch/rolePower',
    name:'角色功能权限明细',
    component:rolePower,
    meta: {requireAuth: true}
  },{
    path: "/departmentManagement/updateDepartment",
    name: '修改部门',
    component: DepartmentUpdate,
    meta:{requireAuth:true}
  },{
    path:"/search/AccountPowerSearch",
    name:"账号权限明细查询",
    component:AccountPowerSearch,
    meta:{requireAuth:true}
  },{
    path: "/relationCompanyList",
    name: '关联公司',
    component: RelationCompanyList,
    meta:{requireAuth:true}
  },{
    path: "/resetPass",
    name: "重置密码",
    component: ResetPass,
    meta:{requireAuth:false}
  }

  ]
})
