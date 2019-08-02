<template>
  <home>
    <el-form :inline="true" label-width="100px" :model="formInline" class="demo-form-inline">
      <el-form-item label="部门名称">
        <el-input v-model="formInline.departmentName"></el-input>
      </el-form-item>
      <el-form-item label="负责人ID">
        <el-input v-model="formInline.StaffId"></el-input>
      </el-form-item>
      <el-form-item label="负责人姓名">
        <el-input v-model="formInline.StaffName"></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="formInline.telePhone"></el-input>
      </el-form-item>
      <el-form-item label="部门所在城市">
        <el-input v-model="formInline.cityName"></el-input>
      </el-form-item>
      <el-form-item label="部门级别">
        <el-input v-model="formInline.level"></el-input>
      </el-form-item>
      <el-form-item label="上级部门">
        <el-input v-model="formInline.upperDepartmentNo"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-input v-model="formInline.status"></el-input>
      </el-form-item>
      <el-form-item label="部门类型">
        <el-select v-model="formInline.departmentType" value="type1">
          <el-option label="type1" value="type11"></el-option>
          <el-option label="type2" value="type22"></el-option>
        </el-select>
      </el-form-item>
      <br>
      <el-form-item size="100px">
        <el-button type="primary" @click="Search">查询</el-button>
        <el-button @click="">导出</el-button>
      </el-form-item>
    </el-form>
    <el-table ref="multipleTable" :data="tableData" border @selection-change='handleSelectionChange'>
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id"><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="departmentNo" label="部门编号" width="140"></el-table-column>
      <el-table-column prop="workplace" label="办公点标识" width="140"></el-table-column>
      <el-table-column prop="departmentName" label="部门名称" width="140"></el-table-column>
      <el-table-column prop="staffId" label="负责人ID" width="140"></el-table-column>
      <el-table-column prop="staffName" label="负责人姓名" width="140"></el-table-column>
      <el-table-column prop="telephone" label="手机号" width="140"></el-table-column>
      <el-table-column prop="level" label="部门级别" width="140"></el-table-column>
      <el-table-column prop="upperDepartmentNo" label="上级部门" width="140"></el-table-column>
      <el-table-column prop="supportBusiness" label="支持业务线" width="140"></el-table-column>
      <el-table-column prop="departmentType" label="部门类型" width="140"></el-table-column>
      <el-table-column prop="status" label="状态" width="140"></el-table-column>
      <el-table-column prop="cityName" label="所在城市" width="140"></el-table-column>
      <el-table-column prop="companyName" label="关联公司名称" width="140"></el-table-column>
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
    data() {
      return {
        total: 0,
        currentPage: 1,
        pagesize: 10,
        form: {
          department_no: '',
        },
        formInline: {
          departmentName: '',
          StaffId: '',
          StaffName: '',
          telePhone: '',
          cityName: '',
          level: '',
          upperDepartmentNo: '',
          status: '',
          departmentType: ''
        },
        tableData: [],
        department_no: '',

      }
    },
    methods: {
      handleSelectionChange(val) {
        this.selection = val;
      },
      Search() {
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pagesize,
          departmentName: self.formInline.departmentName,
          staffName: self.formInline.staffName,
          staffId: self.formInline.staffId,
          telePhone: self.formInline.telePhone,
          cityName: self.formInline.cityName,
          level: self.formInline.level,
          upperDepartmentNo: self.formInline.upperDepartmentNo,
          status: self.formInline.status,
          departmentType: self.formInline.departmentType,
        };
        self.$http.get('/department/searchDepartment.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          console.log(self.tableData);
          self.total = result.page.totalCount;
          /*self.SexEnum = result.SexEnum;
          self.isDimissionEnum = result.isDissmissionEnum;*/
        }).catch(function (error) {
          console.log('department/querylist.do_' + error);
          self.$message.error("获取数据错误");
        });
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.currentPage = 1;
        this.Search(1, val);
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.Search(val, this.pageSize);
      },
    },
  }
</script>
