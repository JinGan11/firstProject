<template>
  <div>
    <div style="margin-bottom: 10px">
      <p>角色信息</p>
    </div>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="110px">
        <el-row>
          <el-col :span="10">
            <el-form-item label="角色ID">
              <el-input style="width:200px;" :disabled="true" v-model="form.roleID"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="角色名称">
              <el-input style="width:200px;" :disabled="true" v-model="form.roleName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批人账号">
              <el-input style="width:200px;" :disabled="true" v-model="form.accountNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人员工编号">
              <el-input style="width:200px;" :disabled="true" v-model="form.staffNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批人姓名">
              <el-input style="width:200px;" :disabled="true" v-model="form.staffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人所属部门">
              <el-input style="width:200px;" :disabled="true" v-model="form.departmentName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="15">
            <el-form-item label="支持业务线">
              <el-checkbox-group v-model="form.businessLine" :disabled="true">
                <el-checkbox label="买买车"></el-checkbox>
                <el-checkbox label="租车"></el-checkbox>
                <el-checkbox label="闪贷"></el-checkbox>
                <el-checkbox label="专车"></el-checkbox>
                <el-checkbox label="保险" ></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="描述">
              <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" :disabled="true" v-model="form.description"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div>
      <p>其它信息</p>
      <div>
        <div style="width:85%; margin-left: 70px">
          <el-form ref="otherForm" :model="otherForm" label-width="110px">
            <el-row>
              <el-col :span="10">
                <el-form-item label="新建人">
                  <el-input style="width:200px;" :disabled="true" v-model="otherForm.createEmp"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="新建时间">
                  <el-input style="width:200px;" :disabled="true" v-model="otherForm.createTime"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="修改人">
                  <el-input style="width:200px;" :disabled="true" v-model="otherForm.modifyEmp"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="修改时间">
                  <el-input style="width:200px;" :disabled="true" v-model="otherForm.modifyTime"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="1">
                <el-form-item label="状态">
                  <el-select v-model="form.roleStatus" :disabled="true" style="width:150px;">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
    </div>
    <div style="text-align: center">
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
          roleID: '',
          roleName: '',
          businessLine: [],
          roleStatus: '',
          accountNum: '',
          staffNum: '',
          staffName: '',
          departmentName: '',
          description: '',
        },

        otherForm:{
          createEmp:'',
          createEmpNum:'',
          createEmpName:'',
          createEmpTime:'',
          modifyEmp:'',
          modifyEmpNum:'',
          modifyEmpName:'',
          modifyEmpTime:'',
        },

        RoleStatusEnum: {},
        options: [
          {
            value: 0,
            label: '无效',
          }, {
            value: 1,
            label: '有效',
          }],
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
      this.fetchData();
      this.fetchOtherData();
    },
    methods: {

      fetchOtherData() {
        var roleid;
        var self = this;
        roleid = this.$route.query.roleID;
        var param = {
          roleID: roleid,
        };
        self.$http.get('roleManage/getOtherOneInf.do_', {
          params: param
        }).then((result) => {
          self.otherForm = result.page;
          self.otherForm.createEmp = `${self.otherForm.createEmpNum}(${self.otherForm.createEmpName})`;
          self.otherForm.modifyEmp = `${self.otherForm.modifyEmpNum}(${self.otherForm.modifyEmpName})`;
        }).catch(function (error) {
          commonUtils.Log("roleManage/getOtherOneInf.do_" + error);
          self.$message.error("获取数据错误");
        });
      },

      cancel(){//关闭角色页面，返回角色管理列表页面
        this.$router.replace('/roleManagement/roleManagement')
      },

      fetchData(){
        var roleid;
        var self = this;
        roleid=this.$route.query.roleID;
        var param = {
          roleID: roleid,
        };
        self.$http.get('roleManage/getOneInf.do_', {
          params: param
        }).then((result) => {
          self.form=result.page;
          self.RoleStatusEnum = result.RoleStatusEnum;
          self.form.businessLine=self.form.businessLine.split(',');
        }).catch(function (error) {
          commonUtils.Log("roleManage/getOneInf.do_:" + error);
          self.$message.error("获取数据错误");
        });
      }
    },



  }


</script>

<style scoped>

</style>
