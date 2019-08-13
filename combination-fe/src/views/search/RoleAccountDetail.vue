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
                  v-for="item in form.businessLineOptions"
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
                  v-for="item in form.roleStatusOptions"
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
                  v-for="item in form.accountStateOptions"
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
                <el-button type="primary" @click="exportVisible=true"style="width:100px">导出</el-button>
              </div>
              <div v-else>
                <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="id" label="角色ID" width="120"></el-table-column>
      <el-table-column prop="roleName" label="角色名称" width="150"></el-table-column>
      <el-table-column prop="businessLine" label="支持业务线" width="150">
        <template slot-scope="scope">
          {{scope.row.businessLine=="1"?'买买车'
          :(scope.row.accountState=="2"?'闪贷':(scope.row.accountState=="3"?'租车'
          :(scope.row.accountState=="4"?'专车':'保险')))}}
        </template>
      </el-table-column>
      <el-table-column prop="accountName" label="登陆账号" width="150"></el-table-column>
      <el-table-column prop="staffNum" label="员工编号" width="150"></el-table-column>
      <el-table-column prop="staffName" label="员工姓名" width="150"></el-table-column>
      <el-table-column prop="departmentName" label="所属部门" width="150"></el-table-column>
      <el-table-column prop="roleStatus" label="角色状态" width="150">
        <template slot-scope="scope">
          {{scope.row.roleStatus===1?'有效':'无效'}}
        </template>
      </el-table-column>
      <el-table-column prop="accountState" label="账号状态" width="150">
        <template slot-scope="scope">
          {{scope.row.accountState=="1"?'正常':(scope.row.accountState=="2"?'冻结':'无效')}}
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
    <!--导出弹窗-->
    </el-dialog>
    <el-dialog :title='excelTitle' :visible.sync="exportVisible" :close-on-click-modal="false" width="600px">
      <template>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="checkedRoleAccountProps" @change="handleCheckedRoleAccountPropsChange">
          <el-checkbox v-for="props in roleAccountProps" :label="props" :key="props">{{props}}</el-checkbox>
        </el-checkbox-group>
      </template>
      <template slot="footer">
        <el-button type="primary" @click="exportVisible">确定导出</el-button>
        <el-button @click="cancel">取 消</el-button>
      </template>
    </el-dialog>
  </home>

</template>

<script>
  import commonUtils from '../../common/commonUtils'

  const roleAccountPropsEnums = ['角色ID', '角色名称', '支持业务线', '登录账号', '员工编号', '员工姓名', '所属部门', '角色状态', '账号状态'];
  export default {
    data() {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        excelForm: {
          accountId:'',
          roleName:'',
          businessLine:'',
          accountName:'',
          staffNum: '',
          staffName: '',
          departmentName: '',
          roleStatus:'',
          accountState:'',
          roleAccountList:[],
        },
        form: {
          roleName:'',
          businessLine:'',
          accountName:'',
          staffNum: '',
          staffName: '',
          roleStatus:'',
          accountState: '',
          departmentName: '',
          businessLineOptions: [{
            value: '',
            label: '全部'
          }, {
            value: '1',
            label: '买买车'
          }, {
            value: '2',
            label: '闪贷'
          }, {
            value: '3',
            label: '租车'
          }, {
            value: '4',
            label: '专车'
          }, {
            value: '5',
            label: '保险'
          }],
          accountStateOptions: [{
            value: '',
            label: '全部'
          }, {
            value: '1',
            label: '正常'
          }, {
            value: '2',
            label: '冻结'
          }, {
            value: '3',
            label: '无效'
          }],
          roleStatusOptions: [{
            value: '',
            label: '全部'
          }, {
            value: '1',
            label: '有效'
          }, {
            value: '0',
            label: '无效'
          }],

          //导出文件
          exportVisible:false,
          isIndeterminate: true,
          checkAll:false,
          excelTitle: '请选择需要导出的字段',
          checkedRoleAccountProps:[],
          roleAccountProps: roleAccountPropsEnums,
          filterVal: [],
        },

        tableData: [],
        selection: '',
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
      this.fetchData();
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
      fetchData() { //根据查询条件获取数据
        var self = this;
        var param = {
          roleName: self.form.roleName,
          businessLine:self.form.businessLine,
          accountName:self.form.accountName,
          staffNum: self.form.staffNum,
          staffName:self.form.staffName,
          roleStatus:self.form.roleStatus,
          accountState:self.form.accountState,
          departmentName: self.form.departmentName,
        };
        self.$http.get('roleAccount/getRoleAccountList.do',{params: param}).then((result) => {
          //对取回来的数据进行处理
          console.log(result.page);
          self.tableData = result.page.list;
          self.roleAccountList = result.roleAccountList;
          self.total = result.page.totalCount;
          self.pageSize=result.page.pageSize;
          self.currentPage=result.page.currPage;
        }).catch(function (error) {
          commonUtils.Log("roleAccount/getRoleAccountList.do:" + error);
          self.$message.error("获取数据错误");
        });
      },

      exportExcel() {
        if(this.checkedRoleAccountProps.length ===0){
          this.$message({
            showClose: false,
            message: '请选择需要导出的字段',
            type: 'error'
          });
        }else{
          require.ensure([], () => {
            const {export_json_to_excel} = require('../../excel/Export2Excel');
            const tHeader = this.checkedRoleAccountProps;
            // 上面设置Excel的表格第一行的标题

            const filterVal = this.exportField(this.checkedRoleAccountProps);
            // 上面的staffNum、accountId、staffName是tableData里对象的属性
            const list = this.roleAccountList;  //把data里的tableData存到list
            for (let i = 0; i < list.length; i++) {
              if(list[i].roleStatus === 0){
                list[i].roleStatus='无效'
              }else if(list[i].roleStatus === 1){
                list[i].roleStatus ='有效'
              }
              if(list[i].accountState === 1){
                list[i].accountState ='正常'
              }else if(list[i].accountState === 2){
                list[i].accountState ='冻结'
              }else if(list[i].accountState === 3){
                list[i].accountState ='无效'
              }
              if(list[i].accountState === 1){
                list[i].accountState ='正常'
              }else if(list[i].accountState === 2){
                list[i].accountState ='冻结'
              }else if(list[i].accountState === 3){
                list[i].accountState='无效'
              }
              if(list[i].businessLine === 1){
                list[i].businessLine ='买买车'
              }else if(list[i].businessLine === 2){
                list[i].businessLine='闪贷'
              }else if(list[i].businessLine === 3){
                list[i].businessLine ='租车'
              }
              else if(list[i].businessLine === 4){
                list[i].businessLine ='专车'
              }else if(list[i].businessLine === 5){
                list[i].businessLine ='保险'
              }
            }
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '角色账号明细列表excel');
            this.$message({
              showClose: true,
              message: '文件导出成功',
              type: 'success'
            });
            this.exportVisible=false;
            this.checkedRoleAccountProps=[];
            this.filterVal=[];
          })
        }
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      cancel() {
        this.exportVisible = false;
      },
      handleCheckAllChange(val) {
        this.checkedRoleAccountProps = val ? roleAccounPropsEnums : [];
        this.isIndeterminate = false;
      },
      handleCheckedRoleAccountPropsChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.roleAccountProps.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.roleAccountProps.length;
      },
      exportField(val) {
        for (let i = 0; i < val.length; i++) {
          if (this.checkedRoleAccountProps[i] === '角色ID') {
            this.filterVal.push('roleID')
          } else if (this.checkedRoleAccountProps[i] === '角色名称') {
            this.filterVal.push('roleName')
          } else if (this.checkedRoleAccountProps[i] === '支持业务线') {
            this.filterVal.push('businessLine')
          } else if (this.checkedRoleAccountProps[i] === '登录账号') {
            this.filterVal.push('accountName')
          } else if (this.checkedRoleAccountProps[i] === '员工编号') {
            this.filterVal.push('staffNum')
          } else if (this.checkedRoleAccountProps[i] === '员工姓名') {
            this.filterVal.push('staffName')
          } else if (this.checkedRoleAccountProps[i] === '所属部门') {
            this.filterVal.push('departmentName')
          } else if (this.checkedRoleAccountProps[i] === '角色状态') {
            this.filterVal.push('roleStatus')
          } else if (this.checkedRoleAccountProps[i] === '账号状态') {
            this.filterVal.push('accountState')
          }
        }
        return this.filterVal;
      },
    }
  }
</script>

<style scoped>


</style>

