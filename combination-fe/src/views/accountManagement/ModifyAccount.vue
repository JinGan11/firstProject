<template>
  <home>
    <div>
      <div>
        <div style="width: 60%; float:left">
          <h2 style="color: #409EFF">|账户信息</h2>
        </div>
        <div style="width: 40%; float: right; margin-top: 20px">
          <el-button type="primary" @click="save" style="width:70px">保存</el-button>
          <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
        </div>
      </div>
      <hr style="width: 70%; float: left; border:1px solid #409EFF; margin-top: -5px; margin-bottom: 15px"></hr>
      <div style="width:85%; margin-left: 30px; float: left">
        <el-form  :model="modifyForm" status-icon :rules="rules" ref="modifyForm" size="medium" label-width="120px"
                  class="demo-ruleForm">
          <el-row>
            <el-col :span="9">
              <el-form-item label="登录账户">
                <el-input  style="width:250px " v-model="modifyForm.accountNum" :disabled="true" autocomplete="off" ></el-input>
              </el-form-item>
              <div style="position: absolute; width: 0px">
                <el-form-item label="">
                  <el-input  autocomplete="off"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="15">
              <div style="position: absolute; width: 0px">
                <el-form-item label="">
                  <el-input  type="password"  autocomplete="off" ></el-input>
                </el-form-item>
              </div>
              <el-form-item label="密码" >
                <el-input  style="width:250px " type="password" v-model="modifyForm.password" :disabled="true" ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="7">
              <el-form-item label="关联员工编号">
                <el-input style="width: 150px" v-model="modifyForm.staffNum" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="2">
              <el-button type="text" @click="changeDialogVisible">选择</el-button>
              <el-button type="text" @click="clearStaffInf">清空</el-button>
            </el-col>
            <el-col :span="8">
              <el-form-item label="员工姓名">
                <el-input style="width: 250px" v-model="modifyForm.staffName" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="数据权限类型" prop="permissions">
                <el-select style="width:250px;" v-model="modifyForm.permissions" clearable placeholder="请选择" @change="pressionChange">
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
              <el-form-item label="密保邮箱" prop="secretEmail">
                <el-input style="width:250px;" v-model="modifyForm.secretEmail" clearable :disabled="emailDisabled"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row v-if="departmentVisible">
            <el-col :span="9">
              <el-form-item label="手动选择部门" prop="permissions">
                <el-tree
                  style="width:250px;"
                  ref="tree"
                  :props="defaultProps"
                  node-key="id"
                  :load="loadNode"
                  lazy="true"
                  show-checkbox
                  check-strictly
                  :default-expanded-keys="modifyForm.trees"
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
      <hr style="width: 70%; float: left; border:1px solid #409EFF; margin-top: -5px; margin-bottom: 15px"></hr>
      <div style="width:85%; margin-left: 30px; float: left">
        <el-form :model="modifyForm" label-width="120px">
          <el-row>
            <el-col :span="9">
              <el-form-item label="新建人">
                <el-input style="width:250px;" :disabled="true" v-model="modifyForm.creatEmpName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="新建时间">
                <el-input style="width:250px;" :disabled="true" v-model="modifyForm.createTime"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="9">
              <el-form-item label="修改人">
                <el-input style="width:250px;" :disabled="true" v-model="modifyForm.modifyEmpName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="修改时间">
                <el-input style="width:250px;" :disabled="true" v-model="modifyForm.modifyTime"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="账号状态">
                <el-select style="width:250px;" :disabled="true" v-model="modifyForm.accountState" clearable placeholder="请选择" @change="pressionChange">
                  <el-option
                    v-for="item in statusList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="备注">
                <el-input style="width: 73%" type="textarea" :rows="2" v-model="modifyForm.remark"></el-input>
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
          isLeaf: 'nodeIsLeaf',
          id: 'id',
          no: 'departmentNo',
        },
        dialogEmployee: false,
        departmentVisible :false,
        relAccount: 1,
        permissionsList:[],
        statusList: [],
        modifyForm: {
          staffId: '',
          oldStaffId: '',
          accountId: '',
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
          tree:'',
          trees: [],
          remark:'',
        },
        emailDisabled: true,
        rules: {
          permissions:[
            { required: true, message: '请选择数据类型'}
          ],
          secretEmail:[
            { required: true, message: '请输入密保邮箱' },
            { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
          ],
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
      self.modifyForm.accountId = localStorage.getItem("accountId");
      self.fetchData();
      self.fetchEnums();
      self.fetchDeparentPower();
    },
    methods: {
      loadNode(node,resolve){//加载部门的树结构
        var self = this;
        self.$http.get('department/buildTree2.do_')
          .then((result) => {
          resolve([result.departmentDto]);
          self.$refs.tree.setCheckedKeys(self.modifyForm.trees);
          self.$refs.tree.setC
        }).catch(function (error) {

        });
      },
      handleClick(data, sel) {//部门树结构父节点关联子节点，子节点不关联父节点功能
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
      fetchDeparentPower(){
        const self = this;
        var param = {
          id: self.modifyForm.accountId
        }
        self.$http.get('account/selectDeparentPower.do_',{
          params: param
        }).then((result) => {
          self.modifyForm.trees = result.trees;
        }).catch(function (error) {
          commonUtils.Log("account/selectDeparentPowerById.do_:"+error);
          self.$message.error("获取数据错误")
        });
      },
      fetchData(){ //根据accountId查询界面要显示的数据
        const self = this;
        var param = {
          id: self.modifyForm.accountId
        }
        self.$http.get('account/selectAccountById.do_',{
          params: param
        }).then((result) => {
          self.modifyForm.staffId = result.account.staffId;
          self.modifyForm.oldStaffId = self.modifyForm.staffId;
          self.modifyForm.accountNum = result.account.accountName;
          self.modifyForm.staffNum = result.account.staffNum;
          self.modifyForm.staffName = result.account.staffName;
          self.modifyForm.permissions = result.account.premissions;
          if ( self.modifyForm.permissions == 5 ){
            self.departmentVisible = true;
          }
          self.modifyForm.secretEmail = result.account.secretEmail;
          if(self.modifyForm.secretEmail == '' || self.modifyForm.secretEmail == null){
            self.emailDisabled = false;
          }
          self.modifyForm.remark = result.account.remark;
          self.modifyForm.creatEmpName = result.account.creatEmpName;
          if(result.account.createrName !=null ){
            self.modifyForm.creatEmpName =self.modifyForm.creatEmpName + '(' + result.account.createrName + ')';
          }
          self.modifyForm.createTime = result.account.createTime;
          self.modifyForm.modifyEmpName = result.account.modifyEmpName;
          if(result.account.modifier != null ){
            self.modifyForm.modifyEmpName =self.modifyForm.modifyEmpName + '(' + result.account.modifier + ')';
          }
          self.modifyForm.modifyTime = result.account.modifyTime;
          self.modifyForm.accountState = result.account.accountState;
        }).catch(function (error) {
          commonUtils.Log("account/selectAccountById.do_:"+error);
          self.$message.error("获取数据错误")
        });
      },
      fetchEnums(){//权限和账号状态枚举
        const self = this;
        self.$http.get('account/enums.do_').then((result) => {
          self.permissionsList = result.permissionList;
          self.statusList = result.accountStatusList;
        }).catch(function (error) {
          commonUtils.Log("account/premission.do_:"+error);
          self.$message.error("获取数据错误")
        });
      },
      save() {//保存修改账户信息
        const self = this;
        self.$refs["modifyForm"].validate(function(valid) {
          if (valid) {
            if (self.modifyForm.permissions == 5 && self.$refs.tree.getCheckedNodes().length == 0) {
              self.$message.info("请选择部门");
            } else {
              if (self.modifyForm.permissions == 5) {
                for (var i = 0; i < self.$refs.tree.getCheckedNodes().length; i++) {
                  self.modifyForm.tree += self.$refs.tree.getCheckedNodes()[i].id + ' ';
                }
              }
              self.$http.post('account/modifyAccount.do_', self.modifyForm)
                .then((result) => {
                  self.$message.info("修改成功");
                  self.$router.replace("/accountManagement");
                }).catch(function (error) {
                commonUtils.Log("account/createAccount.do_:" + error);
                self.$message.error("插入数据错误")
              });
            }
          }else {
            console.log('error submit!!');
            return false;
          }
        })
      },
      cancel(){//关闭新建账户页面，返回账户管理列表页面
        const self = this;
        self.$router.replace('/AccountManagement');
      },
      changeDialogVisible() {//选择员工界面的开关
        const self = this;
        self.dialogEmployee = !this.dialogEmployee;
      },
      clearStaffInf(){//清除选择关联的员工
        const self = this;
        self.modifyForm.staffId = '';
        self.modifyForm.staffName = '';
        self.modifyForm.staffNum = '';
        self.modifyForm.secretEmail = '';
        if(self.modifyForm.permissions == 2 || self.modifyForm.permissions == 3){
          self.modifyForm.permissions = '';
        }
        self.emailDisabled = false;
      },
      chooseStaff(staffData){//关联员工
        const self = this;
        self.modifyForm.staffNum = staffData.staffNum;
        self.modifyForm.staffName = staffData.staffName;
        self.modifyForm.staffId = staffData.id;
        if(staffData.staffEmail == '' || staffData.staffEmail == null){
          this.modifyForm.secretEmail ='';
          self.emailDisabled = false;
        }else{
          this.modifyForm.secretEmail = staffData.staffEmail;
          self.emailDisabled = true;
        }
      },
      pressionChange(){//当数据权限为手动选择是，选择部门框可见
        const self = this;
        if((self.modifyForm.staffNum == ''||self.modifyForm.staffNum == null) && self.modifyForm.permissions >= 2
          && self.modifyForm.permissions <= 3){
          self.modifyForm.permissions = '';
          self.$message.error('未选择员工，不可选该权限');
        }
        self.departmentVisible = (self.modifyForm.permissions==5)?true:false;
      }
    },
  }
</script>

