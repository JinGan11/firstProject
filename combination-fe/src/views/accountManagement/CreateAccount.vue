<template>
  <home>
  <div>
    <div>
      <div style="width: 50%; float:left">
        <h2 style="color: #409EFF">|账户信息</h2>
      </div>
      <div style="width: 50%; float: right; margin-top: 20px">
        <el-button type="primary" @click="save" style="width:70px">保存</el-button>
        <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
      </div>
    </div>
    <hr style="width: 65%; float: left; border:1px solid #409EFF; margin-top: -5px; margin-bottom: 15px"></hr>
    <div style="width:85%; margin-left: 70px; float: left">
      <el-form  :model="newForm" status-icon :rules="rules" ref="ruleForm" size="medium" label-width="100px"
                 class="demo-ruleForm">
        <el-row>
          <el-col :span="8">
            <el-form-item label="登录账户">
              <el-input  v-model="newForm.accountNum" autocomplete="off" clearable></el-input>
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
            <el-form-item label="密码" prop="password">
              <el-input  type="password" v-model="newForm.password" autocomplete="off" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="关联员工编号">
              <div style="float: left; width:100px" >
                <el-input style="width: 180px" v-model="newForm.staffNum" disabled="true"></el-input>
              </div>
              <div style="float: right; width:100px"><el-button @click="changeDialogVisible">选择</el-button></div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="员工姓名">
              <el-input v-model="newForm.staffName" disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="数据权限类型">
              <el-select style="width:180px;" v-model="newForm.permissions" clearable placeholder="请选择" @change="pressionChange">
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
              <el-input style="width:200px;" v-model="newForm.secretEmail" clearable :disabled="emailDisabled"></el-input>
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
      <el-form :model="newForm" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="备注">
              <el-input style="width:200px;" v-model="newForm.remark"></el-input>
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
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else if (!(/((^(?=.*[a-z])(?=.*[A-Z])(?=.*\W)[\da-zA-Z\W]{8,16}$)|(^(?=.*\d)(?=.*[A-Z])(?=.*\W)[\da-zA-Z\W]{8,16}$)|(^(?=.*\d)(?=.*[a-z])(?=.*\W)[\da-zA-Z\W]{8,16}$)|(^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[\da-zA-Z\W]{8,16}$))/.test(value))) {
          callback(new Error('请输入8-16位字符，至少包含数字、大写字母、小写字母、特殊字符中的三种类型'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
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
        newForm: {
          staffId: '',
          accountNum: '',
          password: '',
          staffNum: '',
          staffName: '',
          permissions:'',
          secretEmail: '',
          department: '',
          remark:'',
        },
        emailDisabled: true,
        rules: {
          password: [
            {validator: validatePass, trigger: 'blur'}
          ]
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
      self.$http.get('account/premission.do_').then((result) => {
        self.permissionsList = result.permissionList;
      }).catch(function (error) {
        commonUtils.Log("account/premission.do_:"+error);
        self.$message.error("获取数据错误")
      });
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
      save() {//保存新建账户信息
        const self = this;
        var param={
          accountNum: self.newForm.accountNum,
          password: self.newForm.password,
          staffNum: self.newForm.staffNum,
          permissions: self.newForm.permissions,
          staffId: self.newForm.staffId,
          secretEmail: '',
          remark: self.newForm.remark,
        };
        if(!self.emailDisabled){
          param.secretEmail = self.newForm.secretEmail;
        }
        self.$http.get('account/createAccount.do_', {
          params: param
        }).then((result) => {
          self.$message.info("aaa");
        }).catch(function (error) {
          commonUtils.Log("account/createAccount.do_:"+error);
          self.$message.error("获取数据错误")
        });

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
        self.newForm.staffNum = staffData.staffNum;
        self.newForm.staffName = staffData.staffName;
        self.newForm.staffId = staffData.id;
        if(staffData.staffEmail==''){
          this.newForm.secretEmail ='';
          self.emailDisabled = false;
        }else{
          this.newForm.secretEmail = staffData.staffEmail;
        }
      },
      pressionChange(){//当数据权限为手动选择是，选择部门框可见
        const self = this;
        var a = self.newForm.permissions;
        self.departmentVisible = (self.newForm.permissions==5)?true:false;
      }
    },
  }
</script>

<style scoped>

</style>
