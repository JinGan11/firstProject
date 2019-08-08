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
            <el-input style="width:200px;" v-model="form.staffId" maxlength="20"></el-input>
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
            <el-button type="text" v-model="form.cityId"  @click="chooseCityVisible = true">请选择</el-button>
            <span style="color: red;">*</span>
          </el-form-item>
        </el-col>
      </el-row>


      <el-dialog title= "选择城市" :visible.sync="chooseCityVisible" :close-on-click-modal="false" width="600">
        <el-form :model="chooseCityForm">
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
          <el-button type="primary" @click="chooseCityVisible = false">确 定</el-button>
        </div>
      </el-dialog>






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
            <el-select style="width: 200px;" v-model="form.level">
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
            <el-input style="width:200px;" v-model="form.upperDepartmentNo" maxlength="7"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="16">
          <el-form-item label="支持业务线" style="width:400px;">
            <span style="color: red;">*</span>
            <el-checkbox-group v-model="supports">
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
    import commonUtils from '../../common/commonUtils'
    import citySearch from "../cityInfoManagement/citySearch";
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
                supports: [],
                longitudeNum: '',
                longitudeDirection: '',
                latitudeNum: '',
                latitudeDirection: '',
                createEmpName: '',
                modifyEmpName: '',
                nowTime: '',
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
                chooseCityVisible:false,
                provinceSearchList:[],
                citySearchList:[],
                countySearchList:[],
                // provinceChosen:'',
                chooseCityForm:{provinceChosen:'',cityChosen:'',countyChosen:''},
                isCityDisable:true,
                isCountyDisable:true,
            }
        },
        mounted() {
            this.createEmpName=window.sessionStorage.getItem("loginUsername");
            this.modifyEmpName=window.sessionStorage.getItem("loginUsername");
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
            save () {
                var self=this;

                self.form.supportBusiness = self.$options.methods.addSubSign(self.supports); // 添加分隔符&
                self.form.longitude = self.longitudeNum + self.longitudeDirection;
                self.form.latitude = self.latitudeNum + self.latitudeDirection;

                self.$http.post("department/addDepartment.do_",self.form)
                    .then(result => {
                        self.$router.replace("/departmentManagement");
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
                var result = data[0];
                for(var i=1;i<data.length;i++){
                    result=result+"&"+data[i];
                }
                return result;
            },
            checkInput () {
                var _form = this.form;

            },
            //城市选择
            chooseProvince() {
                // this.chooseCityVisible=true;

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
                this.isCityDisable=false;
                this.isCountyDisable=true;
            },
            chooseCity() {
                // this.chooseCityVisible=true;

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

                    // console.log(self.citySearchList);
                    //

                }).catch(function (error) {
                    commonUtils.Log("/regionManage/citySearch:" + error);
                    self.$message.error("获取数据错误");
                });
            },
            cityChange(){
                this.chooseCityForm.countyChosen='';
                this.isCountyDisable=false;
            },
            chooseCounty() {
                // this.chooseCityVisible=true;

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

                    // console.log(self.citySearchList);
                    //

                }).catch(function (error) {
                    commonUtils.Log("/regionManage/countySearch:" + error);
                    self.$message.error("获取数据错误");
                });
            },
        }
    }
</script>
