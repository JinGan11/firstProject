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
      <el-form  :model="newForm" status-icon :rules="rules" ref="ruleForm" size="medium" label-width="120px"
                 class="demo-ruleForm">
        <el-row>
          <el-col :span="9">
            <el-form-item label="登录账户" prop="accountName">
              <el-input  style="width:300px " v-model="newForm.accountName" autocomplete="off" clearable></el-input>
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
            <el-form-item label="密码" prop="password">
              <el-input style="width:300px " type="password" v-model="newForm.password" autocomplete="off" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="7">
            <el-form-item label="关联员工编号">
              <el-input style="width: 180px" v-model="newForm.staffNum" disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-button type="text" @click="changeDialogVisible">选择</el-button>
            <el-button type="text" @click="clearStaffInf">清空</el-button>
          </el-col>
          <el-col :span="8">
            <el-form-item label="员工姓名">
              <el-input style="width: 300px" v-model="newForm.staffName" disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="9">
            <el-form-item label="数据权限类型" prop="permissions">
              <el-select style="width:300px;" v-model="newForm.permissions" clearable placeholder="请选择" @change="pressionChange">
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
              <el-input style="width:300px;" v-model="newForm.secretEmail" clearable :disabled="emailDisabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="departmentVisible">
          <el-row v-if="departmentVisible">
            <el-col :span="6">
              <el-form-item label="手动选择部门" prop="permissions">
                <el-tree
                  style="width:300px;"
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
      <el-form :model="newForm" label-width="120px">
        <el-row>
          <el-col :span="9">
            <el-form-item label="新建人">
              <el-input style="width:300px;" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="新建时间">
              <el-input style="width:300px;" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="9">
            <el-form-item label="修改人">
              <el-input style="width:300px;" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="修改时间">
              <el-input style="width:300px;" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="账号状态">
              <el-select style="width:700px;" :disabled="true" clearable placeholder="正常" ></el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="备注">
              <el-input style="width: 700px" type="textarea" :rows="2" v-model="newForm.remark"></el-input>
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
        newForm: {
          staffId: '',
          accountName: '',
          password: '',
          staffNum: '',
          staffName: '',
          permissions:'',
          secretEmail: '',
          department: '',
          remark:'',
          tree: '',
        },
        emailDisabled: false,
        rules: {
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' },
            {pattern:/((^(?=.*[a-z])(?=.*[A-Z])(?=.*\W)[\da-zA-Z\W]+$)|(^(?=.*\d)(?=.*[A-Z])(?=.*\W)[\da-zA-Z\W]+$)|(^(?=.*\d)(?=.*[a-z])(?=.*\W)[\da-zA-Z\W]+$)|(^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[\da-zA-Z\W]+$))/,
              message:'密码不合法，密码必须包含大小写字母，数字和特殊字符如(@#$%),且至少包含其中三种', trigger: 'blur'}
          ],
          accountName: [
            { required: true, message: '请输入登入账号', trigger: 'blur' },
            { pattern:/^(.*[\da-zA-Z@._]+$)/,message: '支持\'@._\字母和数字，请勿输入其他字符', trigger: 'blur' },
            { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }

          ],
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
      self.$http.get('account/enums.do_').then((result) => {
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
        self.$refs["ruleForm"].validate(function(valid) {
          if (valid) {
            if (self.newForm.permissions == 5 && self.$refs.tree.getCheckedNodes().length == 0) {
              self.$message.info("请选择部门");
            } else {
              if (self.newForm.permissions == 5) {
                for (var i = 0; i < self.$refs.tree.getCheckedNodes().length; i++) {
                  self.newForm.tree += self.$refs.tree.getCheckedNodes()[i].id + ' ';
                }
              }
              self.$http.post('account/createAccount.do_', self.newForm).then((result) => {
                self.$message.info("新建成功");
                self.$router.replace("/accountManagement");
              }).catch(function (error) {
                commonUtils.Log("account/createAccount.do_:" + error);
                self.$message.error("插入数据错误")
              });
            }
          }else{
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
        self.dialogEmployee = !self.dialogEmployee;
      },
      clearStaffInf(){//清除选择关联的员工
        const self = this;
        self.newForm.staffName = '';
        self.newForm.staffNum = '';
        self.newForm.secretEmail = '';
        self.emailDisabled = false;
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
          self.emailDisabled = true;
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

