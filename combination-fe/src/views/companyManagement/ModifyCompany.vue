<template>
  <home>
    <div>
      <div style="margin-bottom: 10px;margin-left: 70px">
        <span style="font-size: 20px">基本信息</span>
        <span style="margin-left: 800px"><el-button type="primary" @click="save" style="width:70px">保存</el-button>
        <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
        </span>
        <hr><br>
      </div>
      <div style="width:85%; margin-left: 70px">
        <el-form ref="ruleForm" :model="form" :rules="rules" label-width="90px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="公司名称:" prop="companyName">
                <el-input style="width:200px;" v-model="form.companyName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="统一社会信用代码:" prop="creditCode">
                <el-input style="width:200px;" v-model="form.creditCode"></el-input>
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
              <el-form-item label="营业期限:" prop="businessTerm">
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
                <el-input style="width:200px;" v-model="form.companyAddress"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="经营范围:" prop="businessScope">
                <el-input style="width:200px;" v-model="form.businessScope"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="法定代表人:" prop="legalPerson">
                <el-input style="width:200px;" v-model="form.legalPerson"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="注册资本:"  prop="registeredCapital">
                <el-input style="width:200px;" v-model="form.registeredCapital"></el-input>
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
                <el-input style="width:200px;" v-model="form.registeredInstitution"></el-input>
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
                <el-input style="width:200px;" v-model="form.registeredStatus"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <div style="margin-bottom: 10px">
            <br>
            <span style="font-size: 20px">附件信息</span>
            <hr ><br>
            营业执照附件：
          </div>
          <el-row>
            <el-col>
              <ul class="box">
                <license v-for="license in licenses" :license="license" :key="license.id" v-bind:isShow="true"
                         @delete-license="deleteLicense" @preview-license="previewLicense"
                      ></license>
              </ul>
            </el-col>
          </el-row>
          <el-upload
            action="#"
            list-type="picture-card"
            :multiple="true"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :onChange="getFileList"
            :beforeUpload="beforeAvatarUpload"
            :auto-upload="false"
            ref="upload"
            :http-request="uploadLicenses">
            <i class="el-icon-plus"></i>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png/gif文件,且不超过2M,最多只能上传20张图片</div>
          </el-upload>
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
                <el-input style="width:200px;" v-model="form.registrationAddress"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="开户银行:" prop="bankName">
                <el-input style="width:200px;" v-model="form.bankName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="账号:" prop="bankAccount">
                <el-input style="width:200px;" v-model="form.bankAccount"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="注册电话:" prop="telephone">
                <el-input style="width:200px;" v-model="form.telephone"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="总公司标志:" label-width="125px" prop="companyMark">
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
                <el-input style="width:200px;" v-model="form.createTime" :disabled="true"></el-input>
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
                <el-input style="width:200px;" v-model="form.modifyTime" :disabled="true"></el-input>
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
      :visible.sync="preview"
      width="720px">
      <div class="dialog-main" style="overflow: auto;height: 670px">
        <img :id="fileUrl+previewId" :src="fileUrl+previewId" height="650px" width="700px"/>
        </div>
    </el-dialog>
  </home>
</template>

<script>
  import commonUtils from '../../common/commonUtils'
  import license from './component/license';
  export default {
    name:"ModifyCompany",
    components: {license},
    data() {
      return {
        businessTerm:[],
        iscommit:true,
        previewId:'',
        preview:false,
        form: {
          oldCreditCode:'',
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
          companyStatus:'',
          remark:'',
          liscensePath:'',
        },
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
        rules: {
          companyName: [
            { required: true, message: '请输入公司名称', trigger: 'blur' },
            { min: 2, max: 60, message: '长度在 2 到 60 个字符', trigger: 'blur' },
          ],
          creditCode: [
            { required: true, message: '请输入统一社会信用代码', trigger: 'blur' },
            //{ min: 15, max: 18, message: '长度在 15 到 18 个字符或数字', trigger: 'blur' },
            { pattern:/(^[\da-zA-Z]{15}$)|(^[\da-zA-Z]{18}$)/,message: '长度为15或18位，且只支持字母和数字', trigger: 'blur' }
          ],
          companyType:[
            { required: false, message: '公司类型', trigger: 'blur' },

          ],
          businessTerm:[
            { required: false, message: '营业期限', trigger: 'blur' },

          ],
          registeredCapital:[
            { required: false, message: '请选择数据类型'},
            //{ min: 1, max: 20, message: '长度在 1 到 20 位数字', trigger: 'blur' },
            { pattern:/(^[1-9]{1}[\d]+$)/,message: '长度在 1 到 20 位数字，请勿输入其他字符', trigger: 'blur' }

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
            { pattern:/(^[\d]+$)/,message: '只支持 7 到 20 位数字，请勿输入其他字符', trigger: 'blur' }

          ],
          telephone:[
            { min: 11, max: 12, message: '长度在 11 到 12 位数字', trigger: 'blur' },
            { pattern:/(^[\d]+$)/,message: '只支持 11 到 12 位数字，请勿输入其他字符', trigger: 'blur' }

          ],
          companyStatus:[
            { required: false, message: '状态'},

          ],
          remark:[
            { required: false, message: '备注'},

          ],

        },
        licenses:[],
        fileUrl:"http://localhost:8081/combination/company/getLicense?id=",
        img:'',
        bigP:false,
        formData: new FormData()
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
      // bigPic(val){
      //   var viewer = new Viewer(document.getElementById(val), {
      //     url: val
      //   });
      // },
      //覆盖默认上传事件
      uploadLicenses(file) {
        this.formData.append("businessLicenses", file.file);
      },
      handleRemove(file, fileList) {
      },
      handlePreview(file) {

      },
      fetchData(){
        var companyId;
        var self = this;
        companyId=window.localStorage.getItem('companyId');
        var param = {
          companyId: companyId,
        };
        self.$http.get('company/getCompanyById.do_', {
          params: param
        }).then((result) => {
          self.form=result.list.company;
        self.form.createEmp=result.list.createEmp;
        self.form.modifyEmp=result.list.modifyEmp;
        self.form.registeredCapital=self.form.registeredCapital;
        self.businessTerm=[result.list.company.businessStartTime,result.list.company.businessDeadline];
        self.form.oldCreditCode=result.list.company.creditCode;
        self.licenses = result.licenses;
        //下拉框处理
        if(self.form.companyType==null){
          self.form.companyType="";
        }else{
          self.form.companyType=String(self.form.companyType);
        }

        if(self.form.companyStatus==null){
          self.form.companyStatus="";
        }else{
          self.form.companyStatus=String(self.form.companyStatus);
        }




      }).catch(function (error) {
          commonUtils.Log("company/getCompanyById.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },
      save() {//保存修改公司信息
        this.$refs.upload.submit();
        const self = this;
    //    console.log(self.form.registeredCapital)
        if(this.iscommit){
          self.$refs["ruleForm"].validate(function (valid) {
            if (valid) {
              // console.log(self.form.registeredCapital)
              //if(self.$options.methods.checkInput(self)==false) return;
              self.$confirm('此操作将保存该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                var companyId=window.localStorage.getItem('companyId');
                self.form.companyId=companyId;
                self.form.businessStartTime=self.businessTerm[0];
                self.form.businessDeadline=self.businessTerm[1];
                self.form.liscensePath='dfs';
                self.form.createEmp='';
                self.form.modifyEmp='';
                self.formData.append("company", JSON.stringify(self.form));
                //     console.log(self.formData.get("businessLicenses"));
                self.$http.post("company/modifyCompany",self.formData)
                  .then((result) => {
                    console.log(result.msg)
                    if(result.error ==="outLimit"){
                      self.$message.error("最多只能上传20张图片!");
                      self.formData=new FormData();
                      return ;
                    }
                    if (result.msg !="success") {
                      self.$alert("统一社会信用代码已存在，请重新修改！");
                    }else{
                      self.$alert("修改成功！");
                      self.$router.replace("/CompanyManagement");
                    }
                    self.formData=new FormData();
                  })
                  .catch(function (error) {
                    self.formData=new FormData();
                    commonUtils.Log("company/modifyCompany:"+error);
                    self.$message.error("修改公司失败");
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
              //    console.log('error submit!!');
              return false;
            }
          })
        }
          },
      cancel(){//关闭新建公司页面，返回公司管理列表页面
        this.$router.replace('/CompanyManagement')
      },
      //删除执照
      deleteLicense(id) {
        var self=this;
        self.$confirm("是否确认删除此执照附件?", '温馨提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          let param={
            id:id
          }
          self.$http.get('company/deleteLicense.do_', {
            params: param
          }).then((result) => {
            self.$message.success("删除成功");
            self.fetchData();
          }).catch(function (error) {
            commonUtils.Log("company/deleteLicense.do_:" + error);
            self.$message.error("获取数据错误");
          })
        })
      },
      beforeAvatarUpload(file) {
        var testmsg=file.name.substring(file.name.lastIndexOf('.')+1)
        const extension = testmsg === 'jpg'||testmsg === 'JPG';
        const extension2 = testmsg === 'png'||testmsg === 'PNG';
        const extension3 = testmsg === 'gif'||testmsg === 'GIF';
        const isLt2M = file.size / 1024 / 1024 ;
        if(!extension && !extension2 && !extension3) {
          this.$message({
            message: '上传文件只能是jpg/png/gif格式!',
            type: 'warning'
          });
          this.iscommit=false;
          this.formData=new FormData();
        }
        if(isLt2M >2) {
          this.$message({
            message: '上传文件大小不能超过 2MB!',
            type: 'warning'
          });
          this.iscommit=false;
          this.formData=new FormData();
        }
        if((extension || extension2 ||extension3)&& (isLt2M<2)){
          this.iscommit=true;
        }
        return (extension || extension2 ||extension3)&& (isLt2M<2)
      } ,
      previewLicense(id){
        this.preview=true;
        this.previewId=id;
      },
    },
  }


</script>
<style  scoped>
  ul,li{ padding:0; margin:0; overflow:hidden;}
  li{ list-style:none;}
  img{ border:0;}
  .box{ width:800px;}
  .box li{ float:left; width:200px; height:180px; margin-right:10px;}

</style>
