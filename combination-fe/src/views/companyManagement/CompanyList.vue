<template>
  <home>
    <!--    <div class="block">-->
    <!--      <span class="demonstration">选择日期</span>-->
    <!--      <el-date-picker-->
    <!--        v-model="birthdayName"-->
    <!--        type="datetimerange"-->
    <!--        range-separator="至"-->
    <!--        start-placeholder="开始日期"-->
    <!--        end-placeholder="结束日期">-->
    <!--      </el-date-picker>-->

    <!--    </div>-->

    <div style="width:95%; margin-left: 10px">
      <el-form ref="form" :model="form" label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="企业名称" label-width="70px">
              <el-input style="width:200px;" v-model="form.companyName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="统一社会信用代码" label-width="135px" style="margin-left: 20px" >
              <el-input style="width:200px" v-model="form.creditCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="类型" style="margin-left: 60px">
              <el-select v-model="form.companyType" clearable  style="width:130px;" placeholder="请选择">
                <el-option
                  v-for="item in options1"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="公司性质" style="margin-left: 20px">
              <el-select v-model="form.companyNature" clearable  style="width:150px;" placeholder="请选择">
                <el-option
                  v-for="item in options2"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="状态" >
              <el-select v-model="form.companyStatus" clearable  style="width:200px;" placeholder="请选择">
                <el-option
                  v-for="item in options3"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="时间" >
              <el-date-picker
                v-model="form.birthdayName"
                type="datetimerange"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <div style="margin-bottom: 10px">
      <el-button type="primary" @click="createCompany" :disabled="companyButtonPermission.createPermission" style="width:70px">新建</el-button>
      <el-button type="primary" @click="modifyCompany(selection)" :disabled="disabled || companyButtonPermission.modifyPermission" style="width:70px">修改</el-button>
    </div>

    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange" >
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id" @change="changeButton"><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="id" label="编号" width="150">
        <template slot-scope="scope">
          <el-button type="text" @click="companyIdBtn(scope.row.id)">{{scope.row.id}}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="companyName" label="公司名称"width="150"></el-table-column>
      <el-table-column prop="creditCode" label="统一社会信用代码" width="120"></el-table-column>
      <el-table-column prop="companyType" label="类型" width="150" style="text-align: center">
        <template slot-scope="scope">
          {{CompanyTypeEnum[scope.row.companyType]}}
        </template>
      </el-table-column>
      <el-table-column prop="companyNature" label="公司性质" width="100" style="text-align: center">
        <template slot-scope="scope">
          {{CompanyNatureEnum[scope.row.companyNature]}}
        </template>
      </el-table-column>
      <el-table-column prop="companyMark" label="总公司标志" width="100" style="text-align: center">
        <template slot-scope="scope">
          {{CompanyMarkEnum[scope.row.companyMark]}}
        </template>
      </el-table-column>
      <el-table-column prop="companyStatus" label="状态" width="50" style="text-align: center">
        <template slot-scope="scope">
          {{CompanyStatusEnum[scope.row.companyStatus]}}
        </template>
      </el-table-column>
      <el-table-column prop="modifyTime" label="修改时间" width="140"></el-table-column>
      <el-table-column prop="modifyEmp" label="修改人" width="120"></el-table-column>
    </el-table>
    <el-pagination background
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   :current-page="currentPage"
                   :page-sizes="[10, 50, 100, 200]"
                   :page-size="pageSize"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total">
    </el-pagination>

    <el-dialog :title="title" :visible.sync="companyFlag" :close-on-click-modal="false" width="900px">
      <div class="dialog-main">
          <br>
          <span style="font-size: 20px">基本信息</span>
          <hr><br>
        <el-form :inline="true" :model="companyForm" class="demo-form-inline" :disabled="true" label-width="100px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="公司名称">
                <el-input style="width:200px;" v-model="companyForm.companyName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="统一社会信用代码">
                <el-input style="width:200px;" v-model="companyForm.creditCode"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
          <el-col :span="10">
            <el-form-item label="类型">
              <el-select v-model="companyForm.companyType" clearable  style="width:200px;" placeholder="请选择">
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
                <el-input style="width:200px;" v-model="companyForm.companyAddress"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="经营范围">
                <el-input style="width:200px;" v-model="companyForm.businessScope"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="法定代表人">
                <el-input style="width:200px;" v-model="companyForm.legalPerson"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="注册资本">
                <el-input style="width:200px;" v-model="companyForm.registeredCapital"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="成立日期">
                <el-date-picker
                  v-model="companyForm.establishTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="登记机关">
                <el-input style="width:200px;" v-model="companyForm.registeredInstitution"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="核准日期">
                <el-date-picker
                  v-model="companyForm.causeTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="登记状态">
                <el-input style="width:200px;" v-model="companyForm.registeredStatus"></el-input>
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
                <el-radio-group v-model="companyForm.companyNature">
                  <el-radio :label="1">一般纳税人</el-radio>
                  <el-radio :label="2">小规模纳税人</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="注册地址">
                <el-input style="width:200px;" v-model="companyForm.registrationAddress"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="开户银行">
                <el-input style="width:200px;" v-model="companyForm.bankName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="账号">
                <el-input style="width:200px;" v-model="companyForm.bankAccount"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="注册电话">
                <el-input style="width:200px;" v-model="companyForm.telephone"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="总公司标志">
                <el-radio-group v-model="companyForm.companyMark">
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
              <el-form-item label="新建人">
                <el-input style="width:200px;" v-model="companyForm.createEmp" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="新建时间">
                <el-input style="width:200px;" v-model="companyForm.createTime" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="修改人">
                <el-input style="width:200px;" v-model="companyForm.modifyEmp" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="修改时间">
                <el-input style="width:200px;" v-model="companyForm.modifyTime" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="状态">
                <el-select v-model="companyForm.companyStatus" clearable  style="width:200px;" placeholder="请选择">
                  <el-option
                    v-for="item in options3"
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
                <el-input style="width:200px;" v-model="companyForm.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <template slot="footer">
        <el-button type="primary" @click="checkCompanyInfoBtn">确定</el-button>
      </template>
    </el-dialog>
  </home>
</template>


<script>
  import commonUtils from '../../common/commonUtils'
  export default {
    data() {
      return {
        disabled:true,
        startTime:'',
        endTime:'',
        total: 0,
        currentPage: 1,
        pageSize: 10,
        form: {
          companyName: '',
          creditCode: '',
          companyType: '',
          companyNature: '',
          companyStatus: '',
          birthdayName:[],
        },

        companyButtonPermission: {
          createPermission: true,
          modifyPermission: true,
        },

        tableData: [],
        selection:'',
        id: '',
        companyName: '',
        creditCode: '',
        companyType: '',
        CompanyTypeEnum:{},
        companyNature: '',
        CompanyNatureEnum:{},
        companyMark: '',
        CompanyMarkEnum:{},
        companyStatus: '',
        CompanyStatusEnum:{},
        modifyTime: '',
        modifyEmp: '',

        title: '公司详情',
        companyFlag:false,
        businessTerm:[],
        companyForm:{
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
          value: '',
          label: '全部'
        }, {
          value: '1',
          label: '一般纳税人'
        }, {
          value: '2',
          label: '小规模纳税人'
        }],
        options3: [{
          value: '',
          label: '全部'
        }, {
          value: '1',
          label: '有效'
        }, {
          value: '2',
          label: '无效'
        }]
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
    },
    methods: {
      judgmentAuthority() {
        const self = this;
        let permission = self.$store.state.powerList;
        permission.forEach(item=>{
          if (item === 40) {
            self.companyButtonPermission.createPermission = false
          }
          if (item === 41) {
            self.companyButtonPermission.modifyPermission = false
          }
        });
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.currentPage = 1;
        this.fetchData(1, val);
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.fetchData(val, this.pageSize);
      },
      handleSelectionChange(val) {
        this.disabled = false;
        this.selection = val;
      },
      fetchData() { //获取数据
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          companyName:self.form.companyName,
          creditCode:self.form.creditCode,
          companyType:self.form.companyType,
          companyNature:self.form.companyNature,
          companyStatus:self.form.companyStatus,
          startTime:self.form.birthdayName[0],
          endTime:self.form.birthdayName[1],
        };
        self.$http.get('company/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          self.CompanyTypeEnum = result.CompanyTypeEnum;
          self.CompanyNatureEnum = result.CompanyNatureEnum;
          self.CompanyMarkEnum = result.CompanyMarkEnum;
          self.CompanyStatusEnum = result.CompanyStatusEnum;
          commonUtils.Log(self.tableData);
        }).catch(function (error) {
          commonUtils.Log("company/querylist.do_:"+error);
          self.$message.error("获取数据错误");
        });
      },
      createCompany(){//点击新建按钮，跳转到新建公司页面

        this.$router.replace('/CreateCompany')
      },
      modifyCompany(val) {//点击修改按钮，跳转到修改公司页面
        window.localStorage.setItem('companyId',val);
        //alert(window.localStorage.getItem('companyId'));
        this.$router.replace('/ModifyCompany')
      },
      changeButton(){
        this.disabled=false;
      },
      companyIdBtn(val){//显示公司详情
        var self = this;
        var companyId=val;
        self.companyFlag=true;
        var param = {
          companyId: companyId,
        };
        self.$http.get('company/getCompanyById.do_', {
          params: param
        }).then((result) => {
          self.companyForm=result.list.company;
          self.companyForm.createEmp=result.list.createEmp;
          self.companyForm.modifyEmp=result.list.modifyEmp;
          self.companyForm.companyType=String(self.companyForm.companyType);
          self.companyForm.companyStatus=String(self.companyForm.companyStatus);
          self.businessTerm=[result.list.company.businessStartTime,result.list.company.businessDeadline];
          //self.businessTerm[0]=result.list.company.businessStartTime;
          //self.businessTerm[1]=result.list.company.businessDeadline;
        }).catch(function (error) {
          commonUtils.Log("company/getCompanyById.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },
      checkCompanyInfoBtn(){//关闭公司详情信息
        this.companyFlag=false;
      },
    },

  }

</script>
<style scoped>


</style>
