<template>
  <home>
    <el-form :inline="true" label-width="100px" :model="formInline" class="demo-form-inline">
      <el-form-item label="部门名称">
        <el-input v-model="formInline.departmentName"></el-input>
      </el-form-item>
      <el-form-item label="负责人ID">
        <el-input v-model="formInline.departmentStaffId"></el-input>
      </el-form-item>
      <el-form-item label="负责人姓名">
        <el-input v-model="formInline.departmentStaffName"></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="formInline.departmentPhone"></el-input>
      </el-form-item>
      <el-form-item label="部门所在城市">
        <el-input v-model="formInline.departmentCity"></el-input>
      </el-form-item>
      <el-form-item label="部门级别">
        <el-input v-model="formInline.departmentLevel"></el-input>
      </el-form-item>
      <el-form-item label="上级部门">
        <el-input v-model="formInline.departmentUpper"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-input v-model="formInline.departmentStatus"></el-input>
      </el-form-item>
      <el-form-item label="部门类型">
        <el-select v-model="formInline.departmentType" value="type1">
          <el-option label="type1" value="type11"></el-option>
          <el-option label="type2" value="type22"></el-option>
        </el-select>
      </el-form-item><br>
      <el-form-item size="100px">
        <el-button type="primary" @click="Search">查询</el-button>
        <el-button  @click="OutTable">导出</el-button>
      </el-form-item>
    </el-form>
  </home>
</template>
<script>
  export default {
    data(){
      return{
        formInline:{
          departmentName:'',
          departmentStaffId:'',
          departmentStaffName:'',
          departmentPhone:'',
          departmentCity:'',
          departmentLevel:'',
          departmentUpper:'',
          departmentStatus:'',
          departmentType:''
        }
      }
    },
    methods:{
      Search(){

      },
      OutTable(){

      }
    }
  }
</script>
