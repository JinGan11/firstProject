<template>
  <home>
    <div style="width:85%; margin-left: 7px">
      <el-form ref="form" :model="form" label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="角色名称">
              <el-input style="width:200px;" v-model="form.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item>
              <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item>
              <el-button type="primary" style="width:100px" @click="exportRole">导出</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px">
      <el-button type="primary" @click="createRole" style="width:100px">新建</el-button>
      <el-button type="primary" @click="modifyRole(selection)" :disabled="isModify" style="width:100px">修改</el-button>
      <el-button type="primary" @click="deleteRole" :disabled="isModify" style="width:100px">删除</el-button>
      <el-button type="primary" @click="" style="width:100px">添加账号</el-button>
      <el-button type="primary" @click="roleAssignPermission" style="width:100px">分配权限</el-button>
    </div>

    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.roleId" @change="selectionActive(scope.row)"><span
            width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="roleId" label="角色ID" width="150">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="cellTrigger(scope.row.roleId)">{{scope.row.roleId}}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="roleName" label="角色名称" width="150"></el-table-column>
      <el-table-column prop="accountNum" label="审批人账号" width="120"></el-table-column>
      <el-table-column prop="staffNum" label="审批人员工编号"></el-table-column>
      <el-table-column prop="staffName" label="审批人姓名" width="200"></el-table-column>
      <el-table-column prop="departmentName" label="审批人所属部门" width="120"></el-table-column>
      <el-table-column prop="roleStatus" label="状态" width="100" style="text-align: center">
        <template slot-scope="scope">
          {{RoleStatusEnum[scope.row.roleStatus]}}
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" width="120"></el-table-column>
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
    <el-dialog title='excelTitle' :visible.sync="exportDialogVisible" :close-on-click-modal="false" width="600px">
      <template>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="checkRoles" @change="handlecheckRolesChange">
          <el-checkbox v-for="city in roles" :label="city" :key="city">{{city}}</el-checkbox>
        </el-checkbox-group>
      </template>
      <template slot="footer">
        <el-button type="primary" @click="exportExcel">确定导出</el-button>
        <el-button @click="cancel">取 消</el-button>
      </template>
    </el-dialog>
  </home>
</template>

<script>
  import commonUtils from '../../common/commonUtils'
  const rolesOptions = ['角色ID', '角色名称', '审批人账号', '审批人员编号', '审批人姓名', '审批人所属部门', '状态', '描述'];
  export default {
    data() {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        form: {
          name: '',
        },
        tableData: [],
        RoleStatusEnum: {},
        selection: '',
        id: '',
        roleId: '',
        roleName: '',
        accountNum: '',
        staffNum: '',
        staffName: '',
        departmentName: '',
        roleStatus: '',
        description: '',
        isModify: true,

         exportDialogVisible: false,
         checkAll: false,
        checkRoles: [],
        roles:rolesOptions,
        isIndeterminate: true,
         filterVal: [],
         list: [],
         disabled: true,
        roleDtoList:[],
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
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
          roleName: self.form.name,
        };
        self.$http.get("roleManage/querylist.do_", {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          self.RoleStatusEnum = result.RoleStatusEnum;
          self.roleDtoList=result.roleList;
          //self.form.name="dsf";
        }).catch(function (error) {
          commonUtils.Log("roleManage/querylist.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },
      createRole() {
        //点击新建按钮，进入新建角色界面
        this.$router.replace('/CreateRole');
      },
      modifyRole(val) {
        //点击修改按钮，进入修改角色弹窗
        //this.dialogVisible=true;
        this.$router.push({path: '/ModifyRole', query: {roleID: val}});
      },
      deleteRole() {//删除角色信息
        this.$confirm('此操作将删除该角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var self = this;
          var param = {
            selection: self.selection,
          };
          self.$http.get('roleManage/updateStatus.do_', {
            params: param
          }).then(() => {
            self.$router.replace("/roleManagement/roleManagement");
          }).catch(function (error) {
            commonUtils.Log("roleManage/updateStatus.do_:" + error);
            self.$message.error("获取数据错误");
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      selectionActive(val) {
        this.isModify = false;
      },
      cellTrigger(val){//角色详情页
        this.$router.push({path: '/RoleInf', query: {roleID: val}});
      },
      roleAssignPermission() {
        this.$router.replace("/roleManagement/RoleAssignPermission")
      },


      exportExcel() {
        if (this.checkRoles.length === 0) {
          this.$message({
            showClose: false,
            message: '请选择需要导出的字段',
            type: 'error'
          });
        } else {
          require.ensure([], () => {
            const {export_json_to_excel} = require('../../excel/Export2Excel');
            const tHeader = this.checkRoles;
            // 上面设置Excel的表格第一行的标题

            const filterVal = this.exportField(this.checkRoles);
            // 上面的staffNum、accountId、staffName是tableData里对象的属性
            const list = this.roleDtoList;  //把data里的tableData存到list
            for (let i = 0; i < list.length; i++) {
              list[i].roleStatus=this.RoleStatusEnum[list[i].roleStatus];
            }
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '员工管理列表excel');
            this.$message({
              showClose: true,
              message: '文件导出成功',
              type: 'success'
            });
            this.exportDialogVisible = false;
            this.checkRoles = [];
            this.filterVal = [];
          })
        }
      },
      cancel() {
        this.exportDialogVisible = false;
      },
      exportRole() {
        this.exportDialogVisible = true;
      },
      exportField(val) {
        for (let i = 0; i < val.length; i++) {
          if (this.checkRoles[i] === '角色ID') {
            this.filterVal.push('roleId')
          } else if (this.checkRoles[i] === '角色名称') {
            this.filterVal.push('roleName')
          } else if (this.checkRoles[i] === '审批人账号') {
            this.filterVal.push('accountNum')
          } else if (this.checkRoles[i] === '审批人员编号') {
            this.filterVal.push('staffNum')
          } else if (this.checkRoles[i] === '审批人姓名') {
            this.filterVal.push('staffName')
          } else if (this.checkRoles[i] === '审批人所属部门') {
            this.filterVal.push('departmentName')
          } else if (this.checkRoles[i] === '状态') {
            this.filterVal.push('roleStatus')
          } else if (this.checkRoles[i] === '描述') {
            this.filterVal.push('description')
          }
        }
        return this.filterVal;
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      handleCheckAllChange(val) {
        this.checkRoles = val ? rolesOptions : [];
        this.isIndeterminate = false;
      },
      handlecheckRolesChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.roles.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.roles.length;
      },
    }
  }
</script>

<style scoped>


</style>
