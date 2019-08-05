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
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px">
      <el-button type="primary" @click="selectRole"  style="width:100px">确认选择</el-button>
      <el-button type="primary" @click="cancelSelectRole" style="width:100px">取消</el-button>
    </div>

    <el-table ref="multipleTable" :data="tableData" border  @row-click="openDetails"  @selection-change="handleSelectionChange" >
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
        id:1,
        currentPage: 1,
        pageSize: 10,
        form: {
          name: '',
        },
        tableData: [],
        selection:'',
        roleId:'',
        roleName:'',
        accountNum:'',
        staffNum:'',
        staffName:'',
        departmentName:'',
        roleStatus:'',
        description:'',
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
          commonUtils.Log("roleManage/querylist.do_:"+error);
          self.$message.error("获取数据错误");
        });
      },
      cancelSelectRole(){
        //点击取消按钮，跳回角色申请新建页面
        this.$router.replace('/CreateRoleApply')
      },
      selectRole(row){
        //选择角色

        sessionStorage.setItem('id', JSON.stringify(this.$id));
        this.$router.push('/CreateRoleApply')

        // console.log(this.id)
        // let params={
        //   id:this.id
        // }
        // this.$router.push({
        //   path:'/CreateRoleApply',
        //   params:params
        // })

      },
      openDetails(row) {
        var roleName=row.roleId;
        var roleName=row.roleName;
        var staffName=row.staffName;
      },
    }
  }
</script>

<style scoped>


</style>
