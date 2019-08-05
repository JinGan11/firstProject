<template>
  <div>
    <div style="margin-bottom: 10px">
      <p>角色信息</p>
    </div>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="roleForm" :model="roleForm" label-width="110px">
        <el-row>
          <el-col :span="10">
            <el-form-item label="角色ID">
              <el-input style="width:200px;" v-model="roleForm.roleID"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="角色名称">
              <el-input style="width:200px;" v-model="roleForm.roleName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批人账号">
              <el-input style="width:200px;" v-model="roleForm.accountID"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人员工编号">
              <el-input style="width:200px;" v-model="roleForm.staffID"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批人姓名">
              <el-input style="width:200px;" v-model="roleForm.staffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人所属部门">
              <el-input style="width:200px;" v-model="roleForm.departmentID"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>

        </el-row>
      </el-form>
    </div>
  <div>
    <p>其它信息</p>
    <div>
      <div style="width:85%; margin-left: 70px">
        <el-form ref="form" :model="form" label-width="110px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="新建人">
                <el-input style="width:200px;" v-model="form.createEmp"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="新建时间">
                <el-input style="width:200px;" v-model="form.createTime"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="修改人">
                <el-input style="width:200px;" v-model="form.modifyEmp"></el-input>
              </el-form-item>
            </el-col>
              <el-col :span="10">
                <el-form-item label="修改时间">
                  <el-input style="width:200px;" v-model="form.modifyTime"></el-input>
                </el-form-item>
              </el-col>
          </el-row>
          <el-row>
            <el-col :span="1">
              <el-form-item label="状态">
                <el-input style="width:200px;" v-model="form.roleStatus"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
  </div>
  <div>
      <el-button type="primary" @click="save" style="width:70px">保存</el-button>
      <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
  </div>
  </div>


</template>

<script>
  import commonUtils from '../../common/commonUtils'

  export default {
    data() {
      return {

        form: {
          createEmp:'',
          createTime:'',
          modifyEmp:'',
          modifyTime:'',
          roleStatus:'有效',
        },
        roleForm:{
          roleID:'',
          roleName:'',
          accountID:'',
          staffID:'',
          staffName:'',
          departmentID:'',
        },
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
    },
    methods: {
      save() {//保存新建角色信息

      },
      cancel(){//关闭新建角色页面，返回角色管理列表页面
        this.$router.replace('/roleManagement/roleManagement')
      },

    },



  }


</script>

<style scoped>

</style>
