<template>
<home>
  <h1>哈罗word</h1>
  <el-button type="primary" @click="fetchData">查询</el-button>

  <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
    <el-table-column prop="id" label="id"></el-table-column>
    <el-table-column prop="department_no" label="部门编号"></el-table-column>
    <el-table-column prop="department_name" label="部门名称"></el-table-column>
    <el-table-column prop="upper_department_no" label="上级部门编号"></el-table-column>
  </el-table>

</home>
</template>

<script>
  export default {
    data() {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        form: {
          id: '',
          department_no: '',
          department_name: '',
          upper_department_no: ''
        },
        tableData: [],
        selection:[],
        id: '',
        department_no: '',
        department_name: '',
        upper_department_no: ''
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

        };
        self.$http.get('department/querylist.do_', {
          params: null
        }).then((result) => {
          self.tableData = result.departments;
        }).catch(function (error) {
          commonUtils.Log("department/querylist.do_:"+error);
          self.$message.error("获取数据错误");
        });
      },
    }
  }
</script>

<style scoped>


</style>
