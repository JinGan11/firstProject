<template>
  <div>
    <el-form>
      <el-form-item label="员工编号">
        <span>{{StaffInfo.staffNum}}</span>
      </el-form-item>
      <el-form-item label="姓名">
        <span>{{StaffInfo.staffName}}</span>
      </el-form-item>
      <el-form-item label="性别">
        <span>{{StaffInfo.staffSex}}</span>
      </el-form-item>
      <el-form-item label="手机号">
        <span>{{StaffInfo.staffTelephone}}</span>
      </el-form-item>
      <el-form-item label="邮箱">
        <span>{{StaffInfo.staffEmail}}</span>
      </el-form-item>
      <el-form-item label="归属部门">
        <span>{{StaffInfo.departmentName}}</span>
      </el-form-item>
      <el-form-item label="是否离职">
        <span>{{StaffInfo.isDimission}}</span>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
  export default {
     data() {
       return {
         StaffInfo: {}
       }
     },
    created() {
      this.getUserInfo()
    },
    mounted() {
    },
    methods: {
       getUserInfo () {
         const self = this;
         var param = {
           accountName: window.sessionStorage.getItem("loginUsername")
         }
         self.$http.post('user/getEmpInfo', param)
           .then(result => {
             if (result.code === 200) {
               console.log(result);
               self.StaffInfo = result.list[0]
             } else {
               self.$alert("查询失败，请稍后再试！", '消息提醒', {
                 confirmButtonText: '确定',
               });
             }
           })
           .catch(function (error) {
             commonUtils.Log("user/updatePwd:" + error);
             self.$message.error("系统故障，请联系管理员！");
           });
       }
    }
  }
</script>

<style scoped>

</style>
