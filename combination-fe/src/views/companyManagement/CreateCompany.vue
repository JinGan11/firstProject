<template>
  <home>
    <div>
      <div style="margin-bottom: 10px;margin-left: 50px">
        <span style="font-size: 20px">基本信息</span>
        <span style="margin-left: 800px"><el-button type="primary" @click="save" style="width:70px">保存</el-button>
        <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
        </span>
       <hr ><br>
      </div>
      <div style="width:90%; margin-left: 70px">
        <el-form ref="ruleForm" :model="form" :rules="rules" label-width="90px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="公司名称:" prop="companyName">
                <el-input style="width:200px;" v-model="form.companyName" maxlength="60"></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="10">
              <el-form-item label="统一社会信用代码:" prop="creditCode">
                <el-input style="width:200px;" v-model="form.creditCode" maxlength="18"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="类型:" prop="companyType">
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
              <el-form-item label="营业期限:" prop="businessTerm" >
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
              <el-form-item label="住所:" prop="companyAddress">
                <el-input style="width:200px;" v-model="form.companyAddress" maxlength="100"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="经营范围:" prop="businessScope">
                <el-input style="width:200px;" v-model="form.businessScope" maxlength="200"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="法定代表人:" prop="legalPerson">
                <el-input style="width:200px;" v-model="form.legalPerson" maxlength="60"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="注册资本:" prop="registeredCapital">
                <el-input style="width:200px;" v-model="form.registeredCapital" maxlength="20"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="成立日期:" prop="establishTime">
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
              <el-form-item label="登记机关:" prop="registeredInstitution">
                <el-input style="width:200px;" v-model="form.registeredInstitution" maxlength="60"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="核准日期:" prop="causeTime">
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
              <el-form-item label="登记状态:" prop="registeredStatus">
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
              <el-form-item label="公司性质:" prop="companyNature">
                <el-radio-group v-model="form.companyNature">
                  <el-radio :label="1">一般纳税人</el-radio>
                  <el-radio :label="2">小规模纳税人</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="注册地址:" prop="registrationAddress">
                <el-input style="width:200px;" v-model="form.registrationAddress" maxlength="100"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="开户银行:" prop="bankName">
                <el-input style="width:200px;" v-model="form.bankName" maxlength="60"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="账号:" prop="bankAccount">
                <el-input style="width:200px;" v-model="form.bankAccount" maxlength="20"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="注册电话:" prop="telephone">
                <el-input style="width:200px;" v-model="form.telephone" maxlength="12"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="总公司标志:"  prop="companyMark" label-width="125px">
                <el-radio-group v-model="form.companyMark">
                  <el-radio  :label="1">总公司</el-radio>
                  <el-radio  :label="2">子公司</el-radio>
                </el-radio-group>
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
              <el-form-item label="新建人:">
                <el-input style="width:200px;" v-model="form.createEmp" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="新建时间:">
                <el-input style="width:200px;" v-model="nowTime" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="修改人:">
                <el-input style="width:200px;" v-model="form.modifyEmp" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="修改时间:">
                <el-input style="width:200px;" v-model="nowTime" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="状态:" prop="companyStatus">
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
              <el-form-item label="备注:" prop="remark">
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
        fileList: [],
        rules: {
          companyName: [
            { required: true, message: '请输入公司名称', trigger: 'blur' },
            { min: 2, max: 60, message: '长度在 2 到 60 个字符', trigger: 'blur' },
          ],
           creditCode: [
            { required: true, message: '请输入统一社会信用代码', trigger: 'blur' },
            { min: 15, max: 18, message: '长度在 15 到 18 个字符或数字', trigger: 'blur' },
            { pattern:/^([\da-zA-Z]+$)/,message: '只支持字母和数字，请勿输入其他字符', trigger: 'blur' }
          ],
          companyType:[
            { required: false, message: '公司类型', trigger: 'blur' },

          ],
          businessTerm:[
            { required: false, message: '营业期限', trigger: 'blur' },

          ],
          registeredCapital:[
            { required: false, message: '请选择数据类型'},
            { min: 1, max: 20, message: '长度在 1 到 20 位数字', trigger: 'blur' },
            { pattern:/^([\d]+$)/,message: '只支持数字，请勿输入其他字符', trigger: 'blur' }

          ],
          companyAddress:[
            { min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur' },
          ],
          businessScope:[
            { min: 2, max: 200, message: '长度在 2 到 200 个字符', trigger: 'blur' },
          ],
          legalPerson:[
            { min: 2, max: 60, message: '长度在 2 到 60 个字符', trigger: 'blur' },
          ],
          registeredInstitution:[
            { min: 2, max: 60, message: '长度在 2 到 60 个字符', trigger: 'blur' },
          ],
          establishTime:[
            { required: false, message: '成立日期'},

          ],
          causeTime:[
            { required: false, message: '核准日期'},

          ],
          registeredStatus:[
            { min: 2, max: 60, message: '长度在 2 到 60 个字符', trigger: 'blur' },
          ],
          registrationAddress:[
            { min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur' },
          ],
          companyNature:[
            { required: false, message: '公司性质'},

          ],
          companyMark:[
            { required: false, message: '总公司标志'},

          ],
          bankName:[
            { min: 2, max: 60, message: '长度在 2 到 60 个字符', trigger: 'blur' },
          ],
          bankAccount:[
            { min: 7, max: 20, message: '长度在 7 到 20 位数字', trigger: 'blur' },
          ],
          telephone:[
            { min: 11, max: 12, message: '长度在 11 到 12 位数字', trigger: 'blur' },
          ],
          companyStatus:[
            { required: false, message: '状态'},

          ],
          remark:[
            { required: false, message: '备注'},

          ],

        },
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
      save: function () {//保存新建公司信息
        const self = this;
        self.$refs["ruleForm"].validate(function (valid) {
          //if(self.$options.methods.checkInput(self)==false) return;
          if (valid) {
            self.$confirm('此操作将保存该文件, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              self.form.businessStartTime = self.businessTerm[0];
              self.form.businessDeadline = self.businessTerm[1];
              self.form.liscensePath = 'dfs';
              self.form.createEmp = '';
              self.form.modifyEmp = '';
              self.$http.post("company/createCompany", self.form)
                .then((result) => {
                  if (!result.result) {
                    alert(result.msg);
                    return false;
                  }
                  self.$alert("添加成功！");
                  self.$router.replace("/CompanyManagement");
                })
                .catch(function (error) {
                  commonUtils.Log("company/createCompany:" + error);
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
          } else {
            console.log('error submit!!');
            return false;
          }

        })
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


    },
  }


</script>
<style scoped>


</style>
