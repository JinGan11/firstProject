<template>
  <div>
    <el-form>
      <el-form-item label="员工编号">
        <span>{{userInfo.accountId}}</span>
      </el-form-item>
      <el-form-item label="姓名">
        <span>{{userInfo.accountName}}</span>
      </el-form-item>
      <el-form-item label="性别">
        <span>{{userInfo.sex}}</span>
      </el-form-item>
      <el-form-item label="手机号">
        <span>{{userInfo.userName}}</span>
      </el-form-item>
      <el-form-item label="邮箱">
        <span>{{userInfo.userName}}</span>
      </el-form-item>
      <el-form-item label="归属部门">
        <span>{{userInfo.userName}}</span>
      </el-form-item>
      <el-form-item label="是否离职">
        <span>{{userInfo.userName}}</span>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
  export default {
     data() {
       return {
         userInfo: {}
       }
     },
    mounted: {
       // this.getUserInfo()
    },
    methods: {
       getUserInfo () {
         const self = this
         self.$http.post('user/getUserInfo', param)
           .then(result => {
             if (result.code === 200) {
               self.userInfo = result.list[0]
             } else {
               alert("密码修改失败！")
               // self.$router.replace("/index")
             }
           })
           .catch(result => {
             alert("请联系管理员！")
           })
       }
    }
  }
</script>

<style scoped>

</style>
