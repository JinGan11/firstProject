<template>
  <home>
    <div>
      <div style="margin-bottom: 10px;margin-left: 70px">
        <span style="font-size: 20px">基本信息</span>
        <span style="margin-left: 400px"><el-button type="primary" @click="save" style="width:70px">保存</el-button>
        <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
        </span>
       <hr ><br>
      </div>
      <div style="width:85%; margin-left: 70px">
        <el-form ref="form" :model="form" label-width="80px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="公司名称">
                <el-input style="width:200px;" v-model="form.companyName" maxlength="60"></el-input>
                <span style="color: red;">*</span>
              </el-form-item>
            </el-col>

            <el-col :span="10">
              <el-form-item label="统一社会信用代码">
                <el-input style="width:200px;" v-model="form.creditCode" maxlength="18"></el-input>
                <span style="color: red;">*</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="类型">
                <el-select v-model="form.companyType" clearable  style="width:200px;" placeholder="请选择">
                  <el-option
                    v-for="item in options1"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="营业期限" >
                <el-date-picker
                  v-model="businessTerm"
                  type="daterange"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>

              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="住所">
                <el-input style="width:200px;" v-model="form.companyAddress" maxlength="100"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="经营范围">
                <el-input style="width:200px;" v-model="form.businessScope" maxlength="200"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="法定代表人">
                <el-input style="width:200px;" v-model="form.legalPerson" maxlength="60"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="注册资本">
                <el-input style="width:200px;" v-model="form.registeredCapital" maxlength="20"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="成立日期">
                <el-date-picker
                  v-model="form.establishTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="登记机关">
                <el-input style="width:200px;" v-model="form.registeredInstitution" maxlength="60"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="核准日期">
                <el-date-picker
                  v-model="form.causeTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="登记状态">
                <el-input style="width:200px;" v-model="form.registeredStatus" maxlength="60"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <div style="margin-bottom: 10px">
            <span style="font-size: 20px">附件信息</span>
          </div>
            营业执照附件：
            <el-button type="primary" @click="uploadPic" >图片管理</el-button>

          <div style="margin-bottom: 10px">
            <br>
            <span style="font-size: 20px">发票信息</span>
            <hr ><br>
          </div>
          <el-row>
            <el-col :span="10">
              <el-form-item label="公司性质">
                <el-radio v-model="form.companyNature" label="1">一般纳税人</el-radio>
                <el-radio v-model="form.companyNature" label="2">小规模纳税人</el-radio>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="注册地址">
                <el-input style="width:200px;" v-model="form.registrationAddress" maxlength="100"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="开户银行">
                <el-input style="width:200px;" v-model="form.bankName" maxlength="60"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="账号">
                <el-input style="width:200px;" v-model="form.bankAccount" maxlength="20"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="注册电话">
                <el-input style="width:200px;" v-model="form.telephone" maxlength="12"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="总公司标志">
                <el-radio v-model="form.companyMark" label="1">总公司</el-radio>
                <el-radio v-model="form.companyMark" label="2">子公司</el-radio>
              </el-form-item>
            </el-col>
          </el-row>
          <div style="margin-bottom: 10px">
            <hr ><br>
            <span style="font-size: 20px">其他信息</span>
            <br>
          </div>
          <el-row>
            <el-col :span="10">
              <el-form-item label="新建人">
                <el-input style="width:200px;" v-model="form.createEmp" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="新建时间">
                <el-input style="width:200px;" v-model="nowTime" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="修改人">
                <el-input style="width:200px;" v-model="form.modifyEmp" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="修改时间">
                <el-input style="width:200px;" v-model="nowTime" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="状态">
                <el-select v-model="form.companyStatus" clearable  style="width:200px;" placeholder="请选择">
                  <el-option
                    v-for="item in options2"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="10">
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="备注">
                <el-input style="width:200px;" v-model="form.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>

    <el-dialog
      title="图片管理"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose">
      <span>

    <el-upload
      action="#"
      list-type="picture-card"
      :multiple="true"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :onChange="getFileList"
      :auto-upload="false"
      ref="upload">
      <i class="el-icon-plus"></i>
      </el-upload>
      只能上传jpg/png/gif文件,且不超过2M,最多只能上传20张图片
        <el-upload
          class="upload-demo"
          action="https://jsonplaceholder.typicode.com/posts/"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :file-list="fileList"
          list-type="picture">
        </el-upload>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="picDetail">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </home>
</template>

<script>
  import commonUtils from '../../common/commonUtils'

  export default {
    data() {
      return {
        businessTerm:[],
        dialogVisible:false,
        picDetail:false,
        dialogImageUrl: '',
        disabled: false,
        unUploadFile:[],
        form: {
          businessStartTime:'',
          businessDeadline:'',
          companyName:'',
          creditCode:'',
          companyType:'',
          companyAddress:'',
          businessScope:'',
          legalPerson:'',
          registeredCapital:'',
          establishTime:'',
          registeredInstitution:'',
          causeTime:'',
          registeredStatus:'',
          companyNature:'',
          registrationAddress:'',
          bankName:'',
          bankAccount:'',
          telephone:'',
          companyMark:'',
          createEmp:'',
          createTime:'',
          modifyTime:'',
          modifyEmp:'',
          companyStatus:'1',
          remark:'',
          liscensePath:'',
        },
        nowTime: '',
        options1: [{
          value: '',
          label: '全部'
        }, {
          value: '1',
          label: '有限责任公司'
        }, {
          value: '2',
          label: '股份有限公司'
        }],
        options2: [{
          value: '1',
          label: '有效'
        }, {
          value: '2',
          label: '无效'
        }],
        fileList: []
      }
    },
    activated() {
      commonUtils.Log("页面激活");

    },
    mounted() {
      this.form.createEmp=window.sessionStorage.getItem("loginUsername");
      this.form.modifyEmp=window.sessionStorage.getItem("loginUsername");
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
      save() {//保存新建公司信息
        var self=this;
        if(self.$options.methods.checkInput(self)==false) return;
        this.$confirm('此操作将保存该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          self.form.businessStartTime=self.businessTerm[0];
          self.form.businessDeadline=self.businessTerm[1];
          self.form.liscensePath='dfs';
          self.form.createEmp='';
          self.form.modifyEmp='';
          self.$http.post("company/createCompany", self.form)
            .then((result) => {
              self.$router.replace("/CompanyManagement");
            })
            .catch(function (error) {
              commonUtils.Log("company/createCompany:"+error);
              self.$message.error("新建公司失败");
            });
          this.$message({
            type: 'success',
            message: '保存成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消保存'
          });
        });
        // var self=this;
        // self.form.businessStartTime=self.businessTerm[0];
        // self.form.businessDeadline=self.businessTerm[1];
        // self.form.liscensePath='dfs';
        // self.form.createEmp='';
        // self.form.modifyEmp='';
      //   self.$http.post("company/createCompany",self.form)
      //     .then(result => {
      //       self.$router.replace("/CompanyManagement");
      //     })
      //     .catch(function (error) {
      //
      //     })
       },
      cancel(){//关闭新建公司页面，返回公司管理列表页面
        this.$router.replace('/CompanyManagement')
      },
      handleRemove(file, fileList) {
      },
      handlePreview(file) {
        this.dialogImageUrl = file.url;
        this.picDetail = true;
      },
      uploadPic(){
        this.dialogVisible = true;
      },
      handleClose(done,file,fileList) {
        done();
        for (let i = 0; i <this.unUploadFile.length ; i++) {
          this.fileList.push(this.unUploadFile[i]);
        }
        this.$refs.upload.clearFiles()
      },
      getFileList(file, fileList){
        this.unUploadFile=fileList;
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
      // 校验输入
      checkInput(self){
        var _form = self.form;
        // 公司名称
        if(_form.companyName=="" || _form.companyName.length<2){
          alert("【公司名称】不符合规范！至少输入2个字符！");
          return false;
        }
        // 统一社会信用代码
        var testCreditCode1 = /^[a-zA-z0-9]{15}$/;
        var testCreditCode2 = /^[a-zA-z0-9]{18}$/;
        if(!testCreditCode1.test(_form.creditCode) && !testCreditCode2.test(_form.creditCode)){
          alert("【统一社会信用代码】不符合规范！请输入15或18位字符！");
          return false;
        }
        // 注册资本
        if(_form.registeredCapital!=""){
          var testNum = /^[0-9]*$/;
          if(!testNum.test(_form.registeredCapital)){
            alert("【注册资本】只能填写数字！");
            return false;
          }
        }
        // 一些非必填的最少2字符的
        if(_form.companyAddress!="" && _form.companyAddress.length<2){
          alert("【住所】至少2个字符，非必填！");
          return false;
        }
        if(_form.businessScope!="" && _form.businessScope.length<2){
          alert("【经营范围】至少2个字符，非必填！");
          return false;
        }
        if(_form.legalPerson!="" && _form.legalPerson.length<2){
          alert("【法定代表人】至少2个字符，非必填！");
          return false;
        }
        if(_form.registeredInstitution!="" && _form.registeredInstitution.length<2){
          alert("【登记机关】至少2个字符，非必填！");
          return false;
        }
        if(_form.registeredStatus!="" && _form.registeredStatus.length<2){
          alert("【登记状态】至少2个字符，非必填！");
          return false;
        }
        if(_form.registrationAddress!="" && _form.registrationAddress.length<2){
          alert("【注册地址】至少2个字符，非必填！");
          return false;
        }
        if(_form.registrationAddress!="" && _form.registrationAddress.length<2){
          alert("【开户银行】至少2个字符，非必填！");
          return false;
        }
        // 银行卡账号
        var testBankAccount = /^[0-9]{7,20}$/;
        if(_form.bankAccount!="" && !testBankAccount.test(_form.bankAccount)){
          alert("【银行卡账号】只支持7-20位数字，非必填！");
          return false;
        }
        // 注册电话
        var testBankAccount = /^[0-9]{11,12}$/;
        if(_form.telephone!="" && !testBankAccount.test(_form.telephone)){
          alert("【注册电话】只支持11-12位数字，非必填！");
          return false;
        }
        return true;
      }
    },
  }


</script>
<style scoped>


</style>
