<template>
  <home>
    <div style="width: 95%;">
      <el-form ref="form" :model="form" label-width="100px">
        <el-row>
          <el-col :span="5">
            <el-form-item label="登陆账号" >
              <el-input style="width:140px;" v-model="form.accountNo" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工编号">
              <el-input style="width:160px;" v-model="form.staffNo" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工姓名">
              <el-input style="width:180px;" v-model="form.name" clearable></el-input>
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
              <el-input style="width:180px;" v-model="form.departmentId"></el-input>
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
              <el-button type="primary" style="width:100px" @click="">导出</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px">
      <el-button type="primary" @click="creatAccount" style="width:70px">新建</el-button>
      <el-button type="primary" @click="" style="width:70px">修改</el-button>
      <el-button type="primary" @click="" style="width:70px">删除</el-button>
      <el-button type="primary" @click="" style="width:70px">冻结</el-button>
      <el-button type="primary" @click="" style="width:70px">解冻</el-button>
      <el-button type="primary" @click="" style="width:80px">密码重置</el-button>
      <el-button type="primary" @click="assignPermission" style="width:80px">分配权限</el-button>
      <el-button type="primary" @click="" style="width:80px">历史记录</el-button>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange" >
<!--      <el-table-column type="selection" width="35"></el-table-column>-->
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id"><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
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
          accountNo: null,
          staffNo: null,
          name: null,
          permissionsList:[],
          permissionsEnum:{},
          accountStatusList:[],
          accountStatusEnum:{},
          permissions: null,
          departmentId:null,
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
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
    },
    methods: {
      handleCheckChange () {
        var a = '1';
      },
      handleNodeClick(data) {
        console.log(data);
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
      handleSelectionChange(val) {
        this.selection = val;
      },
      fetchData(){
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
      creatAccount(){ //新建账户
        this.$router.replace('/createAccount')
      },
      assignPermission() {
        this.$router.replace('/accountManagement/AssignPermission')
      }
    },
    created() {
      const self = this;
      self.$http.get('account/querylist.do_').then((result) => {
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
  }
</script>

<style scoped>


</style>

