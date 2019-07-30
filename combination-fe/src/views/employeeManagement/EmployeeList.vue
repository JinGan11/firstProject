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
              <el-input style="width:200px;" v-model="form.isDimission"></el-input>
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
              <el-input style="width:200px;" v-model="form.upperDepartment"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
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
      <el-button type="primary" @click="" style="width:70px">新建</el-button>
      <el-button type="primary" @click="" style="width:70px">修改</el-button>
      <el-button type="primary" @click="" style="width:70px">删除</el-button>
      <el-button type="primary" @click="" style="width:70px">离职</el-button>
      <el-button type="primary" @click="" style="width:70px">恢复</el-button>
      <el-button type="primary" @click="" style="width:80px">分配部门</el-button>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange" >
      <el-table-column type="selection" width="35"></el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="staff_num" label="员工编号" width="150"></el-table-column>
      <el-table-column prop="accountId" label="登陆账号"width="150"></el-table-column>
      <el-table-column prop="staff_name" label="员工姓名" width="120"></el-table-column>
      <el-table-column prop="staff_sex" label="性别" width="50" style="text-align: center">
        <template slot-scope="scope">
          {{SexEnum[scope.row.staff_sex]}}
        </template>
      </el-table-column>
      <el-table-column prop="staff_telephone" label="员工手机" ></el-table-column>
      <el-table-column prop="staff_email" label="员工邮箱" width="200"></el-table-column>
      <el-table-column prop="department_id" label="所属部门" width="120"></el-table-column>
      <el-table-column prop="upper_department_no" label="上级部门" width="150"></el-table-column>
      <el-table-column prop="is_dimission" label="是否离职" width="100">
        <template slot-scope="scope">
          {{isDimissionEnum[scope.row.is_dimission]}}
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
          employeeNo: '',
          name: '',
          accountNo: '',
          isDimission: '',
          department: '',
          upperDepartment: '',
        },
        tableData: [],
        selection:[],
        id:'',
        staff_num:'',
        accountId:'',
        staff_name:'',
        staff_sex:'',
        SexEnum:{},
        staff_telephone:'',
        staff_email:'',
        department_id:'',
        upper_department_no:'',
        is_dimission:'',
        isDimissionEnum:{}
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
      },
      fetchData() { //获取数据
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          staff_num:self.form.employeeNo,
          staff_name:self.form.name,
          accountId:self.form.accountNo,
          is_dimission: self.form.isDimission,
          department:self.form.department ,
          upper_department_no: self.form.upperDepartment,
        };
        self.$http.get('employee/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          self.SexEnum = result.SexEnum;
          self.isDimissionEnum = result.isDismissionEnum;
        }).catch(function (error) {
          commonUtils.Log("employee/querylist.do_:"+error);
          self.$message.error("获取数据错误");
        });
      },
    }
  }
</script>

<style scoped>


</style>
