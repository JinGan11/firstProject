/**
* @program:ucarcombination
* @description: 修改员工信息页面
* @author: shiya.li@ucarinc.com
* @create: 2019/8/2 15：40
*/
<template>
  <home>

    <div style="margin-bottom: 10px">
      <p>员工信息</p>
      <el-button type="primary" @click="save" style="width:70px">保存</el-button>
      <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
    </div>

    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" :data="tableData" label-width="80px">
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

      </el-form>
    </div>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="新建时间">
              <el-input style="width:200px;" v-model="form.create_time"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="新建人">
              <el-input style="width:200px;" v-model="form.create_emp"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="修改时间">
              <el-input style="width:200px;" v-model="form.modify_time"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="修改人">
              <el-input style="width:200px;" v-model="form.modify_emp"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="备注">
              <el-input style="width:200px;" v-model="form.remark"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
    </div>
  </home>
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
          create_time: '',
          create_emp: '',
          modify_time: '',
          modify_emp: '',
          remark: '',

        },
        tableData: [],
        employeeNo: '',
        name: '',
        picked: '',
        telephone: '',
        email: '',
        department: '',
        isDimission: '',
        accountNo: '',
        create_time: '',
        create_emp: '',
        modify_time: '',
        modify_emp: '',
        remark: '',
      }
    },

    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
    },
    methods: {
      save() {//保存修改后的员工信息

      },
      cancel() {//关闭修改员工页面，返回员工管理列表页面
        this.$router.replace('/EmployeeManagement')
      },

    },


  }
</script>


<style scoped>

</style>
