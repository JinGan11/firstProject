<template>
  <home>
    <div>
      <div style="margin-bottom: 10px;margin-left:0px">
        <el-button type="primary" @click="addCompanyBtn" style="width:70px">添加</el-button>
        <el-button type="primary" @click="removeCompanyBtn" style="width:70px">移除</el-button>
        <span style="margin-left: 700px">
        <el-button type="primary" @click="saveBtn" style="width:70px">保存</el-button>
        <el-button type="primary" @click="backBtn" style="width:70px">取消</el-button>
        </span>
        <hr ><br>
      </div>
      <el-table ref="multipleTable" :data="tableRelationData" border @selection-change="handleRelationChange" >
        <el-table-column type="selection" width="50"></el-table-column>
        <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
        <el-table-column prop="id" label="编号" width="100px">
          <template slot-scope="scope">
            <el-button type="text" @click="companyIdBtn(scope.row.id)">{{scope.row.id}}</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="companyName" label="公司名称" :show-overflow-tooltip='true' class="el-tooltip__popper"></el-table-column>
        <el-table-column prop="creditCode" label="统一社会信用代码" ></el-table-column>
        <el-table-column prop="companyType" label="类型"  style="text-align: center">
          <template slot-scope="scope">
            {{CompanyTypeEnum[scope.row.companyType]}}
          </template>
        </el-table-column>
        <el-table-column prop="companyNature" label="公司性质" width="110" style="text-align: center">
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
        <el-table-column prop="modifyTime" label="修改时间" ></el-table-column>
        <el-table-column prop="modifyName" label="修改人"></el-table-column>
      </el-table>
    </div>
    <div style="width:95%; margin-left: 10px">
      <el-dialog :title="title" :visible.sync="companyFlag" :close-on-click-modal="false" width="90%">
        <div class="dialog-main" style="overflow: auto">
          <el-form ref="form" :model="form" label-width="100px">
            <el-row>
              <el-col :span="6">
                <el-form-item label="企业名称" label-width="70px">
                  <el-input placeholder="企业名称" style="width:200px;" clearable v-model="form.companyName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="统一社会信用代码" label-width="135px" style="margin-left: 20px" >
                  <el-input placeholder="统一社会信用代码" style="width:200px" clearable v-model="form.creditCode"></el-input>
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
                  <el-select v-model="form.companyStatus" clearable :disabled="true" style="width:200px;" placeholder="请选择">
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
          <div style="margin-bottom: 10px">
            <el-button type="primary" @click="confirmBtn(selection)" style="width:70px">确认选择</el-button>
            <el-button type="primary" @click="cancel" style="width:70px">取消</el-button>
          </div>
          <el-table ref="multipleTable2" :data="tableData" border @selection-change="handleSelectionChange" >
            <el-table-column type="selection" width="50px"></el-table-column>
            <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
            <el-table-column prop="id" label="编号" width="150">
              <template slot-scope="scope">
                <el-button type="text" @click="companyIdBtn(scope.row.id)">{{scope.row.id}}</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="companyName" label="公司名称"width="150" :show-overflow-tooltip='true' class="el-tooltip__popper"></el-table-column>
            <el-table-column prop="creditCode" label="统一社会信用代码"></el-table-column>
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
            <el-table-column prop="companyStatus"  label="状态" width="50" style="text-align: center">
              <template slot-scope="scope" >
                {{CompanyStatusEnum[scope.row.companyStatus]}}
              </template>
            </el-table-column>
            <el-table-column prop="modifyTime" label="修改时间" width="140"></el-table-column>
            <el-table-column prop="modifyName" label="修改人"></el-table-column>
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
        </div>
      </el-dialog>
    </div>
    <el-dialog :title="title" :visible.sync="companyContentFlag" :close-on-click-modal="false" width="950px">
      <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 10px ;">
        <div style="font-family: Consolas; font-size:20px ;margin-bottom: 20px;">基本信息</div>
        <hr><br>
        <el-form :inline="true" :model="companyForm" class="demo-form-inline" :disabled="true" label-width="100px">
          <el-row>
            <el-col :span="9">
              <el-form-item label="公司名称:">
                <el-input style="width:200px;" v-model="companyForm.companyName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="统一社会信用代码:" label-width="140px">
                <el-input style="width:200px;" v-model="companyForm.creditCode"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="住所:">
                <el-input style="width:200px;" v-model="companyForm.companyAddress"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="经营范围:" label-width="140px">
                <el-input style="width:200px;" v-model="companyForm.businessScope"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="法定代表人:">
                <el-input style="width:200px;" v-model="companyForm.legalPerson"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="注册资本:" label-width="140px">
                <el-input style="width:200px;" v-model="companyForm.registeredCapital"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="类型:">
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
              <el-form-item label="登记机关:" label-width="140px">
                <el-input style="width:200px;" v-model="companyForm.registeredInstitution"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="登记状态:">
                <el-input style="width:200px;" v-model="companyForm.registeredStatus"></el-input>
              </el-form-item>

            </el-col>
            <el-col :span="10">
              <el-form-item label="成立日期:" label-width="140px">
                <el-date-picker
                  v-model="companyForm.establishTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>

            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="核准日期:">
                <el-date-picker
                  v-model="companyForm.causeTime"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col >
              <el-form-item label="营业期限:" >
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
          <div style="margin-bottom: 10px">
            <br>
            <span style="font-size: 20px">附件信息</span>
            <hr >营业执照附件：<br><br>
            <ul class="box">
              <li v-for ="item in licenses" :key="item.id">
                <img :id="fileUrl+item.id" :src="fileUrl+item.id" height="150px" width="200px"/>
              </li>
            </ul>
          </div>

          <div style="margin-bottom: 10px">
            <br>
            <span style="font-size: 20px">发票信息</span>
            <hr ><br>
          </div>
          <el-row>
            <el-col :span="10">
              <el-form-item label="注册地址:">
                <el-input style="width:200px;" v-model="companyForm.registrationAddress"></el-input>
              </el-form-item>

            </el-col>
            <el-col :span="10">
              <el-form-item label="公司性质:">
                <el-radio-group v-model="companyForm.companyNature">
                  <el-radio :label="1">一般纳税人</el-radio>
                  <el-radio :label="2">小规模纳税人</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="开户银行:">
                <el-input style="width:200px;" v-model="companyForm.bankName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="账号:">
                <el-input style="width:200px;" v-model="companyForm.bankAccount"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="注册电话:">
                <el-input style="width:200px;" v-model="companyForm.telephone"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="总公司标志:">
                <el-radio-group v-model="companyForm.companyMark">
                  <el-radio  :label="1">总公司</el-radio>
                  <el-radio  :label="2" style="margin-left: 28px">子公司</el-radio>
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
                <el-input style="width:200px;" v-model="companyForm.createEmp" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="新建时间:">
                <el-input style="width:200px;" v-model="companyForm.createTime" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="修改人:">
                <el-input style="width:200px;" v-model="companyForm.modifyEmp" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="修改时间:">
                <el-input style="width:200px;" v-model="companyForm.modifyTime" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="状态:">
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
              <el-form-item label="备注:">
                <el-input style="width:200px;" v-model="companyForm.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <template slot="footer">
        <div align="center">
          <el-button type="primary" @click="checkCompanyInfoBtn">确定</el-button>
        </div>
      </template>
    </el-dialog>
  </home>
</template>
<script>
  import commonUtils from '../../common/commonUtils'
  export default {
    data() {
      return {
        dep:[],

        newRelationList:[],
        oldRelationList:[],
        tableRelationData:[],
        relationSelection:[],
        companyChangesList:[],
        relationCompany:[],
        title:"公司详情页面",
        companyFlag:false,
        total: 0,
        currentPage: 1,
        pageSize: 10,
        form: {
          companyName: '',
          creditCode: '',
          companyType: '',
          companyNature: '',
          companyStatus: "1",
          birthdayName:[],
        },
        companyContentFlag:false,
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
        tableData: [],
        companyList:[],
        selection:[],
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
        licenses:[],
        fileUrl:"http://localhost:8081/combination/company/getLicense?id=",
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
      this.fetchDataRel();//加载关联公司信息
      commonUtils.Log("页面进来");
    },
    methods: {
      fetchDataRel(){//获取相关公司信息
        var self = this;
        //window.localStorage.setItem("departmentId",1);
        var param = {
          departmentId:window.localStorage.getItem("departmentRelId"),
        };
        self.$http.get('company/querylistRel.do_', {
          params: param
        }).then((result) => {
          self.tableRelationData = result.list;
        for(let i=0;i<self.tableRelationData.length;i++){
          self.relationCompany.push(self.tableRelationData[i].id);
        }
        self.companyChangesList=result.list;
        self.CompanyTypeEnum = result.CompanyTypeEnum;
        self.CompanyNatureEnum = result.CompanyNatureEnum;
        self.CompanyMarkEnum = result.CompanyMarkEnum;
        self.CompanyStatusEnum = result.CompanyStatusEnum;
      }).catch(function (error) {
          commonUtils.Log("company/querylistRel.do_:"+error);
          self.$message.error("获取数据错误");
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
      fetchData() { //获取数据，不相关但是有效的数据包括关联公司表中status为无效
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          departmentId:window.localStorage.getItem("departmentRelId"),
          companyName:self.form.companyName,
          creditCode:self.form.creditCode,
          companyType:self.form.companyType,
          companyNature:self.form.companyNature,
          companyStatus:self.form.companyStatus,
          startTime:self.form.birthdayName[0],
          endTime:self.form.birthdayName[1],
        };
        self.$http.get('company/queryRelationList.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
        //判断是0还是Null
        self.total = result.page.totalCount;
        self.CompanyTypeEnum = result.CompanyTypeEnum;
        self.CompanyNatureEnum = result.CompanyNatureEnum;
        self.CompanyMarkEnum = result.CompanyMarkEnum;
        self.CompanyStatusEnum = result.CompanyStatusEnum;
        commonUtils.Log(self.tableData);
      }).catch(function (error) {
          commonUtils.Log("company/queryRelationList.do_:"+error);
          self.$message.error("获取数据错误");
        });
      },
      confirmBtn(){//确认选择
        for(let i=0;i<this.selection.length;i++){
          let flag=0;
          for(let j=0;j<this.companyChangesList.length;j++){
            if(this.selection[i].id==this.companyChangesList[j].id){
              flag=1;
            }
          }
          if(flag==0){
            this.companyChangesList.push(this.selection[i]);
          }
        }

        this.tableRelationData=this.companyChangesList;
        this.companyFlag=false;

      },

      cancel(){//返回关联公司页面
        this.companyFlag=false;
      },
      handleSelectionChange(val){
        this.selection = val;

      },
      handleRelationChange(val){
        this.relationSelection=val;
      },
      addCompanyBtn(){//添加公司
        this.companyFlag=true;
        if(this.selection.length){
          this.$refs.multipleTable2.clearSelection();
        }
        this.fetchData();
        this.selection=[];
      },
      removeCompanyBtn(){//移除公司
        for(let i=0;i<this.relationSelection.length;i++){
          for(let j=0;j<this.companyChangesList.length;j++){
            if(this.relationSelection[i].id==this.companyChangesList[j].id){
              this.companyChangesList.splice(j,1);
            }
          }
        }
        //前端关联公司表动态变化
        this.tableRelationData=this.companyChangesList;
      },
      saveBtn(){//保存按钮，传送新旧关联公司信息
        var self = this;
        for(let i=0;i<this.companyChangesList.length;i++){
          self.newRelationList.push(String(this.companyChangesList[i].id));
        }
        for(let i=0;i<this.relationCompany.length;i++){
          self.oldRelationList.push(String(this.relationCompany[i]));
        }
        self.departmentId=window.localStorage.getItem("departmentRelId");
        var param = {
          departmentId:self.departmentId,

          newRelationList:self.newRelationList+'',
          oldRelationList:self.oldRelationList+'',
        };
        self.$http.get("company/saveRelation.do_", {
          params: param
        }).then((result) => {
          self.$alert("保存成功！");
        this.$router.replace("/departmentManagement/showDepartment");
      }).catch(function (error) {
          commonUtils.Log("company/querylist.do_:"+error);
          self.$message.error("获取数据错误");
        });

      },
      backBtn(){
        this.$router.replace("/departmentManagement/showDepartment");
      },
      checkCompanyInfoBtn(){
        var self = this;
        self.companyContentFlag=false;
      },
      companyIdBtn(val){//显示公司详情
        var self = this;
        var companyId=val;
        self.companyContentFlag=true;
        var param = {
          companyId: companyId,
        };
        self.$http.get('company/getCompanyById.do_', {
          params: param
        }).then((result) => {
          self.companyForm=result.list.company;
        self.companyForm.createEmp=result.list.createEmp;
        self.companyForm.modifyEmp=result.list.modifyEmp;
        self.licenses = result.licenses;
        //处理下拉框
        if(self.companyForm.companyType==null){
          self.companyForm.companyType="";
        }else{
          self.companyForm.companyType=String(self.companyForm.companyType);
        }

        if(self.companyForm.companyStatus==null){
          self.companyForm.companyStatus="";
        }else{
          self.companyForm.companyStatus=String(self.companyForm.companyStatus);
        }
        self.businessTerm=[result.list.company.businessStartTime,result.list.company.businessDeadline];
      }).catch(function (error) {
          commonUtils.Log("company/getCompanyById.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },
    },
  }
</script>
<style scoped>
  ul,li{ padding:0; margin:0; overflow:hidden;}
  li{ list-style:none;}
  img{ border:0;}
  .box{ width:800px;}
  .box li{ float:left; width:200px; height:180px; margin-right:10px;}

</style>
<style>
  .el-tooltip__popper {
    font-size: 14px; max-width:20%
  } /*设置显示隐藏部分内容，按50%显示*/
</style>
