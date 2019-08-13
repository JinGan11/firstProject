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
          <el-col :span="6">
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
          <el-col :span="11">
            <el-form-item label="员工所属部门">
              <el-input style="width:180px;" v-model="form.departmentId"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
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
              <el-button type="primary" style="width:100px" @click="">导出</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px">
      <el-button type="primary" :disabled="AccountButtonPermission.createPermission" @click="creatAccount" style="width:70px">新建</el-button>
      <el-button type="primary" :disabled="deleteDisabled || AccountButtonPermission.modifyPermission" @click="modifyAccount" style="width:70px">修改</el-button>
      <el-button type="primary" :disabled="deleteDisabled || AccountButtonPermission.deletePermission" @click="deleteAccount" style="width:70px">删除</el-button>
      <el-button type="primary" :disabled="deleteDisabled || AccountButtonPermission.frozenPermission" @click="lock" style="width:70px">冻结</el-button>
      <el-button type="primary" :disabled="deleteDisabled || AccountButtonPermission.thawPermission" @click="unlock" style="width:70px">解冻</el-button>
      <el-button type="primary" :disabled="deleteDisabled || AccountButtonPermission.resetPermission" @click="resetPass" style="width:80px">密码重置</el-button>
      <el-button type="primary" :disabled="deleteDisabled || AccountButtonPermission.assignAccountPermission" @click="assignPermission" style="width:80px">分配权限</el-button>
      <el-button type="primary" :disabled="deleteDisabled || AccountButtonPermission.historyPermission" @click="" style="width:80px">历史记录</el-button>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange" >
<!--      <el-table-column type="selection" width="35"></el-table-column>-->
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id" @change="approvalInfo(scope.row)"><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="accountName" label="登陆账号" style="width:auto"></el-table-column>
      <el-table-column prop="staffNum" label="员工编号" style="width:auto"></el-table-column>
      <el-table-column prop="staffName" label="员工姓名" style="width:auto"></el-table-column>
      <el-table-column prop="department" label="所属部门" style="width:auto"></el-table-column>
      <el-table-column prop="premissions" label="数据权限类型" style="width:auto">
        <template slot-scope="scope">
          {{form.permissionsEnum[scope.row.premissions]}}
        </template>
      </el-table-column>
      <el-table-column prop="accountState" label="账号状态" style="width:auto">
        <template slot-scope="scope">
          {{form.accountStatusEnum[scope.row.accountState]}}
        </template>
      </el-table-column>
      <el-table-column prop="modifyTime" label="操作时间" style="width:auto"></el-table-column>
      <el-table-column prop="modifyEmpName" label="操作人" style="width:auto"></el-table-column>
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
                  ref="tree"
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
  </home>

</template>

<script>
  import commonUtils from '../../common/commonUtils'

  export default {
    data() {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        form: {
          accountNo: null,
          staffNo: null,
          name: null,
          permissionsList:[],
          permissionsEnum:{},
          accountStatusList:[],
          accountStatusEnum:{},
          permissions: null,
          departmentId:null,
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
        selection: [],
        disabled: true,
        deleteDisabled: true,
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
        selectedNodes:[],
        accountAssignPermissionFlag: false,
        accountAssignPermissionTitle: '账号权限分配',
        checkStrictly: false,
        row: {},

        AccountButtonPermission: {
          createPermission: true,
          modifyPermission: true,
          deletePermission: true,
          frozenPermission: true,
          thawPermission: true,
          resetPermission: true,
          assignAccountPermission: true,
          historyPermission: true
        }
      }
    },
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
    methods: {
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
        let accountId  = {
          id:this.selection
        }
        this.$http.post("account/lock",accountId)
      },
      unlock(){
        let accountId  = {
          id:this.selection
        }
        this.$http.post("account/unLock",accountId)
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
        const self = this;
         var param = {
           accountId: self.row.id,
           staffNum: self.row.staffNum,
           staffName: self.row.staffName,
           permissions: self.row.premissions,
           accountState: self.row.accountState
         };
        self.$http.post('account/deleAccount.do_',param)
          .then((result)=>{
            self.$message.info("删除成功");
            self.fetchData();

          }).catch(function (error) {
          commonUtils.Log("account/deleAccount.do_:"+error);
          self.$message.error("删除失败")
        });
      },
      approvalInfo(val){
        const self = this;
        self.row = val;
        self.disabled = false;
        if(val.accountState != 3){
          self.deleteDisabled = false;
        }else{
          self.deleteDisabled = true;
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
        self.accountAssignPermissionTitle = self.myAccount.accountName + "权限分配";
        self.$nextTick(() => {
          self.$refs.tree.setCheckedKeys([]);
        });
        self.accountAssignPermissionFlag = true;
        self.initAccountPermission();
        // this.$router.replace('/accountManagement/AssignPermission')
      },
      //初始化账户权限分配
      initAccountPermission() {
        const self = this;
        self.checkStrictly = true;
        let param = {
          id: self.myAccount.id
        }
        self.$http.post('account/getRoleList.do_',param).then((result) => {
          self.roleList = result.notOwnedRole.list;
          self.selected = result.ownedRole.list;
          console.log(result)
        }).catch(function (error) {
          commonUtils.Log("account/getRoleList.do_" + error);
          self.$message.error("获取数据错误")
        });
        self.$http.post('power/getAccountPower.do_',param).then((result) => {
          self.selectedNodes = result.accountPower;
          self.checkStrictly = false;
        }).catch(function (error) {
          commonUtils.Log("power/getAccountPower.do_" + error);
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
        }).catch(function (error) {

        });
      },
      handleClick(data,checked,node){
        if(checked === true) {
          this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
        } else {
          if (this.checkedId === data.id) {
            this.$refs.tree.setCheckedKeys([data.id]);
          }
        }
      },
      handleCheckChange(data, checked, indeterminate) {
        const self = this;
        console.log(self.$refs.tree.getCheckedNodes())
      },
      preservePower() {
        const self = this;
        var param1 = {
          id: self.myAccount.id,
          roleList : self.selected
        };
        var param2 = {
          id: self.myAccount.id,
          powerList: self.$refs.tree.getCheckedNodes().concat(self.$refs.tree.getHalfCheckedNodes())
        };
        self.$http.post("power/modifyAccountRole", param1)
          .then((result) => {
            console.log("success!")
          })
          .catch(function (error) {

          });
        console.log(param2)
        self.$http.post("power/modifySpecialPower", param2)
          .then((result) => {
            console.log("success!")
          })
          .catch(function (error) {

          });
        this.accountAssignPermissionFlag = false;
      },
      cencel(){
        this.accountAssignPermissionFlag = false;
      },
    },
    created() {
      const self = this;
      self.judgmentAuthority();
      self.$http.get('account/querylist.do_').then((result) => {
        self.tableData = result.page.list;
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
  }
</script>

<style scoped>


</style>

