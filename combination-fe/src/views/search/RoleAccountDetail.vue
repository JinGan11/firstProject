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
            <el-form-item label="支持业务线">
              <el-select v-model="form.businessLine" clearable style="width:200px;" placeholder="请选择">
                <el-option
                  v-for="item in businessLineOptions"
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
            <el-form-item label="角色状态">
              <el-select v-model="form.roleStatus" clearable style="width:200px;" placeholder="请选择">
                <el-option
                  v-for="item in roleStatusOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="账号状态">
              <el-select v-model="form.accountState" clearable style="width:200px;" placeholder="请选择">
                <el-option
                  v-for="item in accountStateOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="所属部门">
              <el-input :disabled="true" style="width:200px;" v-model="form.departmentName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-button type="text" @click="chooseDepartmentFlag=true">选择</el-button>
            <el-button type="text" @click="chooseDepartmentFlag=false">取消</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <div>
                <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
                <el-button type="primary" @click="exportVisible=true" style="width:100px">导出</el-button>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <!--      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>-->
      <el-table-column prop="id" label="角色ID" width="120"></el-table-column>
      <el-table-column prop="roleName" label="角色名称" width="150"></el-table-column>
      <el-table-column prop="businessLine" label="支持业务线" width="150">
        <template slot-scope="scope">
          {{scope.row.businessLine=="1"?'买买车'
          :(scope.row.accountState=="2"?'闪贷':(scope.row.accountState=="3"?'租车'
          :(scope.row.accountState=="4"?'专车':'保险')))}}
        </template>
      </el-table-column>
      <el-table-column prop="accountName" label="登陆账号" width="150">
        <template slot-scope="scope">
          <el-button type="text" @click="accountBtn(scope.row.accountId)">{{scope.row.accountName}}</el-button>
        </template>
      </el-table-column>
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
    <el-pagination @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   :current-page="currentPage"
                   :page-sizes="[10, 50, 100]"
                   :page-size="pageSize"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total">
    </el-pagination>
    <el-dialog title='请选择需要导出的字段' :visible.sync="exportVisible" :close-on-click-modal="false" width="600px">
      <template>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="checkedRoleAccount" @change="handleCheckedRoleAccountChange">
          <el-checkbox v-for="(roleAccount,index) in roleAccountOptions" :label="roleAccount" :key="index">
            {{roleAccount}}
          </el-checkbox>
        </el-checkbox-group>
      </template>
      <template slot="footer">
        <el-button type="primary" @click="exportExcel">确定导出</el-button>
        <el-button @click="exportVisible=false">取 消</el-button>
      </template>
    </el-dialog>
    <!--选择部门弹窗-->
    <el-dialog title="选择部门" :visible.sync="chooseDepartmentFlag" width="300px">
      <el-tree
        ref="tree"
        :props="defaultProps1"
        node-key="id"
        :load="loadNodeDepartment"
        lazy="true"
        check-strictly
        show-checkbox
        default-expanded-keys="[1]"
        @check-change="handleClick1">
      </el-tree>
    </el-dialog>
    <el-dialog :visible.sync="accounFlag" :close-on-click-modal="false" width="800px">
      <account-view ref="c1" :accountId="accountId" ></account-view>
    </el-dialog>
  </home>
</template>

<script>
  import commonUtils from '../../common/commonUtils'
  import accountView from '../accountManagement/AccountView'
  export default {
    data() {
      return {
        defaultProps1: {
          label: 'departmentName',
          children: 'children',
          isLeaf: 'nodeIsLeaf',
          id: 'id',
          no: 'departmentNo',
        },
        total: 0,
        currentPage: 1,
        pageSize: 10,
        excelForm: {
          roleId: '',
          accountId: '',
          roleName: '',
          businessLine: '',
          accountName: '',
          staffNum: '',
          staffName: '',
          departmentId: '',
          departmentName: '',
          roleStatus: '',
          accountState: '',
        },
        form: {
          roleName: '',
          businessLine: '',
          accountName: '',
          staffNum: '',
          staffName: '',
          roleStatus: '',
          accountState: '',
          // department:'',
          // departmentId:'',
          departmentName: ''
        },
        //导出文件
        exportVisible: false,
        checkAll: false,
        // checkedRegionProps:[],
        filterVal: [],

        businessLineOptions: [
          {
            value: '',
            label: '全部'
          },
          {
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
        accountStateOptions: [
          {
            value: '',
            label: '全部'
          },
          {
            value: '1',
            label: '正常'
          }, {
            value: '2',
            label: '冻结'
          }, {
            value: '3',
            label: '无效'
          }],
        roleStatusOptions: [
          {
            value: '',
            label: '全部'
          }, {
            value: '1',
            label: '有效'
          }, {
            value: '0',
            label: '无效'
          }],
        roleAccountOptions: ['角色ID', '角色名称', '支持业务线', '登录账号', '员工编号', '员工姓名', '所属部门', '角色状态', '账号状态'],
        chooseDepartmentFlag: false,
        accountStatusEnum:[],
        businessLineEnum:[],
        roleStatusEnum:[],

        tableData: [],
        roleAccountList:[],//符合查询条件的所有数据
        selection: '',
        checkedRoleAccount:[], //导出选择的字段
        isIndeterminate:true,//修改全选复选框样式
        accountId: '',
        accounFlag: false
      }
    },
    // activated() {
    //   commonUtils.Log("页面激活");
    // },
    // mounted() {
    //   commonUtils.Log("页面进来");
    // },
    components: {accountView},
    methods: {
      fetchData() { //根据查询条件获取数据
        var self = this;
        var param = {
          roleName: self.form.roleName,
          businessLine: self.form.businessLine,
          accountName: self.form.accountName,
          staffNum: self.form.staffNum,
          staffName: self.form.staffName,
          roleStatus: self.form.roleStatus,
          accountState: self.form.accountState,
          departmentName: self.form.departmentName,
          currentPage: self.currentPage,
          pageSize: self.pageSize
        };
        self.$http.get('roleAccount/getRoleAccountList.do', {params: param}).then((result) => {
          //对取回来的数据进行处理
          console.log(result);
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          self.pageSize = result.page.pageSize;
          self.currentPage = result.page.currPage;
          self.roleAccountList=result.roleAccountList;
          self.accountStatusEnum=result.accountStatusEnum;
          self.businessLineEnum=result.businessLineEnum;
          self.roleStatusEnum=result.roleStatusEnum;
        }).catch(function (error) {
          commonUtils.Log("roleAccount/getRoleAccountList.do:" + error);
          self.$message.error("获取数据错误");
        });

      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.fetchData();
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.fetchData();
      },
      handleSelectionChange(val) {
        this.selection = val;
      },

      handleCheckAllChange(val) {//导出字段全选按钮
        console.log("check:"+val);
        this.checkedRoleAccount = val ? this.roleAccountOptions : [];
        this.isIndeterminate = false;
      },
      exportExcel() {
        if (this.checkedRoleAccount.length === 0) {
          this.$message({
            showClose: false,
            message: '请选择需要导出的字段',
            type: 'error'
          });
        } else {
          require.ensure([], () => {
            const {export_json_to_excel} = require('../../excel/Export2Excel');
            const tHeader = this.checkedRoleAccount;
            // 上面设置Excel的表格第一行的标题

            const filterVal = this.exportField(this.checkedRoleAccount);
            // 上面的staffNum、accountId、staffName是tableData里对象的属性
            const list = this.roleAccountList;  //把data里的roleAccountList存到list
            for (let i = 0; i < list.length; i++) {
              list[i].roleStatus = this.roleStatusEnum[list[i].roleStatus];
              list[i].businessLine=this.businessLineEnum[list[i].businessLine];
              list[i].accountState=this.accountStatusEnum[list[i].accountState];
            }
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '角色账号明细列表excel');
            this.$message({
              showClose: true,
              message: '文件导出成功',
              type: 'success'
            });
            this.exportVisible = false;
            this.checkedRoleAccount = [];
            this.filterVal = [];
          })
        }
      },
      exportField(val) {
        for (let i = 0; i < val.length; i++) {
          if (this.checkedRoleAccount[i] === '角色ID') {
            this.filterVal.push('id')
          } else if (this.checkedRoleAccount[i] === '角色名称') {
            this.filterVal.push('roleName')
          } else if (this.checkedRoleAccount[i] === '支持业务线') {
            this.filterVal.push('businessLine')
          } else if (this.checkedRoleAccount[i] === '登录账号') {
            this.filterVal.push('accountName')
          } else if (this.checkedRoleAccount[i] === '员工编号') {
            this.filterVal.push('staffNum')
          } else if (this.checkedRoleAccount[i] === '员工姓名') {
            this.filterVal.push('staffName')
          } else if (this.checkedRoleAccount[i] === '所属部门') {
            this.filterVal.push('departmentName')
          } else if (this.checkedRoleAccount[i] === '角色状态') {
            this.filterVal.push('roleStatus')
          } else if (this.checkedRoleAccount[i] === '账号状态') {
            this.filterVal.push('accountState')
          }
        }
        return this.filterVal;
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      handleCheckedRoleAccountChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.roleAccountOptions.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.roleAccountOptions.length;
      },


      closeChooseDepartment() {
        this.chooseDepartmentFlag = false;
      },
      loadNodeDepartment(node, resolve) {
        var self = this;
        self.$http.get('department/buildTree2.do_')
          .then((result) => {
            resolve([result.departmentDto]);
          }).catch(function (error) {

        });
      },
      handleClick1(data, checked, node) {
        console.log(data);
        // 手动设置单选
        if (checked === true) {
          // this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
          // this.form.departmentId = data.id;
          this.form.departmentName = data.departmentName;
        } else {
          // if (this.checkedId == data.id) {
          //   this.$refs.tree.setCheckedKeys([data.id]);
          //   this.form.departmentName = data.departmentName;
          //
          // }
        }
        this.chooseDepartmentFlag=false;
      },
      //账户信息明细
      accountBtn(val){
        localStorage.setItem("accountId",val);
        self.accountId = val;
        this.accounFlag = true;
        this.$refs.c1.fetchData(val);
      }
    }
  }
</script>

<style scoped>


</style>

