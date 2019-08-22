<template>
  <home style="margin-left: 10px;">

    <div style="float: right; position: relative; right: 200px; top: 0px">
      <div>
        <el-button type="primary" @click="save">添加</el-button>
        <el-button type="primary" @click="cancel">取消</el-button>
      </div>
    </div>

    <el-form style="margin-left: 10px; padding-top: 10px" :model="form" ref="form" :rules="rulesDepartment">

      <span>基本信息</span>
      <hr><br>

      <el-row>
        <el-col :span="8">
          <el-form-item label="部门编号" prop="departmentNo">
            <el-input style="width:200px;" v-model="form.departmentNo" maxlength="7"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="部门名称" prop="departmentName">
            <el-input style="width:200px;" v-model="form.departmentName" maxlength="40"></el-input>
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
          <el-form-item label="手机号" prop="telephone">
            <el-input style="width:200px;" v-model="form.telephone" maxlength="11"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="邮箱" prop="email">
            <el-input style="width:200px;" v-model="form.email" maxlength="30"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="座机号" prop="landline">
            <el-input style="width:200px;" v-model="form.landline" maxlength="13"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="所在城市" prop="cityName">
<!--            <el-input style="width:200px;" v-model="cityName" :disabled="true" maxlength="20"></el-input>-->
<!--            <a style="color: blue;cursor: pointer" @click="chooseCityVisible = true">选择</a>-->

<!--            <el-select v-model="cityName" filterable placeholder="请选择">-->
<!--              <el-option-->
<!--                v-for="item in cityOptions"-->
<!--                :key="item.cityId"-->
<!--                :label="item.cityName"-->
<!--                :value="item.cityId">-->
<!--              </el-option>-->
<!--            </el-select>-->

            <el-select
              v-model="cityName"
              filterable
              remote
              placeholder="请输入关键词"
              :remote-method="remoteMethod"
              @focus="cityFocus"
              :loading="loading">
              <el-option
                v-for="item in cityOptions"
                :key="item.cityId"
                :label="item.cityName"
                :value="item.cityId">
              </el-option>
            </el-select>

          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="16">
          <el-form-item label="详细地址" prop="address">
            <div><span :hidden="!haveWorkplace" style="color: #FF0000;">*</span>
            <el-input style="width:500px;" v-model="form.address" maxlength="255" @focus="baiduMapFlag=true"></el-input></div>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="经度" prop="longitudeNum">
            <el-select style="width: 60px" v-model="longitudeDirection" placeholder="选">
              <el-option
                v-for="item in longitudeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input style="width: 140px;" v-model="longitudeNum" maxlength="14"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="纬度" prop="latitudeNum">
            <el-select style="width: 60px" v-model="latitudeDirection" placeholder="选">
              <el-option
                v-for="item in latitudeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-input style="width:140px;" v-model="latitudeNum" maxlength="14"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <br><br>

      <span>业务信息</span>
      <hr><br>

      <el-row>
        <el-col :span="8">
          <el-form-item label="部门级别" prop="level">
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
          <el-form-item label="支持业务线" style="width:400px;" prop="supports">
            <br/>
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
          <el-form-item label="部门类型" prop="departmentType">
            <el-select style="width: 200px;" v-model="form.departmentType" @change="deptTypeChange">
              <el-option
               v-for="item in departmentTypeOptions"
               :key="item.value"
               :label="item.label"
               :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="办公点标识" prop="workplace">
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
            <el-select v-model="form.status" :disabled="true">
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
      <employee-list :relAccount=2></employee-list>
    </el-dialog>

    <el-dialog :visible.sync="baiduMapFlag" width="700px" :show-close="false">
      <div class="container" >
        <div class="search-box">
          <input
            v-model="searchKey"
            type="search"
            id="search">
          <button @click="searchByHand">搜索</button>
          <div class="tip-box" id="searchTip"></div>
        </div>
        <el-amap class="amap-box"
                 :amap-manager="amapManager"
                 :vid="'amap-vue'"
                 :zoom="zoom"
                 :plugin="plugin"
                 :center="center"
                 :events="events"
        >
          <el-amap-marker v-for="(marker, index) in markers" :position="marker" :key="index"></el-amap-marker>
        </el-amap>
        <div slot="footer" class="dialog-footer">
          <el-button @click="mapCencel">取消</el-button>
          <el-button type="primary" @click="mapConfirm">确定</el-button>
        </div>
      </div>
    </el-dialog>
  </home>
</template>

<script>
  import employeeList from '../employeeManagement/EmployeeList'
  import {AMapManager, lazyAMapApiLoaderInstance} from 'vue-amap'
  let amapManager = new AMapManager()
  export default {
    data() {
      let self = this;

      //各种校验 zzz
      var validLevel = (rule, value, callback) => {
        if (this.form.level == '' || this.form.level<= 0)
          return callback(new Error("请选择部门级别"));
        callback();
      };
      var validDepartmentNo = (rule, value, callback) => {
        if( this.form.departmentNo=='' ) callback(new Error("请填写部门编号"));
        if (this.form.level == '' || this.form.level<= 0)
          callback();
        var patternDeptNo;
        switch (this.form.level) {
          case 1:
            patternDeptNo = /^Z[a-zA-Z0-9]{6}$/;
            if (!patternDeptNo.test(this.form.departmentNo))
              callback(new Error("格式错误：7位数字或字母，以Z开头"));
            break;
          case 2:
            patternDeptNo = /^F[a-zA-Z0-9]{6}$/;
            if (!patternDeptNo.test(this.form.departmentNo))
              callback(new Error("格式错误：7位数字或字母，以F开头"));
            break;
          case 3:
            patternDeptNo = /^G[a-zA-Z0-9]{6}$/;
            if (!patternDeptNo.test(this.form.departmentNo))
              callback(new Error("格式错误：7位数字或字母，以G开头"));
            break;
          case 4:
            patternDeptNo = /^Q[a-zA-Z0-9]{6}$/;
            if (!patternDeptNo.test(this.form.departmentNo))
              callback(new Error("格式错误：7位数字或字母，以Z开头"));
            break;
          case 5:
            patternDeptNo = /^B[a-zA-Z0-9]{6}$/;
            if (!patternDeptNo.test(this.form.departmentNo))
              callback(new Error("格式错误：7位数字或字母，以B开头"));
            break;
        }
        callback();
      };
      var validDepartmentType = (rule, value, callback) => {
        if (this.haveWorkplace) {
          if (this.form.departmentType == '' || this.form.departmentType <= 0)
            callback(new Error("请填写部门类型"));
        }
        callback();
      };
      var validWorkplace = (rule, value, callback) => {
        if (this.haveWorkplace) {
          var patternWorkplace = /^[0-9]{1,3}$/;
          if (!patternWorkplace.test(value))
            callback(new Error("请填写办公点标识，支持1-3位数字"));
          if(value==0 || value=='0')
            callback(new Error("办公点标识不能为0"));
        }
        callback();
      };
      var validAddress = (rule, value, callback) => {
        if (this.haveWorkplace && value == '')
          callback(new Error("请填写详细地址"));
        callback();
      };
      var validSupports = (rule, value, callback) => {
        if (this.supports == [] || this.supports == '' || this.supports.length <= 0)
          return callback(new Error("请至少选择一个业务线"));
        callback();
      };
      var validLongitude = (rule, value, callback) => {
        if(this.longitudeNum=='') callback();
        var patternLongitudeNum = /^(0(\.\d{1,10})?|([1-9](\d)?)(\.\d{1,10})?|1[0-7]\d{1}(\.\d{1,10})?|180\.0{1,10})$/;
        if (!patternLongitudeNum.test(this.longitudeNum))
          return callback(new Error("经度格式有误，格式为'120.1234567'"));
        callback();
      };
      var validLatitude = (rule, value, callback) => {
        if(this.latitudeNum=='') callback();
        var patternLatitudeNum = /^((0|([1-8]\d?))(\.\d{1,10})?|90(\.0{1,10})?)$/;
        if (!patternLatitudeNum.test(this.latitudeNum))
          return callback(new Error("纬度格式有误，格式为'70.1234567'"));
        callback();
      };
      var validCityName = (rule, value, callback) => {
        if (this.cityName == '')
          return callback(new Error("请选择城市"));
        this.form.cityId=this.cityName;
        callback();
      };
      return {
        loading: false,
        address: null,
        searchKey: '',
        amapManager,
        markers: [],
        searchOption: {
          city: '全国',
          citylimit: true
        },
        center: [121.329402, 31.228667],
        zoom: 17,
        lng: 0,
        lat: 0,
        loaded: false,
        events: {
          init() {
            lazyAMapApiLoaderInstance.load().then(() => {
              self.initSearch()
            })
          },
          // 点击获取地址的数据
          click(e) {
            // console.log(e)
            self.markers = []
            let {lng, lat} = e.lnglat
            self.lng = lng
            self.lat = lat
            self.center = [lng, lat]
            self.markers.push([lng, lat])
            // 这里通过高德 SDK 完成。
            let geocoder = new AMap.Geocoder({
              radius: 1000,
              extensions: 'all'
            })
            geocoder.getAddress([lng, lat], function (status, result) {
              if (status === 'complete' && result.info === 'OK') {
                if (result && result.regeocode) {
                  console.log(result.regeocode.formattedAddress)
                  self.address = result.regeocode.addressComponent
                  self.form.address = self.address.township + self.address.street + self.address.streetNumber;
                  self.searchKey = result.regeocode.formattedAddress
                  self.longitudeNum = lng;
                  self.latitudeNum = lat
                  self.$nextTick()
                }
              }
            })
          }
        },
        // 一些工具插件
        plugin: [
          // {
          //   // 定位
          //   pName: 'Geolocation',
          //   events: {
          //     init (o) {
          //       // o是高德地图定位插件实例
          //       o.getCurrentPosition((status, result) => {
          //         if (result && result.position) {
          //           // 设置经度
          //           self.lng = result.position.lng
          //           // 设置维度
          //           self.lat = result.position.lat
          //           // 设置坐标
          //           self.center = [self.lng, self.lat]
          //           self.markers.push([self.lng, self.lat])
          //           // load
          //           self.loaded = true
          //           // 页面渲染好后
          //           self.$nextTick()
          //         }
          //       })
          //     }
          //   }
          // },
          {
            // 搜索
            pName: 'PlaceSearch',
            events: {
              init(instance) {
                // console.log(instance)
              }
            }
          }
        ],

        baiduMapFlag: false,

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
          latitude: '',
          forIEFresh: new Date().getTime()
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
        }, {
          value: 2,
          label: '分公司'
        }, {
          value: 3,
          label: '管理部'
        }, {
          value: 4,
          label: '区域'
        }, {
          value: 5,
          label: '办公点'
        }],
        departmentTypeOptions: [{
          value: 1,
          label: '门店'
        }, {
          value: 2,
          label: '停车场'
        }, {
          value: 3,
          label: '交车中心'
        }, {
          value: 4,
          label: '维修厂'
        }],
        statusOptions: [{
          value: 1,
          label: '有效'
        }, {
          value: 0,
          label: '无效'
        }],
        longitudeOptions: [{
          value: 'E',
          label: 'E'
        }, {
          value: 'W',
          label: 'W'
        }],
        latitudeOptions: [{
          value: 'N',
          label: 'N'
        }, {
          value: 'S',
          label: 'S'
        }],
        //城市选择
        chooseCityVisible: false,
        provinceSearchList: [],
        citySearchList: [],
        countySearchList: [],
        chooseCityForm: {provinceChosen: '', cityChosen: '', countyChosen: ''},
        isCityDisable: true,
        isCountyDisable: true,
        // 城市选择_new
        cityOptions:[],
        //员工选择
        dialogEmployee: false,
        relAccount: 1,
        // 数据校验
        rulesDepartment: {
          departmentNo:[
            {required:true, validator:validDepartmentNo, trigger: 'blur'}
          ],
          departmentName: [
            {required: true, message: '请输入部门名称', trigger: 'blur'},
          ],
          telephone: [
            {required: true, message: '请输入手机号', trigger: 'blur'},
            {pattern: /^1[0-9]{10}$/, message: '手机号格式不正确', trigger: 'blur'}
          ],
          email: [
            {pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{1,4}){1,4})$/, message: '邮箱格式不正确', trigger: 'blur'}
          ],
          landline: [
            {pattern: /^[0-9]{4}\-[0-9]{7,8}$/, message: "座机号格式不正确，格式为'0592-8888888'", trigger: 'blur'}
          ],
          cityName: [
            {required: true, validator: validCityName, trigger: 'blur'}
          ],
          level: [
            { required:true, validator: validLevel, trigger: 'blur'}
          ],
          supports: [
            {required: true, validator: validSupports, trigger: 'change'},
          ],
          longitudeNum: [
            {validator: validLongitude, trigger: 'blur'}
          ],
          latitudeNum: [
            {validator: validLatitude, trigger: 'blur'}
          ],
          departmentType: [
            {required: true, validator: validDepartmentType, trigger: 'blur'}
          ],
          workplace: [
            {required: true, validator: validWorkplace, trigger: 'blur'}
          ],
          address: [
            {validator: validAddress, trigger: 'blur'}
          ]
          // ccc

        },
      }
    },
    provide(){
      return{
        changeDialogVisible:this.changeDialogVisible,
        chooseStaff:this.chooseStaff
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
        departmentNo: self.form.upperDepartmentNo,
        forIEFresh: new Date().getTime()
      }
      self.$http.get("department/getSupportBusiness.do_",{ params: param })
        .then(result => {
          var sups = result.split(/[&;]/);
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
    computed: {

    },
    destroyed () {
      // 结束时清除定时器
      if (this.timer) {
        clearInterval(this.timer);
      }
    },
    components: {employeeList},
    methods: {
      cancel () {
        this.$router.replace('/departmentManagement/showDepartment');
      },
      levelChange() {
        if(this.form.level==5) this.haveWorkplace=true;
        else this.haveWorkplace=false;
        this.$options.methods.checkInputByHand(this,'level');
        this.$options.methods.checkInputByHand(this,'address');
        this.$options.methods.checkInputByHand(this,'departmentNo');
      },
      deptTypeChange(){
        this.$options.methods.checkInputByHand(this,'departmentType');
      },
      checkInputByHand(self,name){
        self.$refs.form.validateField(name);
      },
      save () {
        var self=this;
        self.$refs['form'].validate(function (valid) {
          if(valid){
            self.form.supportBusiness = self.$options.methods.addSubSign(self.supports); // 添加分隔符;
            self.form.longitude = self.longitudeNum + self.longitudeDirection;
            self.form.latitude = self.latitudeNum + self.latitudeDirection;

            // 给不需要的值统一设置0避免占用数字
            if(self.haveWorkplace==false){
              self.form.workplace=0;
              self.departmentType=0;
            }

            // 前端校验输入
            //if(!self.$options.methods.checkInput(self)) return;
            // alert("假装添加成功！");
            // return;
            self.$http.post("department/addDepartment.do_",self.form)
              .then(result => {
                if(!result.result){
                  self.$message.error(result.msg);
                  return false;
                }
                self.$message.success("添加成功！");
                self.$router.replace("/departmentManagement/showDepartment");
              })
              .catch(function (error) {

              })

          }
        });

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
        formatDateTime = Y + '-' + M + '-' + D + '- ' + H + ':' + Min + ':' + S;
        return formatDateTime;
      },
      addSubSign (data) {
        if(data.length<=0) return "";
        var result = data[0];
        for(var i=1;i<data.length;i++){
          result=result+";"+data[i];
        }
        return result;
      },
      // 前端校验输入数据是否有问题
      checkInput (self) {
        var _form = self.form;
        // 校验部门编号 和 部门级别
        var pattern_deptNo;
        switch (_form.level) {
          case 1:
            pattern_deptNo=/^Z[a-zA-Z0-9]{6}$/;
            if(!pattern_deptNo.test(_form.departmentNo)){
              self.$message.error("【部门编号】不符合规范！总部：7位数字或字母，以Z开头。");
              return false;
            }
            break;
          case 2:
            pattern_deptNo=/^F[a-zA-Z0-9]{6}$/;
            if(!pattern_deptNo.test(_form.departmentNo)){
              self.$message.error("【部门编号】不符合规范！分公司：7位数字或字母，以F开头。");
              return false;
            }
            break;
          case 3:
            pattern_deptNo=/^G[a-zA-Z0-9]{6}$/;
            if(!pattern_deptNo.test(_form.departmentNo)){
              self.$message.error("【部门编号】不符合规范！管理部：7位数字或字母，以G开头。");
              return false;
            }
            break;
          case 4:
            pattern_deptNo=/^Q[a-zA-Z0-9]{6}$/;
            if(!pattern_deptNo.test(_form.departmentNo)){
              self.$message.error("【部门编号】不符合规范！区域：7位数字或字母，以Q开头。");
              return false;
            }
            break;
          case 5:
            pattern_deptNo=/^B[a-zA-Z0-9]{6}$/;
            if(!pattern_deptNo.test(_form.departmentNo)){
              self.$message.error("【部门编号】不符合规范！办公点：7位数字或字母，以B开头。");
              return false;
            }
            break;
          default:
            self.$message.error("请选择【部门级别】！");return false;
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

          if(_form.address.trim()==""){
            self.$message.error("请填写【详细地址】！");
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
        // 座机号
        if(_form.landline!=""){
          var pattern_landline = /^[0-9]{4}\-[0-9]{7,8}$/;
          if(!pattern_landline.test(_form.landline)){
            self.$message.error("请输入正确的【座机号】，格式为'0592-8888888'，或者不填写任何内容！");
            return false;
          }
        }
        // 经度
        if(self.longitudeNum!=""){
          var pattern_longitudeNum = /^((0|1?[0-7]?[0-9]?)(([.][0-9]{1,10})?)|180(([.][0]{1,10})?))$/;
          if(!pattern_longitudeNum.test(self.longitudeNum)){
            self.$message.error("【纬度】有误！格式：70.1234567");
            return false;
          }
        }
        // 纬度
        if(self.latitudeNum!=""){
          var pattern_latitudeNum = /^([0-8]?\d{1}\.\d{1,10}|90\.0{1,10})$/;
          if(!pattern_latitudeNum.test(self.latitudeNum)){
            self.$message.error("【经度】有误！格式：120.1234567");
            return false;
          }
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
        // 城市校验
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
        if(staffData.isDimission!=1){
          self.form.staffId = staffData.id;
          self.form.staffName = staffData.staffName;
        }else{
          self.$message.error("无法选择离职的员工！");
          self.dialogEmployee = !self.dialogEmployee;
        }
      },

      // 城市选择
      chooseProvince() {
        var self=this;
        var param={
          page:1,
          limit:10,
          regionCode:'',
          regionName:'',
          regionStatus: '',
          forIEFresh: new Date().getTime()
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
          forIEFresh: new Date().getTime()
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
          regionStatus: '',
          forIEFresh: new Date().getTime()
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
      cityFocus(){
        var self = this;
        self.$http.get('/regionManage/searchCityByKeyword.do_', {
          params: null
        }).then((result) => {
          self.cityOptions = result;
        }).catch(function (error) {
          self.$message.error("获取数据错误");
        });
      },
      remoteMethod(query) {
        var self = this;
        self.loading = true;
        setTimeout(() => {
          // this.loading = false;
          // this.cityOptions = this.list.filter(item => {
          //   return item.label.toLowerCase()
          //     .indexOf(query.toLowerCase()) > -1;
          // });
          var param = {
            keyword: query
          }
          this.$http.get('/regionManage/searchCityByKeyword.do_', {
            params: param
          }).then((result) => {
            self.loading = false;
            self.cityOptions = result;
          }).catch(function (error) {
            self.loading = false;
            self.$message.error("获取数据错误");
          });
        }, 200);
      },
      chooseCityDo2(){
        self.$message.info(this.countySearchList[2].regionName);
      },
      chooseCityDo() {
        var self = this;
        if(self.chooseCityForm.countyChosen != ""){
          for(var i=0;i<self.countySearchList.length;i++){
            if(self.countySearchList[i].cityID == self.chooseCityForm.countyChosen){
              self.cityName=self.countySearchList[i].regionName;
              self.form.cityId=self.chooseCityForm.countyChosen;
              self.chooseCityVisible=false;
              self.$options.methods.checkInputByHand(self,'cityName');
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
              self.$options.methods.checkInputByHand(self,'cityName');
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
              self.$options.methods.checkInputByHand(self,'cityName');
              return;
            }
          }
        }
        self.$message.error("请至少选择一个城市！");
        self.$options.methods.checkInputByHand(self,'cityName');
      },
      initSearch () {
        let vm = this;
        let map = this.amapManager.getMap();
        AMapUI.loadUI(['misc/PoiPicker'], function (PoiPicker) {
          var poiPicker = new PoiPicker({
            input: 'search',
            placeSearchOptions: {
              map: map,
              pageSize: 10
            },
            suggestContainer: 'searchTip',
            searchResultsContainer: 'searchTip'
          });
          vm.poiPicker = poiPicker;
          // 监听poi选中信息
          poiPicker.on('poiPicked', function (poiResult) {
            // console.log(poiResult)
            let source = poiResult.source;
            let poi = poiResult.item;
            if (source !== 'search') {
              poiPicker.searchByKeyword(poi.name)
            } else {
              poiPicker.clearSearchResults()
              vm.markers = []
              let lng = poi.location.lng
              let lat = poi.location.lat
              let address = poi.cityname + poi.adname + poi.name
              console.log(poi)
              vm.center = [lng, lat]
              vm.markers.push([lng, lat])
              vm.lng = lng
              vm.lat = lat
              vm.address = address
              vm.searchKey = address
              vm.form.address = poi.address;
              vm.longitudeNum = lng;
              vm.latitudeNum = lat
            }
          })
        })
      },
      searchByHand () {
        if (this.searchKey !== '') {
          this.poiPicker.searchByKeyword(this.searchKey)
        }
      },
      mapCencel() {
        this.baiduMapFlag = false
      },
      mapConfirm() {
        const self = this;
        self.form.address = self.searchKey
        self.baiduMapFlag = false;
        this.$options.methods.checkInputByHand(this,'address'); // 校验输入地址
      }
    }
  }
</script>

<style>
  .container {
    width: 700px;
    height: 450px;
    position: fixed;
    left: 50%;
    top: 50%;
    transform: translate3d(-50%, -50%, 0);
    border: 1px solid #999;
  }
  .search-box {
    position: absolute;
    z-index: 5;
    width: 70%;
    left: 13%;
    top: 10px;
    height: 30px;
  }
  .search-box input {
    float: left;
    width: 80%;
    height: 100%;
    border: 1px solid #30ccc1;
    padding: 0 8px;
    outline: none;
  }
  .search-box button {
    float: left;
    width: 20%;
    height: 100%;
    background: #30ccc1;
    border: 1px solid #30ccc1;
    color: #fff;
    outline: none;
  }
  .tip-box {
    width: 100%;
    max-height:260px;
    position: absolute;
    top: 30px;
    overflow-y: auto;
    background-color: #fff;
  }
</style>
