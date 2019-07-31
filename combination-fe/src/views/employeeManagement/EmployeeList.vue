<template>
  <home>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="员工编号">
              <el-input style="width:200px;" v-model="form.staffNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工姓名">
              <el-input style="width:200px;" v-model="form.staffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="登陆账号">
              <el-input style="width:200px;" v-model="form.accountId"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="是否离职">
              <el-select v-model="form.isDimission" clearable  style="width:200px;" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="所属部门">
              <el-input style="width:200px;" v-model="form.departmentId"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级部门">
              <el-input style="width:200px;" v-model="form.upperDepartmentNo"></el-input>
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
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id" ><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="staffNum" label="员工编号" width="150"></el-table-column>
      <el-table-column prop="accountId" label="登陆账号"width="150"></el-table-column>
      <el-table-column prop="staffName" label="员工姓名" width="120"></el-table-column>
      <el-table-column prop="staffSex" label="性别" width="50" style="text-align: center">
        <template slot-scope="scope">
          {{SexEnum[scope.row.staffSex]}}
        </template>
      </el-table-column>
      <el-table-column prop="staffTelephone" label="员工手机" ></el-table-column>
      <el-table-column prop="staffEmail" label="员工邮箱" width="200"></el-table-column>
      <el-table-column prop="departmentId" label="所属部门" width="120"></el-table-column>
      <el-table-column prop="upperDepartmentNo" label="上级部门" width="150"></el-table-column>
      <el-table-column prop="isDimission" label="是否离职" width="100">
        <template slot-scope="scope">
          {{isDimissionEnum[scope.row.isDimission]}}
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
          staffNum: '',
          staffName: '',
          accountId: '',
          isDimission: '',
          departmentId: '',
          upperDepartmentNo: '',
        },
        tableData: [],
        selection:'',
        id:'',
        staffName:'',
        accountId:'',
        staff_name:'',
        staffSex:'',
        SexEnum:{},
        staffTelephone:'',
        staffEmail:'',
        departmentId:'',
        upperDepartmentNo:'',
        isDimission:'',
        isDimissionEnum:{},
        options: [{
          value: '0',
          label: '在职'
        }, {
          value: '1',
          label: '离职'
        }]
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
          staffNum:self.form.staffNum,
          staffName:self.form.staffName,
          departmentId:self.form.departmentId,
          isDimission: self.form.isDimission,
          accountId:self.form.accountId ,
          upper_department_no: self.form.upperDepartmentNo,
        };
        self.$http.get('employee/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          console.log(self.tableData);
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
