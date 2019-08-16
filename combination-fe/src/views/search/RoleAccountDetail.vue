<template>
  <home>
    <div style="width:100%;">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="角色名称" label-width="150px;">
              <el-input placeholder="角色名称" style="width:200px;" v-model="form.roleName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="支持业务线" label-width="150px;">
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
            <el-form-item label="登录账号" label-width="150px;">
              <el-input placeholder="登录账号" style="width:200px;" v-model="form.accountName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工编号" label-width="150px;">
              <el-input placeholder="员工编号" style="width:200px;" v-model="form.staffNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="员工姓名" label-width="150px;">
              <el-input placeholder="员工姓名" style="width:200px;" v-model="form.staffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="角色状态" label-width="150px;">
              <el-select v-model="form.roleStatus" clearable style="width:200px;margin-left: 15px;" placeholder="请选择">
                <el-option
                  v-for="item in roleStatusOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="账号状态" label-width="150px;">
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
          <el-col :span="6">
            <el-form-item label="所属部门" label-width="150px;">
              <el-input :disabled="true" style="width:200px;" v-model="form.departmentName"></el-input>
            <el-button type="text" @click="chooseDepartmentFlag=true">选择</el-button>
            <el-button type="text" @click="chooseDepartmentFlag=false">取消</el-button>
            </el-form-item>
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
      <el-table-column prop="roleId" label="角色ID" width="120">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="ChooseOnDetail(scope.row.roleId)">{{scope.row.roleId}}</el-button>
        </template>
      </el-table-column>
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
<!--角色详情页弹窗-->
    <el-dialog title="角色详情信息" :visible.sync="roleInfoDetailFlag" :close-on-click-modal="false" width="900px">
<!--      <el-form ref="form" :model="formDetail" label-width="80px">-->
        <div>
          <div style="margin-bottom: 10px">
            <p>角色信息</p>
          </div>
          <div style="width:85%; margin-left: 70px">
            <el-form ref="form" :model="formInfo" label-width="110px">
              <el-row>
                <el-col :span="10">
                  <el-form-item label="角色ID">
                    <el-input style="width:200px;" :disabled="true" v-model="formInfo.roleID"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="角色名称">
                    <el-input style="width:200px;" :disabled="true" v-model="formInfo.roleName"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="10">
                  <el-form-item label="审批人账号">
                    <el-input style="width:200px;" :disabled="true" v-model="formInfo.accountNum"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="审批人员工编号">
                    <el-input style="width:200px;" :disabled="true" v-model="formInfo.staffNum"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="10">
                  <el-form-item label="审批人姓名">
                    <el-input style="width:200px;" :disabled="true" v-model="formInfo.staffName"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="审批人所属部门">
                    <el-input style="width:200px;" :disabled="true" v-model="formInfo.departmentName"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="15">
                  <el-form-item label="支持业务线">
                    <!--<template v-for="item in chks">
                      <input type="checkbox" name="hobby" :value="item.id"
                           :checked="form.loopsss.indexOf(item.id) > -1"/>{{item.name}}
                    </template>
                    -->
                    <input type="checkbox" :disabled="true" v-model="formInfo.businessLine" value="买买车">买买车
                    <input type="checkbox" :disabled="true" v-model="formInfo.businessLine" value="租车">租车
                    <input type="checkbox" :disabled="true" v-model="formInfo.businessLine" value="闪贷">闪贷
                    <input type="checkbox" :disabled="true" v-model="formInfo.businessLine" value="专车">专车
                    <input type="checkbox" :disabled="true" v-model="formInfo.businessLine" value="保险">保险
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="10">
                  <el-form-item label="描述">
                    <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" :disabled="true" v-model="formInfo.description"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <div>
            <p>其它信息</p>
            <div>
              <div style="width:85%; margin-left: 70px">
                <el-form ref="otherForm" :model="otherForm" label-width="110px">
                  <el-row>
                    <el-col :span="10">
                      <el-form-item label="新建人">
                        <el-input style="width:200px;" :disabled="true" v-model="otherForm.createEmp"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="10">
                      <el-form-item label="新建时间">
                        <el-input style="width:200px;" :disabled="true" v-model="otherForm.createTime"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="10">
                      <el-form-item label="修改人">
                        <el-input style="width:200px;" :disabled="true" v-model="otherForm.modifyEmp"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="10">
                      <el-form-item label="修改时间">
                        <el-input style="width:200px;" :disabled="true" v-model="otherForm.modifyTime"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="1">
                      <el-form-item label="状态">
                        <el-select v-model="formInfo.roleStatus" :disabled="true" style="width:150px;">
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
                </el-form>
              </div>
            </div>
          </div>
        </div>
    </el-dialog>
<!--账号详情页弹窗-->
    <el-dialog :visible.sync="accountFlag" :close-on-click-modal="false" width="800px">
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

        //角色详情
        roleInfoDetailFlag:false,
        formInfo: {
          roleID: '',
          roleName: '',
          businessLine: [],
          roleStatus: '',
          accountNum: '',
          staffNum: '',
          staffName: '',
          departmentName: '',
          description: '',
        },
        otherForm:{
          createEmp:'',
          createEmpNum:'',
          createEmpName:'',
          createEmpTime:'',
          modifyEmp:'',
          modifyEmpNum:'',
          modifyEmpName:'',
          modifyEmpTime:'',
        },

        tableData: [],
        roleAccountList:[],//符合查询条件的所有数据
        selection: '',
        checkedRoleAccount:[], //导出选择的字段
        isIndeterminate:true,//修改全选复选框样式
        accountId: '',
        accountFlag: false,

      }
    },

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
            //获取当前时间
            var date = new Date();
            var seperator1 = "-";
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
              month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
              strDate = "0" + strDate;
            }
            var currentdate = year + seperator1 + month + seperator1 + strDate;
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '角色账号明细'+currentdate);
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
            this.filterVal.push('roleId')
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

      //角色信息明细
      ChooseOnDetail(roleId) {
        const self = this;
        self.roleInfoDetailFlag = true;
        var param = {
          roleID: roleId,
        };
        self.$http.get('roleManage/getOtherOneInf.do_', {
          params: param
        }).then((result) => {
          self.otherForm = result.page;
          self.otherForm.createEmp = `${self.otherForm.createEmpNum}(${self.otherForm.createEmpName})`;
          self.otherForm.modifyEmp = `${self.otherForm.modifyEmpNum}(${self.otherForm.modifyEmpName})`;
        }).catch(function (error) {
          commonUtils.Log("roleManage/getOtherOneInf.do_" + error);
          self.$message.error("获取数据错误");
        });
        self.$http.get('roleManage/getOneInf.do_', {
          params: param
        }).then((result) => {
          self.formInfo=result.page;
          self.RoleStatusEnum = result.RoleStatusEnum;
          self.formInfo.businessLine=self.formInfo.businessLine.split(',');
        }).catch(function (error) {
          commonUtils.Log("roleManage/getOneInf.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },

      //账户信息明细
      accountBtn(val){
        this.$router.push({path: '/ModifyRole', query: {roleID: val}});
        this.accounFlag = true;
        this.$refs.c1.fetchData(val);
      }
    }
  }
</script>

<style scoped>


</style>

