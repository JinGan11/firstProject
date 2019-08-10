<template>
  <home>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="角色名称">
              <el-input placeholder="角色名称" style="width:200px;" v-model="form.roleName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="支持业务线">
              <el-select placeholder="请选择" style="width:200px;"v-model="form.businessLine" value="0">
                <el-option label="买买车" value="0"></el-option>
                <el-option label="闪贷" value="1"></el-option>
                <el-option label="租车" value="2"></el-option>
                <el-option label="专车" value="3"></el-option>
                <el-option label="保险" value="4"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="审批人账号">
              <el-input placeholder="审批人账号" style="width:200px;" v-model="form.approverAccount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="审批人员工编号">
              <el-input placeholder="审批人员工编号" style="width:200px;" v-model="form.approverStaffNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="审批人姓名">
              <el-input placeholder="审批人姓名" style="width:200px;" v-model="form.approverName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="审批人所属部门">
              <el-input style="width:80px;" v-model="form.approverDepartmentName"></el-input>
              <el-button type="text" @click="selectDepartment">选择</el-button>
              <el-button type="text" @click="clearDepartment">清空</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="角色状态">
              <el-select placeholder="请选择" v-model="form.roleStatus" value="0">
                <el-option label="有效" value="0"></el-option>
                <el-option label="无效" value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="权限名称">
              <el-input placeholder="权限名称" :disabled="true" style="width:200px;" v-model="form.powerName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <div v-if="!buttonDisabled">
                <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
                <el-button type="primary" style="width:100px" @click="">导出</el-button>
              </div>
              <div v-else>
                <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px" v-if="!buttonDisabled">
    </div>
    <div style="margin-bottom: 10px" v-else>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="roleId" label="角色ID" width="120"></el-table-column>
      <el-table-column prop="roleName" label="角色名称" width="150"></el-table-column>
      <el-table-column prop="businessLine" label="支持业务线" width="150"></el-table-column>
      <el-table-column prop="approverAccount" label="审批人账号" width="150"></el-table-column>
      <el-table-column prop="approverStaffNum" label="审批人员工编号" width="150"></el-table-column>
      <el-table-column prop="approverName" label="审批人姓名" width="150"></el-table-column>
      <el-table-column prop="approverDepartmentName" label="审批人所属部门" width="150"></el-table-column>
      <el-table-column prop="roleState" label="角色状态" width="150"></el-table-column>
      <el-table-column prop="powerName" label="权限名称" width="150"></el-table-column>
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
        total: 0,
        currentPage: 1,
        pageSize: 10,
        form: {
          roleId:'',
          roleName:'',
          businessLine:'',
          approverAccount:'',
          approverStaffNum:'',
          approverName:'',
          approverDepartmentName:'',
          roleState:'',
          powerName:''
        },
        defaultProps: {
          label: 'departmentName',
          children: 'children',
          id: 'id',
          status: 'status'
        },
        tableData: [],
        dialogVisibleDepartment:false,
        buttonDisabled: false,
        titleDepartment:'选择部门',
      }
    },
    activated() {
      commonUtils.Log("页面激活");
     },
     mounted() {
      commonUtils.Log("页面进来");
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
        /*var self = this;
        var param = {
          //   page: self.currentPage,
          //   limit: self.pageSize,
          roleName: self.form.roleName,
          businessLine:self.form.businessLine,
          accountName:self.form.accountName,
          staffNum: self.form.staffNum,
          staffName:self.form.staffName,
          roleStatus:self.form.roleStatus,
          accountState:self.form.accountState,
          departmentName: self.form.departmentName,
          //   isDimission: (self.form.isDimission === '2') ? '' : self.form.isDimission,
          //   accountId: self.form.accountId,
        };
        self.$http.get('roleAccount/getRoleAccountList.do',{params: param}).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          // self.SexEnum = result.SexEnum;
          // self.isDimissionEnum = result.isDismissionEnum;
          // self.staffDtoList = result.staffDtoList;
        }).catch(function (error) {
          commonUtils.Log("employee/querylist.do_:" + error);
          self.$message.error("获取数据错误");
        });*/
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
      getCheckedDepartment() {
        // 获取部门 回填到文本框中
        // alert(this.$refs.tree.getCheckedNodes()[0].departmentName);
        this.form.approverDepartmentName=this.$refs.tree.getCheckedNodes()[0].departmentName;
        this.dialogVisibleDepartment=false;
      },
      selectDepartment(){//选择部门
        this.dialogVisibleDepartment=true;
      },
      clearDepartment(){//清除部门的值
        this.form.applyDepartmentName='';
      },
    }
  }
</script>

<style scoped>


</style>

