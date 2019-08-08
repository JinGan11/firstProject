<template>
  <home>
    <div>
      <div>
        <div style="width: 50%; float:left">
          <h2 style="color: #409EFF">|账户信息1</h2>
        </div>
        <div style="width: 50%; float: right; margin-top: 20px">
          <el-button type="primary" @click="save" style="width:70px">保存</el-button>
          <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
        </div>
      </div>
      <hr style="width: 65%; float: left; border:1px solid #409EFF; margin-top: -5px; margin-bottom: 15px"></hr>
      <div style="width:85%; margin-left: 70px; float: left">
        <el-form  :model="modifyForm" status-icon :rules="rules" ref="ruleForm" size="medium" label-width="100px"
                  class="demo-ruleForm">
          <el-row>
            <el-col :span="8">
              <el-form-item label="登录账户">
                <el-input  v-model="modifyForm.accountNum" disabled="true" autocomplete="off" ></el-input>
              </el-form-item>
              <div style="position: absolute; width: 0px">
                <el-form-item label="">
                  <el-input  autocomplete="off"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div style="position: absolute; width: 0px">
                <el-form-item label="">
                  <el-input  type="password"  autocomplete="off" ></el-input>
                </el-form-item>
              </div>
              <el-form-item label="密码" >
                <el-input  type="password" v-model="modifyForm.password" disabled="true" ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="关联员工编号">
                <div style="float: left; width:100px" >
                  <el-input style="width: 180px" v-model="modifyForm.staffNum" disabled="true"></el-input>
                </div>
                <div style="float: right; width:100px"><el-button @click="changeDialogVisible">选择</el-button></div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="员工姓名">
                <el-input v-model="modifyForm.staffName" disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="数据权限类型">
                <el-select style="width:180px;" v-model="modifyForm.permissions" clearable placeholder="请选择" @change="pressionChange">
                  <el-option
                    v-for="item in permissionsList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="密保邮箱">
                <el-input style="width:200px;" v-model="modifyForm.secretEmail" clearable :disabled="emailDisabled"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row v-if="departmentVisible">
            <el-col :span="6">
              <el-form-item label="手动选择部门">
                <el-tree
                  ref="tree"
                  :props="defaultProps"
                  node-key="id"
                  :load="loadNode"
                  lazy="true"
                  show-checkbox
                  check-strictly
                  :render-content="renderContent"
                  @check-change="handleClick">
                </el-tree>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div>
        <div style="width: 50%; float:left">
          <h2 style="color: #409EFF">|其他信息</h2>
        </div>
      </div>
      <hr style="width: 65%; float: left; border:1px solid #409EFF; margin-top: -5px; margin-bottom: 15px"></hr>
      <div style="width:85%; margin-left: 70px; float: left">
        <el-form :model="modifyForm" label-width="80px">
          <el-low>
            <el-col :span="6">
              <el-form-item label="新建人">
                <el-input style="width:200px;" v-model="modifyForm.creatEmpName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="新建时间">
                <el-input style="width:200px;" v-model="modifyForm.createTime"></el-input>
              </el-form-item>
            </el-col>
          </el-low>

          <el-low>
            <el-col :span="6">
              <el-form-item label="修改人">
                <el-input style="width:200px;" v-model="modifyForm.modifyEmpName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="修改时间">
                <el-input style="width:200px;" v-model="modifyForm.modifyTime"></el-input>
              </el-form-item>
            </el-col>
          </el-low>

          <el-row>
            <el-col :span="6">
              <el-form-item label="备注">
                <el-input style="width:200px;" v-model="modifyForm.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
    <el-dialog fullscreen :visible.sync="dialogEmployee" :close-on-click-modal="false" width="700px">
      <employee-list :relAccount="relAccount" ></employee-list>
    </el-dialog>
  </home>
</template>

<script>
  import employeeList from '../employeeManagement/EmployeeList'
  import commonUtils from '../../common/commonUtils'
  export default {
    data() {

      return {
        defaultProps: {
          label: 'departmentName',
          children: 'children',
          id: 'id',
          status: 'status'
        },
        dialogEmployee: false,
        departmentVisible :false,
        relAccount: 1,
        permissionsList:[],
        modifyForm: {
          staffId: '',
          accountNum: '',
          password: '******',
          staffNum: '',
          staffName: '',
          permissions:'',
          secretEmail: '',
          department: '',
          creatEmpName: '',
          createTime: '',
          modifyTime: '',
          modifyEmpName: '',
          accountState: '',
          remark:'',
        },
        emailDisabled: true,
        rules: {
        },
      }
    },
    components: {employeeList},
    provide(){
      return{
        changeDialogVisible:this.changeDialogVisible,
        chooseStaff:this.chooseStaff
      }
    },
    created() {
      const self = this;
      self.fetchData();
    },
    methods: {
      loadNode(node,resolve){
        var self = this;
        self.$http.get('department/buildTree.do_', {
          params: null
        }).then((result) => {
          resolve([result.departmentDto]);
        }).catch(function (error) {

        });
      },
      handleClick(data, sel) {
        if (sel) {
          traverseTree(data).forEach((item) => {
            this.$refs.tree.setChecked(item, true)
          })
        } else {
          traverseTree(data).forEach((item) => {
            this.$refs.tree.setChecked(item, false)
          })
        }
        function traverseTree(node) {
          var child = node.children,
            arr = [];

          arr.push(node.id);
          if (child) {
            child.forEach(function (node) {
              arr = arr.concat(traverseTree(node));
            });
          }
          return arr;
        }
      },
      fetchData(){
        const self = this;
        var id = localStorage.getItem("accountId");
        var param = {
          id: id
        }
        self.$http.get('account/selectAccountById.do_',{
          params: param
        }).then((result) => {
          self.modifyForm.accountNum = result.account.accountName;
          self.modifyForm.staffNum = result.account.staffNum;
          self.modifyForm.staffName = result.account.staffName;
          self.modifyForm.permissions = result.account.premissions;
          self.modifyForm.secretEmail = result.account.secretEmail;
          self.modifyForm.remark = result.account.remark;
          self.modifyForm.creatEmpName = result.account.creatEmpName;
          self.modifyForm.createTime = result.account.createTime;
          self.modifyForm.modifyEmpName = result.account.modifyEmpName;
          self.modifyForm.modifyTime = result.account.modifyTime;
          self.modifyForm.accountState = result.account.accountState;
        }).catch(function (error) {
          commonUtils.Log("account/createAccount.do_:"+error);
          self.$message.error("获取数据错误")
        });
        self.$http.get('account/enums.do_').then((result) => {
          self.permissionsList = result.permissionList;
        }).catch(function (error) {
          commonUtils.Log("account/premission.do_:"+error);
          self.$message.error("获取数据错误")
        });
      },
      save() {//保存新建账户信息
        const self = this;
        var tree = '';
        if(self.modifyForm.permissions == 5 && this.$refs.tree.getCheckedNodes().length == 0){
          self.$message.info("请选择部门");
        }else {
          if(self.modifyForm.permissions == 5){
            for(var i = 0; i < this.$refs.tree.getCheckedNodes().length; i++){
              tree += this.$refs.tree.getCheckedNodes()[i].id+' ';
            }
          }
          var param = {
            accountNum: self.modifyForm.accountNum,
            password: self.modifyForm.password,
            staffNum: self.modifyForm.staffNum,
            permissions: self.modifyForm.permissions,
            staffId: self.modifyForm.staffId,
            secretEmail: '',
            remark: self.modifyForm.remark,
            tree: tree
          };
          if (!self.emailDisabled) {
            param.secretEmail = self.modifyForm.secretEmail;
          }
          self.$http.get('account/createAccount.do_', {
            params: param
          }).then((result) => {
            self.$message.info("aaa");
          }).catch(function (error) {
            commonUtils.Log("account/createAccount.do_:" + error);
            self.$message.error("插入数据错误")
          });
        }
      },
      cancel(){//关闭新建账户页面，返回账户管理列表页面
        const self = this;
        this.$refs.tree.getCheckedNodes();
        self.$router.replace('/AccountManagement');
      },
      changeDialogVisible() {//选择员工界面的开关
        const self = this;
        self.dialogEmployee = !this.dialogEmployee;
      },
      chooseStaff(staffData){//关联员工
        const self = this;
        self.modifyForm.staffNum = staffData.staffNum;
        self.modifyForm.staffName = staffData.staffName;
        self.modifyForm.staffId = staffData.id;
        if(staffData.staffEmail==''){
          this.modifyForm.secretEmail ='';
          self.emailDisabled = false;
        }else{
          this.modifyForm.secretEmail = staffData.staffEmail;
        }
      },
      pressionChange(){//当数据权限为手动选择是，选择部门框可见
        const self = this;
        var a = self.modifyForm.permissions;
        self.departmentVisible = (self.modifyForm.permissions==5)?true:false;
      }
    },
  }
</script>

