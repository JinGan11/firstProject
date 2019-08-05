<template>
  <div>
    <div style="margin-bottom: 10px">
      <el-row>
      <el-col>员工信息</el-col>
      <el-button type="primary" @click="save" style="width:70px">保存</el-button>
      <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
      </el-row>
    </div>

    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="8">
            <el-form-item  label="员工编号">
              <el-input style="width:200px;" v-model="form.staffNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工姓名">
              <el-input style="width:200px;" v-model="form.staffName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item  label="性别">
              <el-input style="width:200px;" v-model="form.staffSex"></el-input>
              <!--<input type="radio" id="man" value="男" v-model="form.picked">
              <label for="man">男</label>

              <input type="radio" id="woman" value="女" v-model="form.picked">
              <label for="woman">女</label>
            -->

            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item  label="员工手机号">
              <el-input style="width:200px;" v-model="form.staffTelephone"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item  label="员工邮箱">
              <el-input style="width:200px;" v-model="form.staffEmail"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item  label="归属部门">
              <el-input style="width:200px;" v-model="form.departmentId"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item  label="是否离职">
              <el-input style="width:200px;" v-model="form.isDimission"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="登录账号">
              <el-input style="width:200px;" :disabled="true" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
         <!--<div>
           <p>其它信息</p>
           <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange" >

             <el-table-column prop="create_time" label="新建时间" width="50" style="width:80px"></el-table-column>
             <el-table-column prop="create_emp" label="新建人" width="100"></el-table-column>
             <el-table-column prop="modify_time" label="修改时间"width="150"></el-table-column>
             <el-table-column prop="modify_emp" label="修改人" width="100"></el-table-column>
             <el-table-column prop="remark" label="备注" width="120" style="width:80px"></el-table-column>

           </el-table>
         </div>-->
        <div><p>其它信息</p></div>
        <div style="width:85%; margin-left: 70px">
          <el-form ref="form" :model="form" label-width="80px">
            <el-row>
              <el-col :span="8">
                <el-form-item  label="新建时间">
                  <el-input style="width:200px;" :disabled="true" v-model="form.createTime"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="新建人">
                  <el-input style="width:200px;" :disabled="true" v-model="form.createEmp"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="修改时间">
                  <el-input style="width:200px;" :disabled="true" v-model="form.modifyTime"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="修改人">
                  <el-input style="width:200px;" :disabled="true" v-model="form.modifyEmp"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item prop="staff_num"label="备注">
                  <el-input style="width:200px;"  v-model="form.remark"></el-input>
                </el-form-item>
              </el-col>

            </el-row>
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
          staffNum: '',
          name: '',
         /* picked: '',*/
          sex:'',
          telephone: '',
          email: '',
          department: '',
          isDimission: '',
          accountNo: '',
          createTime: '',
          createEmp:'',
          modifyTime:'',
          modifyEmp: '',
          remark:'',
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
      save() {//保存新建员工信息
          var self=this;
          self.$http.post("employee/insertStaff",self.form)
            .then(result => {
              self.$router.replace("/employeeManagement");
            })
            .catch(function (error) {
            commonUtils.Log("employee/insertStaff:"+error);
            self.$message.error("新建用户失败");
          })
      },
      cancel(){//关闭新建员工页面，返回员工管理列表页面
        this.$router.replace('/EmployeeManagement');
      },

    },



  }


</script>

<style scoped>

</style>
