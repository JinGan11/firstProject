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
              <el-input style="width:200px;" v-model="form.roleName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批人账号">
              <el-input style="width:200px;" v-model="form.accountNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人员工编号">
              <el-input style="width:200px;" v-model="form.staffNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="审批人姓名">
              <el-input style="width:200px;" v-model="form.staffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="审批人所属部门">
              <el-input style="width:200px;" v-model="form.departmentName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="支持业务线">
              <!--<template v-for="item in chks">
                <input type="checkbox" name="hobby" :value="item.id"
                     :checked="form.loopsss.indexOf(item.id) > -1"/>{{item.name}}
              </template>
              -->
              <input type="checkbox" v-model="form.businessLine" value="买买车">买买车
              <input type="checkbox" v-model="form.businessLine" value="租车">租车
              <input type="checkbox" v-model="form.businessLine" value="闪贷">闪贷
              <input type="checkbox" v-model="form.businessLine" value="专车">专车
              <input type="checkbox" v-model="form.businessLine" value="保险">保险
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="描述">
              <el-input style="width:200px;" v-model="form.description"></el-input>
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
                  <el-input style="width:200px;" :disabled="true" v-model="form.createEmp"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="新建时间">
                  <el-input style="width:200px;" :disabled="true" v-model="form.createTime"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="修改人">
                  <el-input style="width:200px;" :disabled="true" v-model="form.modifyEmp"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="修改时间">
                  <el-input style="width:200px;" :disabled="true" v-model="form.modifyTime"></el-input>
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
          createEmp: '',
          createTime: '',
          modifyEmp: '',
          modifyTime: '',
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
    },
    methods: {

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
