<template>
  <home>
    <div style="width:85%; margin-left: 7px">
      <el-form ref="form" :model="form" label-width="100px" @submit.native.prevent>
        <el-row>
          <el-col :span="6">
            <el-form-item label="角色名称">
              <el-input style="width:200px;" placeholder="角色名称" v-model="form.name" @keyup.13.native = "fetchData"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item>
              <el-button type="primary" @click="fetchData1" style="width:100px">查询</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item>
              <el-button type="primary" style="width:100px" @click="exportRole">导出</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px">
      <el-button type="primary" @click="createRole" v-if="!buttonPermission.createPermission" style="width:100px">新建</el-button>
      <el-button type="primary" @click="modifyRole(selection.roleId)" v-if="!buttonPermission.modifyPermission" :disabled="isModify" style="width:100px">修改</el-button>
      <el-button type="primary" @click="deleteRole(selection.roleId)" v-if="!buttonPermission.deletePermission" :disabled="isModify" style="width:100px">删除</el-button>
      <el-button type="primary" @click="addAccount" v-if="!buttonPermission.addAccountPermission" :disabled="isAddCount" style="width:100px">添加账号</el-button>
      <el-button type="primary" @click="roleAssignPermission" v-if="!buttonPermission.assignPermission" :disabled="isModify" style="width:100px">分配权限</el-button>
    </div>

    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column label="选择" width="50px">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row" @change="selectionActive(scope.row)"><span
            width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="roleId" label="角色ID" width="150">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="cellTrigger(scope.row.roleId)">{{scope.row.roleId}}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="roleName" label="角色名称" width="150" :show-overflow-tooltip='true' class="el-tooltip__popper"></el-table-column>
      <el-table-column prop="businessLine" v-if="false" label="支持业务线"></el-table-column>
      <el-table-column prop="accountNum" label="审批人账号" width="120"></el-table-column>
      <el-table-column prop="staffNum" label="审批人员工编号"></el-table-column>
      <el-table-column prop="staffName" label="审批人姓名" width="200"></el-table-column>
      <el-table-column prop="departmentName" label="审批人所属部门" width="120"></el-table-column>
      <el-table-column prop="roleStatus" label="状态" width="100" style="text-align: center">
        <template slot-scope="scope">
          {{RoleStatusEnum[scope.row.roleStatus]}}
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" width="120" :show-overflow-tooltip='true' class="el-tooltip__popper"></el-table-column>
    </el-table>

    <el-pagination background
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   :current-page="currentPage"
                   :page-sizes="[10, 50, 100, 200]"
                   :page-size="pageSize"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total">
    </el-pagination>
    <el-dialog title='请选择需要导出的字段' :visible.sync="exportDialogVisible" :close-on-click-modal="false" width="600px">
      <template>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="checkRoles" @change="handlecheckRolesChange">
          <el-checkbox v-for="city in roles" :label="city" :key="city">{{city}}</el-checkbox>
        </el-checkbox-group>
      </template>
      <template slot="footer">
        <el-button type="primary" @click="exportExcel">确定导出</el-button>
        <el-button @click="cancel">取 消</el-button>
      </template>
    </el-dialog>

    <el-dialog title='添加账号' :visible.sync="addAccountDialogVisible" :close-on-click-modal="false" width="80%" >
      <div class="dialog-main" style="overflow: auto">
      <div >
        登陆账号：
        <el-input style="width:200px;" placeholder="登陆账号" v-model="loginAccount"></el-input>
        <el-button type="primary" @click="fetchAccountData" style="width:100px">查询</el-button>
      </div>
      <div style="margin-top: 20px;margin-bottom: 20px">
        <el-button type="primary" @click="removeRoleAccount" :disabled="isRemoveRoleAccount" style="width:100px">移除</el-button>
        <el-button type="primary" @click="addRoleAccount" style="width:100px">添加</el-button>
      </div>
      <el-table ref="multipleTable" :data="accountTableData" border @selection-change="handleAccountSelectionChange">
        <el-table-column type="selection" width="50px"></el-table-column>
        <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
        <el-table-column prop="accountName" label="登陆账号" style="width:auto"></el-table-column>
        <el-table-column prop="staffNum" label="员工编号" style="width:auto"></el-table-column>
        <el-table-column prop="staffName" label="员工姓名" style="width:auto"></el-table-column>
        <el-table-column prop="department" label="所属部门" style="width:auto"></el-table-column>
        <el-table-column prop="premissions" label="数据权限类型" style="width:auto">
          <template slot-scope="scope">
            {{permissionsEnum[scope.row.premissions]}}
          </template>
        </el-table-column>
        <el-table-column prop="accountState" label="账号状态" style="width:auto">
          <template slot-scope="scope">
            {{accountStatusEnum[scope.row.accountState]}}
          </template>
        </el-table-column>
        <el-table-column prop="modifyTime" label="操作时间" style="width:auto"></el-table-column>
        <el-table-column prop="modifyEmpName" label="操作人" style="width:auto"></el-table-column>
      </el-table>
      <el-pagination background
                     @size-change="handleAccountSizeChange"
                     @current-change="handleAccountCurrentChange"
                     :current-page="currentAccounytPage"
                     :page-sizes="[10, 50, 100, 200]"
                     :page-size="accountPageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="totalAccount">
      </el-pagination>
      </div>
    </el-dialog>

    <el-dialog :title="roleAssignPermissionTitle" :visible.sync="roleAssignPermissionFlag" :close-on-click-modal="false"
               width="700px">
      <div class="dialog-main" >
        <el-form>
          <div style="height: 80px;display: flex;align-items: center;margin-left: 80%">
            <el-button type="primary" style="margin-right: 10px" @click="preservePower">保存</el-button>
            <el-button type="primary" @click="cencel">取消</el-button>
          </div>
          <el-row type="flex" justify="center" style="width: 100%;">
            <el-col :span="11">
              <el-scrollbar style="width: 400px">
                <el-tree
                  style="float: left;margin-left: 100px;height: 350px"
                  ref="tree"
                  :props="defaultProps"
                  node-key="powerId"
                  :load="loadNode"
                  lazy="true"
                  :default-expanded-keys="[1]"
                  :default-checked-keys="selectedNodes"
                  show-checkbox
                  :check-strictly="checkStrictly"
                  @check-change="handleCheckChange">
                </el-tree>
              </el-scrollbar>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-dialog>

    <el-dialog :title="chooseAdd" :visible.sync="chooseAccountPage" :close-on-click-modal="false"
               :before-close="handleClose" width="80%">
      <div class="dialog-main" style="overflow: auto">
      <div style="width: 95%">
        <el-form ref="form" :model="form" label-width="100px">
          <el-row>
            <el-col :span="6">
              <el-form-item label="登陆账号" >
                <el-input style="width:150px;" v-model="form.accountNo" placeholder="登陆账号" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="员工编号">
                <el-input style="width:160px;" v-model="form.staffNo" placeholder="员工编号" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="员工姓名">
                <el-input style="width:150px;" v-model="form.name" placeholder="员工姓名" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="数据权限类型">
                <el-select style="width:150px;" v-model="form.permissions" clearable placeholder="全选">
                  <el-option
                    v-for="item in form.permissionsList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="员工所属部门">
                <el-input style="width:150px;" v-model="form.department" placeholder="员工所属部门"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <div style="float: left; margin-left: 5px; margin-right: 140px">
                <el-col>
                  <el-button type="text" @click="chooseDepartment">选择</el-button>
                  <el-button type="text" @click="clearDepartment">取消</el-button>
                </el-col>
              </div>
            </el-col>
            <el-col :span="6">
              <el-form-item label="是否关联员工">
                <el-select style="width: 150px" v-model="form.isRelStaff" clearable placeholder="全部">
                  <el-option
                    v-for="item in form.isRelStaffoptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="账号状态">
                <el-select style="width:150px;" v-model="form.status" clearable placeholder="全部">
                  <el-option
                    v-for="item in StatusList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <el-form ref="form" :model="form">
          <el-row>
            <el-col style="text-align: center">
              <el-form-item>
                <el-button type="primary" @click="fetchAddData" style="width:100px">查询</el-button>
                <el-button type="primary" style="width:100px" :disabled="isComfirmAdd" @click="comfirmAdd">确认选择</el-button>
                <el-button type="primary" style="width:100px" @click="cancelAdd">取消</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <el-table ref="multipleTable" :data="addAccountData" border @selection-change="handleAddSelectionChange" >
        <el-table-column type="selection" width="50px"></el-table-column>
        <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
        <el-table-column prop="accountName" label="登陆账号" style="width:auto"></el-table-column>
        <el-table-column prop="staffNum" label="员工编号" style="width:auto"></el-table-column>
        <el-table-column prop="staffName" label="员工姓名" style="width:auto"></el-table-column>
        <el-table-column prop="department" label="所属部门" style="width:auto"></el-table-column>
        <el-table-column prop="premissions" label="数据权限类型" style="width:auto">
          <template slot-scope="scope">
            {{permissionsEnum[scope.row.premissions]}}
          </template>
        </el-table-column>
        <el-table-column prop="accountState" label="账号状态" style="width:auto">
          <template slot-scope="scope">
            {{accountStatusEnum[scope.row.accountState]}}
          </template>
        </el-table-column>
        <el-table-column prop="modifyTime" label="操作时间" style="width:auto"></el-table-column>
        <el-table-column prop="modifyEmpName" label="操作人" style="width:auto"></el-table-column>
      </el-table>
      <el-pagination background
                     @size-change="handleAddSizeChange"
                     @current-change="handleAddCurrentChange"
                     :current-page="currentAddPage"
                     :page-sizes="[10, 50, 100, 200]"
                     :page-size="pageAddSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="addTotal">
      </el-pagination>
    </div>
    </el-dialog>

    <el-dialog title="选择部门" :visible.sync="chooseDepartmentFlag">
      <el-tree
        ref="tree"
        :props="defaultPropsTree"
        node-key="id"
        :load="loadNodeDepartment"
        lazy
        default-expanded-keys="[1]"
        check-strictly
        show-checkbox
        @check-change="handleClickChange">
      </el-tree>
    </el-dialog>
  </home>
</template>

<script>
  import commonUtils from '../../common/commonUtils'

  const rolesOptions = ['角色ID', '角色名称', '审批人账号', '审批人员编号', '审批人姓名', '审批人所属部门', '状态', '描述'];
  export default {
    data() {
      return {
        defaultPropsTree: {
          label: 'departmentName',
          children: 'children',
          isLeaf: 'nodeIsLeaf',
          id: 'id',
          no: 'departmentNo',
        },
        departmentDto:[],
        total: 0,
        currentPage: 1,
        pageSize: 10,
        StatusList:[{
          value:'1',
          label:'正常'
        },{
          value:'2',
          label:'冻结'
      }],
        tableData: [],
        RoleStatusEnum: {},
        selection: {},
        id: '',
        roleId: '',
        roleName: '',
        businessLine:'',
        accountNum: '',
        staffNum: '',
        staffName: '',
        departmentName: '',
        roleStatus: '',
        description: '',
        isModify: true,
        isAddCount:true,
        loginAccount:'',
        exportDialogVisible: false,
        checkAll: false,
        checkRoles: [],
        roles: rolesOptions,
        isIndeterminate: true,
        filterVal: [],
        list: [],
        disabled: true,
        roleDtoList: [],
        addAccountDialogVisible: false,
        accountTableData: [],
        selectionAccount: [],
        currentAccounytPage: 1,
        accountPageSize: 10,
        totalAccount: 0,
        permissionsEnum: {},
        accountStatusEnum: {},

        roleAssignPermissionFlag: false,
        powerSelectedList: [],
        selected: [],
        defaultProps: {
          label: 'powerName',
          children: 'children',
          id: 'powerId'
        },
        selectedNodes: [],
        myRole: [],
        roleAssignPermissionTitle: '角色权限分配',
        powerTree: [],
        checkStrictly: false,

        buttonPermission: {
          createPermission: true,
          modifyPermission: true,
          deletePermission: true,
          addAccountPermission: true,
          assignPermission: true
        },
        list:[],
        isRemoveRoleAccount:true,
        chooseAdd:'选择添加账户',
        chooseAccountPage:false,
        addAccountData: [],
        currentAddPage: 1,
        pageAddSize: 10,
        addTotal: 0,
        addSelection:[],
        accountNameList:[],
        form: {
          accountNo: '',
          staffNo: '',
          name: '',
          permissionsList:[],
          permissionsEnum:{},
          accountStatusList:[],
          accountStatusEnum:{},
          permissions: '',
          departmentId:'',
          department:'',
          isRelStaffoptions:[{
            value: '',
            label: '全部'
          },{
              value: '1',
              label: '是'
         },{
            value: '0',
            label: '否'
          }],
          isRelStaff: '',
          status:''
         },
        selectAccountIds:[],
        isComfirmAdd:true,
        strict: false,
        chooseDepartmentFlag:false,
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    created() {
      this.judgmentAuthority();
      const self = this;
      self.$http.get('department/buildTree2.do_')
        .then((result) => {
          self.departmentDto = result.departmentDto;
        }).catch(function (error) {

      });
    },
    mounted() {
      commonUtils.Log("页面进来");
      this.fetchData();


    },
    methods: {
      judgmentAuthority() {
        const self = this;
        let permission = self.$store.state.powerList;
        permission.forEach(item=>{
          if (item === 33) {
            self.buttonPermission.createPermission = false
          }
          if (item === 34) {
            self.buttonPermission.modifyPermission = false
          }
          if (item === 35) {
            self.buttonPermission.deletePermission = false
          }
          if (item === 36) {
            self.buttonPermission.addAccountPermission = false
          }
          if (item === 37) {
            self.buttonPermission.assignPermission = false
          }
        });
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.currentPage = 1;
        var string = {};
        string = this.form;
        this.form = {};
        this.fetchData(1, val);
        this.form = string;
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        var string = {};
        string = this.form;
        this.form = {};
        this.fetchData(val, this.pageSize);
        this.form = string;
      },
      handleSelectionChange(val) {
        this.selection = val;
      },
      handleAccountSizeChange(val) {
        this.accountPageSize = val;
        this.currentAccounytPage = 1;
        this.fetchAccountData(1, val);
      },
      handleAccountCurrentChange(val) {
        this.currentAccounytPage = val;
        this.fetchAccountData(val, this.accountPageSize);
      },
      handleAccountSelectionChange(val) {
        if(val.length != 0){
          this.isRemoveRoleAccount = false;
        }else{
          this.isRemoveRoleAccount = true;
        }
        this.selectionAccount = val;
        this.list=[];
        this.accountNameList=[];
        for (let i = 0; i <this.selectionAccount.length ; i++) {
          this.list.push(val[i].id);
          this.accountNameList.push(val[i].accountName)
        }
      },
      handleAddSizeChange(val) {
        this.pageAddSize = val;
        this.currentAddPage = 1;
        this.fetchAddData(1, val);
      },
      handleAddCurrentChange(val) {
        this.currentAddPage = val;
        this.fetchAddData(val, this.pageAddSize);
      },
      handleAddSelectionChange(val) {
        this.addSelection = val;
        if(val.length != 0){
          this.isComfirmAdd = false;
        }else{
          this.isComfirmAdd = true;
        }
        this.selectAccountIds=[];
        for (let i = 0; i <this.addSelection.length ; i++) {
          this.selectAccountIds.push(val[i].id)
        }
      },
      fetchAddData(){
        var self = this;
        var param = {
          page: self.currentAddPage,
          limit: self.pageAddSize,
          accountName: self.form.accountNo,
          staffNo: self.form.staffNo,
          name:self.form.name,
          permissions: self.form.permissions,
          department: self.form.departmentId,
          isRelStaff: self.form.isRelStaff,
          status: self.form.status,
          filterIds: [],
          defaultStatus:"1",
          date : new Date().getTime(),
        };
        for(var i=0;i<self.accountTableData.length;i++){
          param.filterIds.push(self.accountTableData[i].id);
        }
        param.filterIds=param.filterIds+"";
        self.$http.get('account/querylist.do_', {
          params: param
        }).then((result) => {
          self.addAccountData = result.page.list;
          self.form.permissionsList = result.permissionList;
          self.form.permissionsEnum = result.permissionEnum;
          self.form.accountStatusEnum = result.accountStatusEnum;
          self.form.accountStatusList = result.accountStatusList;
          self.addTotal = result.page.totalCount;
        }).catch(function (error) {
          commonUtils.Log("account/querylist.do_:"+error);
          self.$message.error("获取数据错误")
        });
      },
      fetchData() { //获取数据
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          roleName: self.form.name,
          //flag: '1',
          date : new Date().getTime(),
        };
        self.$http.get("roleManage/querylist.do_", {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          self.RoleStatusEnum = result.RoleStatusEnum;
          self.roleDtoList = result.roleList;
          //self.form.name="dsf";
        }).catch(function (error) {
          commonUtils.Log("roleManage/querylist.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },
      fetchData1() { //获取数据
        this.currentPage = 1;
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          roleName: self.form.name,
          //flag: '1',
          date : new Date().getTime(),
        };
        self.$http.get("roleManage/querylist.do_", {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          self.RoleStatusEnum = result.RoleStatusEnum;
          self.roleDtoList = result.roleList;
          this.isAddCount = true;
          this.isModify = true;
          //self.form.name="dsf";
        }).catch(function (error) {
          commonUtils.Log("roleManage/querylist.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },

      createRole() {
        //点击新建按钮，进入新建角色界面
        this.$router.replace('/CreateRole');
      },
      modifyRole(val) {
        //点击修改按钮，进入修改角色弹窗
        //this.dialogVisible=true;
        var self = this;
        var param = {
          roleID: val,
          date : new Date().getTime(),
        };
        self.$http.get('roleManage/getOneInf.do_', {
          params: param
        }).then((result) => {
          if (result.page.roleStatus ===0){
            self.$message.info("该角色已经被删除，不可修改");
            this.isModify = true;
            this.isAddCount = true;
            this.form.name = '';
            this.fetchData();
          }
          else{
            this.$router.push({path: '/ModifyRole', query: {roleID: val}});
          }
        }).catch(function (error) {
          commonUtils.Log("roleManage/getOneInf.do_" + error);
          self.$message.error("获取数据错误");
        });
      },

      deleteRole(val) {//删除角色信息
        this.$confirm('此操作将删除该角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {

              var self = this;
              var param = {
                selection: self.selection.roleId,
                date : new Date().getTime(),
              };
              self.$http.get('roleManage/updateStatus.do_', {
                params: param
              }).then((result) => {
                if(result.msg === "角色删除失败，角色已经被删除"){
                  this.$message.info("角色删除失败，角色已经被删除");
                }
                else{
                  self.$message.info("角色删除成功");
                }
                this.form.name = '';
                this.fetchData();
                this.isAddCount = true;
                this.isModify = true;

              }).catch(function (error) {
                commonUtils.Log("roleManage/updateStatus.do_:" + error);
                self.$message.error("角色删除失败");
              });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      selectionActive(val) {
        this.myRole.roleId = val.roleId;
        this.myRole.roleName = val.roleName;
        if(val.roleStatus === 1){
          this.isAddCount = false;
          this.isModify = false;
        }else if(val.roleStatus === 0){
          this.isAddCount = true;
          this.isModify = true;
        }
      },
      cellTrigger(val) {//角色详情页
        this.$router.push({path: '/RoleInf', query: {roleID: val}});
      },
      addAccount() {
        this.addAccountDialogVisible = true;
        this.fetchAccountData();
      },
      fetchAccountData() {
        var self = this;
        var param = {
          page: self.currentAccounytPage,
          limit: self.accountPageSize,
          roleId:self.selection.roleId,
          accountName:self.loginAccount,
          date : new Date().getTime(),
        };
        self.$http.get('roleManage/getRoleAccountList.do_', {
          params: param
        }).then((result) => {
          self.accountTableData = result.page.list;
          self.permissionsEnum = result.permissionEnum;
          self.accountStatusEnum = result.accountStatusEnum;
          self.totalAccount = result.page.totalCount;
        }).catch(function (error) {
          commonUtils.Log("roleManage/getRoleAccountList.do_:" + error);
          self.$message.error("获取数据错误")
        });
      },


      exportExcel() {
        if (this.checkRoles.length === 0) {
          this.$message({
            showClose: false,
            message: '请选择需要导出的字段',
            type: 'error'
          });
        } else {
          require.ensure([], () => {
            const {export_json_to_excel} = require('../../excel/Export2Excel');
            const tHeader = this.checkRoles;
            // 上面设置Excel的表格第一行的标题

            const filterVal = this.exportField(this.checkRoles);
            // 上面的staffNum、accountId、staffName是tableData里对象的属性
            const list = this.roleDtoList;  //把data里的tableData存到list
            for (let i = 0; i < list.length; i++) {
              list[i].roleStatus = this.RoleStatusEnum[list[i].roleStatus];
            }
            //获取当前时间
            var date = new Date();
            var seperator1 = "-";
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
              month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
              strDate = "0" + strDate;
            }
            var currentdate = year + seperator1 + month + seperator1 + strDate;
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '角色管理'+currentdate);
            this.$message({
              showClose: true,
              message: '文件导出成功',
              type: 'success'
            });
            this.exportDialogVisible = false;
            this.checkRoles = [];
            this.filterVal = [];
            this.checkAll=false;
          })
        }
      },
      cancel() {
        this.exportDialogVisible = false;
      },
      exportRole() {
        this.checkAll=false;
        this.exportDialogVisible = true;
      },
      exportField(val) {
        for (let i = 0; i < val.length; i++) {
          if (this.checkRoles[i] === '角色ID') {
            this.filterVal.push('roleId')
          } else if (this.checkRoles[i] === '角色名称') {
            this.filterVal.push('roleName')
          } else if (this.checkRoles[i] === '审批人账号') {
            this.filterVal.push('accountNum')
          } else if (this.checkRoles[i] === '审批人员编号') {
            this.filterVal.push('staffNum')
          } else if (this.checkRoles[i] === '审批人姓名') {
            this.filterVal.push('staffName')
          } else if (this.checkRoles[i] === '审批人所属部门') {
            this.filterVal.push('departmentName')
          } else if (this.checkRoles[i] === '状态') {
            this.filterVal.push('roleStatus')
          } else if (this.checkRoles[i] === '描述') {
            this.filterVal.push('description')
          }
        }
        return this.filterVal;
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      handleCheckAllChange(val) {
        this.checkRoles = val ? rolesOptions : [];
        this.isIndeterminate = false;
      },
      handlecheckRolesChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.roles.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.roles.length;
      },
      roleAssignPermission() {
        const self = this;
        self.roleAssignPermissionTitle = self.myRole.roleName + "权限分配";
        self.$nextTick(() => {
          self.$refs.tree.setCheckedKeys([]);
        });
        self.roleAssignPermissionFlag = true;
        self.getRolePower();
      },
      //获取角色的权限
      getRolePower() {
        const self = this;
        self.checkStrictly = true;
        var param = {
          roleInfoId: self.myRole.roleId,
          date : new Date().getTime(),
        }
        self.$http.post('roleManage/getRolePower.do_', param).then((result) => {
          self.selectedNodes = result.rolePowerList;
          if (self.strict) {
            self.checkStrictly = false;
          }
        }).catch(function (error) {
          commonUtils.Log("roleManage/getRolePower.do_" + error);
          self.$message.error("获取数据错误")
        });
      },
      //获取树数据
      loadNode(node, resolve) {
        var self = this;
        self.$http.post('power/getPowerList', {
          params: null
        }).then((result) => {
          resolve([result.powerTree]);
          self.checkStrictly = false;
          self.strict = true;
        }).catch(function (error) {
          commonUtils.Log("account/querylist.do_:" + error);
          self.$message.error("获取数据错误")
        });

      },
      handleCheckChange(data, checked, indeterminate) {
      },
      preservePower() {
        const self = this;
        var param = {
          roleInfoId: self.myRole.roleId,
          powerList: self.$refs.tree.getCheckedNodes().concat(self.$refs.tree.getHalfCheckedNodes())
        };
        self.$http.post("roleManage/assignPermission", param)
          .then((result) => {
            if (result.code === 203) {
              self.$alert(result.msg, '消息提醒', {
                confirmButtonText: '确定',
              });
            } else {
              self.$message.info("权限分配成功！")
            }
            self.fetchData();
          })
          .catch(function (error) {
            self.$alert("系统错误，请稍后再试！", '消息提醒', {
              confirmButtonText: '确定',
            });
          });
        self.roleAssignPermissionFlag = false;
      },
      cencel() {
        this.roleAssignPermissionFlag = false;
      },
      removeRoleAccount(){
        let acccountName='';
        for (let i = 0; i <this.accountNameList.length ; i++) {
          acccountName+=this.accountNameList[i];
          if(i !=(this.accountNameList.length-1)){
            acccountName+=',';
          }
        }
        this.$confirm('确定将下列账号从'+this.myRole.roleName+'角色中移除么？<br><br>'+acccountName, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          dangerouslyUseHTMLString: true
        }).then(() => {
          var self = this;
          var param = {
            roleId:self.myRole.roleId,
            accountIds:self.list.toString(),
            accountNameList:self.accountNameList.toString(),
            date : new Date().getTime(),
          };
          self.$http.get('roleManage/removeRoleAccount.do_', {
            params: param
          }).then((result) => {
            console.log(result.romoveAccounts);
            if(result.msg==='成功删除' && result.romoveAccounts.length ===0){
              self.$message.success("成功删除");
              self.fetchAccountData();
            }else if(result.msg==='成功删除' && result.romoveAccounts.length !=0) {
                  let nameList="";
              for (let i = 0; i < result.romoveAccounts.length ; i++) {
                  nameList +=result.romoveAccounts[i];
                if(i != (result.romoveAccounts.length-1)){
                  nameList+=",";
                }
              }
              self.$message.error("账户"+nameList+"已经已从角色中移除");
              self.fetchAccountData();
            }else{
              self.$message.error("删除失败");
            }
          }).catch(function (error) {
            commonUtils.Log("roleManage/removeRoleAccount.do_" + error);
            self.$message.error("删除失败");
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      addRoleAccount(){
        this.selectAccountIds=[];
         this.chooseAccountPage = true;
         this.fetchAddData();
      },
      comfirmAdd(){
        var self = this;
        var param = {
          roleId:self.myRole.roleId,
          accountIds:self.selectAccountIds.toString(),
          date : new Date().getTime(),
        };
        self.$http.get('roleManage/addRoleAccount.do_', {
          params: param
        }).then((result) => {
          if(result.msg === "添加成功") {
            this.chooseAccountPage = false;
            this.fetchAccountData();
            this.clear();
          }else{
            self.$message.error("添加失败，该角色已失效！")
            this.fetchAccountData();
            this.fetchData();
          }
        }).catch(function (error) {
          commonUtils.Log("roleManage/addRoleAccount.do_:" + error);
          self.$message.error("获取数据错误")
        });

      },
      cancelAdd(){
        this.chooseAccountPage = false;
        this.selectAccountIds=[];
        this.clear();
      },
      handleClose(done) {
        this.clear();
        done();
      },
      clear(){
        this.currentAddPage=1;
        this.pageAddSize=10;
        this.form.accountNo='';
        this.form.staffNo='';
        this.form.name='';
        this.form.permissions='';
        this.form.department='';
        this.form.departmentId='';
        this.form.isRelStaff='';
        this.form.status='';
      },
      chooseDepartment(){
        this.chooseDepartmentFlag = true;
      },
      clearDepartment(){
        this.form.departmentId = '';
        this.form.department = '';
      },
      loadNodeDepartment(node,resolve){
        var self = this;
        var departmentDto = [self.departmentDto];
        if (node.level === 0) {
          resolve(departmentDto);
        }
        if (node.level === 1) {
          resolve(self.departmentDto.children);
        }
        if (node.level > 1){
          var id = node.data.id;
          resolve(getChilder(id,departmentDto));
        }
        function getChilder(id,datas) {
          var d = null;
          for(var i = 0;i<datas.length;i++){
            var data = datas[i];
            if (data.id != id && data.nodeIsLeaf == false){
              d = getChilder(id,data.children);
              if (d != null ){
                return d;
              }
            }else if(data.id==id){
              return data.children;
            }
          }
          return d;
        }
      },
      handleClickChange(data,checked,node){
        // 手动设置单选
        if(checked === true) {
          this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
          this.form.departmentId = data.id;
          this.form.department = data.departmentName;
        } else {
          if (this.checkedId == data.id) {
            this.$refs.tree.setCheckedKeys([data.id]);
          }
        }
        this.closeChooseDepartment();
      },
      closeChooseDepartment(){
        this.chooseDepartmentFlag = false;
      },
    }
  }
</script>

<style>
  .el-tooltip__popper {
    font-size: 14px; max-width:20%
  } /*设置显示隐藏部分内容，按50%显示*/
</style>
