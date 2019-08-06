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
      <el-button type="primary" @click="approval" style="width:100px">审核</el-button>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id"><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="roleApplyNum" label="角色申请编号" width="150"></el-table-column>
      <el-table-column prop="roleId" label="申请角色ID"width="150"></el-table-column>
      <el-table-column prop="roleName" label="申请角色名称"  width="150"></el-table-column>
      <el-table-column prop="approverStaffName" label="审批负责人" width="150"></el-table-column>
      <el-table-column prop="businessLine" label="角色支持业务线" width="150" style="text-align: center">
        <template slot-scope="scope">
          {{BusinessLineEnum[scope.row.businessLine]}}
        </template>
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
    <el-dialog :title='title' :visible.sync="dialogVisible" :close-on-click-modal="false" width="900px"> <el-form ref="form" :model="form" label-width="80px">
      <div>
        <div style="font-family: Consolas; font-size:20px ; margin-left:50px;margin-bottom: 20px;">申请信息</div>
        <el-row>
          <el-col :span="12">
            <el-form-item label="角色申请编号:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.role_apply_id"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请角色:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.role_id"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="角色审批人:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.approver_emp"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色支持业务线:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.business_line"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>

      <div>
        <div style="font-family: Consolas; font-size:20px ; margin-left:50px;margin-bottom: 20px;">申请账号明细</div>
        <el-table
          style="width: 85%;margin-left: 40px">
          <el-table-column
            prop="apply.apply_account_name"
            label="申请账号"
            width="180">
          </el-table-column>
          <el-table-column
            prop="apply.apply_staff_name"
            label="关联员工姓名"
            width="150">
          </el-table-column>
          <el-table-column
            prop="apply.apply_staff_num"
            label="关联员工编号"
            width="150">
          </el-table-column>
          <el-table-column
            prop="apply.apply_department_name"
            label="关联员工所属部门"
            width="150">
          </el-table-column>
          <el-table-column
            prop="apply.operate"
            label="申请操作">
          </el-table-column>
        </el-table>
      </div>

      <div>
        <div style="font-family: Consolas; font-size:20px ; margin-left:50px;margin-bottom: 20px;">其他信息</div>
        <el-row>
          <el-col :span="12">
            <el-form-item label="申请时间:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.apply_time"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请人:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.apply_staff_name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="修改时间:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.modify_time"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="修改人:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.modify_emp"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="审批时间:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.approvalTime"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审批人:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.approver_emp"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="状态:" label-width="150px">
              <el-input style="width:200px;" v-model="apply.statue"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>

      <el-row>
        <el-col style="text-align: center">
          <el-form-item>
            <el-button type="primary" @click="" style="width:100px">审批通过</el-button>
            &nbsp&nbsp&nbsp&nbsp&nbsp
            <el-button type="primary" style="width:100px" @click="">审批拒绝</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    </el-dialog>
  </home>
</template>
<script>
  import commonUtils from '../../common/commonUtils'

  export default {
    data() {
      return {
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
        tableData: [],
        selection: '',
        tableData: [],
        selection: [],
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
          type:'角色审核'
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
      }
    }
  }
</script>

<style scoped>

</style>
