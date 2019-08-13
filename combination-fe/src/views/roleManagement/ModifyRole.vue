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
              <el-input style="width:200px;" :disabled="true" v-model="form.roleID"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="角色名称" prop="roleName">
              <el-input style="width:200px;" v-model="form.roleName" placeholder="请填入名称(1-30字符)" maxlength="30"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批人账号" prop="accountNum">
              <el-input style="width:200px;" :disabled="true" v-model="form.accountNum"></el-input>
              <a style="color: #ffd408" @click="chooseAccount">选择</a>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人员工编号">
              <el-input style="width:200px;" :disabled="true" v-model="form.staffNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批人姓名">
              <el-input style="width:200px;" :disabled="true" v-model="form.staffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人所属部门">
              <el-input style="width:200px;" :disabled="true" v-model="form.departmentName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="支持业务线" prop="businessLine">
              <!--<template v-for="item in chks">
                <input type="checkbox" name="hobby" :value="item.id"
                     :checked="form.loopsss.indexOf(item.id) > -1"/>{{item.name}}
              </template>
              -->
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
              <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" placeholder="填写角色描述"  maxlength="200" v-model="form.description"></el-input>
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
                  <el-input style="width:200px;" :disabled="true" v-model="form.createEmp"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="新建时间">
                  <el-input style="width:200px;" :disabled="true" v-model="form.createTime"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="修改人">
                  <el-input style="width:200px;" :disabled="true" v-model="modifyEmp"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="修改时间">
                  <el-input style="width:200px;" :disabled="true" v-model="modifyTime"></el-input>
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
    <el-dialog :title='title' :visible.sync="dialogVisibleAccount"  :close-on-click-modal="false" width="80%">
      <div style="width: 95%;">
        <el-form ref="accountForm" :model="accountForm" label-width="100px">
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
                <el-input style="width:180px;" placeholder="员工姓名" v-model="accountForm.name" clearable></el-input>
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
        <el-form ref="accountForm" :model="accountForm">
          <el-row>
            <el-col style="text-align: center">
              <el-form-item>
                <el-button type="primary" @click="fetchAccountData" style="width:100px">查询</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="selectionConfirm" style="width:70px">确认选择</el-button>
        <el-button type="primary" @click="selectionCancel" style="width:70px">取消</el-button>

      </div>
      <el-table ref="multipleTable" :data="tableData" border @current-change="handleSelectionChange" >
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
        rules: {
          roleName: [{required: true, message: '角色名不能为空', trigger: ['blur','change']}],
          accountNum: [{required: true, message: '账户不能为空', trigger: ['blur','change']}],
          businessLine: [{required: true, message: '支持业务线不能为空', trigger: ['blur','change']}]
        },
        total: 0,
        currentPage: 1,
        pageSize: 10,
        title:'选择账户',
        dialogVisibleAccount:false,
        otherForm:{},
        form: {
          roleID:'',
          roleName:'',
          businessLine:[],
          roleStatus:'',
          accountNum:'',
          staffNum:'',
          staffName:'',
          departmentName:'',
          description:'',
          createEmp:'',
          createTime:'',
        },
        modifyEmp:'',
        modifyTime:'',
        RoleStatusEnum:{},
        options:[
          {
            value:0,
            label:'无效',
          },{
            value:1,
            label:'有效',
          }],
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
        selection:[],
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
      this.fetchData();
      this.modifyEmp=window.sessionStorage.getItem("loginUsername");
      this.modifyTime=this.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    },
    methods: {
      format(date, fmt) {//时间格式
        let o = {
          "M+": date.getMonth() + 1, //月份
          "d+": date.getDate(), //日
          "H+": date.getHours(), //小时
          "m+": date.getMinutes(), //分
          "s+": date.getSeconds(), //秒
          "q+": Math.floor((date.getMonth() + 3) / 3), //季度
          "S": date.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (let k in o)
          if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
      },
      selectionConfirm(){
        this.form.accountNum =this.selection.accountName;
        this.form.staffNum =this.selection.staffNum;
        this.form.staffName = this.selection.staffName;
        this.form.departmentName = this.selection.department;
        this.dialogVisibleAccount=false;
      },
      selectionCancel(){
        this.dialogVisibleAccount=false;
      },
      handleSelectionChange(val) {
        this.selection = val;
      },
      save(formName) {//保存修改角色信息
        const self = this;
        self.$refs[formName].validate((valid) =>{
          if (valid) {
            alert(valid);
            var param = {
              roleName: self.form.roleName,
            };
            self.$http.get("roleManage/judgeExist.do_", {
              params: param
            }).then((result) => {
              if (result.page.roleStatus ===0||result.page.roleStatus===1 ){
                alert("角色名称已存在");
              }
              else {
                self.$confirm('此操作将保存该文件, 是否继续?', '提示', {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  type: 'warning'
                }).then(() => {
                  self.form.businessLine = self.form.businessLine.join(',');
                  self.$http.post("roleManage/updateByModify.do_", self.form)
                    .then((result) => {
                      self.$router.replace("/roleManagement/roleManagement");
                    })
                    .catch(function (error) {
                      commonUtils.Log("roleManage/updateByModify.do_" + error);
                      self.$message.error("保存数据错误");
                      self.$router.replace("/roleManagement/roleManagement");
                    });
                  self.$message({
                    type: 'success',
                    message: '保存成功!'
                  });
                }).catch(() => {
                  self.$message({
                    type: 'info',
                    message: '已取消保存'
                  });
                });
              }
            }).catch(function (error) {
              commonUtils.Log("roleManage/judgeExist.do_:"+error);
              self.$message.error("获取数据错误sdfas")
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      cancel(){//关闭新建角色页面，返回角色管理列表页面
        this.$router.replace('/roleManagement/roleManagement')
      },
      chooseAccount(){
        this.accountForm.accountNo = null;
        this.accountForm.staffNo = null;
        this.accountForm.name = null;
        this.accountForm.permissions = null;
        this.accountForm.department = null;
        this.accountForm.isRelStaff = null;
        this.accountForm.status = null;
        this.dialogVisibleAccount=true;
        this.fetchAccountData();
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

      fetchAccountData(){//获取账户信息
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

      fetchData(){
        var roleid;
        var self = this;
        roleid=this.$route.query.roleID;
        var param = {
          roleID: roleid,
        };
        self.$http.get('roleManage/getOneInf.do_', {
          params: param
        }).then((result) => {
          self.form=result.page;
          self.RoleStatusEnum = result.RoleStatusEnum;
          self.form.businessLine=self.form.businessLine.split(',');
        }).catch(function (error) {
          commonUtils.Log("roleManage/getOneInf.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },
  },



  }


</script>

<style scoped>

</style>
