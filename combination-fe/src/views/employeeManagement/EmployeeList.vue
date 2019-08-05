<template>
  <home>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="员工编号">
              <el-input placeholder="员工编号" style="width:200px;" v-model="form.staffNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工姓名">
              <el-input placeholder="员工姓名" style="width:200px;" v-model="form.staffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="登陆账号">
              <el-input placeholder="登陆账号" style="width:200px;" v-model="form.accountId"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item  label="是否离职">
              <el-select v-model="form.isDimission" clearable style="width:200px;" placeholder="请选择">
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
          <el-col :span="10">
            <el-form-item label="所属部门">
              <el-input placeholder="所属部门" disabled="true" style="width:400px;" v-model="form.departmentId"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-button
              type="text"
              @click="">选择</el-button>
            <el-button
              type="text"
              @click="">清空</el-button>
          </el-col>
          <el-col :span="10">
            <el-form-item label="上级部门">
              <el-input  placeholder="上级部门" disabled="true" style="width:400px;" v-model="form.upperDepartmentNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-button
              type="text"
              @click="">选择</el-button>
            <el-button
              type="text"
              @click="">清空</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
              <el-button type="primary" style="width:100px" @click="add">导出</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px">
      <el-button type="primary" @click="createEmployee" style="width:70px">新建</el-button>
      <el-button type="primary" @click="modifyEmployee" style="width:70px">修改</el-button>
      <el-button type="primary" @click="deleteEmployee" style="width:70px">删除</el-button>
      <el-button type="primary" @click="" style="width:70px">离职</el-button>
      <el-button type="primary" @click="" style="width:70px">恢复</el-button>
      <el-button type="primary" @click="distributionDepartment" style="width:80px">分配部门</el-button>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id"><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="staffNum" label="员工编号" width="150"></el-table-column>
      <el-table-column prop="accountId" label="登陆账号" width="150"></el-table-column>
      <el-table-column prop="staffName" label="员工姓名" width="120"></el-table-column>
      <el-table-column prop="staffSex" label="性别" width="50" style="text-align: center">
        <template slot-scope="scope">
          {{SexEnum[scope.row.staffSex]}}
        </template>
      </el-table-column>
      <el-table-column prop="staffTelephone" label="员工手机">
        <template slot-scope="scope">
          {{scope.row.staffTelephone|protect}}
        </template>
      </el-table-column>
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
    <el-dialog :title="title" :visible.sync="deleteEmployeeFlag" :close-on-click-modal="false" width="700px">
      <div class="dialog-main">
        <p>确认要删除该员工吗？</p>
      </div>
      <template slot="footer">
        <el-button type="primary" @click="sureDelete">确定</el-button>
        <el-button @click="cancelDelete">取消</el-button>
      </template>
    </el-dialog>
    <el-dialog :title="title" :visible.sync="distributionDepartmentFlag" :close-on-click-modal="false" width="700px">
      <div class="dialog-main">
        <el-form :inline="true" :model="formdiStributionDepartment" class="demo-form-inline" label-width="100px">
          <el-form-item label="分配部门"></el-form-item>
          <br>
          <el-form-item label="员工编号">
            <el-input v-model="formdiStributionDepartment.staffId" placeholder="请输入员工编号"></el-input>
          </el-form-item>
          <el-form-item label="员工姓名">
            <el-input v-model="formdiStributionDepartment.staffName" placeholder="请输入员工姓名"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="formdiStributionDepartment.staffSex" placeholder="请输入性别"></el-input>
          </el-form-item>
          <el-form-item label="员工手机">
            <el-input v-model="formdiStributionDepartment.staffPhone" placeholder="请输入员工手机"></el-input>
          </el-form-item>
          <el-form-item label="当前归属部门">
            <el-input v-model="formdiStributionDepartment.staffBeforeDepartment" placeholder="请输入当前部门"></el-input>
          </el-form-item>
          <el-form-item label="调整后部门">
            <el-input v-model="formdiStributionDepartment.staffAfterDepartment" placeholder="请输入归属部门"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <template slot="footer">
        <el-button type="primary" @click="saveDepartment">保存</el-button>
        <el-button @click="cancelDepartment">取消</el-button>
      </template>
    </el-dialog>
    <el-dialog :title='excelTitle' :visible.sync="dialogVisible" :close-on-click-modal="false" width="600px">
      <template>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange">
          <el-checkbox v-for="city in cities" :label="city" :key="city">{{city}}</el-checkbox>
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
  import commonUtils from '../../common/commonUtils'

  const cityOptions = ['员工编号', '登录账号', '员工姓名', '性别', '员工手机', '员工邮箱', '所属部门', '上级部门', '是否离职'];
  export default {
    data() {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        excelForm: {
          staffNum: '',
          staffName: '',
          accountId: '',
          staffSex: '',
          staffTelephone: '',
          staffEmail: '',
          departmentId: '',
          upperDepartmentNo: '',
          isDimission: '',
        },
        form: {
          staffNum: '',
          staffName: '',
          accountId: '',
          isDimission: '',
          departmentId: '',
          upperDepartmentNo: '',
        },
        tableData: [],
        selection: '',
        id: '',
        staffName: '',
        accountId: '',
        staffSex: '',
        SexEnum: {},
        staffTelephone: '',
        staffEmail: '',
        departmentId: '',
        upperDepartmentNo: '',
        isDimission: '',
        isDimissionEnum: {},
        title: '模板',
        excelTitle: '请选择需要导出的字段',
        dialogVisible: false,
        templateGroupName: '测试',
        description: '测试',
        staffDtoList: [],
        formdiStributionDepartment: {
          staffId: '',
          staffName: '',
          staffSex: '',
          staffPhone: '',
          staffBeforeDepartment: '',
          staffAfterDepartment: '',
        },
        distributionDepartmentFlag: false,
        deleteEmployeeFlag: false,
        options: [{
          value: '2',
          label: '全部'
        }, {
          value: '0',
          label: '在职'
        }, {
          value: '1',
          label: '离职'
        }],
        checkAll: false,
        checkedCities: [],
        cities: cityOptions,
        isIndeterminate: true,
        filterVal: [],
        list:[]
      }
    },
    filters: {
      protect: function(value) {
        if (!value) {
          return ''
        }
        return value.substr(0, 3) + "****" + value.substr(-4);
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
          staffNum: self.form.staffNum,
          staffName: self.form.staffName,
          departmentId: self.form.departmentId,
          isDimission: (self.form.isDimission === '2') ? '' : self.form.isDimission,
          accountId: self.form.accountId,
          upper_department_no: self.form.upperDepartmentNo,
        };
        self.$http.get('employee/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          self.SexEnum = result.SexEnum;
          self.isDimissionEnum = result.isDismissionEnum;
          self.staffDtoList = result.staffDtoList;
        }).catch(function (error) {
          commonUtils.Log("employee/querylist.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },
      save() {
        this.dialogVisible = false;
      },
      cancel() {
        this.dialogVisible = false;
      },
      add() {
        this.dialogVisible = true;
      },
      createEmployee() {//点击新建按钮，跳转到新建页面
        this.$router.replace('/CreateEmployee')

      },
      modifyEmployee() {//点击修改按钮，跳转到修改页面
        this.$router.replace('/ModifyEmployee')
      },
      deleteEmployee() {//删除员工
        this.deleteEmployeeFlag = true;
      },
      sureDelete() {//确认框中确认删除员工

      },
      cancelDelete() {//确认框中取消删除员工,并返回到员工管理列表页
        this.$router.replace('/EmployeeManagement');
      },
      distributionDepartment() {
        this.distributionDepartmentFlag = true;
      },
      saveDepartment() {

      },
      cancelDepartment() {

      },
      exportExcel() {
        if(this.checkedCities.length ===0){
          this.$message({
            showClose: false,
            message: '请选择需要导出的字段',
            type: 'error'
          });
        }else{
        require.ensure([], () => {
          const {export_json_to_excel} = require('../../excel/Export2Excel');
          const tHeader = this.checkedCities;
          // 上面设置Excel的表格第一行的标题

          const filterVal = this.exportField(this.checkedCities);
          // 上面的staffNum、accountId、staffName是tableData里对象的属性
          const list = this.staffDtoList;  //把data里的tableData存到list
          for (let i = 0; i < list.length; i++) {
             if(list[i].isDimission === 0){
               list[i].isDimission='在职'
             }else{
               list[i].isDimission='离职'
             }
            if(list[i].staffSex === 1){
              list[i].staffSex='男'
            }else{
              list[i].staffSex='女'
            }
          }
          console.log(list);
          const data = this.formatJson(filterVal, list);
          export_json_to_excel(tHeader, data, '员工管理列表excel');
          this.$message({
            showClose: true,
            message: '文件导出成功',
            type: 'success'
          });
          this.dialogVisible=false;
          this.checkedCities=[];
          this.filterVal=[];
        })
        }
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      handleCheckAllChange(val) {
        this.checkedCities = val ? cityOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckedCitiesChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.cities.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
      },
      exportField(val) {
        for (let i = 0; i < val.length; i++) {
          if (this.checkedCities[i] === '员工编号') {
            this.filterVal.push('staffNum')
          } else if (this.checkedCities[i] === '登录账号') {
            this.filterVal.push('accountId')
          } else if (this.checkedCities[i] === '员工姓名') {
            this.filterVal.push('staffName')
          } else if (this.checkedCities[i] === '性别') {
            this.filterVal.push('staffSex')
          } else if (this.checkedCities[i] === '员工手机') {
            this.filterVal.push('staffTelephone')
          } else if (this.checkedCities[i] === '员工邮箱') {
            this.filterVal.push('staffEmail')
          } else if (this.checkedCities[i] === '所属部门') {
            this.filterVal.push('departmentId')
          } else if (this.checkedCities[i] === '上级部门') {
            this.filterVal.push('upperDepartmentNo')
          } else if (this.checkedCities[i] === '是否离职') {
            this.filterVal.push('isDimission')
          }
        }
        return this.filterVal;
      }
    }
  }
</script>

<style scoped>


</style>
