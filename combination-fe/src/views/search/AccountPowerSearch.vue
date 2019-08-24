<template>
  <home>
    <el-form :inline="true" label-width="100px" :model="accountForm" class="demo-form-inline">
      <el-row>
        <el-col :span="7">
          <el-form-item label="登录账号">
            <el-input v-model="accountForm.accountName" clearable>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="员工编号">
            <el-input v-model="accountForm.staffNum" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="员工所属部门">
            <el-input :disabled="true" v-model="accountForm.departmentName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-button type="text" @click="selectDepartment">选择</el-button>
          <el-button type="text" @click="clearDepartment">清空</el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="7">
          <el-form-item label="员工姓名">
            <el-input v-model="accountForm.staffName" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="账号状态">
            <el-select v-model="accountForm.accountState">
              <el-option label="全部" value=""></el-option>
              <el-option label="无效" value="3"></el-option>
              <el-option label="正常" value="1"></el-option>
              <el-option label="冻结" value="2"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="权限名称">
            <el-input v-model="accountForm.path" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="1.5">
          <el-button type="text" @click="selectPower">选择</el-button>
          <el-button type="text" @click="clearPower">清空</el-button>
        </el-col>
      </el-row>
      <el-row style="text-align: center">
        <el-form-item size="100px">
          <el-button type="primary" @click="Search1">查询</el-button>
          <el-button v-if="!BtnPermission.exportPermission" @click="exportRole">导出</el-button>
        </el-form-item>
      </el-row>
    </el-form>
    <el-table ref="multipleTable" :data="tableData" border @selection-change='handleSelectionChange'>
      <el-table-column prop="accountName" label="登录账号" width="170">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="formDetails(scope.row)">{{scope.row.accountName}}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="staffNum" label="员工编号" width="170"></el-table-column>
      <el-table-column prop="staffName" label="员工姓名" width="170"></el-table-column>
      <el-table-column prop="departmentName" label="员工所属部门" width="170"></el-table-column>
      <el-table-column prop="accountState" label="账号状态" width="170">
        <template slot-scope="scope">
          {{AccountStatusEnums[scope.row.accountState]}}
        </template>
      </el-table-column>
      <el-table-column prop="path" label="权限名称">
        <template slot-scope="scope">
          <el-popover
            placement="bottom"
            width="200"
            trigger="hover"
            :content="scope.row.path">
            <el-button slot="reference" type="text">{{scope.row.path|ellipsis}}</el-button>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="accountId" v-if="false"></el-table-column>
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
    <el-dialog title='详情页面' :visible.sync="detailDialogVisible" :close-on-click-modal="false" width="800px">
      <hr style="height: 1px">
      <div style="font-family:Consolas;font-size:20px;margin-left: 50px;margin-bottom: 20px;">账号信息</div>
      <el-form label-width="130px" :model="formDetail" class="demo-form-inline">
        <el-row>
          <el-col :span="10">
            <el-form-item label="登录账号">
              <el-input :disabled="true" v-model="formDetail.accountName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="密保邮箱">
              <el-input :disabled="true" v-model="formDetail.secretEmail"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="员工编号">
              <el-input :disabled="true" v-model="formDetail.staffNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="员工姓名">
              <el-input :disabled="true" v-model="formDetail.staffName">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="数据权限类型">
              <el-input :disabled="true" v-model="formDetail.premissions"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="账号状态">
              <el-input :disabled="true" v-model="formDetail.accountState"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <hr style="height: 1px;">
        <div style="font-family:Consolas;font-size:20px;margin-left: 50px;margin-bottom: 20px;">其他信息</div>
        <el-row>
          <el-col :span="10">
            <el-form-item label="新建人">
              <el-input :disabled="true" v-model="formDetail.creatEmpName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="新建时间">
              <el-input :disabled="true" v-model="formDetail.createTime"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="修改人">
              <el-input :disabled="true" v-model="formDetail.modifyEmpName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="修改时间">
              <el-input :disabled="true" v-model="formDetail.modifyTime"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注">
          <el-input type="textarea" :disabled="true" v-model="formDetail.remark"></el-input>
        </el-form-item>

      </el-form>
    </el-dialog>
    <!--选择部门时弹出的窗口-->
    <el-dialog :title='titleDepartment' :visible.sync="dialogVisibleDepartment" :close-on-click-modal="false"
               width="50%">
      <div>
        <span>选择要操作的部门</span>
        <br><br>
        <el-tree
          ref="tree"
          :props="defaultProps"
          node-key="id"
          :load="loadNode"
          lazy="true"
          check-strictly
          show-checkbox
          :render-content="renderContent"
          @check-change="handleClick">
        </el-tree>
        <br>
        <el-button type="primary" @click="getCheckedDepartment">确定</el-button>
      </div>
    </el-dialog>
    <!--选择权限-->
    <el-dialog :title="roleAssignPermissionTitle" :visible.sync="roleAssignPermissionFlag" :close-on-click-modal="false"
               width="700px">
      <div class="dialog-main">
        <el-form>
          <el-row type="flex" justify="center" style="width: 100%;">
            <el-col :span="11">
              <el-scrollbar style="width: 400px">
                <el-tree
                  style="float: left;margin-left: 100px;height: 350px"
                  ref="permTree"
                  :props="PermissionProps"
                  node-key="powerId"
                  :load="loadNodePermTree"
                  lazy="true"
                  show-checkbox
                  check-strictly
                  @check-change="handleCheckChange">
                </el-tree>
              </el-scrollbar>
            </el-col>
          </el-row>
          <div style="height: 80px;display: flex;align-items: center;margin-left: 80%">
            <el-button type="primary" style="margin-right: 10px" @click="getCheckedPermission">确定</el-button>
          </div>
        </el-form>
      </div>
    </el-dialog>
  </home>
</template>

<script>
  const rolesOptions = ['登录账号', '员工编号', '员工姓名', '员工所属部门', '账号状态', '权限名称'];
  export default {
    name: "accountPowerSearch",
    data() {
      return {
        total: 0,
        pageSize: 10,
        currentPage: 1,
        selection: '',
        accountForm: {
          accountName: '',
          staffId: '',
          staffNum: '',
          staffName: '',
          departmentName: '',
          path: '',
          accountState: '',
          status: '',
          powerId: '',
        },
        formDetail: {
          accountName: '',
          staffId: '',
          staffNum: '',
          staffName: '',
          email: '',
          createEmp: '',
          createTime: '',
          modifyEmp: '',
          modifyTime: '',
          premissions: '',
          accountState: '',
          remark: '',
        },
        defaultProps: {
          label: 'departmentName',
          children: 'children',
          id: 'id',
          status: 'status'
        },
        tableData: [],
        AccountStatusEnums: {},
        titleDepartment: '选择部门',
        dialogVisibleDepartment: false,
        detailDialogVisible: false,
        exportDialogVisible: false,
        checkAll: false,
        checkRoles: [],
        roles: rolesOptions,
        isIndeterminate: true,
        filterVal: [],
        list: [],
        disabled: true,
        accountPowerList: [],
        permissionEnum: [],
        accountStatusEnum: [],
        powerDialogVisible: false,
        selectedNodes: null,
        checkStrictly: false,

        roleAssignPermissionFlag: false,
        powerSelectedList: [],
        selected: [],
        PermissionProps: {
          label: 'powerName',
          children: 'children',
          id: 'powerId'
        },
        selectedNodes: [],
        myRole: [],
        roleAssignPermissionTitle: '选择权限',
        powerTree: [],
        checkStrictly: false,
        roleInfoDetailFlag: false,

        BtnPermission: {
          exportPermission: true,
        },
      }
    },
    methods: {
      handleSizeChange(val) {
        this.pageSize = val;
        this.currentPage = 1;
        this.Search(1, val);
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.Search(val, this.pageSize);
      },
      Search() {
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          accountName: this.accountForm.accountName,
          staffNum: this.accountForm.staffNum,
          staffName: this.accountForm.staffName,
          departmentName: this.accountForm.departmentName,
          powerId: this.accountForm.powerId,
          accountState: this.accountForm.accountState,
          date:new Date().getTime(),
        };
        self.$http.get('/roleAccount/getAccountPowerList.do_', {
          params: param
        }).then((result) => {
          if (result.status == "success") {
            self.tableData = result.page.list;
            self.total = result.page.totalCount;
            this.AccountStatusEnums = result.AccountStatusEnums;
            this.accountPowerList = result.accountPowerDtoList;
          } else {
            alert("查询失败");
          }
        })
      },
      Search1() {
        this.currentPage=1;
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          accountName: this.accountForm.accountName,
          staffNum: this.accountForm.staffNum,
          staffName: this.accountForm.staffName,
          departmentName: this.accountForm.departmentName,
          powerId: this.accountForm.powerId,
          accountState: this.accountForm.accountState,
          date:new Date().getTime(),
        };
        self.$http.get('/roleAccount/getAccountPowerList.do_', {
          params: param
        }).then((result) => {
          if (result.status == "success") {
            self.tableData = result.page.list;
            self.total = result.page.totalCount;
            this.AccountStatusEnums = result.AccountStatusEnums;
            this.accountPowerList = result.accountPowerDtoList;
          } else {
            alert("查询失败");
          }
        })
      },
      handleSelectionChange(val) {
        this.selection = val;
      },
      exportRole() {
        this.checkAll=false;
        this.exportDialogVisible = true;
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
            const list = this.accountPowerList;  //把data里的tableData存到list
            for (let i = 0; i < list.length; i++) {
              list[i].accountState = this.AccountStatusEnums[list[i].accountState];
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
            export_json_to_excel(tHeader, data, '账号功能权限明细' + currentdate);
            this.$message({
              showClose: true,
              message: '文件导出成功',
              type: 'success'
            });
            this.exportDialogVisible = false;
            this.checkRoles = [];
            this.filterVal = [];
          })
        }
      },
      cancel() {
        this.exportDialogVisible = false;
        this.checkRoles = [];
        this.filterVal = [];
      },
      //将每列与数据对应
      exportField(val) {
        for (let i = 0; i < val.length; i++) {
          if (this.checkRoles[i] === '登录账号') {
            this.filterVal.push('accountName')
          } else if (this.checkRoles[i] === '员工编号') {
            this.filterVal.push('staffNum')
          } else if (this.checkRoles[i] === '员工姓名') {
            this.filterVal.push('staffName')
          } else if (this.checkRoles[i] === '员工所属部门') {
            this.filterVal.push('departmentName')
          } else if (this.checkRoles[i] === '账号状态') {
            this.filterVal.push('accountState')
          } else if (this.checkRoles[i] === '权限名称') {
            this.filterVal.push('path')
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
      //显示详情页，并注入详情页信息
      formDetails(val) {
        var param = {
          id: val.accountId,
          date:new Date().getTime(),
        }
        this.$http.get("/account/selectById.do_", {
          params: param
        }).then((result) => {
          this.formDetail = result.account;
          this.formDetail.premissions = result.permissionEnum[this.formDetail.premissions];
          this.formDetail.accountState = result.accountStatusEnum[this.formDetail.accountState];
        })
        this.detailDialogVisible = true;
      },
      /*selectDepartment到getCheckedDepartment是对应的部门操作*/
      selectDepartment() {//选择部门
        this.dialogVisibleDepartment = true;
      },
      selectPower() {
        const self = this;
        self.roleAssignPermissionFlag = true;
      },
      clearDepartment() {//清除部门的值
        this.accountForm.departmentName = '';
      },
      clearPower() {
        this.accountForm.path = '';
        this.accountForm.powerId = '';
      },

      //权限树的获得
      //获取树数据
      loadNode(node, resolve) {
        var self = this;
        self.$http.get('department/buildTree2.do_', {
          params: null
        }).then((result) => {
          resolve([result.departmentDto]);
        }).catch(function (error) {

        });
      },
      renderContent(h, {node, data, store}) {
        // 这里编译器有红色波浪线不影响运行...
        if (data.status != 1) {
          return (
            < span
          style = "color:red" > {node.label} < /span>
        )
          ;
        } else {
          return (
            < span > {node.label} < /span>
        )
          ;
        }
      },
      handleClick(data, checked, node) {
        // 手动设置单选
        if (checked === true) {
          this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
          // 设置按钮是否可选（选中节点后调用两次handleClick，第一次checked为true，所以设置按钮写在这）
          if (data.status === 1) {
            this.operationBtnActive = false;
          } else {
            this.operationBtnActive = true;
          }
        } else {
          if (this.checkedId == data.id) {
            this.$refs.tree.setCheckedKeys([data.id]);
          }
        }
      },
      getCheckedDepartment() {
        // 获取部门 回填到文本框中
        // alert(this.$refs.tree.getCheckedNodes()[0].departmentName);
        this.accountForm.departmentName = this.$refs.tree.getCheckedNodes()[0].departmentName;
        this.dialogVisibleDepartment = false;
      },

      getCheckedPermission() {
        this.accountForm.path = this.$refs.permTree.getCheckedNodes()[0].powerName;
        this.roleAssignPermissionFlag = false;
      },
      //获取树数据
      loadNodePermTree(node, resolve) {
        var self = this;
        self.$http.get('power/getPowerList', {
          params: null
        }).then((result) => {
          resolve([result.powerTree]);
        }).catch(function (error) {
          commonUtils.Log("account/querylist.do_:" + error);
          self.$message.error("获取数据错误")
        });

      },
      handleCheckChange(data, checked, node) {
        // 手动设置单选
        if (checked === true) {
          this.accountForm.powerId = data.powerId;
          this.checkedId = data.powerId;
          this.$refs.permTree.setCheckedKeys([data.powerId]);
        } else {
          if (this.checkedId == data.powerId) {
            this.$refs.permTree.setCheckedKeys([data.powerId]);
          }
        }
      },
      clearPermission() {//清除权限的值
        this.form.powerName = '';
      },
      judgmentAuthority() {
        const self = this;
        let permission = self.$store.state.powerList;
        permission.forEach(item=>{
          if (item === 69) {
            self.BtnPermission.exportPermission = false
          }
        });
      },
    },
    filters: {
      ellipsis(value) {
        if (!value) return "";
        if (value.length > 30) {
          return value.slice(0, 30) + "...";
        }
        return value;
      }
    },
    mounted() {
      this.judgmentAuthority();
    }
  }
</script>

<style scoped>

</style>
