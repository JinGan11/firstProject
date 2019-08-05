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
          <el-col :span ="3">
            <el-form-item>
              <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
            </el-form-item>
          </el-col>
          <el-col :span ="6">
            <el-form-item>
              <el-button type="primary" style="width:100px" @click="">导出</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px">
      <el-button type="primary" @click="createRole" style="width:100px">新建</el-button>
      <el-button type="primary" @click="modifyRole" style="width:100px">修改</el-button>
      <el-button type="primary" @click="deletRole" style="width:100px">删除</el-button>
      <el-button type="primary" @click="" style="width:100px">添加账号</el-button>
      <el-button type="primary" @click="" style="width:100px">分配权限</el-button>
    </div>

    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange" >
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.roleId" ><span width="0px;"></span></el-radio>
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
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   :current-page="currentPage"
                   :page-sizes="[10, 50, 100, 200]"
                   :page-size="pageSize"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total">
    </el-pagination>
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
          name: '',
        },
        tableData: [],
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
        self.$http.get('roleManage/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
        }).catch(function (error) {
          commonUtils.Log("roleManage/querylist.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },
      createRole() {
        //点击新建按钮，进入新建角色界面
        this.$router.replace('/CreateRole')
      },
      modifyRole() {
        //点击修改按钮，进入修改角色页面
        this.$router.replace('/ModifyRole')
      },
      deletRole() {//删除角色信息
        this.$confirm('此操作将删除该角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var self = this;
          var param = {
            selection:self.selection,
          };
          self.$http.get('roleManage/updateStatus.do_', {
            params: param
          }).then(() => {
            self.$message.success("成功删除");
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
      }
    }
  }
</script>

<style scoped>


</style>
