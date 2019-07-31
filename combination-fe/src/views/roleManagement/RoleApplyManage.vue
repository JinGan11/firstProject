<template>
  <home>
    <div style="width:96%; margin-left: 40px">
      <el-form ref="form" :model="form" label-width="110px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="角色申请编号">
              <el-input style="width:150px;" v-model="form.apply_id"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请角色ID">
              <el-input style="width:150px;" v-model="form.role_id"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请角色名称">
              <el-input style="width:150px;" v-model="form.role_name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请人登录账号">
              <el-input style="width:150px;" v-model="form.account"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="申请人员工编号">
              <el-input style="width:150px;" v-model="form.employeeNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请人员工姓名">
              <el-input style="width:150px;" v-model="form.name">
              </el-input>

            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请人所属部门">
              <el-input style="width:150px;" v-model="form.accountNo"></el-input>

            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态">
              <el-select v-model="value" clearable  style="width:150px;" placeholder="请选择">
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
        <el-row>
          <el-col :span="100">
            <el-form-item label="申请时间">
              <el-date-picker
                v-model="beginDateScope"
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
                v-model="beginDateScope"
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
      </el-form>
    </div>
    <div style="margin-bottom: 10px; margin-top:20px; margin-left:40px">
      <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
      <el-button type="primary" @click="" style="width:70px">新建</el-button>
      <el-button type="primary" @click="" style="width:70px">修改</el-button>
      <el-button type="primary" @click="" style="width:70px">删除</el-button>
      <el-button type="primary" @click="" style="width:70px">提交审核</el-button>
      <el-button type="primary" style="width:100px" @click="">导出</el-button>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange" >
      <el-table-column type="selection" width="35"></el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="role_apply_id" label="角色申请编号" width="150"></el-table-column>
      <el-table-column prop="role_id" label="申请角色ID"width="150"></el-table-column>
      <el-table-column prop="role_name" label="申请角色名称"  width="150"></el-table-column>
      <el-table-column prop="approver_emp" label="审批负责人" width="150"></el-table-column>
      <el-table-column prop="business_line" label="角色支持业务线" width="150"></el-table-column>
      <el-table-column prop="apply_account_name" label="申请人登录账号" width="150"></el-table-column>
      <el-table-column prop="apply_staff_num" label="申请人员工编号" width="150"></el-table-column>
      <el-table-column prop="apply_staff_name" label="申请人员工姓名" width="150"></el-table-column>
      <el-table-column prop="apply_department_name" label="申请人所属部门" width="150"></el-table-column>
      <el-table-column prop="apply_time" label="申请时间"width="150"></el-table-column>
      <el-table-column prop="status" label="状态" width="150"></el-table-column>
      <el-table-column prop="modify_emp" label="操作人" width="150"></el-table-column>
      <el-table-column prop="modify_time" label="操作时间" width="150"></el-table-column>
      <el-table-column prop="reject_reason" label="拒绝原因" width="150"></el-table-column>
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
          apply_id: '',
          role_id: '',
          role_name: '',
        },
        tableData: [],
        selection: [],
        role_apply_id: '',
        role_id: '',
        role_name: '',
        approver_emp: '',
        business_line: '',
        apply_account_name: '',
        apply_staff_num: '',
        apply_staff_name: '',
        apply_department_name: '',
        apply_time: '',
        status: '',
        modify_emp: '',
        modify_time: '',
        reject_reason: '',
        value:'',
        beginDateScope:'',
        options: [{
          value: '选项1',
          label: '全部'
        }, {
          value: '选项2',
          label: '已新建'
        }, {
          value: '选项3',
          label: '待审批'
        }, {
          value: '选项4',
          label: '审批通过'
        }, {
          value: '选项5',
          label: '审批拒绝'
        },{
          value:'选项6',
          label:'已删除'
        }]
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
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
        };
        self.$http.get('roleApply/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
        }).catch(function (error) {
          commonUtils.Log("roleApply/querylist.do_:"+error);
          self.$message.error("获取数据错误");
        });
      },
    }
  }
</script>

<style scoped>

</style>
