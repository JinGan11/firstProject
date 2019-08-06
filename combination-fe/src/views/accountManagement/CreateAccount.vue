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
<!--      <el-form ref="form" :model="form" label-width="80px">-->
      <el-form :model="form" status-icon :rules="rules" ref="ruleForm" size="medium" label-width="100px"
                 class="demo-ruleForm">
        <el-row>
          <el-col :span="8">
            <el-form-item label="登录账户">
              <el-input  v-model="newForm.accountNum" autocomplete="off"></el-input>
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
              <el-input  type="password"  autocomplete="off"></el-input>
            </el-form-item>
            </div>
            <el-form-item label="密码" prop="password">
              <el-input  type="password" v-model="newForm.password" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="关联员工编号">
              <div style="float: left; width:100px">
                <el-input style="width: 180px" v-model="newForm.staffNum"></el-input>
              </div>
              <div style="float: right; width:100px"><el-button @click="test">选择</el-button></div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="员工姓名">
              <el-input v-model="newForm.staffName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="数据权限类型">
              <el-input style="width:200px;" v-model="newForm.permissions"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="密保邮箱">
              <el-input style="width:200px;" v-model="newForm.secretEmail"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="手动选择部门">
              <el-input style="width:200px;" v-model="newForm.department"></el-input>
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
      <el-form ref="form" :model="form" label-width="80px">
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
    <el-dialog fullscreen="true" :title="title"  :visible.sync="flag1" :close-on-click-modal="false" width="700px">
      <employee-list :relAccount="relAccount" ></employee-list>
    </el-dialog>
  </home>
</template>

<script>
   import employeeList from '../employeeManagement/EmployeeList'
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
        flag1: false,
        relAccount: true,
        newForm: {
          accountNum: '',
          password: '',
          staffNum: '',
          staffName: '',
          permissions:'',
          secretEmail: '',
          department: '',
          remark:''
        },
        rules: {
          password: [
            {validator: validatePass, trigger: 'blur'}
          ]
        },
        tableData: [],
        create_time:'',
        create_emp:'',
        modify_time:'',
        modify_emp:'',
        remark:'',
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
    },
    components: {employeeList},
    provide(){
      return{
        test1:this.test1
      }
    },
    methods: {
      save() {//保存新建员工信息

      },
      cancel(){//关闭新建员工页面，返回员工管理列表页面

      },
      test() {
        this.flag1 = true;
      },
      test1() {
        this.flag1 = false;
      },
    },
  }


</script>

<style scoped>

</style>
