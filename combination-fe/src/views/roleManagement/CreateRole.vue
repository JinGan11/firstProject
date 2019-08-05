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
            <el-col :span="10">
              <el-form-item label="支持业务线">
                  <template v-for="item in chks">
                    <input type="checkbox" name="hobby" :value="item.id"
                         :checked="loopsss.indexOf(item.id) > -1"/>{{item.name}}
                  </template>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="描述">
              <el-input style="width:200px;" v-model="roleForm.description"></el-input>
            </el-form-item>
          </el-col>
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
  <div style="text-align: center">
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

        chks: [
          {id:1,name:"买买车"},
          {id:2,name:"租车"},
          {id:3,name:"闪贷"},
          {id:4,name:"专车"},
          {id:5,name:"保险"}
        ],
        loopsss:'',

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
          businessLine:'',
          description:'',
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
          this.$confirm('此操作将保存该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$message({
              type: 'success',
              message: '保存成功!'
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消保存'
            });
          });
      },
      cancel(){//关闭新建角色页面，返回角色管理列表页面
        this.$router.replace('/roleManagement/roleManagement')
      },

    },



  }


</script>

<style scoped>

</style>
