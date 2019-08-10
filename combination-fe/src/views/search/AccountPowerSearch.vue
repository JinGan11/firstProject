<template>
  <home>
    <el-form :inline="true" label-width="100px" :model="accountForm" class="demo-form-inline">
      <el-form-item label="登录账号">
        <el-input v-model="accountForm.accountName">
        </el-input>
      </el-form-item>
      <el-form-item label="权限名称">
        <el-input v-model="accountForm.status"></el-input>
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
        <el-select v-model="accountForm.status" value="0">
          <el-option label="无效" value="0"></el-option>
          <el-option label="正常" value="1"></el-option>
          <el-option label="冻结" value="2"></el-option>
        </el-select>
      </el-form-item>
      <br>
      <el-form-item size="100px">
        <el-button type="primary" @click="Search">查询</el-button>
        <el-button @click="">导出</el-button>
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
  </home>
</template>

<script>
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
          status:'',
        },
        tableData:{},
        AccountStatusEnums:{},
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
          status:this.accountForm.status,
        };
        self.$http.get('/roleAccount/getAccountPowerList.do_', {
          params: param
        }).then((result) => {
          if (result.status=="success"){
            self.tableData=result.page.list;
            self.total=result.page.totalCount;
            this.AccountStatusEnums=result.AccountStatusEnums;
          }else {
            alert("查询失败");
          }
        })
      },
      handleSelectionChange(val){
        this.selection=val;
      },
    }
  }
</script>

<style scoped>

</style>
