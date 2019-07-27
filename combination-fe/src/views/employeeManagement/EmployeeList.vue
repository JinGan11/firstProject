<template>
  <home>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="员工编号">
              <el-input style="width:200px;" v-model="form.employeeNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工姓名">
              <el-input style="width:200px;" v-model="form.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="登陆账号">
              <el-input style="width:200px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="是否离职">
              <el-input style="width:200px;" v-model="form.isLeave"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="所属部门">
              <el-input style="width:200px;" v-model="form.department"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级部门">
              <el-input style="width:200px;" v-model="form.uperDepartment"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
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
    <div>
      <el-button type="primary" @click="" style="width:70px">新建</el-button>
      <el-button type="primary" @click="" style="width:70px">修改</el-button>
      <el-button type="primary" @click="" style="width:70px">删除</el-button>
      <el-button type="primary" @click="" style="width:70px">离职</el-button>
      <el-button type="primary" @click="" style="width:70px">恢复</el-button>
      <el-button type="primary" @click="" style="width:80px">分配部门</el-button>
    </div>
    <el-table :data="tableData" border @selection-change="handleSelectionChange" >
      <el-table-column type="selection" width="35"></el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="satff_num" label="员工编号" width="150"></el-table-column>
      <el-table-column prop="accountId" label="登陆账号"width="150"></el-table-column>
      <el-table-column prop="staff_name" label="员工姓名" ></el-table-column>
      <el-table-column prop="staff_sex" label="性别" width="80"></el-table-column>
      <el-table-column prop="staff_telephone" label="员工手机" width="200"></el-table-column>
      <el-table-column prop="staff_email" label="员工邮箱" width="200"></el-table-column>
      <el-table-column prop="department_id" label="所属部门" width="150"></el-table-column>
      <el-table-column prop="uper_department_id" label="上级部门" width="150"></el-table-column>
      <el-table-column prop="is_dimission" label="是否离职" width="100"></el-table-column>
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
        form: {
          employeeNo: '',
          name: '',
          accountNo: '',
          isLeave: '',
          department: '',
          uperDepartment: '',
        },
        tableData: [],
        selection:[],
        id:'',
        satff_num:'',
        accountId:'',
        staff_name:'',
        staff_sex:'',
        staff_telephone:'',
        staff_email:'',
        department_id:'',
        uper_department_id:'',
        is_dimission:'',
        currentPage: 1,
        pageSize: 10,
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
        this.getTableData(1, val);
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.getTableData(val, this.pageSize);
      },
      handleSelectionChange(val) {
        this.selection = val;
      },
    }
  }
</script>

<style scoped>


</style>
