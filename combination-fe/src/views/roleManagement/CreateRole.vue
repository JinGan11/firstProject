<template>
  <div>

    <div style="margin-bottom: 10px">
      <p>角色信息</p>
    </div>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="110px">
        <el-row>
          <el-col :span="10">
            <el-form-item label="角色ID">
              <el-input style="width:200px;" :disabled="true"  placeholder="保存自动填入" v-model="form.roleID"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="角色名称">
              <span style="color: red;">*</span>
              <el-input style="width:200px;" placeholder="请填入名称" v-model="form.roleName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批人账号">
              <span style="color: red;">*</span>
              <el-input style="width:200px;" :disabled="true"  placeholder="选择回填信息账号" v-model="form.accountNum"></el-input>
              <a style="color: #ffd408" @click="chooseAccount">选择</a>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人员工编号">
              <el-input style="width:200px;" :disabled="true"  placeholder="回填信息" v-model="form.staffNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批人姓名">
              <el-input style="width:200px;" :disabled="true"  placeholder="回填信息" v-model="form.staffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人所属部门">
              <el-input style="width:200px;" :disabled="true"  placeholder="回填信息" v-model="form.departmentName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
            <el-col :span="15">
              <el-form-item label="支持业务线">
                <span style="color: red;">*</span>
                  <!--<template v-for="item in chks">
                    <input type="checkbox" name="form.hobby" :value="item.id"
                         :checked="form.loopsss.indexOf(item.id) > -1"/>{{item.name}}
                  </template>-->
                <input type="checkbox" v-model="form.businessLine" value="买买车">买买车
                <input type="checkbox" v-model="form.businessLine" value="租车">租车
                <input type="checkbox" v-model="form.businessLine" value="闪贷">闪贷
                <input type="checkbox" v-model="form.businessLine" value="专车">专车
                <input type="checkbox" v-model="form.businessLine" value="保险">保险
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="描述">
              <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="填写角色描述" v-model="form.description"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <div>
    <p>其它信息</p>
    <div>
      <div style="width:85%; margin-left: 70px">
        <el-form ref="form" :model="form" label-width="110px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="新建人">
                <el-input style="width:200px;" :disabled="true" placeholder="当前操作账户" v-model="createEmp"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="新建时间">
                <el-input style="width:200px;" :disabled="true" placeholder="当前时间" v-model="form.createTime"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="修改人">
                <el-input style="width:200px;" :disabled="true" placeholder="当前操作账户" v-model="modifyEmp"></el-input>
              </el-form-item>
            </el-col>
              <el-col :span="10">
                <el-form-item label="修改时间">
                  <el-input style="width:200px;" :disabled="true" placeholder="当前操作时间" v-model="form.modifyTime"></el-input>
                </el-form-item>
              </el-col>
          </el-row>
          <el-row>
            <el-col :span="1">
              <el-form-item label="状态">
                <el-input placeholder="有效" style="width:200px;" :disabled="true" v-model="form.roleStatus"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
  </div>

  <div style="text-align: center">
      <el-button type="primary" @click="save" style="width:70px">保存</el-button>
      <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
  </div>

    <el-dialog :title='title' :visible.sync="dialogVisibleAccount"  :close-on-click-modal="false" width="80%">
      <div style="width: 95%;">
        <el-form ref="form" :model="form" label-width="100px">
          <el-row>
            <el-col :span="5">
              <el-form-item label="登陆账号" >
                <el-input style="width:140px;" placeholder="登录账号" v-model="accountForm.accountNo" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="员工编号">
                <el-input style="width:160px;" placeholder="员工编号" v-model="accountForm.staffNo" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="员工姓名">
                <el-input style="width:150px;" placeholder="员工姓名" v-model="accountForm.name" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="数据权限类型">
                <el-select style="width:180px;" v-model="accountForm.permissions" clearable placeholder="请选择">
                  <el-option
                    v-for="item in accountForm.permissionsList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="员工所属部门">
                <el-input style="width:180px;" placeholder="员工所属部门" v-model="accountForm.departmentId"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="是否关联员工">
                <el-select style="width: 180px" v-model=" accountForm.isRelStaff" clearable placeholder="请选择">
                  <el-option
                    v-for="item in accountForm.isRelStaffoptions"
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
                    v-for="item in accountForm.accountStatusList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <el-form ref="form" :model="form">
          <el-row>
            <el-col style="text-align: center">
              <el-form-item>
                <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="selectionConfirm()" style="width:70px">确认选择</el-button>
        <el-button type="primary" @click="selectionCancel" style="width:70px">取消</el-button>

      </div>
      <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange" >
        <!--      <el-table-column type="selection" width="35"></el-table-column>-->
        <el-table-column label="选择" width="45">
          <template slot-scope="scope">
            <el-radio v-model="selection" :label="scope.row.id" @change="approvalInfo(scope.row)"><span width="0px;"></span></el-radio>
          </template>
        </el-table-column>
        <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
        <el-table-column prop="accountName" label="登陆账号" style="width:auto"></el-table-column>
        <el-table-column prop="staffNum" label="员工编号" style="width:auto"></el-table-column>
        <el-table-column prop="staffName" label="员工姓名" style="width:auto"></el-table-column>
        <el-table-column prop="department" label="所属部门" style="width:auto"></el-table-column>
        <el-table-column prop="premissions" label="数据权限类型" style="width:auto">
          <template slot-scope="scope">
            {{accountForm.permissionsEnum[scope.row.premissions]}}
          </template>
        </el-table-column>
        <el-table-column prop="accountState" label="账号状态" style="width:auto">
          <template slot-scope="scope">
            {{accountForm.accountStatusEnum[scope.row.accountState]}}
          </template>
        </el-table-column>
        <el-table-column prop="modifyTime" label="操作时间" style="width:auto"></el-table-column>
        <el-table-column prop="modifyEmpName" label="操作人" style="width:auto"></el-table-column>
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
    </el-dialog>

  </div>
</template>

<script>
  import commonUtils from '../../common/commonUtils'

  export default {
    data() {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        title:'选择账户',
        dialogVisibleAccount:false,
        form: {
          roleName:'',
          businessLine:[],
          roleStatus:'',
          accountNum:'',
          staffNum:'',
          staffName:'',
          departmentName:'',
          description:'',
          createTime:'',
          modifyTime:'',
        },
        createEmp:'',
        modifyEmp:'',
        accountForm: {//选择账户
          accountNo: null,
          staffNo: null,
          name: null,
          permissionsList:[],
          permissionsEnum:{},
          accountStatusList:[],
          accountStatusEnum:{},
          permissions: null,
          department:null,
          isRelStaffoptions:[{
            value: '1',
            label: '是'
          },{
            value: '0',
            label: '否'
          }],
          isRelStaff: null,
          status:null
        },
        tableData:[],
        selection:'',
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
      this.createEmp=window.sessionStorage.getItem("loginUsername");
      this.modifyEmp=window.sessionStorage.getItem("loginUsername");
    },

    methods: {
      approvalInfo(val){
        this.form.accountNum = val.accountName;
        this.form.staffNum = val.staffNum;
        this.form.staffName = val.staffName;
        this.form.departmentName = val.department;
      },
      selectionConfirm(){
        this.dialogVisibleAccount=false;
      },
      selectionCancel(){
        this.dialogVisibleAccount=false;
      },
      handleSelectionChange(val) {
        this.selection = val;
      },
      save() {//保存新建角色信息
        var self = this;
        if(!self.$options.methods.checkInput(self)) return;
        this.$confirm('此操作将保存该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          self.form.businessLine=self.form.businessLine.join(',');
          self.$http.post("roleManage/insertRole.do_", self.form)
            .then((result) => {
              self.$router.replace("/roleManagement/roleManagement");
            })
            .catch(function (error) {
              commonUtils.Log("roleManage/insertRole:"+error);
              self.$message.error("角色名称唯一");
              self.$router.replace("/roleManagement/roleManagement");
            });
          this.$message({
            type: 'success',
            message: '保存成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消保存'
          });
        });
      },
      cancel(){//关闭新建角色页面，返回角色管理列表页面
        this.$router.replace('/roleManagement/roleManagement')
      },
      chooseAccount(){
        this.dialogVisibleAccount=true;
        this.fetchData();
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.currentPage = 1;
        this.fetchData(1, val);
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.fetchData(val, this.pageSize);
      },
      fetchData(){
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          accountName: self.accountForm.accountNo,
          staffNo: self.accountForm.staffNo,
          name:self.accountForm.name,
          permissions: self.accountForm.permissions,
          department: self.accountForm.departmentId,
          isRelStaff: self.accountForm.isRelStaff,
          status: self.accountForm.status
        };
        self.$http.get('account/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.accountForm.permissionsList = result.permissionList;
          self.accountForm.permissionsEnum = result.permissionEnum;
          self.accountForm.accountStatusEnum = result.accountStatusEnum;
          self.accountForm.accountStatusList = result.accountStatusList;
          self.total = result.page.totalCount;
        }).catch(function (error) {
          commonUtils.Log("account/querylist.do_:"+error);
          self.$message.error("获取数据错误")
        });
      },
      checkInput(val){
        if (val.form.roleName==''){
          alert("角色名称不能为空");
          return false;
        }
        if (val.form.accountNum==''){
          alert("请选择审批人账号");
          return false;
        }
        if (val.form.businessLine==''){
          alert("支持业务线不能为空");
          return false;
        }
        return true;
      },
    },



  }


</script>

<style scoped>

</style>
