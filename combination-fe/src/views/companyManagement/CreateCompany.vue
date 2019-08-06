<template>
  <home>
    <div>
      <div style="margin-bottom: 10px;margin-left: 70px">
        <span style="font-size: 20px">基本信息</span>
        <span style="margin-left: 800px"><el-button type="primary" @click="save" style="width:70px">保存</el-button>
        <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
        </span>
       <hr ><br>
      </div>
      <div style="width:85%; margin-left: 70px">
        <el-form ref="form" :model="form" label-width="80px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="公司名称">
                <el-input style="width:200px;" v-model="form.companyName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="统一社会信用代码">
                <el-input style="width:200px;" v-model="form.creditCode"></el-input>
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
                <el-input style="width:200px;" v-model="form.companyAddress"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="经营范围">
                <el-input style="width:200px;" v-model="form.businessScope"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="法定代表人">
                <el-input style="width:200px;" v-model="form.legalPerson"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="注册资本">
                <el-input style="width:200px;" v-model="form.registeredCapital"></el-input>
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
                <el-input style="width:200px;" v-model="form.registeredInstitution"></el-input>
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
                <el-input style="width:200px;" v-model="form.registeredStatus"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <div style="margin-bottom: 10px">
            <br>
            <span style="font-size: 20px">附件信息</span>
            <hr ><br>
          </div>
          <el-row>
            <el-col :span="10">
            <p>附件照片</p>
            </el-col>
          </el-row>
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
                <el-input style="width:200px;" v-model="form.registrationAddress"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="开户银行">
                <el-input style="width:200px;" v-model="form.bankName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="账号">
                <el-input style="width:200px;" v-model="form.bankAccount"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="注册电话">
                <el-input style="width:200px;" v-model="form.telephone"></el-input>
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
                <el-input style="width:200px;" v-model="form.createTime" :disabled="true"></el-input>
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
                <el-input style="width:200px;" v-model="form.modifyTime" :disabled="true"></el-input>
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
  </home>
</template>

<script>
  import commonUtils from '../../common/commonUtils'

  export default {
    data() {
      return {
        businessTerm:[],

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
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
    },
    methods: {
      save() {//保存新建公司信息
        var self=this;
        self.form.businessStartTime=self.businessTerm[0];
        self.form.businessDeadline=self.businessTerm[1];
        self.form.liscensePath='dfs';
        self.$http.post("company/createCompany",self.form)
          .then(result => {
            self.$router.replace("/CompanyManagement");
          })
          .catch(function (error) {

          })
      },
      cancel(){//关闭新建公司页面，返回公司管理列表页面
        this.$router.replace('/CompanyManagement')
      },
    },
  }


</script>
<style scoped>


</style>
