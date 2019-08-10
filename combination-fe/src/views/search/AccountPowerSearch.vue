<template>
  <home>
    <el-form :inline="true" label-width="100px" :model="accountForm" class="demo-form-inline">
      <el-form-item label="登录账号">
        <el-input v-model="accountForm.accountName">
        </el-input>
      </el-form-item>
      <el-form-item label="员工编号">
        <el-input v-model="accountForm.staffNum"></el-input>
      </el-form-item>
      <el-form-item label="员工姓名">
        <el-input v-model="accountForm.staffName"></el-input>
      </el-form-item>
      <el-form-item label="员工所属部门">
        <el-input v-model="accountForm.departmentName"></el-input>
      </el-form-item>
      <el-form-item label="账号状态">
        <el-select v-model="accountForm.accountState" value="0">
          <el-option label="无效" value="0"></el-option>
          <el-option label="正常" value="1"></el-option>
          <el-option label="冻结" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="权限名称">
        <el-input v-model="accountForm.powerName"></el-input>
      </el-form-item>
      <br>
      <el-form-item size="100px">
        <el-button type="primary" @click="Search">查询</el-button>
        <el-button @click="exportRole">导出</el-button>
      </el-form-item>
    </el-form>
    <el-table ref="multipleTable" :data="tableData" border @selection-change='handleSelectionChange'>
      <el-table-column prop="accountName" label="登录账号" width="140"></el-table-column>
      <el-table-column prop="staffNum" label="员工编号" width="140"></el-table-column>
      <el-table-column prop="staffName" label="员工姓名" width="140"></el-table-column>
      <el-table-column prop="departmentName" label="员工所属部门" width="140"></el-table-column>
      <el-table-column prop="accountState" label="账号状态" width="140">
        <template slot-scope="scope">
          {{AccountStatusEnums[scope.row.accountState]}}
        </template>
      </el-table-column>
      <el-table-column prop="powerName" label="权限名称" width="140"></el-table-column>
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
    <el-dialog title='excelTitle' :visible.sync="exportDialogVisible" :close-on-click-modal="false" width="600px">
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

  </home>
</template>

<script>
  const rolesOptions = ['登录账号', '员工编号', '员工姓名', '员工所属部门', '账号状态', '权限名称'];
  export default {
    name: "accountPowerSearch",
    data() {
      return {
        total:0,
        pageSize:10,
        currentPage:1,
        selection:'',
        accountForm:{
          accountName:'',
          staffId:'',
          staffNum:'',
          staffName:'',
          departmentName:'',
          powerName:'',
          accountState:'',
          status:'',
        },
        tableData:[],
        AccountStatusEnums:{},
        exportDialogVisible:false,
        checkAll: false,
        checkRoles: [],
        roles: rolesOptions,
        isIndeterminate: true,
        filterVal: [],
        list: [],
        disabled: true,
        accountPowerList: [],
      }
    },
    methods: {
      handleSizeChange(val) {
        this.pageSize = val;
        this.currentPage = 1;
        this.Search(1, val);
      },
      handleCurrentChange(val){
        this.currentPage=val;
        this.Search(val,this.pageSize);
      },
      Search() {
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          accountName:this.accountForm.accountName,
          staffNum:this.accountForm.staffNum,
          staffName:this.accountForm.staffName,
          departmentName:this.accountForm.departmentName,
          powerName:this.accountForm.powerName,
          accountState:this.accountForm.accountState,
        };
        self.$http.get('/roleAccount/getAccountPowerList.do_', {
          params: param
        }).then((result) => {
          if (result.status=="success"){
            self.tableData=result.page.list;
            self.total=result.page.totalCount;
            this.AccountStatusEnums=result.AccountStatusEnums;
            this.accountPowerList=result.page.list;
          }else {
            alert("查询失败");
          }
        })
      },
      handleSelectionChange(val){
        this.selection=val;
      },
      exportRole() {
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
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '员工管理列表excel');
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
            this.filterVal.push('powerName')
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
    }
  }
</script>

<style scoped>

</style>
