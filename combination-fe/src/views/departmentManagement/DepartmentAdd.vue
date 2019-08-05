<template>
  <home style="margin-left: 10px;">

    <div style="float: right; position: relative; right: 200px; top: 0px">
      <div>
        <el-button type="primary" @click="save">添加</el-button>
        <el-button type="primary" @click="cancel">取消</el-button>
      </div>
    </div>

    <el-form style="margin-left: 10px; margin-top: 10px">

      <span>基本信息</span>
      <hr><br>

      <el-row>
        <el-col :span="8">
          <el-form-item label="部门编号">
            <el-input style="width:200px;" v-model="form.departmentNo" maxlength="7"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="部门名称">
            <el-input style="width:200px;" v-model="form.departmentName" maxlength="40"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="负责人ID">
            <el-input style="width:200px;" v-model="form.staffId" maxlength="20"></el-input>
            <a style="color: blue" @click="choosePerson">选择</a>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="负责人姓名">
            <el-input style="width:200px;" :disabled="false" v-model="form.staffName" maxlength="30"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="手机号">
            <el-input style="width:200px;" v-model="form.telephone" maxlength="11"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="邮箱">
            <el-input style="width:200px;" v-model="form.email" maxlength="30"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="座机号">
            <el-input style="width:200px;" v-model="form.landline" maxlength="12"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="所在城市">
            <el-input style="width:200px;" v-model="form.cityId" maxlength="20"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="16">
          <el-form-item label="详细地址">
            <el-input style="width:500px;" v-model="form.address" maxlength="255"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="经度">
            <select style="width: 40px">
              <option value="E">E</option>
              <option value="W">W</option>
            </select>
            <el-input style="width:180px;" v-model="form.longitude" maxlength="19"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="纬度">
            <select style="width: 40px">
              <option value="N">N</option>
              <option value="S">S</option>
            </select>
            <el-input style="width:180px;" v-model="form.latitude" maxlength="19"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <br><br>

      <span>业务信息</span>
      <hr><br>

      <el-row>
        <el-col :span="8">
          <el-form-item label="部门类型">
            <select style="width: 200px;">
              <option>总部</option>
              <option>分公司</option>
              <option>管理部</option>
              <option>区域</option>
              <option selected>办公点</option>
            </select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="上级部门">
            <el-input style="width:200px;" v-model="form.upperDepartmentId" maxlength="20"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="16">
          <el-form-item label="支持业务线">
            <el-checkbox-group v-model="form.supportBusiness">
              <el-checkbox label="买买车"></el-checkbox>
              <el-checkbox label="闪贷"></el-checkbox>
              <el-checkbox label="租车"></el-checkbox>
              <el-checkbox label="专车"></el-checkbox>
              <el-checkbox label="保险"></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="部门类型">
            <select style="width: 200px;">
              <option selected>门店</option>
              <option>停车场</option>
              <option>交车中心</option>
              <option>维修厂</option>
            </select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="办公点标识">
            <el-input style="width:200px;" v-model="form.workplace" maxlength="3"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <br><br>

      <span>其他信息</span>
      <hr><br>

      <el-row>
        <el-col :span="8">
          <el-form-item label="新建时间">
            <el-input style="width:200px;" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="新建人">
            <el-input style="width:200px;" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="修改时间">
            <el-input style="width:200px;" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="修改人">
            <el-input style="width:200px;" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="状态">
            <select style="width:200px;">
              <option selected>有效</option>
              <option>无效</option>
            </select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="16">
          <el-form-item label="备注">
            <el-input style="width:500px;" v-model="form.remark"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>

    <br>
    <br>
    <br>

  </home>
</template>

<script>
  export default {
    data() {
      return {
        form: {
          departmentNo: '',
          workplace: '',
          departmentName: '',
          staffId: '',
          staffName: '',
          cityId: '',
          address: '',
          telephone: '',
          email: '',
          landline: '',
          level: '',
          upperDepartmentNo: '',
          supportBusiness: [],
          departmentType: '',
          status: '',
          remark: '',
          longtude: '',
          atitude: '',
          remark: ''
        }
      }
    },
    methods: {
      cancel(){//关闭新建员工页面，返回员工管理列表页面
        this.$router.replace('/departmentManagement/showDepartment');
      },
      choosePerson(){
        alert("没写呢，别急");
      },
      save() {//保存新建员工信息
        var self=this;
        self.form.supportBusiness="测试1&测试2&测试3";
        self.$http.post("department/addDepartment.do_",self.form)
          .then(result => {
            self.$router.replace("/departmentManagement");
          })
          .catch(function (error) {

          })
      },
    }
  }
</script>

