<template>
  <home>
    <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 20px ">
      <el-row :gutter="20">
        <el-col :span="5" style="color: #409EFF">
           申请信息
        </el-col>
        <el-col :span="2">
          <el-button type="primary" size="mini" @click="">保存</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" size="mini" @click="">保存并提交</el-button>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" size="mini" @click="">取消</el-button>
        </el-col>
      </el-row>
    </div>

    <div style="margin-top: 20px;margin-left: 40px;margin-bottom:20px;">
      <br>
      <el-form ref="form" :model="form" label-width="110px">
        <el-row>
          <el-col :span="10">
            <el-form-item label="角色申请编号">
              <el-input style="width:300px;" v-model="form.roleApplyNum" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="申请角色">
              <el-input style="width:250px;"  placeholder="请选择角色" v-model="form.roleName" :disabled="true"></el-input>
              <el-button type="text" @click="selectRoleForRoleApply">选择</el-button>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="角色审批人">
              <el-input style="width:300px;" v-model="form.approverStaffName" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="角色支持业务线">
              <el-input style="width:300px;" v-model="form.businessLine" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-left: 40px;margin-bottom:20px;border-bottom:1px solid gray;padding-bottom: 20px">
      <el-row :gutter="20">
        <el-col :span="5" style="color: #409EFF">
          申请账号明细
        </el-col>
        <el-col :span="2">
          <el-button type="primary" size="mini" @click="addAccountForApply">添加</el-button>
        </el-col>
      </el-row>
    </div>
    <div style="margin-left: 40px;">
      <el-table ref="multipleTable" :data="tableData" border>
        <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
        <el-table-column prop="accountName" label="申请账号" width="150"></el-table-column>
        <el-table-column prop="staffName" label="关联员工姓名"width="150"></el-table-column>
        <el-table-column prop="staffNum" label="关联员工编号"  width="150"></el-table-column>
        <el-table-column prop="staffDepartmentName" label="关联员工所属部门" width="200"></el-table-column>
        <el-table-column prop="applyOperation" label="申请操作" width="150" style="text-align: center"></el-table-column>
        <el-table-column prop="operation" label="操作" width="150"></el-table-column>
      </el-table>
    </div>
    <div style="margin-left: 40px;margin-top:20px;margin-bottom:20px;border-bottom:1px solid gray;padding-bottom: 20px">
      <el-row :gutter="20">
        <el-col :span="5" style="color: #409EFF">
          其他信息
        </el-col>
      </el-row>
    </div>

    <div style="margin-top: 20px;margin-left: 40px;">
      <br>
      <el-form ref="form" :model="otherInfo" label-width="110px">
        <el-row>
          <el-col :span="10">
            <el-form-item label="申请时间">
              <el-input style="width:300px;" v-model="otherInfo.applyTime" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="申请人">
              <el-input style="width:300px;" v-model="otherInfo.applyStaffName" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="修改时间">
              <el-input style="width:300px;" v-model="otherInfo.modifyTime" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="修改人">
              <el-input style="width:300px;" v-model="otherInfo.modifyStaffName" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批时间">
              <el-input style="width:300px;" v-model="form.approveTime" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人">
              <el-input style="width:300px;" v-model="form.approverStaffName" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="状态">
              <el-input style="width:300px;" v-model="form.applyStatus" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>



  </home>

</template>

<script>
    export default {
      name: "CreateRoleApply",
      data(){
        return{
          form:{
            roleApplyNum:'',
            roleName:'',
            approverStaffName:'',
            businessLine:''
          },
          otherInfo:{
            applyTime:'',
            applyStaffName:'',
            modifyTime:'',
            modifyStaffName:'',
            approveTime:'',
            approverStaffName:'',
            applyStatus:'',
          },
          tableData:[],
          id:'',
          accountName:'',
          staffName:'',
          staffNum:'',
          staffDepartmentName:'',
          applyOperation:'',
          operation:'',

        }
      },
      methods:{
        selectRoleForRoleApply(){
          //点击选择按钮，进入角色选择界面
          this.$router.replace('/SelectroleRoleApply')
        },
        addAccountForApply(){
          //点击添加按钮，进入账号选择页面
          this.$router.replace('/SelectAccountRoleApply')

        }
      },
      created() {
        var a = sessionStorage.getItem('id')
        // let mm=this.$route.params.id;
        console.log(a)
        //后台数据库查询
        //
      }


    }
</script>

<style scoped>

</style>
