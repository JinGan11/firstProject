<template>
  <home>
    <div style="width: 95%;">
      <el-form ref="form" :model="form" label-width="100px">
        <el-row>
          <el-col :span="5">
            <el-form-item label="登陆账号" >
              <el-input style="width:140px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工编号">
              <el-input style="width:160px;" v-model="form.employeeNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工姓名">
              <el-input style="width:160px;" v-model="form.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="数据权限类型">
              <el-select v-model="form.dataType" clearable placeholder="请选择">
                <el-option style="width:160px;"
                  v-for="item in form.dataTypeList"
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
              <el-input style="width:140px;" v-model="form.department"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="是否关联员工">
              <el-input style="width:160px;" v-model="form.isRelEmployee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="账号状态">
              <el-input style="width:170px;" v-model="form.status"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-form ref="form" :model="form">
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <el-button type="primary" @click="" style="width:100px">查询</el-button>
              <el-button type="primary" style="width:100px" @click="">导出</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px">
      <el-button type="primary" @click="" style="width:70px">新建</el-button>
      <el-button type="primary" @click="" style="width:70px">修改</el-button>
      <el-button type="primary" @click="" style="width:70px">删除</el-button>
      <el-button type="primary" @click="" style="width:70px">冻结</el-button>
      <el-button type="primary" @click="" style="width:70px">解冻</el-button>
      <el-button type="primary" @click="" style="width:80px">密码重置</el-button>
      <el-button type="primary" @click="" style="width:80px">分配权限</el-button>
      <el-button type="primary" @click="" style="width:80px">历史记录</el-button>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange" >
      <el-table-column type="selection" width="35"></el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="name" label="登陆账号" style="width:auto"></el-table-column>
      <el-table-column prop="staffNum" label="员工编号" style="width:auto"></el-table-column>
      <el-table-column prop="staffName" label="员工姓名" style="width:auto"></el-table-column>
      <el-table-column prop="department" label="所属部门" style="width:auto"></el-table-column>
      <el-table-column prop="premissions" label="数据权限类型" style="width:auto">
        <template slot-scope="scope">
          {{form.dataTypeEnum[scope.row.premissions]}}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="账号状态" style="width:auto">
        <template slot-scope="scope">
          {{form.accountStatusEnum[scope.row.status]}}
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
          accountNo: '',
          employeeNo: '',
          name: '',
          dataTypeList:[],
          dataTypeEnum:{},
          accountStatusEnum:{},
          dataType: '',
          department: '',
          isRelEmployee: '',
          status:''
        },
        tableData: [],
        selection: [],
        id: '',
        account_name: '',
        staff_num: '',
        staff_name: '',
        department: '',
        data_type: '',
        account_status: '',
        modify_time: '',
        modify_emp: '',
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
    },
    methods: {
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
      }
    },
    created() {
      const self = this;
      // self.$http.get('account/permission.do_').then((result) => {
      //   self.form.dataTypeList = result.permissionList;
      // });
      self.$http.get('account/querylist.do_').then((result) => {
        self.tableData = result.page.list;
        self.form.dataTypeList = result.permissionList;
        self.form.dataTypeEnum = result.permissionEnum;
        self.form.accountStatusEnum = result.accountStatusEnum;

      });
    },
  }
</script>

<style scoped>


</style>

