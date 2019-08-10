<template>
  <home>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="角色名称">
              <el-input placeholder="角色名称" style="width:200px;" v-model="form.roleName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item  label="支持业务线">
              <el-select v-model="form.businessLine" clearable style="width:200px;" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="登录账号">
              <el-input placeholder="登录账号" style="width:200px;" v-model="form.accountName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工编号">
              <el-input placeholder="员工编号" style="width:200px;" v-model="form.staffNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="员工姓名">
              <el-input placeholder="员工姓名" style="width:200px;" v-model="form.staffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item  label="角色状态">
              <el-select v-model="form.roleStatus" clearable style="width:200px;" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item  label="账号状态">
              <el-select v-model="form.accountState" clearable style="width:200px;" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="所属部门">
              <el-input placeholder="所属部门" disabled="true" style="width:400px;" v-model="form.departmentName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <div v-if="!buttonDisabled">
                <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
                <el-button type="primary" style="width:100px" @click="exportExcel">导出</el-button>
              </div>
              <div v-else>
                <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px" v-if="!buttonDisabled">
    </div>
    <div style="margin-bottom: 10px" v-else>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.staffNum" @change="selectionActive"><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="id" label="角色ID" width="120"></el-table-column>
      <el-table-column prop="roleName" label="角色名称" width="150"></el-table-column>
      <el-table-column prop="businessLine" label="支持业务线" width="150"></el-table-column>
      <el-table-column prop="accountName" label="登陆账号" width="150"></el-table-column>
      <el-table-column prop="staffNum" label="员工编号" width="150"></el-table-column>
      <el-table-column prop="staffName" label="员工姓名" width="150"></el-table-column>
      <el-table-column prop="departmentName" label="所属部门" width="150"></el-table-column>
      <el-table-column prop="roleStatus" label="角色状态" width="150"></el-table-column>
      <el-table-column prop="accountState" label="账号状态" width="150"></el-table-column>
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
    <el-dialog :title='excelTitle' :visible.sync="dialogVisible" :close-on-click-modal="false" width="600px">
      <template>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="checkedemployees" @change="handleCheckedEmployeesChange">
          <el-checkbox v-for="city in employees" :label="city" :key="city">{{city}}</el-checkbox>
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

  const roleAccountOptions = ['角色ID', '角色名称', '支持业务线', '登录账号', '员工编号', '员工姓名', '所属部门', '角色状态', '账号状态'];
  export default {
    // name: "roleAccount-list",
    // inject:['test1'],
    props: {
      relAccount: false
    },
    data() {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        excelForm: {
          roleId:'',
          roleName:'',
          businessLine:'',
          accountName:'',
          staffNum: '',
          staffName: '',
          departmentName: '',
          roleStatus:'',
          accountState:'',

        },
        form: {
          roleName:'',
          businessLine:'',
          accountName:'',
          staffNum: '',
          staffName: '',
          roleStatus:'',
          accountState: '',
          // isDimission: '',
          departmentName: '',
        },
        tableData: [],
        selection: '',
        // id: '',
        // staffName: '',
        // accountId: '',
        // staffSex: '',
        // SexEnum: {},
        // staffTelephone: '',
        // staffEmail: '',
        // departmentId: '',
        // upperDepartmentNo: '',
        // isDimission: '',
        // isDimissionEnum: {},
        // title: '模板',
        // excelTitle: '请选择需要导出的字段',
        // dialogVisible: false,
        // templateGroupName: '测试',
        // description: '测试',
        // staffDtoList: [],
        // formdiStributionDepartment: {
        //   staffId: '',
        //   staffName: '',
        //   staffSex: '',
        //   staffPhone: '',
        //   staffBeforeDepartment: '',
        //   staffAfterDepartment: '',
        // },
        // distributionDepartmentFlag: false,
        // deleteEmployeeFlag: false,
        // options: [{
        //   value: '2',
        //   label: '全部'
        // }, {
        //   value: '0',
        //   label: '在职'
        // }, {
        //   value: '1',
        //   label: '离职'
        // }],
        // cloumnDisabled: false,
        // buttonDisabled: false,
        // checkAll: false,
        // checkedroleAccount: [],
        // roleAccount:roleAccountOptions,
        // isIndeterminate: true,
        // filterVal: [],
        // list:[],
        // disabled:true
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
    // activated() {
    //   commonUtils.Log("页面激活");
    // },
    // mounted() {
    //   commonUtils.Log("页面进来");
    // },
    created(){
      var self = this;
      if(self.relAccount) {
        self.form.isDimission = '在职';
        self.buttonDisabled = true;
        self.cloumnDisabled = true;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          isDimission: 0,
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
        })
      };
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
        //   page: self.currentPage,
        //   limit: self.pageSize,
           roleName: self.form.roleName,
           businessLine:self.form.businessLine,
           accountName:self.form.accountName,
           staffNum: self.form.staffNum,
           staffName:self.form.staffName,
           roleStatus:self.form.roleStatus,
           accountState:self.form.accountState,
           departmentName: self.form.departmentName,
        //   isDimission: (self.form.isDimission === '2') ? '' : self.form.isDimission,
        //   accountId: self.form.accountId,
         };
        self.$http.get('roleAccount/getRoleAccountList.do',{params: param}).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          // self.SexEnum = result.SexEnum;
          // self.isDimissionEnum = result.isDismissionEnum;
          // self.staffDtoList = result.staffDtoList;
        }).catch(function (error) {
          commonUtils.Log("employee/querylist.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },

      exportExcel() {
        if(this.checkedroleAccount.length ===0){
          this.$message({
            showClose: false,
            message: '请选择需要导出的字段',
            type: 'error'
          });
        }else{
          require.ensure([], () => {
            const {export_json_to_excel} = require('../../excel/Export2Excel');
            const tHeader = this.checkedroleAccount;
            // 上面设置Excel的表格第一行的标题

            const filterVal = this.exportField(this.checkedroleAccount);
            // 上面的staffNum、accountId、staffName是tableData里对象的属性
            const list = this.staffDtoList;  //把data里的tableData存到list
            for (let i = 0; i < list.length; i++) {
              if(list[i].isDimission === 0){
                list[i].isDimission='在职'
              }else if(list[i].isDimission === 1){
                list[i].isDimission='离职'
              }
              if(list[i].staffSex === 1){
                list[i].staffSex='男'
              }else if(list[i].staffSex === 2){
                list[i].staffSex='女'
              }
            }
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '员工管理列表excel');
            this.$message({
              showClose: true,
              message: '文件导出成功',
              type: 'success'
            });
            this.dialogVisible=false;
            this.checkedroleAccount=[];
            this.filterVal=[];
          })
        }
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      handleCheckAllChange(val) {
        this.checkedroleAccount = val ? roleAccountOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckedEmployeesChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.roleAccount.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.roleAccount.length;
      },
      exportField(val) {
        for (let i = 0; i < val.length; i++) {
          if (this.checkedroleAccount[i] === '员工编号') {
            this.filterVal.push('staffNum')
          } else if (this.checkedemployees[i] === '登录账号') {
            this.filterVal.push('accountId')
          } else if (this.checkedemployees[i] === '员工姓名') {
            this.filterVal.push('staffName')
          } else if (this.checkedemployees[i] === '性别') {
            this.filterVal.push('staffSex')
          } else if (this.checkedemployees[i] === '员工手机') {
            this.filterVal.push('staffTelephone')
          } else if (this.checkedemployees[i] === '员工邮箱') {
            this.filterVal.push('staffEmail')
          } else if (this.checkedemployees[i] === '所属部门') {
            this.filterVal.push('departmentId')
          } else if (this.checkedemployees[i] === '上级部门') {
            this.filterVal.push('upperDepartmentNo')
          } else if (this.checkedemployees[i] === '是否离职') {
            this.filterVal.push('isDimission')
          }
        }
        return this.filterVal;
      },
      selectionActive(){
        this.disabled=false
      }
    }
  }
</script>

<style scoped>


</style>

