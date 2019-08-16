<template>
  <home>
    <div style="width:100%; margin-left: 110px">
      <div>
        <div style="width: 60%; float:left">
          <h2 style="color: #409EFF">|账户信息</h2>
        </div>
      </div>
      <hr style="width: 70%; float: left; border:1px solid #409EFF; margin-top: -5px; margin-bottom: 15px"></hr>
      <div style="width:85%;">
        <el-form  :model="modifyForm" status-icon ref="modifyForm" size="medium" label-width="120px"
                  class="demo-ruleForm">
          <el-row>
            <el-col :span="9">
              <el-form-item label="登录账户">
                <el-input  style="width:150px" v-model="modifyForm.accountNum" :disabled="true" autocomplete="off" ></el-input>
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
                <el-input  style="width:150px " type="password" v-model="modifyForm.password" :disabled="true" ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="9">
              <el-form-item label="关联员工编号">
                <el-input style="width: 150px" v-model="modifyForm.staffNum" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="员工姓名">
                <el-input style="width: 150px" v-model="modifyForm.staffName" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="数据权限类型" prop="permissions">
                <el-select style="width:150px;" :disabled="true" v-model="modifyForm.permissions" clearable placeholder="请选择">
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
                <el-input style="width:150px;" v-model="modifyForm.secretEmail" clearable :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row v-if="departmentVisible">
            <el-col>
              <el-form-item label="手动选择部门" prop="permissions">
                <el-tree :disabled="true"
                         ref="tree"
                         :props="defaultProps"
                         node-key="id"
                         :load="loadNode"
                         lazy="true"
                         show-checkbox
                         check-strictly>
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
      <div style="width:85%;">
        <el-form :model="modifyForm" label-width="120px">
          <el-row>
            <el-col :span="9">
              <el-form-item label="新建人">
                <el-input style="width:150px;" :disabled="true" v-model="modifyForm.creatEmpName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="新建时间">
                <el-input style="width:150px;" :disabled="true" v-model="modifyForm.createTime"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="9">
              <el-form-item label="修改人">
                <el-input style="width:150px;" :disabled="true" v-model="modifyForm.modifyEmpName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="修改时间">
                <el-input style="width:150px;" :disabled="true" v-model="modifyForm.modifyTime"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="账号状态">
                <el-select style="width:400px;" :disabled="true" v-model="modifyForm.accountState" clearable placeholder="请选择" >
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
            <el-col :span="9">
              <el-form-item label="备注">
                <el-input style="width: 400px" :disabled="true" type="textarea" :rows="2" v-model="modifyForm.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
  </home>
</template>

<script>
  export default {
    name: "account_view",
    props: {
      accountId: 0,
    },
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
      }
    },
    created() {
      const self = this;
      var val = localStorage.getItem("accountId")
      self.fetchData(val);
      self.fetchEnums();
    },
    methods: {
      loadNode(node,resolve){//加载部门的树结构
        var self = this;
        self.$http.get('department/buildTree2.do_')
          .then((result) => {
            resolve([result.departmentDto]);
            self.$refs.tree.setCheckedKeys(self.modifyForm.trees);
          }).catch(function (error) {

        });
      },
      fetchDeparentPower(val){
        const self = this;
        var param = {
          id: val
        }
        self.$http.get('account/selectDeparentPower.do_',{
          params: param
        }).then((result) => {
          self.modifyForm.trees = result.trees;
          self.$refs.tree.setCheckedKeys(self.modifyForm.trees);
          self.$refs.tree.setCheckedKeys(self.modifyForm.trees);
        }).catch(function (error) {
          commonUtils.Log("account/selectDeparentPower.do_:"+error);
          self.$message.error("获取数据错误")
        });
      },
      fetchData(val){ //根据accountId查询界面要显示的数据
        const self = this;
        var param = {
          id: val
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
            self.fetchDeparentPower(val);
          }else{
            self.departmentVisible = false;
          }
          self.modifyForm.secretEmail = result.account.secretEmail;
          if(self.modifyForm.secretEmail == '' || self.modifyForm.secretEmail == null){
            self.emailDisabled = false;
          }
          self.modifyForm.remark = result.account.remark;
          self.modifyForm.creatEmpName = result.account.creatEmpName;
          self.modifyForm.createTime = result.account.createTime;
          self.modifyForm.modifyEmpName = result.account.modifyEmpName;
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
          commonUtils.Log("account/enums.do_:"+error);
          self.$message.error("获取数据错误")
        });
      },
    },
  }
</script>

