<template>
  <home>
    <div style="width: 95%;">
      <el-form ref="form" :model="form" label-width="100px">
        <el-row>
          <el-col :span="5">
            <el-form-item label="登陆账号" >
              <el-input style="width:140px;" v-model="form.accountNo" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工编号">
              <el-input style="width:160px;" v-model="form.staffNo" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="员工姓名">
              <el-input style="width:180px;" v-model="form.name" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="数据权限类型">
              <el-select style="width:180px;" v-model="form.permissions" clearable placeholder="请选择">
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
              <el-input style="width:140px;" :disabled="true" v-model="form.department"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-button type="text" @click="chooseDepartment">选择</el-button>
            <el-button type="text" @click="clearDepartment">取消</el-button>
          </el-col>
          <el-col :span="7">
            <el-form-item label="是否关联员工">
              <el-select style="width: 180px" v-model="form.isRelStaff" clearable placeholder="请选择">
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
              <el-select style="width:180px;" v-model="form.status" clearable placeholder="请选择">
                <el-option
                  v-for="item in form.accountStatusList"
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
              <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
              <el-button type="primary" @click="add1" style="width:100px" >导出</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px">
      <el-button type="primary" v-if="!AccountButtonPermission.createPermission" @click="creatAccount" style="width:70px">新建</el-button>
      <el-button type="primary" v-if="!AccountButtonPermission.modifyPermission" :disabled="deleteDisabled" @click="modifyAccount" style="width:70px">修改</el-button>
      <el-button type="primary" v-if="!AccountButtonPermission.deletePermission" :disabled="deleteDisabled" @click="deleteAccount" style="width:70px">删除</el-button>
      <el-button type="primary" v-if="!AccountButtonPermission.frozenPermission" :disabled="deleteDisabled || frozenDisabled" @click="lock" style="width:70px">冻结</el-button>
      <el-button type="primary" v-if="!AccountButtonPermission.thawPermission" :disabled="deleteDisabled || thawDisabled" @click="unlock" style="width:70px">解冻</el-button>
      <el-button type="primary" v-if="!AccountButtonPermission.resetPermission" :disabled="deleteDisabled" @click="resetPass" style="width:80px">密码重置</el-button>
      <el-button type="primary" v-if="!AccountButtonPermission.assignAccountPermission" :disabled="deleteDisabled" @click="assignPermission" style="width:80px">分配权限</el-button>
      <el-button type="primary" v-if="!AccountButtonPermission.historyPermission" :disabled="disabled" @click="getHistory" style="width:80px">历史记录</el-button>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange" >
      <el-table-column label="选择" width="50px">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id" @change="approvalInfo(scope.row)"><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="accountName" label="登陆账号" width="130px">
        <template slot-scope="scope">
          <el-button type="text" @click="accountBtn(scope.row.id)">{{scope.row.accountName}}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="staffNum" label="员工编号" width="130px" ></el-table-column>
      <el-table-column prop="staffName" label="员工姓名" width="120px"></el-table-column>
      <el-table-column prop="department" label="所属部门" width="120px"></el-table-column>
      <el-table-column prop="premissions" label="数据权限类型" width="120px">
        <template slot-scope="scope">
          {{form.permissionsEnum[scope.row.premissions]}}
        </template>
      </el-table-column>
      <el-table-column prop="accountState" label="账号状态" width="120px">
        <template slot-scope="scope">
          {{form.accountStatusEnum[scope.row.accountState]}}
        </template>
      </el-table-column>
      <el-table-column prop="modifyTime" label="操作时间" width=""></el-table-column>
      <el-table-column prop="modifyEmpName" label="操作人" width="180px">
        <template slot-scope="scope">
          <p v-if="scope.row.modifier!=null">{{scope.row.modifyEmpName}}({{scope.row.modifier}})</p>
          <p v-else>{{scope.row.modifyEmpName}}</p>
        </template>
      </el-table-column>
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
    <el-dialog :title="accountAssignPermissionTitle" :visible.sync="accountAssignPermissionFlag" :close-on-click-modal="false" width="850px">
      <div class="dialog-main">
        <span style="font-size: 25px"></span>
        <el-form>
          <div style="height: 80px;display: flex;align-items: center;margin-left: 80%">
            <el-button type="primary" style="margin-right: 10px" @click="preservePower">保存</el-button>
            <el-button type="primary" @click="cencel">取消</el-button>
          </div>
          <div style="height: 50px;">
            <label style="margin-left: 5%; font-size: 22px; color: cornflowerblue;">所拥有角色</label>
            <label style="margin-left: 45%; font-size: 22px; color: cornflowerblue;">所拥有特殊权限</label>
          </div>
          <el-row type="flex" justify="center" style="width: 100%;">
            <el-col :span="11" style="width: 20%">
              <el-table ref="currAvaiVarRef" class="total-variable" height="350" border @cell-click="currCellClick"
                        @selection-change="selectionChangeLeft" :data="roleList">
                <el-table-column type="selection" width="44"></el-table-column>
                <el-table-column prop="roleName" label="可分配角色"></el-table-column>
              </el-table>
            </el-col>
            <el-col :span="2" class="button-col" style="display: flex; align-items: center;">
              <div class="button-group">
                <el-button type="primary" :disabled="leftButtonDisable" style="margin-left: 7px;margin-bottom: 15px"
                           class="button-select el-icon-arrow-right" round @click="selectVariable"></el-button>
                <el-button type="primary" :disabled="rightButtonDisable" style="margin-left: 7px"
                           class="button-select el-icon-arrow-left" round @click="abandonVariable"></el-button>
              </div>
            </el-col>
            <el-col :span="11" style="width: 20%">
              <el-table ref="selectVarRef" class="selected-variable" height="350" :data="selected" border
                        @cell-click="selectCellClick"
                        @selection-change="selectionChangeRight">
                <el-table-column type="selection"></el-table-column>
                <el-table-column prop="roleName" label="已拥有角色"></el-table-column>
              </el-table>
            </el-col>
            <el-col :span="11">
              <el-scrollbar style="width: 400px">
                <el-tree
                  style="float: left;margin-left: 100px;height: 350px"
                  ref="powerTree"
                  :props="defaultProps"
                  node-key="powerId"
                  :load="loadNode"
                  lazy="true"
                  :default-expanded-keys="[1]"
                  :default-checked-keys="selectedNodes"
                  :check-strictly="checkStrictly"
                  show-checkbox
                  @check-change="handleCheckChange">
                </el-tree>
              </el-scrollbar>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-dialog>
    <el-dialog title="选择部门" :visible.sync="chooseDepartmentFlag" width="300px">
      <el-tree
        ref="tree"
        :props="defaultPropsTree"
        node-key="id"
        :load="loadNodeDepartment"
        lazy
        check-strictly
        show-checkbox

        @check-change="handleClickChange">
      </el-tree>
    </el-dialog>
    <el-dialog :visible.sync="accounFlag" :close-on-click-modal="false" width="800px">
        <account-view ref="c1" :accountId="accountId" ></account-view>
    </el-dialog>
    <el-dialog :title='excelTitle' :visible.sync="exportVisible" :close-on-click-modal="false" width="600px">
      <template>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="checkedAccounts" @change="handleCheckedAccountsChange">
          <el-checkbox v-for="account in accounts" :label="account" :key="account">{{account}}</el-checkbox>
        </el-checkbox-group>
      </template>
      <template slot="footer">
        <el-button type="primary" @click="exportExcel">确定导出</el-button>
        <el-button @click="cancel">取 消</el-button>
      </template>
    </el-dialog>





    <el-dialog title="历史记录" :visible.sync="historyRecordsVisible" width="1000px">
    <el-table ref="multipleTable" :data="historyRecords" border height="550px">
      <el-table-column prop="staffNum" label="员工编号" width="130"></el-table-column>
      <el-table-column prop="staffName" label="员工姓名" width="120"></el-table-column>
      <el-table-column prop="permissions" label="数据权限类型" width="150">
        <template slot-scope="scope">
          {{form.permissionsEnum[scope.row.permissions]}}
        </template>
      </el-table-column>
      <el-table-column prop="accountState" label="账号状态" width="120">
        <template slot-scope="scope">
          {{form.accountStatusEnum[scope.row.accountState]}}
        </template>
      </el-table-column>
      <el-table-column prop="historyOperationType" label="操作类型" width="120"></el-table-column>
      <el-table-column prop="accountName" label="操作人" width="130"></el-table-column>
      <el-table-column prop="createTime" label="操作时间" width="200"></el-table-column>
    </el-table>
  </el-dialog>
  </home>
</template>

<script>
  import commonUtils from '../../common/commonUtils'
  import accountView from './AccountView'
  const accountOptions = ['登陆账号','员工编号','员工姓名','所属部门','数据权限类型','账号状态','操作时间','操作人'];
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
        total: 0,
        currentPage: 1,
        pageSize: 10,
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
            value: '1',
            label: '是'
          },{
            value: '0',
            label: '否'
          }],
          isRelStaff: null,
          status:null
        },
        tableData: [],
        accountDtoList: [],
        selection: [],
        disabled: true,
        deleteDisabled: true,
        frozenDisabled: true,
        thawDisabled: true,
        myAccount:[],
        roleList: [],
        roleSelectedList: [],
        powerSelectedList: [],
        selected: [],
        leftSelected: [],
        rightSelected: [],
        isLazy: true,
        defaultProps: {
          label: 'powerName',
          children: 'children',
          id: 'powerId'
        },
        selectedNodes: null,
        accountAssignPermissionFlag: false,
        chooseDepartmentFlag: false,
        accounFlag: false,
        contentFormFlag: false,
        exportVisible: false,
        excelTitle: '请选择需要导出的字段',
        isIndeterminate: true,
        checkAll: false,
        accounts: accountOptions,
        checkedAccounts: [],
        filterVal: [],
        accountAssignPermissionTitle: '账号权限分配',
        checkStrictly: false,
        row: {},
        accountId: '',
        departmentDto:[],
        AccountButtonPermission: {
          createPermission: true,
          modifyPermission: true,
          deletePermission: true,
          frozenPermission: true,
          thawPermission: true,
          resetPermission: true,
          assignAccountPermission: true,
          historyPermission: true
        },
//控制历史记录对话框可见性
        historyRecordsVisible:false,
//历史记录表格数据
        historyRecords:[]
      }
    },
     components: {accountView},
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
    },
    computed: {
      leftButtonDisable()
      {
        return this.leftSelected.length === 0
      },
      rightButtonDisable()
      {
        return this.rightSelected.length === 0
      }
    },
    created() {
      const self = this;
      self.judgmentAuthority();
      self.$http.get('department/buildTree2.do_')
        .then((result) => {
          self.departmentDto = result.departmentDto;
        }).catch(function (error) {

      });
      self.$http.get('account/querylist.do_').then((result) => {
        self.tableData = result.page.list;
        self.accountDtoList = result.accountDtoList;
        self.form.permissionsList = result.permissionList;
        self.form.permissionsEnum = result.permissionEnum;
        self.form.accountStatusEnum = result.accountStatusEnum;
        self.form.accountStatusList = result.accountStatusList;
        self.total = result.page.totalCount;
      }).catch(function (error) {
        commonUtils.Log("account/querylist.do_:"+error);
        self.$message.error("获取数据错误")
      });
    },
    methods: {
      getHistory(){
        let accountId  = {id:this.selection};
        this.historyRecordsVisible=true;
        this.$http.post("account/accountHistory",accountId).then((result) => {
          console.log(result);
          this.historyRecords=result.historyList;
          console.log(this.form.permissionsEnum);

        })
      },
      test(){
        this.contentFormFlag = true;
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
      judgmentAuthority() {
        const self = this;
        let permission = self.$store.state.powerList;
        permission.forEach(item=>{
          if (item === 21) {
            self.AccountButtonPermission.createPermission = false
          }
          if (item === 22) {
            self.AccountButtonPermission.modifyPermission = false
          }
          if (item === 23) {
            self.AccountButtonPermission.deletePermission = false
          }
          if (item === 24) {
            self.AccountButtonPermission.frozenPermission = false
          }
          if (item === 25) {
            self.AccountButtonPermission.thawPermission = false
          }
          if (item === 26) {
            self.AccountButtonPermission.resetPermission = false
          }
          if (item === 27) {
            self.AccountButtonPermission.assignAccountPermission = false
          }
          if (item === 28) {
            self.AccountButtonPermission.historyPermission = false
          }
        });
      },
      lock(){
        this.$confirm('此操作将冻结该员工, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let accountId  = {
            id:this.selection
          };
          this.$http.post("account/lock",accountId).then((result) => {
            this.$message.success("冻结成功")
            this.fetchData();
          }).catch(function (error) {
            commonUtils.Log("account/lock" + error);
            this.$message.error("冻结失败");
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消冻结'
          });
        });
      },
      unlock(){
        this.$confirm('此操作将冻结该员工, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let accountId  = {
            id:this.selection
          };
          this.$http.post("account/unLock",accountId).then((result) => {
            this.$message.success("解冻成功");
            this.fetchData();
          }).catch(function (error) {
            commonUtils.Log("account/unlock" + error);
            this.$message.error("解冻失败")

          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消解冻'
          });
        });
      },
      handleCheckChange () {
        var a = '1';
      },
      handleNodeClick(data) {
        console.log(data);
      },
        handleSizeChange(val) {
        this.pageSize = val;
        this.currentPage = 1;
        this.fetchData(1, val);
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.fetchData(val, this.pageSize);
      },
      handleSelectionChange(val) {
        this.selection = val;
      },
      fetchData(){
        var self = this;
        self.disabled = true;
        self.deleteDisabled = true;
        self.frozenDisabled = true;
        self.thawDisabled = true;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          accountName: self.form.accountNo,
          staffNo: self.form.staffNo,
          name:self.form.name,
          permissions: self.form.permissions,
          department: self.form.departmentId,
          isRelStaff: self.form.isRelStaff,
          status: self.form.status
        };
        self.$http.get('account/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.accountDtoList = result.accountDtoList;
          self.form.permissionsList = result.permissionList;
          self.form.permissionsEnum = result.permissionEnum;
          self.form.accountStatusEnum = result.accountStatusEnum;
          self.form.accountStatusList = result.accountStatusList;
          self.total = result.page.totalCount;
          self.selection = 0;
        }).catch(function (error) {
          commonUtils.Log("account/querylist.do_:"+error);
          self.$message.error("获取数据错误")
        });
      },
      creatAccount(){ //新建账户
        this.$router.replace('/createAccount')
      },
      modifyAccount(){
        this.$router.replace('/modifyAccount')
      },
      deleteAccount(){
        this.$confirm('此操作将删除该员工, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const self = this;
          var param = {
            accountId: self.row.id,
            staffNum: self.row.staffNum,
            staffName: self.row.staffName,
            permissions: self.row.premissions,
            accountState: self.row.accountState
          };
          self.$http.post('account/deleAccount.do_', param)
            .then((result) => {
              self.$message.info("删除成功");
              self.fetchData();
            }).catch(function (error) {
            commonUtils.Log("account/deleAccount.do_:" + error);
            self.$message.error("删除失败")
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      approvalInfo(val){
        const self = this;
        self.row = val;
        self.disabled = false;
        if(val.accountState == 1){
          self.deleteDisabled = false;
          self.frozenDisabled = false;
          self.thawDisabled = true;
        }else if(val.accountState == 2){
          self.deleteDisabled = false;
          self.frozenDisabled = true;
          self.thawDisabled = false;
        }else{
          self.deleteDisabled = true;
          self.frozenDisabled = true;
          self.thawDisabled = true;
        }
        self.myAccount.id = val.id;
        self.myAccount.accountName = val.accountName;
        localStorage.setItem("accountId",val.id);
      },
      resetPass(){
        const self = this;
        let param = {
          id: self.myAccount.id
        }
        self.$confirm('确定要重置密码吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          self.$http.post("/account/resetPass",param)
            .then(result => {
              if (result.code === 200) {
                self.$alert("重置密码邮件已发送成功，请注意查收！", '消息提醒', {
                  confirmButtonText: '确定',
                });
                self.fetchData();
              } else if (result.code === 201) {
                self.$alert("操作失败！", '消息提醒', {
                  confirmButtonText: '确定',
                });
              } else if (result.code === 202) {
                self.$alert("密保邮箱有误", '消息提醒', {
                  confirmButtonText: '确定',
                });
              }
            })
            .catch(function (error) {
              commonUtils.Log("account/resetPass:" + error);
              self.$message.error("系统错误，请稍后再试！");
            });
        })
      },
      assignPermission() {
        const self = this;
        self.initAccountPermission();
        self.accountAssignPermissionTitle = self.myAccount.accountName + "权限分配";
        self.$nextTick(() => {
          self.$refs.powerTree.setCheckedKeys([]);
        });
        self.accountAssignPermissionFlag = true;
      },
      //初始化账户权限分配
      initAccountPermission() {
        const self = this;
        self.checkStrictly = true;
        let param = {
          id: self.myAccount.id
        };
        self.$http.post('power/getAccountPower.do_',param).then((result) => {
          self.selectedNodes = result.accountPower;
          self.checkStrictly = false;
        }).catch(function (error) {
          commonUtils.Log("power/getAccountPower.do_" + error);
          self.$message.error("获取数据错误")
        });
        self.$http.post('account/getRoleList.do_',param).then((result) => {
          self.roleList = result.notOwnedRole.list;
          self.selected = result.ownedRole.list;
          console.log(result)
        }).catch(function (error) {
          commonUtils.Log("account/getRoleList.do_" + error);
          self.$message.error("获取数据错误")
        });

      },
      selectionChangeLeft(val) {
        this.leftSelected = val
      },
      selectionChangeRight(val) {
        this.rightSelected = val
      },
      currCellClick(row, column, cell, event){
        this.$refs.currAvaiVarRef.toggleRowSelection(row)
      },
      selectCellClick(row, column, cell, event){
        this.$refs.selectVarRef.toggleRowSelection(row);
      },
      // 点击选择变量
      selectVariable() {
        let self = this;
        // 右边设值
        self.leftSelected.forEach((item) => self.selected.push(item));
        // 左边删除变量
        self.roleList = self.roleList.filter((item) => {
          return self.selected.indexOf(item) === -1
        });
        self.leftSelected = [];
        // 重排
        self.selected.sort((a, b) => {
          return a.id - b.id
        })
      },
      // 点击舍弃变量
      abandonVariable() {
        let self = this;
        // 右边删除该元素，左边添加此元素
        self.rightSelected.forEach((item) => self.roleList.push(item));
        self.selected = self.selected.filter((item) => {
          return self.rightSelected.indexOf(item) === -1
        })
        self.rightSelected = []
        self.roleList.sort((a, b) => {
          return a.id - b.id
        })
      },
      //获取树数据
      loadNode(node,resolve){
        var self = this;
        self.$http.get('power/getPowerList', {
          params: null
        }).then((result) => {
          resolve([result.powerTree]);
          self.checkStrictly = false;
        }).catch(function (error) {

        });
      },
      handleClick(data,checked,node){
        if(checked === true) {
          this.checkedId = data.id;
          this.$refs.powerTree.setCheckedKeys([data.id]);
        } else {
          if (this.checkedId === data.id) {
            this.$refs.powerTree.setCheckedKeys([data.id]);
          }
        }
      },
      handleCheckChange(data, checked, indeterminate) {
        const self = this;
        console.log(self.$refs.powerTree.getCheckedNodes())
      },
      preservePower() {
        const self = this;
        var param = {
          id: self.myAccount.id,
          roleList : self.selected,
          powerList: self.$refs.powerTree.getCheckedNodes().concat(self.$refs.powerTree.getHalfCheckedNodes())
        };
        self.$http.post("power/modifyPermission", param)
          .then((result) => {
            self.$message.info("权限分配成功！")
            self.fetchData();
          })
          .catch(function (error) {
            self.$alert("系统错误，请稍后再试！", '消息提醒', {
              confirmButtonText: '确定',
            });
          });
        this.accountAssignPermissionFlag = false;
      },
      cencel(){
        this.accountAssignPermissionFlag = false;
      },
      chooseDepartment(){
        this.chooseDepartmentFlag = true;
      },
      clearDepartment(){
        this.form.departmentId = '';
        this.form.department = '';
      },
      closeChooseDepartment(){
        this.chooseDepartmentFlag = false;
      },
      //账户信息明细
      accountBtn(val){
        localStorage.setItem("accountId",val);
        self.accountId = val;
        this.accounFlag = true;
        if(this.$refs.c1 != undefined){
          this.$refs.c1.fetchData(val);
        }
      },
      //打开导出弹窗
      add1() {
        this.exportVisible = true;
      },
      cancel(){
        this.exportVisible = false;
      },
      handleCheckAllChange(val) {
        this.checkedAccounts = val ? accountOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckedAccountsChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.accounts.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.employees.length;
      },
      exportExcel() {
        if (this.checkedAccounts.length === 0) {
          this.$message({
            showClose: false,
            message: '请选择需要导出的字段',
            type: 'error'
          });
        } else {
          require.ensure([], () => {
            const {export_json_to_excel} = require('../../excel/Export2Excel');
            const tHeader = this.checkedAccounts;
            // 上面设置Excel的表格第一行的标题

            const filterVal = this.exportField(this.checkedAccounts);
            // 上面的staffNum、accountId、staffName是tableData里对象的属性
            const list = this.accountDtoList;  //把data里的tableData存到list
            for (let i = 0; i < list.length; i++) {
              if (list[i].premissions === 1) {
                list[i].premissions = '全部'
              } else if (list[i].premissions === 2) {
                list[i].premissions = '递归'
              } else if (list[i].premissions === 3) {
                list[i].premissions = '本部门'
              } else if (list[i].premissions === 4) {
                list[i].premissions = '本人'
              } else if (list[i].premissions === 5) {
                list[i].premissions = '手动选择'
              }
              if (list[i].accountState === 1) {
                list[i].accountState = '正常'
              } else if (list[i].accountState === 2) {
                list[i].accountState = '冻结'
              } else if (list[i].accountState === 3) {
                list[i].accountState = '无效'
              }
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
            export_json_to_excel(tHeader, data, '账号管理'+currentdate);
            this.$message({
              showClose: true,
              message: '文件导出成功',
              type: 'success'
            });
            this.dialogVisible = false;
            this.checkedemployees = [];
            this.filterVal = [];
          })
        }
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      exportField(val) {
        for (let i = 0; i < val.length; i++) {
          if (this.checkedAccounts[i] === '登陆账号') {
            this.filterVal.push('accountName')
          } else if (this.checkedAccounts[i] === '员工编号') {
            this.filterVal.push('staffNum')
          } else if (this.checkedAccounts[i] === '员工姓名') {
            this.filterVal.push('staffName')
          } else if (this.checkedAccounts[i] === '所属部门') {
            this.filterVal.push('department')
          } else if (this.checkedAccounts[i] === '数据权限类型') {
            this.filterVal.push('premissions')
          } else if (this.checkedAccounts[i] === '账号状态') {
            this.filterVal.push('accountState')
          } else if (this.checkedAccounts[i] === '操作时间') {
            this.filterVal.push('modifyTime')
          } else if (this.checkedAccounts[i] === '操作人') {
            this.filterVal.push('modifyEmpName')
          }
        }
        return this.filterVal;
      },
    },
  }
</script>

<style scoped>


</style>

