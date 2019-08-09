<template>
  <home>
    <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 20px ">
      <el-row :gutter="20">
        <el-col :span="5" style="color: #409EFF">
          申请信息
        </el-col>
        <el-col :span="2">
          <el-button type="primary" size="mini" @click="">保存</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" size="mini" @click="">保存并提交</el-button>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" size="mini" @click="">取消</el-button>
        </el-col>
      </el-row>
    </div>

    <div style="margin-top: 20px;margin-left: 40px;margin-bottom:20px;">
      <br>
      <el-form ref="form" :model="formRoleInfo" label-width="110px">
        <el-row>
          <el-col :span="10">
            <el-form-item label="角色申请编号">
              <el-input style="width:300px;" v-model="formRoleInfo.roleApplyNum" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="申请角色">
              <el-input style="width:250px;"  placeholder="请选择角色" v-model="formRoleInfo.roleName" :disabled="true"></el-input>
              <el-button type="text" @click="selectRoleForRoleApply">选择</el-button>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="角色审批人">
              <el-input style="width:300px;" v-model="formRoleInfo.approverStaffName" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="角色支持业务线">
              <el-input style="width:300px;" v-model="formRoleInfo.businessLine" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-left: 40px;margin-bottom:20px;border-bottom:1px solid gray;padding-bottom: 20px">
      <el-row :gutter="20">
        <el-col :span="5" style="color: #409EFF">
          申请账号明细
        </el-col>
        <el-col :span="2">
          <el-button type="primary" size="mini" @click="addAccountForApply()">添加</el-button>
        </el-col>
      </el-row>
    </div>
    <div style="margin-left: 40px;">
      <el-table ref="multipleTable" :data="tableDataAccount" border>
        <el-table-column type="selection" width="35"></el-table-column>
        <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
        <el-table-column prop="accountName" label="申请账号" width="150"></el-table-column>
        <el-table-column prop="staffName" label="关联员工姓名"width="150"></el-table-column>
        <el-table-column prop="staffNum" label="关联员工编号"  width="150"></el-table-column>
        <el-table-column prop="staffDepartmentName" label="关联员工所属部门" width="200"></el-table-column>
        <el-table-column prop="applyOperation" label="申请操作" width="150" style="text-align: center">
          <el-select v-model="optionOfApply" clearable style="width:100px;" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-table-column>
        <el-table-column prop="operationOfDelete" label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="text"  @click="deleteSelect(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="margin-left: 40px;margin-top:20px;margin-bottom:20px;border-bottom:1px solid gray;padding-bottom: 20px">
      <el-row :gutter="20">
        <el-col :span="5" style="color: #409EFF">
          其他信息
        </el-col>
      </el-row>
    </div>

    <div style="margin-top: 20px;margin-left: 40px;">
      <br>
      <el-form ref="form" :model="otherInfo" label-width="110px">
        <el-row>
          <el-col :span="10">
            <el-form-item label="申请时间">
              <el-input style="width:300px;" v-model="otherInfo.applyTime" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="申请人">
              <el-input style="width:300px;" v-model="otherInfo.applyStaffName" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="修改时间">
              <el-input style="width:300px;" v-model="otherInfo.modifyTime" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="修改人">
              <el-input style="width:300px;" v-model="otherInfo.modifyStaffName" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批时间">
              <el-input style="width:300px;" v-model="otherInfo.approveTime" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人">
              <el-input style="width:300px;" v-model="otherInfo.approverStaffName" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="状态">
              <el-input style="width:300px;" v-model="otherInfo.applyStatus" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>




    <el-dialog :title='title1' :visible.sync="dialogVisibleRole"  :close-on-click-modal="false" width="85%">
      <div>
        <el-form ref="form" :model="formSelectRole" label-width="100px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="角色名称">
                <el-input style="width:200px;" v-model="formSelectRole.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span ="10">
              <el-form-item>
                <el-button type="primary" @click="fetchDataRole" style="width:100px">查询</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div style="margin-left:20px;margin-bottom: 10px;">
        <el-button type="primary" @click="selectRole"  style="width:100px">确认选择</el-button>
        <el-button type="primary" @click="cancelSelectRole" style="width:100px">取消</el-button>
      </div>
      <el-table :data="tableDataRole" border @row-click="getRoleDetails"  @selection-change="handleCurrentChangeRole" >
        <el-table-column label="选择" width="45">
          <template slot-scope="scope">
            <el-radio v-model="selectionRoleInfo" :label="scope.row.roleId"><span width="0px;"></span></el-radio>
          </template>
        </el-table-column>
        <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
        <el-table-column prop="roleId" label="角色ID" width="150"></el-table-column>
        <el-table-column prop="roleName" label="角色名称"width="150"></el-table-column>
        <el-table-column prop="accountNum" label="审批人账号" width="120"></el-table-column>
        <el-table-column prop="staffNum" label="审批人员工编号" ></el-table-column>
        <el-table-column prop="staffName" label="审批人姓名" width="200"></el-table-column>
        <el-table-column prop="departmentName" label="审批人所属部门" width="120"></el-table-column>
        <el-table-column prop="roleStatus" label="状态" width="100"></el-table-column>
        <el-table-column prop="description" label="描述" width="120"></el-table-column>
      </el-table>
      <el-pagination background
                     @size-change="handleSizeChangeRole"
                     @current-change="handleCurrentChangeRole"
                     :current-page="currentPage"
                     :page-sizes="[10, 50, 100, 200]"
                     :page-size="pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total">
      </el-pagination>
    </el-dialog>


    <el-dialog :title='title2' :visible.sync="dialogVisibleAccount"  :close-on-click-modal="false" width="80%">
      <div style="width: 95%;">
        <el-form ref="form" :model="form" label-width="100px">
          <el-row>
            <el-col :span="5">
              <el-form-item label="登陆账号" >
                <el-input style="width:140px;" v-model="form.accountNo"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="员工编号">
                <el-input style="width:160px;" v-model="form.staffNo"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="员工姓名">
                <el-input style="width:180px;" v-model="form.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="数据权限类型">
                <el-select style="width:180px;" v-model="form.permissions" clearable placeholder="请选择">
                  <el-option
                    v-for="item in form.permissionsList"
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
                <el-select v-model="form.department" placeholder="请选择" multiple collapse-tags>
                  <el-option  :value="mineStatusValue" style="height: auto">
                    <el-tree :data="data" check-strictly=true show-checkbox node-key="id" ref="tree" highlight-current :props="defaultProps"></el-tree>
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="是否关联员工">
                <el-select style="width: 180px" v-model="form.isRelStaff" clearable placeholder="请选择">
                  <el-option
                    v-for="item in form.isRelStaffoptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="账号状态">
                <el-select style="width:180px;" v-model="form.status" clearable placeholder="请选择">
                  <el-option
                    v-for="item in form.accountStatusList"
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
        <el-button type="primary" @click="selectAccountConfirm" style="width:70px">确认选择</el-button>
        <el-button type="primary" @click="cancelSelectAccount" style="width:70px">取消</el-button>
      </div>
      <el-table ref="multipleTable" :data="tableData" border  @selection-change="handleSelectAccount">
        <el-table-column type="selection" width="35"></el-table-column>
        <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
        <el-table-column prop="accountName" label="登陆账号" style="width:auto"></el-table-column>
        <el-table-column prop="staffNum" label="员工编号" style="width:auto"></el-table-column>
        <el-table-column prop="staffName" label="员工姓名" style="width:auto"></el-table-column>
        <el-table-column prop="department" label="所属部门" style="width:auto"></el-table-column>
        <el-table-column prop="premissions" label="数据权限类型" style="width:auto">
          <template slot-scope="scope">
            {{form.permissionsEnum[scope.row.premissions]}}
          </template>
        </el-table-column>
        <el-table-column prop="accountState" label="账号状态" style="width:auto">
          <template slot-scope="scope">
            {{form.accountStatusEnum[scope.row.accountState]}}
          </template>
        </el-table-column>
        <el-table-column prop="modifyTime" label="操作时间" style="width:auto"></el-table-column>
        <el-table-column prop="modifyEmpName" label="操作人" style="width:auto"></el-table-column>
      </el-table>
      <el-pagination background
                     @size-change="handleSizeChangeAccount"
                     @current-change="handleCurrentChangeAccount"
                     :current-page="currentPage"
                     :page-sizes="[10, 50, 100, 200]"
                     :page-size="pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total">
      </el-pagination>
    </el-dialog>










  </home>
</template>






<script>
    import commonUtils from '../../common/commonUtils'
    export default {
        name: "modifyRoleApply",
        data(){
          return{
            total: 0,
            currentPage: 1,
            pageSize: 10,
            title1:'选择角色',
            title2:'选择账户',
            dialogVisibleRole: false,
            dialogVisibleAccount:false,
            dialogCategory: '',//控制显示对应的具体弹窗
            multipleSelection: [],
            tableDataAccount:[],
            data:[],
            defaultProps:'',
            applyId:'',

            formRoleInfo: {//申请信息
              roleApplyNum: '',
              roleName: '',
              approverStaffName: '',
              businessLine: ''
            },

            otherInfo: {//其他信息
              applyTime: '',
              applyStaffName: '',
              modifyTime: '',
              modifyStaffName: '',
              approveTime: '',
              approverStaffName: '',
              applyStatus: '',
            },

            formSelectRole: {
              name: '',
            },
            tableDataRole: [],
            selectionRoleInfo: '',


            form: {//选择账户
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

            tableData: [],
            selection: [],
            id: '',
            mineStatusValue:'',

            optionOfApply: '',
            options: [{
              value: '1',
              label: '添加'
            }, {
              value: '2',
              label: '移除'
            }],
          }
        },
      activated() {
        commonUtils.Log("页面激活");
      },
      mounted() {
        commonUtils.Log("页面进来");
        alert("修改角色申请页面进来");
        this.applyId=sessionStorage.getItem('applyIdFromApply');
        // alert(this.applyId);
        console.log(this.applyId);
        this.formRoleInfo.roleName=sessionStorage.getItem('roleNameFromApply');
        this.formRoleInfo.approverStaffName=sessionStorage.getItem('approverStaffNameFromApply');
        this.formRoleInfo.businessLine=sessionStorage.getItem('businessLineFromApply');
        this.otherInfo.approverStaffName=sessionStorage.getItem('approverStaffNameFromApply');
        this.otherInfo.applyTime=sessionStorage.getItem('applyTimeFromApply');
        this.otherInfo.applyStaffName=sessionStorage.getItem('loginUsername');
        this.otherInfo.modifyTime=sessionStorage.getItem('modifyTimeFromApply');
        this.otherInfo.modifyStaffName=sessionStorage.getItem('modifyEmpFromApply');
        this.showAccountListByApplyId();
      },
      methods: {
        handleSizeChangeRole(val) {
          this.pageSize = val;
          this.currentPage = 1;
          this.fetchDataRole(1, val);
        },
        handleCurrentChangeRole(val) {
          this.currentPage = val;
          this.fetchDataRole(val, this.pageSize);
        },


        handleSizeChangeAccount(val) {
          this.pageSize = val;
          this.currentPage = 1;
          this.fetchData(1, val);
        },
        handleCurrentChangeAccount(val) {
          this.currentPage = val;
          this.fetchData(val, this.pageSize);
        },




        selectRoleForRoleApply(){
          //点击选择，弹出选择角色对话框
          this.dialogVisibleRole=true;
          //显示所有角色列表
          this.fetchDataRole();
        },
        fetchDataRole() { //获取数据
          var self = this;
          var param = {
            page: self.currentPage,
            limit: self.pageSize,
            roleName: self.formSelectRole.name,
          };
          self.$http.get("roleManage/querylist.do_", {
            params: param
          }).then((result) => {
            self.tableDataRole = result.page.list;
            self.total = result.page.totalCount;
          }).catch(function (error) {
            commonUtils.Log("roleManage/querylist.do_:" + error);
            self.$message.error("获取数据错误");
          });
        },
        getRoleDetails(row) {
          //获取选中行的数据
          this.formRoleInfo.roleName=row.roleName;
          this.formRoleInfo.approverStaffName=row.staffName;
          this.otherInfo.approverStaffName=row.staffName;
        },
        selectRole(){
          //确认选择按钮 选择角色
          this.dialogVisibleRole=false;
        },
        cancelSelectRole(){
          //取消按钮
          this.dialogVisibleRole=false;
        },


        addAccountForApply(){
          //点击添加按钮，进入账号选择页面
          this.dialogVisibleAccount=true;
          this.fetchData();
          //清楚表格所选的记录
          this.$refs.multipleTable.clearSelection();
        },

        fetchData(){//账户列表
          var self = this;
          var param = {
            page: self.currentPage,
            limit: self.pageSize,
            accountName: self.form.accountNo,
            staffNo: self.form.staffNo,
            name:self.form.name,
            permissions: self.form.permissions,
            department: self.form.departmentId,
            isRelStaff: self.form.isRelStaff,
            status: self.form.status
          };
          self.$http.get('account/querylist.do_', {
            params: param
          }).then((result) => {
            self.tableData = result.page.list;
            self.form.permissionsList = result.permissionList;
            self.form.permissionsEnum = result.permissionEnum;
            self.form.accountStatusEnum = result.accountStatusEnum;
            self.form.accountStatusList = result.accountStatusList;
            self.total = result.page.totalCount;
          }).catch(function (error) {
            commonUtils.Log("account/querylist.do_:"+error);
            self.$message.error("获取数据错误")
          });
        },
        cancelSelectAccount(){
          //取消按钮
          this.dialogVisibleAccount=false;
        },
        handleSelectAccount(val){
          this.multipleSelection = val;
        },
        selectAccountConfirm(){
          this.tableDataAccount=this.multipleSelection;
          for(let i=0;i<this.multipleSelection.length;i++){
            this.tableDataAccount[i].id=this.multipleSelection[i].id;
            this.tableDataAccount[i].accountName=this.multipleSelection[i].accountName;
            this.tableDataAccount[i].staffName=this.multipleSelection[i].staffName;
            this.tableDataAccount[i].staffNum=this.multipleSelection[i].staffNum;
            this.tableDataAccount[i].staffDepartmentName=this.multipleSelection[i].department;
            // this.multipleSelection[i].operationOfDelete=
          }
          this.dialogVisibleAccount=false;
        },

        deleteSelect(index){ //移除添加的账户 删除行
          this.tableDataAccount.splice(index, 1)
        },


        showAccountListByApplyId(){ //修改页面进来 显示账户列表信息
          var self=this;
          var param={
            applyId:self.applyId,
          };
          self.$http.get('roleApply/showAccountListByApplyId.do_', {
            params: param
          }).then((result) => {
            alert(111111);
            //请求成功回调
            self.tableDataAccount = result.list;
            alert(22222);
          }).catch(function (error) {
            //请求失败回调
            commonUtils.Log("roleApply/showAccountListByApplyId.do_:"+error);
            self.$message.error("获取数据错误");
          });
        }
      }
    }
</script>

<style scoped>

</style>
