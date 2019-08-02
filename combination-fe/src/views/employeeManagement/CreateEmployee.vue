<template>
  <div>
    <div style="margin-bottom: 10px">
      <p>员工信息</p>
      <el-button type="primary" @click="save" style="width:70px">保存</el-button>
      <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
    </div>

    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="员工编号">
              <el-input style="width:200px;" v-model="form.employeeNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工姓名">
              <el-input style="width:200px;" v-model="form.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="性别">
              <input type="radio" id="man" value="男" v-model="form.picked">
              <label for="man">男</label>

              <input type="radio" id="woman" value="女" v-model="form.picked">
              <label for="woman">女</label>

            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工手机号">
              <el-input style="width:200px;" v-model="form.telephone"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="员工邮箱">
              <el-input style="width:200px;" v-model="form.email"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="归属部门">
              <el-input style="width:200px;" v-model="form.department"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="是否离职">
              <el-input style="width:200px;" v-model="form.isDimission"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="登录账号">
              <el-input style="width:200px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <div>
           <p>其它信息</p>
           <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange" >

             <el-table-column prop="create_time" label="新建时间" width="50" style="width:80px"></el-table-column>
             <el-table-column prop="create_emp" label="新建人" width="100"></el-table-column>
             <el-table-column prop="modify_time" label="修改时间"width="150"></el-table-column>
             <el-table-column prop="modify_emp" label="修改人" width="100"></el-table-column>
             <el-table-column prop="remark" label="备注" width="120" style="width:80px"></el-table-column>

           </el-table>
         </div>-->

      </el-form>
    </div>
  </div>

</template>

<script>
  import commonUtils from '../../common/commonUtils'

  export default {
    data() {
      return {

        form: {
          employeeNo: '',
          name: '',
          picked: '',
          telephone: '',
          email: '',
          department: '',
          isDimission: '',
          accountNo: '',
        },
        tableData: [],
        create_time:'',
        create_emp:'',
        modify_time:'',
        modify_emp:'',
        remark:'',
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
    },
    methods: {
      save() {//保存新建员工信息

      },
      cancel(){//关闭新建员工页面，返回员工管理列表页面
        this.$router.replace('/EmployeeManagement')
      },

    },



  }


</script>

<style scoped>

</style>
