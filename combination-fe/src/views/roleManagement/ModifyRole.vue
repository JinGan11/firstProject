<template>
  <div>
    <div style="margin-bottom: 10px">
      <p>角色信息</p>
    </div>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="110px" :rules="rules">
        <el-row>
          <el-col :span="10">
            <el-form-item label="角色ID">
              <el-input style="width:200px;" :disabled="true" v-model.trim="form.roleID"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="角色名称" prop="roleName">
              <el-input style="width:200px;" v-model.trim="form.roleName" placeholder="请填入名称(1-30字符)"
                        maxlength="30"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批人账号" prop="accountNum">
              <el-input style="width:200px;" :disabled="true" v-model.trim="form.accountNum"></el-input>
              <a style="color: #ffd408" @click="chooseAccount">选择</a>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人员工编号">
              <el-input style="width:200px;" :disabled="true" v-model.trim="form.staffNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批人姓名">
              <el-input style="width:200px;" :disabled="true" v-model.trim="form.staffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人所属部门">
              <el-input style="width:200px;" :disabled="true" v-model.trim="form.departmentName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="15">
            <el-form-item label="支持业务线" prop="businessLine">
              <el-checkbox-group v-model.trim="form.businessLine">
                <el-checkbox label="买买车"></el-checkbox>
                <el-checkbox label="租车"></el-checkbox>
                <el-checkbox label="闪贷"></el-checkbox>
                <el-checkbox label="专车"></el-checkbox>
                <el-checkbox label="保险"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="描述">
              <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="填写角色描述" maxlength="200"
                        v-model="form.description"></el-input>
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
                  <el-select v-model="form.roleStatus" :disabled="true" style="width:150px;">
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
    <div style="text-align: center">
      <el-button type="primary" @click="save('form')" style="width:70px">保存</el-button>
      <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
    </div>
    <el-dialog :title='title' :visible.sync="dialogVisibleAccount" :close-on-click-modal="false" width="80%">
      <div class="dialog-main" style="overflow: auto">
        <el-form ref="accountForm" :model="accountForm" label-width="100px">
          <el-row>
            <el-col :span="5">
              <el-form-item label="登陆账号">
                <el-input style="width:140px;" placeholder="登录账号" v-model.trim="accountForm.accountNo" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="员工编号">
                <el-input style="width:160px;" placeholder="员工编号" v-model.trim="accountForm.staffNo" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="员工姓名">
                <el-input style="width:150px;" placeholder="员工姓名" v-model.trim="accountForm.name" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="数据权限类型">
                <el-select style="width:180px;" v-model="accountForm.permissions" clearable placeholder="请选择">
                  <el-option
                    v-for="item in permissionsList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="员工所属部门">
                <el-input style="width:140px;" placeholder="员工所属部门" v-model.trim="accountForm.department"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-button type="text" @click="chooseDepartment">选择</el-button>
              <el-button type="text" @click="clearDepartment">取消</el-button>
            </el-col>
            <el-col :span="6">
              <el-form-item label="是否关联员工">
                <el-select style="width: 180px" v-model=" accountForm.isRelStaff" clearable placeholder="请选择">
                  <el-option
                    v-for="item in isRelStaffoptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="账号状态">
                <el-select style="width:180px;" v-model="accountForm.status" clearable placeholder="请选择">
                  <el-option
                    v-for="item in accountStatusList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <el-form ref="accountForm" :model="accountForm">
          <el-row>
            <el-col style="text-align: center">
              <el-form-item>
                <el-button type="primary" @click="fetchAccountData(1)" style="width:100px">查询</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div style="margin-bottom: 10px">
          <el-button type="primary" @click="selectionConfirm" :disabled="isChoose" style="width:70px">确认选择</el-button>
          <el-button type="primary" @click="selectionCancel" style="width:70px">取消</el-button>
        </div>
        <el-table ref="multipleTable" :data="tableData" border @current-change="handleSelectionChange">
          <!--      <el-table-column type="selection" width="35"></el-table-column>-->
          <el-table-column label="选择" width="45">
            <template slot-scope="scope">
              <el-radio v-model="selection" :label="scope.row"><span width="0px;"></span></el-radio>
            </template>
          </el-table-column>
          <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
          <el-table-column prop="accountName" label="登陆账号" style="width:auto"></el-table-column>
          <el-table-column prop="staffNum" label="员工编号" style="width:auto"></el-table-column>
          <el-table-column prop="staffName" label="员工姓名" style="width:auto"></el-table-column>
          <el-table-column prop="department" label="所属部门" style="width:auto"></el-table-column>
          <el-table-column prop="premissions" label="数据权限类型" style="width:auto">
            <template slot-scope="scope">
              {{permissionsEnum[scope.row.premissions]}}
            </template>
          </el-table-column>
          <el-table-column prop="accountState" label="账号状态" style="width:auto">
            <template slot-scope="scope">
              {{accountStatusEnum[scope.row.accountState]}}
            </template>
          </el-table-column>
          <el-table-column prop="modifyTime" label="操作时间" style="width:auto"></el-table-column>
          <el-table-column prop="modifyEmpName" label="操作人" style="width:auto">
            <template slot-scope="scope">
              <p v-if="scope.row.modifier!=null">{{scope.row.modifyEmpName}}({{scope.row.modifier}})</p>
              <p v-else>{{scope.row.modifyEmpName}}</p>
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
      </div>
    </el-dialog>
    <el-dialog title="选择部门" :visible.sync="chooseDepartmentFlag" width="300px">
      <el-tree
        ref="tree"
        :props="defaultPropsTree"
        node-key="id"
        :load="loadNodeDepartment"
        lazy="true"
        check-strictly
        show-checkbox
        :default-expanded-keys="[1]"
        @check-change="handleClickChange">
      </el-tree>
    </el-dialog>
  </div>
</template>

<script>
  import commonUtils from '../../common/commonUtils'

  export default {
    data() {
      var validatePass = (rule, value, callback) => {
        const self = this;
        var roleid = this.$route.query.roleID;
        var param = {
          roleName: value,
          date: new Date().getTime(),
        };
        self.$http.get("roleManage/judgeExist.do_", {
          params: param
        }).then((result) => {
          if ((result.page.roleStatus === 0 || result.page.roleStatus === 1) && (result.page.roleId !== roleid)) {
            callback(new Error('角色名称已存在'));
          } else {
            if (self.form.checkPass !== '') {
              self.$refs.form.validateField('checkPass');
            }
            callback();
          }
        }).catch(function (error) {
          commonUtils.Log("roleManage/judgeExist.do_" + error);
          self.$message.error("角色名称校验错误");
        });
      };
      return {
        defaultPropsTree: {
          label: 'departmentName',
          children: 'children',
          isLeaf: 'nodeIsLeaf',
          id: 'id',
          no: 'departmentNo',
        },
        chooseDepartmentFlag: false,
        isChoose: true,
        rules: {
          roleName: [{required: true, message: '角色名不能为空', trigger: ['blur', 'change']},
            {validator: validatePass, trigger: 'blur'}],
          accountNum: [{required: true, message: '账户不能为空', trigger: ['blur', 'change']}],
          businessLine: [{required: true, message: '支持业务线不能为空', trigger: ['blur', 'change']}]
        },
        total: 0,
        currentPage: 1,
        pageSize: 10,
        title: '选择账户',
        dialogVisibleAccount: false,
        otherForm: {
          createEmp: '',
          createEmpNum: '',
          createEmpName: '',
          createEmpTime: '',
          modifyEmp: '',
          modifyEmpNum: '',
          modifyEmpName: '',
          modifyEmpTime: '',
        },
        form: {
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
        RoleStatusEnum: {},
        options: [
          {
            value: 0,
            label: '无效',
          }, {
            value: 1,
            label: '有效',
          }],
        accountForm: {//选择账户
          accountNo: null,
          staffNo: null,
          name: null,
          permissions: null,
          department: null,
          departmentId: '',
          isRelStaff: null,
          status: null,
        },
        permissionsEnum: {},
        accountStatusEnum: {},
        accountStatusList: [
          {
            value: 0,
            label: '全部'
          }, {
            value: 1,
            label: "正常"
          }, {
            value: 2,
            label: "冻结"
          }
        ],
        permissionsList: [
          {
            value: 0,
            label: '全选'
          }, {
            value: 1,
            label: "全部"
          }, {
            value: 2,
            label: "递归"
          }, {
            value: 3,
            label: "本部门"
          }, {
            value: 4,
            label: "本人"
          }, {
            value: 5,
            label: "手动选择"
          }
        ],
        differentFetchAccountData:0,
        isRelStaffoptions: [{
          value: '',
          label: '全部'
        }, {
          value: '1',
          label: '是'
        }, {
          value: '0',
          label: '否'
        }],
        tableData: [],
        selection: {},
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
      this.fetchData();
      this.fetchOtherData();
    },
    methods: {
      chooseDepartment() {
        this.chooseDepartmentFlag = true;
      },
      clearDepartment() {
        this.accountForm.departmentId = '';
        this.accountForm.department = '';
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
      handleClickChange(data, checked, node) {
        // 手动设置单选
        if (checked === true) {
          this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
          this.accountForm.departmentId = data.id;
          this.accountForm.department = data.departmentName;
        } else {
          if (this.checkedId == data.id) {
            this.$refs.tree.setCheckedKeys([data.id]);
          }
        }
        this.closeChooseDepartment();
      },
      fetchOtherData() {
        var roleid;
        var self = this;
        roleid = this.$route.query.roleID;
        var param = {
          roleID: roleid,
          date: new Date().getTime(),
        };
        self.$http.get('roleManage/getOtherOneInf.do_', {
          params: param
        }).then((result) => {
          self.otherForm = result.page;
          self.otherForm.createEmp = `${self.otherForm.createEmpNum}(${self.otherForm.createEmpName})`;
          self.otherForm.modifyEmp = `${self.otherForm.modifyEmpNum}(${self.otherForm.modifyEmpName})`;
        }).catch(function (error) {
          commonUtils.Log("roleManage/getOtherOneInf.do_" + error);
          if(error.message.includes('timeout')){
            self.$message.error("请求超时！");
          }else{
            self.$message.error("获取数据错误");
          }
        });
      },
      selectionConfirm() {
        const self = this;
        var param = {
          id: self.selection.id,
          date: new Date().getTime(),
        };
        self.$http.get('account/selectAccountById.do_', {
          params: param
        }).then((result) => {
          if (result.account.accountState === 3) {
            self.$message.info("该账户已被删除，不可选择");
            this.isChoose = true;
            this.formatForm();
            this.fetchAccountData(0);
          } else {
            this.form.accountNum = this.selection.accountName;
            this.form.staffNum = this.selection.staffNum;
            this.form.staffName = this.selection.staffName;
            this.form.departmentName = this.selection.department;
            this.isChoose = true;
            this.dialogVisibleAccount = false;
          }
        }).catch(function (error) {
          commonUtils.Log("account/selectAccountById.do_:" + error);
          if(error.message.includes('timeout')){
            self.$message.error("请求超时！");
          }else{
            self.$message.error("获取数据错误");
          }
        });
      },
      selectionCancel() {
        this.isChoose = true;
        this.dialogVisibleAccount = false;
      },
      handleSelectionChange(val) {
        this.selection = val;
        this.isChoose = false;
      },
      save(formName) {//保存修改角色信息
        const self = this;
        self.$refs[formName].validate((valid) => {
          if (valid) {
            self.$confirm('此操作将保存该文件, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              self.form.businessLine = self.form.businessLine.join(';');
              self.$http.post("roleManage/updateByModify.do_", self.form)
                .then((result) => {
                  if (result.msg === "该账户已被删除，不可选择") {
                    self.$message.info("该账户已被删除，不可选择");
                    self.form.businessLine = self.form.businessLine.split(';');
                  } else if (result.msg === "该角色已被删除，不可修改") {
                    self.$message.info("该角色已被删除，不可修改");
                    self.$router.replace("/roleManagement/roleManagement");
                  } else {
                    self.$message({
                      type: 'success',
                      message: '保存成功!'
                    });
                    self.$router.replace("/roleManagement/roleManagement");
                  }
                })
                .catch(function (error) {
                  commonUtils.Log("roleManage/updateByModify.do_" + error);
                  self.$message.error("保存数据错误");
                  self.$router.replace("/roleManagement/roleManagement");
                });
            }).catch(() => {
              self.$message({
                type: 'info',
                message: '已取消保存'
              });
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      cancel() {//关闭新建角色页面，返回角色管理列表页面
        this.$router.replace('/roleManagement/roleManagement')
      },
      chooseAccount() {
        this.formatForm();
        this.isChoose = true;
        this.dialogVisibleAccount = true;
        this.fetchAccountData(0);
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.currentPage = 1;
        this.fetchAccountData(0);
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.fetchAccountData(0);
      },
      fetchAccountData(val) {//获取账户信息
        var self = this;
        var accountForm1 = {accountNo :'',staffNo:'',name:'',permissions:0,department:'',
          departmentId:'',isRelStaff:'',status:0};
        if(val === 1) {
          this.currentPage = 1;
          if ((self.accountForm.accountNo !== '')||
            (self.accountForm.staffNo !== '')||
            (self.accountForm.name !== '')||
            (self.accountForm.permissions !== 0)||
            (self.accountForm.department !== '')||
            (self.accountForm.departmentId !== '')||
            (self.accountForm.isRelStaff !== '')||
            (self.accountForm.status !== 0)){
            self.differentFetchAccountData = 1;
          }
          else{
            self.differentFetchAccountData = 0;
          }
          accountForm1 = this.accountForm;
        }
        else{
          if (self.differentFetchAccountData === 1){
            accountForm1 = this.accountForm;
          }
        }
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          accountName: accountForm1.accountNo,
          staffNo: accountForm1.staffNo,
          name: accountForm1.name,
          permissions: accountForm1.permissions,
          department: accountForm1.departmentId,
          isRelStaff: accountForm1.isRelStaff,
          status: accountForm1.status,
          flag: 1,
          date: new Date().getTime(),
        };
        self.$http.get('account/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.permissionsEnum = result.permissionEnum;
          self.accountStatusEnum = result.accountStatusEnum;
          self.total = result.page.totalCount;
        }).catch(function (error) {
          commonUtils.Log("account/querylist.do_:" + error);
          if(error.message.includes('timeout')){
            self.$message.error("请求超时！");
          }else{
            self.$message.error("获取数据错误");
          }
        });
      },
      fetchData() {
        var roleid;
        var self = this;
        roleid = this.$route.query.roleID;
        var param = {
          roleID: roleid,
          date: new Date().getTime(),
        };
        self.$http.get('roleManage/getOneInf.do_', {
          params: param
        }).then((result) => {
          self.form = result.page;
          self.RoleStatusEnum = result.RoleStatusEnum;
          self.form.businessLine = self.form.businessLine.split(';');
        }).catch(function (error) {
          commonUtils.Log("roleManage/getOneInf.do_" + error);
          if(error.message.includes('timeout')){
            self.$message.error("请求超时！");
          }else{
            self.$message.error("获取数据错误");
          }
        });
      },
    },
  }


</script>

<style scoped>

</style>
