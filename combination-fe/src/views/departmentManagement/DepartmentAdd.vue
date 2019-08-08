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
            <span style="color: red;">*</span>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="部门名称">
            <el-input style="width:200px;" v-model="form.departmentName" maxlength="40"></el-input>
            <span style="color: red;">*</span>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="负责人ID">
            <el-input style="width:200px;" v-model="form.staffId" :disabled="true" maxlength="20"></el-input>
            <a style="color: blue" @click="choosePerson">选择</a>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="负责人姓名">
            <el-input style="width:200px;" :disabled="true" v-model="form.staffName" maxlength="30"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="手机号">
            <el-input style="width:200px;" v-model="form.telephone" maxlength="11"></el-input>
            <span style="color: red;">*</span>
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
            <el-input style="width:200px;" v-model="form.cityId" :disabled="false" maxlength="20"></el-input>
            <span style="color: red;">*</span>
            <a style="color: blue" @click="chooseCity">选择</a>
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
            <el-select style="width: 60px" v-model="longitudeDirection" placeholder="选">
              <el-option
                v-for="item in longitudeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input style="width: 140px;" v-model="longitudeNum" maxlength="19"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="纬度">
            <el-select style="width: 60px" v-model="latitudeDirection" placeholder="选">
              <el-option
                v-for="item in latitudeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input style="width:140px;" v-model="latitudeNum" maxlength="19"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <br><br>

      <span>业务信息</span>
      <hr><br>

      <el-row>
        <el-col :span="8">
          <el-form-item label="部门级别">
            <el-select style="width: 200px;" v-model="form.level" @change="levelChange">
              <el-option
                v-for="item in departmentLevelOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <span style="color: red;">*</span>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="上级部门">
            <el-input style="width:200px;" v-model="upperDepartmentName" :disabled="true" maxlength="40"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="16">
          <el-form-item label="支持业务线" style="width:400px;">
            <span style="color: red;">*</span>
            <el-checkbox-group v-model="supports">
              <el-checkbox label="买买车" :disabled="businessDisable.maimaiche"></el-checkbox>
              <el-checkbox label="闪贷" :disabled="businessDisable.shandai"></el-checkbox>
              <el-checkbox label="租车" :disabled="businessDisable.zuche"></el-checkbox>
              <el-checkbox label="专车" :disabled="businessDisable.zhuanche"></el-checkbox>
              <el-checkbox label="保险" :disabled="businessDisable.baoxian"></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row v-if="haveWorkplace">
        <el-col :span="8">
          <el-form-item label="部门类型">
            <el-select style="width: 200px;" v-model="form.departmentType">
              <el-option
               v-for="item in departmentTypeOptions"
               :key="item.value"
               :label="item.label"
               :value="item.value">
              </el-option>
            </el-select>
            <span style="color: red;">*</span>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="办公点标识">
            <el-input style="width:200px;" v-model="form.workplace" maxlength="3"></el-input>
            <span style="color: red;">*</span>
          </el-form-item>
        </el-col>
      </el-row>

      <br><br>

      <span>其他信息</span>
      <hr><br>

      <el-row>
        <el-col :span="8">
          <el-form-item label="新建时间">
            <el-input style="width:200px;" :disabled="true" v-model="nowTime"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="新建人">
            <el-input style="width:200px;" :disabled="true" v-model="createEmpName"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="修改时间">
            <el-input style="width:200px;" :disabled="true" v-model="nowTime"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="修改人">
            <el-input style="width:200px;" :disabled="true" v-model="modifyEmpName"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="状态">
            <el-select v-model="form.status">
              <el-option
                v-for="item in statusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="16">
          <el-form-item label="备注">
            <el-input style="width:500px;" v-model="form.remark" maxlength="255"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>

    <br>

    <el-button type="primary" @click="save" style="margin-left: 200px">添加</el-button>

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
          supportBusiness: '',
          departmentType: '',
          status: 1,
          remark: '',
          longitude: '',
          latitude: ''
        },
        upperDepartmentName: '',
        cityName: '',
        supports: [],
        longitudeNum: '',
        longitudeDirection: 'E',
        latitudeNum: '',
        latitudeDirection: 'N',
        createEmpName: '',
        modifyEmpName: '',
        nowTime: '',
        haveWorkplace: false,
        businessDisable: {
          zuche: true,
          zhuanche: true,
          shandai: true,
          baoxian: true,
          maimaiche: true
        },
        departmentLevelOptions: [{
          value: 1,
          label: '总部'
        },{
          value: 2,
          label: '分公司'
        },{
          value: 3,
          label: '管理部'
        },{
          value: 4,
          label: '区域'
        },{
          value: 5,
          label: '办公点'
        }],
        departmentTypeOptions: [{
          value: 1,
          label: '门店'
        },{
          value: 2,
          label: '停车场'
        },{
          value: 3,
          label: '交车中心'
        },{
          value: 4,
          label: '维修厂'
        }],
        statusOptions:[{
          value: 1,
          label: '有效'
        },{
          value: 0,
          label: '无效'
        }],
        longitudeOptions: [{
          value: 'E',
          label: 'E'
        },{
          value: 'W',
          label: 'W'
        }],
        latitudeOptions: [{
          value: 'N',
          label: 'N'
        },{
          value: 'S',
          label: 'S'
        }]
      }
    },
    mounted() {
      this.form.upperDepartmentNo=window.localStorage.getItem("dept_no");
      this.upperDepartmentName=window.localStorage.getItem("dept_name");
      this.createEmpName=window.sessionStorage.getItem("loginUsername");
      this.modifyEmpName=window.sessionStorage.getItem("loginUsername");

      // 设置可选择的业务线
      var self = this;
      var param = {
        departmentNo: self.form.upperDepartmentNo
      }
      self.$http.get("department/getSupportBusiness.do_",{ params: param })
        .then(result => {
          var sups = result.split("&");
          for(var i=0;i<sups.length;i++){
            if(sups[i] == "闪贷") { self.businessDisable.shandai=false; continue; }
            if(sups[i] == "租车") { self.businessDisable.zuche=false; continue; }
            if(sups[i] == "专车") { self.businessDisable.zhuanche=false; continue; }
            if(sups[i] == "保险") { self.businessDisable.baoxian=false; continue; }
            if(sups[i] == "买买车") { self.businessDisable.maimaiche=false; continue; }
          }
        })
        .catch(function (error) {

        });
      // 页面加载完显示当前时间
      this.nowTime = this.dealWithTime(new Date());
      // 定时器，定时修改显示的时间
      let _this = this;
      this.timer = setInterval(function () {
        _this.nowTime = _this.dealWithTime(new Date())
      }, 1000);
    },
    destroyed () {
      // 结束时清除定时器
      if (this.timer) {
        clearInterval(this.timer);
      }
    },
    methods: {
      cancel () {
        this.$router.replace('/departmentManagement/showDepartment');
      },
      choosePerson () {
        alert("没写呢，别急");
      },
      chooseCity() {
        alert("这个也没写");
      },
      levelChange() {
        if(this.form.level==5) this.haveWorkplace=true;
        else this.haveWorkplace=false;
      },
      save () {
        var self=this;

        self.form.supportBusiness = self.$options.methods.addSubSign(self.supports); // 添加分隔符&
        self.form.longitude = self.longitudeNum + self.longitudeDirection;
        self.form.latitude = self.latitudeNum + self.latitudeDirection;

        // 给不需要的值统一设置0避免占用数字
        if(self.haveWorkplace==false){
          self.form.workplace=0;
          self.departmentType=0;
        }

        // 前端校验输入
        if(!self.$options.methods.checkInput(self)) return;

        self.$http.post("department/addDepartment.do_",self.form)
          .then(result => {
            self.$router.replace("/departmentManagement/showDepartment");
          })
          .catch(function (error) {

          })
      },
      dealWithTime (data) {
        let formatDateTime;
        let Y = data.getFullYear();
        let M = data.getMonth() + 1;
        let D = data.getDate();
        let H = data.getHours();
        let Min = data.getMinutes();
        let S = data.getSeconds();
        let W = data.getDay();
        H = H < 10 ? ('0' + H) : H;
        Min = Min < 10 ? ('0' + Min) : Min;
        S = S < 10 ? ('0' + S) : S;
        formatDateTime = Y + '年' + M + '月' + D + '日 ' + H + ':' + Min + ':' + S;
        return formatDateTime;
      },
      addSubSign (data) {
        if(data.length<=0) return "";
        var result = data[0];
        for(var i=1;i<data.length;i++){
          result=result+"&"+data[i];
        }
        return result;
      },
      // 前端校验输入数据是否有问题
      checkInput (self) {
        var _form = self.form;
        // 校验部门编号 和 部门级别
        var pattern_deptNo;
        switch (_form.level) {
          case 1:pattern_deptNo=/^Z[a-zA-Z0-9]{6}$/;break;
          case 2:pattern_deptNo=/^F[a-zA-Z0-9]{6}$/;break;
          case 3:pattern_deptNo=/^G[a-zA-Z0-9]{6}$/;break;
          case 4:pattern_deptNo=/^Q[a-zA-Z0-9]{6}$/;break;
          case 5:pattern_deptNo=/^B[a-zA-Z0-9]{6}$/;break;
          default:alert("请选择【部门级别】！");return false;
        }
        if(!pattern_deptNo.test(_form.departmentNo)){
          alert("【部门编号】不符合规范！\n" +
            "要求：\n" +
            "总部：7位数字或字母，以Z开头\n" +
            "分公司：7位数字或字母，以F开头\n" +
            "管理部：7位数字或字母，以G开头\n" +
            "区域：7位数字或字母，以F开头\n" +
            "办公点：7位数字或字母，以B开头\n");
          return false;
        }
        // 办公点：部门类型 及 办公点标识 校验
        if(self.haveWorkplace){
          if(_form.departmentType<=0){
            alert("办公点需要填写【部门类型】！");
            return false;
          }
          var pattern_deptNo = /^[0-9]{1,3}$/;
          if(!pattern_deptNo.test(_form.workplace)){
            alert("【办公点标识】不符合规范！\n" +
              "要求：1-3位数字");
            return false;
          }
        }
        // 部门名称
        if(_form.departmentName.trim()==""){
          alert("【部门名称】不能为空！");
          return false;
        }
        // 手机号
        var pattern_phone = /^1[0-9]{10}$/;
        if(!pattern_phone.test(_form.telephone)){
          alert("请输入正确的11位【手机号】");
          return false;
        }
        // 邮箱
        if(_form.email!=""){
          var pattern_email = /^\w+@[a-z0-9]+\.[a-z]{2,4}$/;
          if(pattern_email.test(_form.email)){
            alert("请输入正确的【邮箱】，或者不填写任何内容！");
            return false;
          }
        }
        // 支持业务线
        if(_form.supportBusiness==""){
          alert("请至少选择一个【支持业务线】！");
          return false;
        }
        // 城市还没做！！！
        if(_form.cityId==""){
          alert("请选择【城市】！");
          return false;
        }
        return true;
      }
    }
  }
</script>
