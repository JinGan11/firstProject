<template>
  <home style="margin-left: 10px;">

    <div style="float: right; position: relative; right: 200px; top: 0px">
      <div>
        <el-button type="primary" @click="save">修改</el-button>
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
            <el-input style="width:160px;" v-model="form.staffId" :disabled="true" maxlength="20"></el-input>
            <a style="color: blue;cursor: pointer" @click="changeDialogVisible">选择</a>
            <a style="color: blue;cursor: pointer" @click="clearStaffInf">清空</a>
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
            <el-input style="width:200px;" v-model="cityName" :disabled="true" maxlength="20"></el-input>
            <span style="color: red;">*</span>
            <a style="color: blue" @click="chooseCityVisible = true">选择</a>
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
            <el-input style="width:200px;" v-model="form.upperDepartmentName" :disabled="true" maxlength="40"></el-input>
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
            <el-input style="width:200px;" :disabled="true" v-model="form.createTime"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="新建人">
            <el-input style="width:200px;" :disabled="true" v-model="form.createName"></el-input>
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

    <el-button type="primary" @click="save" style="margin-left: 200px">修改</el-button>

    <br>
    <br>
    <br>


    <el-dialog title= "选择城市" :visible.sync="chooseCityVisible" :close-on-click-modal="false" width="600">
      <el-form>
        <el-row>
          <el-col :span="8" >
            <el-form-item label="省/直辖市"  label-width="130" >
              <el-select v-model="chooseCityForm.provinceChosen"  placeholder="请选择" @focus="chooseProvince" @change="provinceChange">
                <el-option
                  v-for="province in provinceSearchList"
                  :key="province.cityID"
                  :label="province.regionName"
                  :value="province.cityID"
                  :disabled="province.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8" >
            <el-form-item label="城市"  label-width="130" >
              <el-select v-model="chooseCityForm.cityChosen" :disabled="isCityDisable" placeholder="请选择" @focus="chooseCity" @change="cityChange">
                <el-option
                  v-for="city in citySearchList"
                  :key="city.cityID"
                  :label="city.regionName"
                  :value="city.cityID"
                  :disabled="city.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8" >
            <el-form-item label="区县"  label-width="130" >
              <el-select v-model="chooseCityForm.countyChosen" :disabled="isCountyDisable" placeholder="请选择" @focus="chooseCounty">
                <el-option
                  v-for="county in countySearchList"
                  :key="county.cityID"
                  :label="county.regionName"
                  :value="county.cityID"
                  :disabled="county.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="chooseCityVisible = false">取 消</el-button>
        <el-button type="primary" @click="chooseCityDo">确 定</el-button>
      </div>
    </el-dialog>


    <el-dialog fullscreen :visible.sync="dialogEmployee" :close-on-click-modal="false" width="700px">
      <employee-list :relAccount="relAccount" ></employee-list>
    </el-dialog>


  </home>
</template>

<script>
  import employeeList from '../employeeManagement/EmployeeList'
  export default {
    data() {
      return {
        form: {
          id: '',
          departmentNo: '',
          workplace: 0,
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
          departmentType: 1,
          status: 1,
          remark: '',
          createTime: '',
          modifyTime: '',
          longitude: '',
          latitude: '',
          upperDepartmentName: '',
          cityName: '',
          createName: '',
          modifyName: ''
        },
        supports: [],
        longitudeNum: '',
        longitudeDirection: 'E',
        latitudeNum: '',
        latitudeDirection: 'N',
        createEmpName: '',
        modifyEmpName: '',
        nowTime: '',
        haveWorkplace: true,
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
        }],
        //城市选择
        //这边的cityName和form里面的cityName要注意区分，一个是后台读入，一个是前台选择
        cityName: '',
        chooseCityVisible:false,
        provinceSearchList:[],
        citySearchList:[],
        countySearchList:[],
        chooseCityForm:{ provinceChosen:'',cityChosen:'',countyChosen:'' },
        isCityDisable:true,
        isCountyDisable:true,
        //员工选择
        dialogEmployee: false,
        relAccount: 1,
      }
    },
    provide(){
      return{
        changeDialogVisible:this.changeDialogVisible,
        chooseStaff:this.chooseStaff
      }
    },
    mounted() {
      var self = this;
      self.form.id=window.localStorage.getItem("dept_id");
      // 获取当前部门的信息
      var deptParam = {
        id: self.form.id
      };
      self.$http.get("department/selectDepartmentById.do_",{ params: deptParam })
        .then(result => {
          self.$options.methods.fillData(self,result.departmentEdit); // 填充其他数据
        })
        .catch(function (error) {

        });
    },
    components: {employeeList},
    methods: {
      fillData(self,data){
        self.form=data;
        self.cityName=data.cityName;
        self.$options.methods.setSupportBusiness(self,data.upperDepartmentNo);
        //设置已选业务线
        var sups = data.supportBusiness.split("&");
        for(var i=0;i<sups.length;i++){
          if(sups[i] == "闪贷" && !self.businessDisable.shandai) { self.supports.push("闪贷"); continue; }
          if(sups[i] == "租车"  && !self.businessDisable.zuche) { self.supports.push("租车"); continue; }
          if(sups[i] == "专车" && !self.businessDisable.zhuanche) { self.supports.push("专车"); continue; }
          if(sups[i] == "保险" && !self.businessDisable.baoxian) { self.supports.push("保险"); continue; }
          if(sups[i] == "买买车" && !self.businessDisable.maimaiche) { self.supports.push("买买车"); continue; }
        }
        // 设置经纬度
        if(data.longitude.trim()!=""){
          var tmp = data.longitude;
          self.longitudeNum = tmp.substr(0,tmp.length-1);
          var tmp2 = tmp.substr(tmp.length-1,1);
          if(tmp2=="E"||tmp2=="W"){
            self.longitudeDirection=tmp2;
          }
        }
        if(data.latitude.trim()!=""){
          var tmp = data.latitude;
          self.latitudeNum = tmp.substr(0,tmp.length-1);
          var tmp2 = tmp.substr(tmp.length-1,1);
          if(tmp2=="N"||tmp2=="S"){
            self.latitudeDirection=tmp2;
          }
        }
        // 办公点
        if(data.level!=5) self.haveWorkplace=false;
      },

      // 设置可选择业务线
      setSupportBusiness(self,dept_no){
        var supParam = {
          departmentNo: dept_no
        };
        self.$http.get("department/getSupportBusiness.do_",{ params: supParam })
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
      },
      cancel () {
        this.$router.replace('/departmentManagement/showDepartment');
      },
      // 部门级别修改后显示内容变更
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

        self.$http.post("department/updateDepartment.do_",self.form)
          .then(result => {
            if(!result.result){
              self.$message.error(result.msg);
              return false;
            }
            self.$message.success("修改成功！");
            self.$router.replace("/departmentManagement/showDepartment");
          })
          .catch(function (error) {

          })
      },
      // 添加业务线的分隔符
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
          default:self.$message.error("请选择【部门级别】！");return false;
        }
        if(!pattern_deptNo.test(_form.departmentNo)){
          self.$message.error("【部门编号】不符合规范！\n" +
            "要求：\n" +
            "总部：7位数字或字母，以Z开头。\n" +
            "分公司：7位数字或字母，以F开头。\n" +
            "管理部：7位数字或字母，以G开头。\n" +
            "区域：7位数字或字母，以F开头。\n" +
            "办公点：7位数字或字母，以B开头。\n");
          return false;
        }
        // 办公点：部门类型 及 办公点标识 校验
        if(self.haveWorkplace){
          if(_form.departmentType<=0){
            self.$message.error("办公点需要填写【部门类型】！");
            return false;
          }
          var pattern_deptNo = /^[0-9]{1,3}$/;
          if(!pattern_deptNo.test(_form.workplace)){
            self.$message.error("【办公点标识】不符合规范！\n" +
              "要求：1-3位数字");
            return false;
          }
        }
        // 部门名称
        if(_form.departmentName.trim()==""){
          self.$message.error("【部门名称】不能为空！");
          return false;
        }
        // 手机号
        var pattern_phone = /^1[0-9]{10}$/;
        if(!pattern_phone.test(_form.telephone)){
          self.$message.error("请输入正确的11位【手机号】");
          return false;
        }
        // 邮箱
        if(_form.email!=""){
          var pattern_email = /^\w+@[a-z0-9]+\.[a-z]+$/i;
          if(!pattern_email.test(_form.email)){
            self.$message.error("请输入正确的【邮箱】，或者不填写任何内容！");
            return false;
          }
        }
        // 支持业务线
        if(_form.supportBusiness==""){
          self.$message.error("请至少选择一个【支持业务线】！");
          return false;
        }
        // 城市还没做！！！
        if(_form.cityId==""){
          self.$message.error("请选择【城市】！");
          return false;
        }
        return true;
      },

      //选择员工
      changeDialogVisible() {//选择员工界面的开关
        const self = this;
        self.dialogEmployee = !self.dialogEmployee;
      },
      clearStaffInf(){//清除选择关联的员工
        const self = this;
        self.form.staffId = '';
        self.form.staffName = '';
      },
      chooseStaff(staffData) {//关联员工
        var self = this;
        self.form.staffId = staffData.id;
        self.form.staffName = staffData.staffName;
      },


      // 城市选择
      chooseProvince() {
        var self=this;
        var param={
          page:1,
          limit:10,
          regionCode:'',
          regionName:'',
          regionStatus: ''
        };
        self.$http.get('/regionManage/provinceSearch',{
          params:param
        }).then((result)=>{
          //对取回来的数据进行处理
          self.provinceSearchList=result.provinceSearchList;
          // console.log(self.provinceSearchList);
        }).catch(function (error) {
          commonUtils.Log("/regionManage/provinceSearch:" + error);
          self.$message.error("获取数据错误");
        });
      },
      provinceChange(){
        this.chooseCityForm.cityChosen='';
        this.chooseCityForm.countyChosen='';
        this.citySearchList=[];
        this.countySearchList=[];
        this.isCityDisable=false;
        this.isCountyDisable=true;
      },
      chooseCity() {
        var self=this;
        var param={
          page:1,
          limit:10,
          upperRegionID:self.chooseCityForm.provinceChosen,
        };
        self.$http.get('/regionManage/citySearch',{
          params:param
        }).then((result)=>{
          //对取回来的数据进行处理
          self.citySearchList=result.citySearchList;
        }).catch(function (error) {
          commonUtils.Log("/regionManage/citySearch:" + error);
          self.$message.error("获取数据错误");
        });
      },
      cityChange(){
        this.chooseCityForm.countyChosen='';
        this.countySearchList=[];
        this.isCountyDisable=false;
      },
      chooseCounty() {
        var self = this;
        var param = {
          page: 1,
          limit: 10,
          regionCode: '',
          regionName: '',
          upperRegion: '',
          upperRegionID:self.chooseCityForm.cityChosen,
          regionStatus: ''
        };
        self.$http.get('/regionManage/countySearch', {
          params: param
        }).then((result) => {
          //对取回来的数据进行处理
          self.countySearchList = result.countySearchList;
          for(var i=0;i<self.countySearchList.length;i++){
            console.log(self.countySearchList[i].cityID);
          }
        }).catch(function (error) {
          commonUtils.Log("/regionManage/countySearch:" + error);
          self.$message.error("获取数据错误");
        });
      },
      chooseCityDo2(){
        self.$message.error(this.countySearchList[2].regionName);
      },
      chooseCityDo() {
        var self = this;
        if(self.chooseCityForm.countyChosen != ""){
          for(var i=0;i<self.countySearchList.length;i++){
            if(self.countySearchList[i].cityID == self.chooseCityForm.countyChosen){
              self.cityName=self.countySearchList[i].regionName;
              self.form.cityId=self.chooseCityForm.countyChosen;
              self.chooseCityVisible=false;
              return;
            }
          }
        }
        if(self.chooseCityForm.cityChosen != ""){
          for(var i=0;i<self.citySearchList.length;i++){
            if(self.citySearchList[i].cityID == self.chooseCityForm.cityChosen){
              self.cityName=self.citySearchList[i].regionName;
              self.form.cityId=self.chooseCityForm.cityChosen;
              self.chooseCityVisible=false;
              return;
            }
          }
        }
        if(self.chooseCityForm.provinceChosen != ""){
          for(var i=0;i<self.provinceSearchList.length;i++){
            if(self.provinceSearchList[i].cityID == self.chooseCityForm.provinceChosen){
              self.cityName=self.provinceSearchList[i].regionName;
              self.form.cityId=self.chooseCityForm.provinceChosen;
              self.chooseCityVisible=false;
              return;
            }
          }
        }
        self.$message.error("请至少选择一个城市！");
      }
    }
  }
</script>
