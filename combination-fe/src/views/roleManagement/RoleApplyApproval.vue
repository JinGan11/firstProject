<template>
  <home>
    <div style="width:96%; margin-left: 40px">
      <el-form ref="form" :model="form" label-width="110px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="角色申请编号">
              <el-input placeholder="角色申请编号" style="width:150px;" v-model="form.roleApplyNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请角色ID">
              <el-input placeholder="申请角色ID" style="width:150px;" v-model="form.roleId"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请角色名称">
              <el-input placeholder="申请角色名称" style="width:150px;" v-model="form.roleName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请人登录账号">
              <el-input placeholder="申请人登录账号" style="width:150px;" v-model="form.applyAccountName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="申请人员工编号">
              <el-input placeholder="申请人员工编号" style="width:150px;" v-model="form.applyStaffNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请人员工姓名">
              <el-input placeholder="申请人员工姓名" style="width:150px;" v-model="form.applyStaffName">
              </el-input>

            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请人所属部门">
              <el-input placeholder="申请人所属部门" style="width:150px;" v-model="form.applyDepartmentName"></el-input>
              <el-button type="text" @click="selectDepartment">选择</el-button>
              <el-button type="text" @click="clearDepartment">清空</el-button>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="100">
            <el-form-item label="申请时间">
              <el-date-picker
                v-model="applyTime"
                unlink-panels
                size="mini"
                type="daterange"
                value-format="yyyy-MM-dd"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="200">
            <el-form-item label="操作时间">
              <el-date-picker
                v-model="modifyTime"
                unlink-panels
                size="mini"
                type="daterange"
                value-format="yyyy-MM-dd"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px; margin-top:20px; margin-left:40px">
      <el-button type="primary" @click="approval" :disabled="isApproval" style="width:100px">审核</el-button>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id" @change="approvalInfo(scope.row)"><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="roleApplyNum" label="角色申请编号" width="150">
        <template slot-scope="scope">
          <el-button type="text" @click="roleApplyNumBtn(scope.row)">{{scope.row.roleApplyNum}}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="roleId" label="申请角色ID"width="150">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="cellTrigger(scope.row.roleId)">{{scope.row.roleId}}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="roleName" label="申请角色名称"  width="150"></el-table-column>
      <el-table-column prop="approverStaffName" label="审批负责人" width="150"></el-table-column>
      <el-table-column prop="businessLine" label="角色支持业务线" width="150" style="text-align: center">
        <!--<template slot-scope="scope">-->
          <!--{{BusinessLineEnum[scope.row.businessLine]}}-->
        <!--</template>-->
      </el-table-column>
      <el-table-column prop="applyAccountName" label="申请人登录账号" width="150"></el-table-column>
      <el-table-column prop="applyStaffNum" label="申请人员工编号" width="150"></el-table-column>
      <el-table-column prop="applyStaffName" label="申请人员工姓名" width="150"></el-table-column>
      <el-table-column prop="applyDepartmentName" label="申请人所属部门" width="150"></el-table-column>
      <el-table-column prop="applyTime" label="申请时间"width="150"></el-table-column>
      <el-table-column prop="applyStatus" label="状态" width="150" style="text-align: center">
        <template slot-scope="scope">
          {{ApplyStatusEnum[scope.row.applyStatus]}}
        </template>
      </el-table-column>
      <el-table-column prop="modifyEmp" label="操作人" width="150"></el-table-column>
      <el-table-column prop="modifyTime" label="操作时间" width="150"></el-table-column>
    </el-table>
    <el-pagination background
                   @size-change="handleSizeChange"
                   highlight-current-row
                   @current-change="handleCurrentChange"
                   :current-page="currentPage"
                   :page-sizes="[10, 50, 100, 200]"
                   :page-size="pageSize"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total">
    </el-pagination>
    <el-dialog :title='title' :visible.sync="dialogVisible" :close-on-click-modal="false" width="65%"> <el-form ref="form" :model="form" label-width="80px">
      <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 10px ;">
        <div style="font-family: Consolas; font-size:20px ;margin-bottom: 20px;">申请信息</div>
        <el-row>
          <el-col :span="12">
            <el-form-item label="角色申请编号:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.role_apply_id" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请角色:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.role_id" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="角色审批人:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.approver_emp" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色支持业务线:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.business_line" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>

      <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 20px;padding-top: 20px">
        <div style="font-family: Consolas; font-size:20px ;margin-bottom: 20px;">申请账号明细</div>
        <el-table ref="multipleTable" :data="tableDataAccount" border>
          <el-table-column prop="id"  v-if="false" label="隐藏账号id"></el-table-column>
          <el-table-column prop="accountName" label="申请账号" width="150"></el-table-column>
          <el-table-column prop="staffName" label="关联员工姓名"width="150"></el-table-column>
          <el-table-column prop="staffNum" label="关联员工编号"  ></el-table-column>
          <el-table-column prop="department" label="关联员工所属部门" width="200"></el-table-column>
          <el-table-column prop="applyOperation" label="申请操作" width="150" style="text-align: center">
          </el-table-column>
        </el-table>
      </div>

      <div>
        <div style="font-family: Consolas; font-size:20px ; margin-left:50px;margin-bottom: 20px;padding-top: 20px">其他信息</div>
        <el-row>
          <el-col :span="12">
            <el-form-item label="申请时间:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.apply_time" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请人:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.apply_staff_name" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="修改时间:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.modify_time" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="修改人:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.modify_emp" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="审批时间:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.approvalTime" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审批人:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.approver_emp" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="状态:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.statue" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>

      <el-row>
        <el-col style="text-align: center">
          <el-form-item>
            <el-button type="primary" @click="approvalPass" style="width:100px">审批通过</el-button>
            &nbsp&nbsp&nbsp&nbsp&nbsp
            <el-button type="primary" style="width:100px" @click=" rejectDialog = true">审批拒绝</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    </el-dialog>
    <el-dialog
      title="请输入拒绝的理由"
      :visible.sync="rejectDialog"
      width="30%">
      <span>
         <div style="margin-top: 15px;margin-bottom: 15px">
           <el-input
             type="textarea"
             :rows="4"
             placeholder="请输入内容"
             v-model="rejectReason">
           </el-input>
         </div>
      </span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="cancelReject">取 消</el-button>
    <el-button type="primary" @click="approvalReject">确 定</el-button>
  </span>
    </el-dialog>

    <el-dialog :title='titleRoleApplyInfo' :visible.sync="dialogVisibleRoleApplyInfo"  :close-on-click-modal="false" width="80%">
      <home>
        <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 20px ">
          <el-row :gutter="20">
            <el-col :span="5" style="color: #409EFF">
              申请信息
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
                  <el-input style="width:250px;"  v-model="formRoleInfo.roleName" :disabled="true"></el-input>
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
          </el-row>
        </div>
        <div style="margin-left: 40px;">
          <el-table ref="multipleTable" :data="tableDataAccount" border>
            <!--            <el-table-column type="selection" width="35"></el-table-column>-->
            <!--            <el-table-column prop="id" label="隐藏id"></el-table-column>-->
            <el-table-column prop="accountName" label="申请账号" width="150"></el-table-column>
            <el-table-column prop="staffName" label="关联员工姓名"width="150"></el-table-column>
            <el-table-column prop="staffNum" label="关联员工编号"  width="150"></el-table-column>
            <el-table-column prop="department" label="关联员工所属部门" width="200"></el-table-column>
            <el-table-column prop="applyOperation" label="申请操作" width="150" style="text-align: center">
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
                <el-form-item label="申请人登录账号">
                  <el-input style="width:300px;" v-model="otherInfo.applyAccountName" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="申请人员工编号">
                  <el-input style="width:300px;" v-model="otherInfo.applyStaffNum" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="申请人员工姓名">
                  <el-input style="width:300px;" v-model="otherInfo.applyStaffName" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="申请人所属部门">
                  <el-input style="width:300px;" v-model="otherInfo.applyDepartmentName" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="申请时间">
                  <el-input style="width:300px;" v-model="otherInfo.applyTime" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="申请状态">
                  <el-input style="width:300px;" v-model="otherInfo.applyStatus" :disabled="true"></el-input>
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
          </el-form>
          <div style="text-align: center">
            <el-button type="primary" @click="cancalShowApplyDetails" style="width:70px">取消</el-button>
          </div>
        </div>
        <br/>
      </home>
    </el-dialog>

    <el-dialog :title='titleDepartment' :visible.sync="dialogVisibleDepartment"  :close-on-click-modal="false" width="50%">
      <div>
        <span>选择要操作的部门</span>
        <br><br>
        <el-tree
          ref="tree"
          :props="defaultProps"
          node-key="id"
          :load="loadNode"
          lazy="true"
          check-strictly
          show-checkbox
          :render-content="renderContent"
          @check-change="handleClick">
        </el-tree>
        <br>
        <el-button type="primary" @click="getCheckedDepartment">确定</el-button>
      </div>
    </el-dialog>
  </home>
</template>
<script>
  import commonUtils from '../../common/commonUtils'

  export default {
    data() {
      return {
        titleDepartment:'选择部门',
        dialogVisibleDepartment:false,
        selectedNodes: [],
        checkStrictly: false,
        defaultProps: {
          label: 'departmentName',
          children: 'children',
          id: 'id',
          status: 'status'
        },
        total: 0,
        currentPage: 1,
        pageSize: 10,
        form: {
          roleApplyNum:'',
          roleId: '',
          roleName: '',
          applyAccountName:'',
          applyStaffNum:'',
          applyStaffName:'',
          applyDepartmentName:'',
          applyTime:'',
          modifyTime:'',
        },
        apply:{
          role_apply_id:'',
          role_id: '',
          approver_emp: '',
          business_line:'',
          apply_staff_num: '',
          apply_staff_name: '',
          apply_department_name: '',
          apply_time:'',
          operate:'',
          modify_emp:'',
          modify_time:'',
          approvalTime:'',
          statue:''
        },
        tableDataAccount:[],
        options: [{
          value: 1,
          label: '添加'
        }, {
          value: 2,
          label: '移除'
        }],

        tableData: [],
        selection: '',
        tableData: [],
        dialogVisibleRoleApplyInfo:false,
        titleRoleApplyInfo:'角色申请详情',
        radio:'',
        id:'',
        roleApplyNum: '',
        roleId: '',
        roleName: '',
        approverStaffName: '',
        BusinessLineEnum:{},
        businessLine: '',
        applyAccountName: '',
        applyStaffNum: '',
        applyStaffName: '',
        applyDepartmentName: '',
        applyTime: '',
        ApplyStatusEnum:{},
        applyStatus: '',
        modifyEmp: '',
        modifyTime: '',
        beginDateScope:'',
        dialogVisible:false,
        title:'角色申请审核',
        isApproval:true,
        rejectDialog:false,
        rejectReason:'',
        formRoleInfo: {//申请信息
          roleApplyNum: '',
          roleName: '',
          approverStaffName: '',
          businessLine: ''
        },
        applyId:'',
        otherInfo: {//其他信息
          applyAccountName: '',
          applyStaffNum: '',
          applyStaffName: '',
          applyDepartmentName: '',
          applyStatus: '',
          applyTime: '',
          modifyTime: '',
          modifyStaffName: '',
          approveTime: '',
          approverStaffName: '',
        },
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
    },
    created(){
      this.fetchData();
    },
    methods: {
      handleSizeChange(val) {
        this.pageSize = val;
        this.currentPage = 1;
        this.fetchData(1, val);
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.fetchData(val, this.pageSize);
      },
      handleSelectionChange(val) {
        this.selection = val;
      },
      fetchData() { //获取数据
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          roleApplyNum:self.form.roleApplyNum,
          roleId:self.form.roleId,
          roleName:self.form.roleName,
          applyAccountName: self.form.applyAccountName,
          applyStaffNum:self.form.applyStaffNum ,
          applyStaffName: self.form.applyStaffName,
          applyDepartmentName:self.form.applyDepartmentName,
          applyStatus: '2',
          applyTime:self.form.applyTime ,
          modifyTime: self.form.modifyTime,
          type:'角色审核',
          date : new Date().getTime(),
        };
        self.$http.get('roleApply/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          self.BusinessLineEnum = result.BusinessLineEnum;
          self.ApplyStatusEnum = result.applyStatusEnum;
        }).catch(function (error) {
          commonUtils.Log("roleApply/querylist.do_:"+error);
          self.$message.error("获取数据错误");
        });
      },
      approval(){
        this.dialogVisible=true;
      },
      approvalInfo(val){
        if(this.selection !=''){
          this.isApproval=false;
        }else{
          this.isApproval=true;
        }
        var param = {
          applyId: this.selection,
          date : new Date().getTime(),
        };
        this.$http.get('roleApply/showAccountListByApplyId.do_', {
          params: param
        }).then((result) => {

          //请求成功回调
          this.tableDataAccount = result.list;
          for(let i=0;i<this.tableDataAccount.length;i++){
            if(this.tableDataAccount[i].applyOperation===1){
              this.tableDataAccount[i].applyOperation='添加';
            }
            if(this.tableDataAccount[i].applyOperation===2){
              this.tableDataAccount[i].applyOperation='移除';
            }
          }

        }).catch(function (error) {
          //请求失败回调
          commonUtils.Log("roleApply/showAccountListByApplyId.do_:" + error);
          this.$message.error("获取数据错误");
        });

        this.apply.role_apply_id = val.roleApplyNum;
        this.apply.role_id = val.roleId;
        this.apply.approver_emp = val.approverStaffName;
        this.apply.business_line = val.businessLine;
        this.apply.apply_staff_num = val.applyStaffNum;
        this.apply.apply_department_name = val.applyDepartmentName;
        this.apply.apply_time = val.applyTime;
        this.apply.apply_staff_name =val.applyStaffName;
        this.apply.modify_emp = val.modifyEmp;
        this.apply.modify_time = val.modifyTime;
        this.apply.statue = this.ApplyStatusEnum[val.applyStatus];
      },
      approvalPass(){
        var self = this;
        var accountIds=[];
        var removeAccountIds=[];

        for (let i = 0; i < self.tableDataAccount.length; i++) {
          if(self.tableDataAccount[i].applyOperation ==='移除'){
            removeAccountIds.push(self.tableDataAccount[i].id)
          }else if(self.tableDataAccount[i].applyOperation ==='添加'){
            accountIds.push(self.tableDataAccount[i].id)
          }
        }
        console.log( accountIds);
        console.log( removeAccountIds);
        var param = {
          id:self.selection,
          accountIds:accountIds.toString(),
          removeAccountIds:removeAccountIds.toString(),
          roleId:self.apply.role_id,
          date : new Date().getTime(),
        };
        self.$http.get('roleApplyApproval/approvalPass.do_', {
          params: param
        }).then((result) => {
          self.$message({
              showClose: true,
              message: '审批通过成功',
              type: 'success'
            });
            self.dialogVisible=false;
            self.fetchData();
        }).catch(function (error) {
          commonUtils.Log("roleApplyApproval/approvalPass.do_:"+error);
          self.$message.error("审批通过失败");
        });
      },
      approvalReject(){
        var self = this;
        var param = {
          id:self.selection,
          rejectReason:self.rejectReason,
          date : new Date().getTime(),
        };
        self.$http.get('roleApplyApproval/approvalReject.do_', {
          params: param
        }).then((result) => {
            this.$message({
              showClose: true,
              message: '审批拒绝成功',
              type: 'success'
            });
          self.fetchData();
        }).catch(function (error) {
          commonUtils.Log("roleApplyApproval/approvalReject.do_:"+error);
          self.$message.error("拒绝失败");
        });
        self.rejectDialog=false;
        self.dialogVisible=false;
      },
      cancelReject(){
        this.rejectReason='';
        this.rejectDialog = false;
      },
      cellTrigger(val) {//角色详情页
        this.$router.push({path: '/RoleInf', query: {roleID: val,type:'审核'}});
      },
      roleApplyNumBtn(row){ //弹出框 角色申请详情
        this.dialogVisibleRoleApplyInfo=true;
        this.applyId= row.id;
        this.formRoleInfo.roleName = row.roleName;//角色名字
        this.formRoleInfo.roleApplyNum=row.roleApplyNum;//申请编号
        this.otherInfo.approverStaffName = row.approverStaffName;//审批人名字
        this.formRoleInfo.businessLine = row.businessLine;//业务线
        this.otherInfo.applyAccountName =row.applyAccountName;//申请人登录账号
        this.otherInfo.applyStaffNum=row.applyStaffNum;//员工编号
        this.otherInfo.applyStaffName=row.applyStaffName;//员工姓名
        this.otherInfo.applyDepartmentName=row.applyDepartmentName;//部门
        this.otherInfo.applyTime = row.applyTime;//申请时间
        this.otherInfo.modifyTime = row.modifyTime;//修改时间
        this.otherInfo.modifyStaffName =row.modifyEmp;//修改人员工姓名
        this.otherInfo.approveTime=row.approveTime;//审批时间
        this.formRoleInfo.approverStaffName = row.approverStaffName;//审批人
        switch (row.applyStatus) {
          case 1: this.otherInfo.applyStatus='已新建'; break;
          case 2: this.otherInfo.applyStatus='待审批'; break;
          case 3: this.otherInfo.applyStatus='审批通过';break;
          case 4: this.otherInfo.applyStatus='审批拒绝'; break;
          case 5: this.otherInfo.applyStatus='已删除'; break;
          default: this.otherInfo.applyStatus=null;
        }
        this.showAccountListByApplyId();//查询改申请编号 包含的账号列表
      },
      cancalShowApplyDetails(){
        this.dialogVisibleRoleApplyInfo=false;
      },
      showAccountListByApplyId() {
        var self = this;
        var param = {
          applyId: self.applyId,
          date : new Date().getTime(),
        };
        self.$http.get('roleApply/showAccountListByApplyId.do_', {
          params: param
        }).then((result) => {
          //请求成功回调
          self.tableDataAccount = result.list;
          for(let i=0;i<self.tableDataAccount.length;i++){
            if(self.tableDataAccount[i].applyOperation===1){
              self.tableDataAccount[i].applyOperation='添加';
            }
            if(self.tableDataAccount[i].applyOperation===2){
              self.tableDataAccount[i].applyOperation='移除';
            }
          }

        }).catch(function (error) {
          //请求失败回调
          commonUtils.Log("roleApply/showAccountListByApplyId.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },
      loadNode(node,resolve){
        var self = this;
        self.$http.get('department/buildTree.do_', {
          params: null
        }).then((result) => {
          resolve([result.departmentDto]);
        }).catch(function (error) {

        });
      },
      renderContent(h, { node, data, store }) {
        // 这里编译器有红色波浪线不影响运行...
        if(data.status != 1){
          return (
            <span style="color:red">{node.label}</span>
        );
        }else{
          return (
            <span>{node.label}</span>
        );
        }
      },
      handleClick(data,checked,node){
        // 手动设置单选
        if(checked === true) {
          this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
          // 设置按钮是否可选（选中节点后调用两次handleClick，第一次checked为true，所以设置按钮写在这）
          if(data.status === 1){
            this.operationBtnActive=false;
          }else{
            this.operationBtnActive=true;
          }
        } else {
          if (this.checkedId == data.id) {
            this.$refs.tree.setCheckedKeys([data.id]);
          }
        }
      },
      selectDepartment(){//选择部门
        this.dialogVisibleDepartment=true;
      },
      clearDepartment(){//清除部门的值
        this.form.applyDepartmentName='';
      },
      getCheckedDepartment() {
        // 获取部门 回填到文本框中
        // alert(this.$refs.tree.getCheckedNodes()[0].departmentName);
        this.form.applyDepartmentName=this.$refs.tree.getCheckedNodes()[0].departmentName;
        this.dialogVisibleDepartment=false;
      },
    }
  }
</script>

<style scoped>

</style>
