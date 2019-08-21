<template>
  <home>
    <el-form :inline="true" :model="formInline" class="demo-form-inline" label-width="100px">
      <el-row>
        <el-col :span="7">
          <el-form-item label="部门名称">
            <el-input v-model="formInline.departmentName" clearable>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="负责人ID">
            <el-input v-model="formInline.staffId" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="负责人姓名">
            <el-input v-model="formInline.staffName" clearable></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="7">
          <el-form-item label="手机号">
            <el-input v-model="formInline.telephone" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="部门所在城市">
            <el-autocomplete
              v-model="formInline.cityName"
              :fetch-suggestions="querySearchAsync"
              placeholder="请输入内容"
              @select="handleSelect"
              clearable
            ></el-autocomplete>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="上级部门">
            <el-input v-model="formInline.upperDepartmentName" disabled>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col style="width: 100px">
          <el-button type="text" @click="selectDepartment" :span="2">选择</el-button>
          <el-button type="text" @click="clearDepartment" :span="2">清空</el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="7">
          <el-form-item label="部门级别">
            <el-select v-model="formInline.level">
              <el-option label="全部" value=""></el-option>
              <el-option label="总部" value="1"></el-option>
              <el-option label="分公司" value="2"></el-option>
              <el-option label="管理部" value="3"></el-option>
              <el-option label="区域" value="4"></el-option>
              <el-option label="办公点" value="5"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="状态">
            <el-select v-model="formInline.status" value="0">
              <el-option label="无效" value="0"></el-option>
              <el-option label="有效" value="1"></el-option>
              <el-option label="全部" value=""></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="部门类型">
            <el-select v-model="formInline.departmentType" value="0">
              <el-option label="全部" value=""></el-option>
              <el-option label="门店" value="1"></el-option>
              <el-option label="停车场" value="2"></el-option>
              <el-option label="交车中心" value="3"></el-option>
              <el-option label="维修厂" value="4"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col style="text-align: center">
          <el-form-item size="100px">
            <div>
              <el-button type="primary" @click="Search" style="width: 100px">查询</el-button>
              <el-button @click="exportDepartment" style="width: 100px">导出</el-button>
            </div>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>
    <el-table ref="multipleTable" :data="tableData" border @selection-change='handleSelectionChange'>
      <el-table-column label="部门编号" width="140">
        //此处是BUG吧，一摸一样的写法，上面个TEMPLATE就不行，下面个行
        <!--  <template slot-scope="scope">
            <el-button type="text" size="small" @click="ChooseOnDetail(scope.row.id)">{{scop.row.departmentNo}}</el-button>
          </template>-->
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="ChooseOnDetail(scope.row.id)">{{scope.row.departmentNo}}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="workplace" label="办公点标识" width="140"></el-table-column>
      <el-table-column prop="departmentName" label="部门名称" width="140"></el-table-column>
      <el-table-column prop="staffId" label="负责人ID" width="140"></el-table-column>
      <el-table-column prop="staffName" label="负责人姓名" width="140"></el-table-column>
      <el-table-column prop="telephone" label="手机号" width="140"></el-table-column>
      <el-table-column prop="level" label="部门级别" width="140">
        <template slot-scope="scope">
          {{LevelEnum[scope.row.level]}}
        </template>
      </el-table-column>
      <el-table-column prop="upperDepartmentNo" label="上级部门" width="140"></el-table-column>
      <el-table-column prop="supportBusiness" label="支持业务线" width="140"></el-table-column>
      <el-table-column prop="departmentType" label="部门类型" width="140">
        <template slot-scope="scope">
          {{DepartmentTypeEnum[scope.row.departmentType]}}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="140">
        <template slot-scope="scope">
          {{StatusEnum[scope.row.status]}}
        </template>
      </el-table-column>
      <el-table-column prop="cityName" label="所在城市" width="140"></el-table-column>
      <el-table-column prop="companyName" label="关联公司名称" width="140">
        <template slot-scope="scope">
          <el-popover
            placement="bottom"
            width="200"
            trigger="hover"
            :content="scope.row.companyName">
            <el-button slot="reference" type="text">{{scope.row.companyName|ellipsis}}</el-button>
          </el-popover>
        </template>
      </el-table-column>
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
    <el-dialog title="详情信息" :visible.sync="dialogVisibleDetail" :close-on-click-modal="false" width="900px">
      <el-form ref="form" :model="formDetail" label-width="80px">
        <hr style="height: 1px">
        <div>
          <div style="font-family:Consolas;font-size:20px;margin-left: 50px;margin-bottom: 20px;">申请信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="部门编号" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.departmentNo" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="部门名称" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.departmentName" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="负责人ID" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.staffId" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="负责人姓名" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.staffName" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="手机号" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.telephone" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.email" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="座机号" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.landline" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所在城市" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.cityName" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="详细地址" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.address" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="经度" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.longitude" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="纬度" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.latitude" disabled></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <hr style="height: 1px">
        <div>
          <div style="font-family:Consolas;font-size:20px;margin-left: 50px;margin-bottom: 20px;">业务信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="部门级别" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.level" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="上级部门" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.upperDepartmentNo" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="支持业务线" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.supportBusiness" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="部门类型" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.departmentType" disabled>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="办公点标识" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.workplace" disabled></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <hr style="height: 1px">
        <div>
          <div style="font-family:Consolas;font-size:20px;margin-left: 50px;margin-bottom: 20px;">关联公司列表</div>
          <el-table :data="tableCity" ref="multipleTable">
            <el-table-column prop="companyId" label="公司编号"></el-table-column>
            <el-table-column prop="companyName" label="公司名称"></el-table-column>
            <el-table-column prop="creditCode" label="统一社会信用代码"></el-table-column>
            <el-table-column prop="companyType" label="类型">
              <template slot-scope="scope">
                {{CompanyTypeEnum[scope.row.companyType]}}
              </template>
            </el-table-column>
            <el-table-column prop="companyNature" label="公司性质">
              <template slot-scope="scope">
                {{CompanyNatureEnum[scope.row.companyNature]}}
              </template>
            </el-table-column>
            <el-table-column prop="companyMark" label="总公司标志">
              <template slot-scope="scope">
                {{CompanyMarkEnum[scope.row.companyMark]}}
              </template>
            </el-table-column>
            <el-table-column prop="status" label="公司状态">
              <template slot-scope="scope">
                {{CompanyStatusEnum[scope.row.status]}}
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background
                         @size-change="handleSizeChangeCompany"
                         @current-change="handleCurrentChangeCompany"
                         :current-page="currentPageCompany"
                         :page-sizes="[5, 10, 15, 20]"
                         :page-size="pageSizeCompany"
                         layout="total, sizes, prev, pager, next, jumper"
                         :total="totalCompany">
          </el-pagination>
        </div>
        <br>
        <br>
        <br>
        <hr style="height: 1px">
        <div>
          <div style="font-family:Consolas;font-size:20px;margin-left: 50px;margin-bottom: 20px;">部门及部门下属所在城市</div>
              <el-input style="width: 90%;text-align: center" v-model="this.departmentListById.toString()" disabled></el-input>
        </div>
        <hr style="height: 1px">
        <div>
          <div style="font-family:Consolas;font-size:20px;margin-left: 50px;margin-bottom: 20px;">其他信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="新建时间" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.createTime" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="新建人" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.createEmpName" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="修改时间" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.modifyTime" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="修改人" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.modifyEmpName" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.status" disabled></el-input>
              </el-form-item>
            </el-col>
            <br>
          </el-row>
          <el-row >
            <el-col >
              <el-form-item label="备注" >
                <el-input type="textarea"  v-model="formDetail.remark" disabled></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
    </el-dialog>
    <el-dialog title='请选择需要导出的字段' :visible.sync="exportDialogVisible" :close-on-click-modal="false" width="600px">
      <template>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="checkdepartment" @change="handlecheckRolesChange">
          <el-checkbox v-for="city in department" :label="city" :key="city">{{city}}</el-checkbox>
        </el-checkbox-group>
      </template>
      <template slot="footer">
        <el-button type="primary" @click="exportExcel">确定导出</el-button>
        <el-button @click="cancel">取 消</el-button>
      </template>
    </el-dialog>
    <!--选择部门时弹出的窗口-->
    <el-dialog :title='titleDepartment' :visible.sync="dialogVisibleDepartment" :close-on-click-modal="false"
               width="50%">
      <div>
        <span>选择要操作的部门</span>
        <br><br>
        <el-tree
          ref="tree"
          :props="defaultProps"
          node-key="id"
          :load="loadNode"
          lazy="true"
          check-strictly
          show-checkbox
          :render-content="renderContent"
          @check-change="handleClick">
        </el-tree>
        <br>
        <el-button type="primary" @click="getCheckedDepartment">确定</el-button>
      </div>
    </el-dialog>
  </home>
</template>
<script>
  const departmentOptions = ['部门编号', '办公点标识', '部门名称', '负责人ID', '负责人姓名', '手机号', '部门级别', '上级部门', '支持业务线', '部门类型', '状态', '所在城市', '关联公司名称'];
  export default {
    data() {
      return {
        options: [],
        value: [],
        list: [],
        state:'',
        loading: false,
        total: 0,
        currentPage: 1,
        pageSize: 10,
        totalCompany: 0,
        currentPageCompany: 1,
        pageSizeCompany: 5,
        companyId:'',
        departmentId:'',
        formDetail: {
          departmentNo: '',
          departmentName: '',
          staffId: '',
          staffName: '',
          telephone: '',
          email: '',
          landline: '',
          cityName: '',
          address: '',
          level: '',
          upperDepartmentNo: '',
          supportBusiness: '',
          departmentType: '',
          workplace: '',
          createTime: '',
          createEmp: '',
          createEmpName:'',
          modifyTime: '',
          modifyEmp: '',
          modifyEmpName:'',
          status: '',
          remark: '',
          longitude: '',
          latitude: '',
        },
        formInline: {
          departmentName: '',
          staffId: '',
          staffName: '',
          telephone: '',
          cityName: '',
          level: '',
          upperDepartmentName: '',
          upperDepartmentNo: '',
          status: '',
          departmentType: ''
        },
        defaultProps: {
          label: 'departmentName',
          children: 'children',
          id: 'id',
          status: 'status'
        },
        tableData: [],
        tableCity: [],
        department_no: '',
        dialogVisibleDetail: false,
        selection: '',
        DepartmentTypeEnum: {},
        StatusEnum: {},
        LevelEnum: {},
        CompanyStatusEnum: {},
        CompanyTypeEnum: {},
        CompanyMarkEnum: {},
        CompanyNatureEnum: {},
        DepartmentStatusEnum: {},
        AccountStatusEnums: {},
        DepartmetnLevelEnums: {},
        titleDepartment: '选择部门',
        dialogVisibleDepartment: false,
        exportDialogVisible: false,
        checkAll: false,
        checkdepartment: [],
        department: departmentOptions,
        isIndeterminate: true,
        filterVal: [],
        list: [],
        disabled: true,
        departmentList: [],
        departmentListById:'',
      }
    },
    /*这里在关联部门多出两个的时候用'...'表示，现在还不能实现此功能
    filters:{
      ellipsis(value){
      if(!value) return '';
      else {
        this.values=value.split(';');
        if (this.values.length>2){
        return this.values[0]+';'+this.values[1]+'...';
         }else {
        return value;
        }
      }
    }
      },*/
    filters:{
    ellipsis(value){
      if(value&& value.length > 10) {
        value= value.substring(0,10)+ '...';
      }
      return value;
    }
  },
    methods: {
      //编号点击开始详情页
      ChooseOnDetail(val) {
        // alert(this.row.tableData[0].departmentNo);
        this.departmentListById='';
        this.departmentId=val;
        var param = {
          //id: this.selection,
          id: val,
          page: this.currentPageCompany,
          limit: this.pageSizeCompany,
          date:new Date().getTime(),
        };
        this.$http.get("/department/selectDepartment.do_", {
          params: param
        }).then((resultss) => {
          this.formDetail = resultss.department;
          this.CompanyStatusEnum = resultss.CompanyStatusEnum;
          this.CompanyTypeEnum = resultss.CompanyTypeEnum;
          this.CompanyMarkEnum = resultss.CompanyMarkEnum;
          this.CompanyNatureEnum = resultss.CompanyNatureEnum;
          //枚举类型转换
          this.formDetail.departmentType = resultss.DepartmentTypeEnum[this.formDetail.departmentType];
          this.formDetail.level = resultss.LevelEnum[this.formDetail.level];
          this.formDetail.status = resultss.DepartmentStatusEnum[this.formDetail.status];

        }).catch(function (error) {

          this.$message.error("获取数据错误");
        });
        this.SearchCompany();
        this.SearchCityList();
        this.departmentId='';
        this.dialogVisibleDetail = true;
      },

      //查找公司的集合，用于分页
      SearchCompany(){
        var param={
        id:this.departmentId,
        page:this.currentPageCompany,
        limit:this.pageSizeCompany,
        date:new Date().getTime(),
       }
       this.$http.get('/company/queryCompanyList.do_',{
       params:param,
      }).then((result)=>{
         this.totalCompany = result.page.totalCount;
         this.tableCity = result.page.list;
      });
      },

      //查找本部门即下属部门所在城市
      SearchCityList(){
      var param={
        id:this.departmentId,
        date:new Date().getTime(),
      }
      this.$http.get('/regionManage/citySearchListById.do_',{
        params:param,
      }).then((result)=>{
        this.departmentListById=result.cityList;
      })

    },
      handleSelectionChange(val) {
        this.selection = val;
      },
      Search() {
        //this.formInline.cityName='全部';
        if (this.formInline.cityName=='全部'){
          this.formInline.cityName='';
        }
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          departmentName: self.formInline.departmentName,
          staffName: self.formInline.staffName,
          staffId: self.formInline.staffId,
          telephone: self.formInline.telephone,
          cityName: self.formInline.cityName,
          level: self.formInline.level,
          upperDepartmentNo: self.formInline.upperDepartmentNo,
          status: self.formInline.status,
          departmentType: self.formInline.departmentType,
          date:new Date().getTime(),
        };
        self.$http.get('/department/searchDepartment.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.DepartmentTypeEnum = result.DepartmentTypeEnum;
          self.StatusEnum = result.StatusEnum;
          self.total = result.page.totalCount;
          self.LevelEnum = result.LevelEnum;
          self.departmentList = result.listSearch;
        }).catch(function (error) {
          self.$message.error("获取数据错误");
        });
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.currentPage = 1;
        this.Search(1, val);
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.Search(val, this.pageSize);
      },
      handleSizeChangeCompany(val) {
        this.pageSizeCompany = val;
        this.currentPageCompany = 1;
        //this.Search(1, val);
      },
      handleCurrentChangeCompany(val) {
        this.currentPageCompany = val;
        //this.Search(val, this.pageSizeCompany);
      },
      exportExcel() {
        if (this.checkdepartment.length === 0) {
          this.$message({
            showClose: false,
            message: '请选择需要导出的字段',
            type: 'error'
          });
        } else {
          require.ensure([], () => {
            const {export_json_to_excel} = require('../../excel/Export2Excel');
            const tHeader = this.checkdepartment;
            // 上面设置Excel的表格第一行的标题

            const filterVal = this.exportField(this.checkdepartment);
            // 上面的staffNum、accountId、staffName是tableData里对象的属性
            const list = this.departmentList;  //把data里的tableData存到list
            for (let i = 0; i < list.length; i++) {
              list[i].departmentType = this.DepartmentTypeEnum[list[i].departmentType];
              list[i].status = this.StatusEnum[list[i].status];
              list[i].level = this.LevelEnum[list[i].level];
            }
            //获取当前时间
            var date = new Date();
            var seperator1 = "-";
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
              month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
              strDate = "0" + strDate;
            }
            var currentdate = year + seperator1 + month + seperator1 + strDate;
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '部门管理 '+currentdate);
            this.$message({
              showClose: true,
              message: '文件导出成功',
              type: 'success'
            });
            this.exportDialogVisible = false;
            this.checkdepartment = [];
            this.filterVal = [];
          })
        }
      },
      exportDepartment() {
        this.checkAll=false;
        this.exportDialogVisible = true;
      },
      cancel() {
        this.exportDialogVisible = false;
      },
      //将每列与数据对应
      exportField(val) {
        for (let i = 0; i < val.length; i++) {
          if (this.checkdepartment[i] === '部门编号') {
            this.filterVal.push('departmentNo')
          } else if (this.checkdepartment[i] === '办公点标识') {
            this.filterVal.push('workplace')
          } else if (this.checkdepartment[i] === '部门名称') {
            this.filterVal.push('departmentName')
          } else if (this.checkdepartment[i] === '负责人ID') {
            this.filterVal.push('staffId')
          } else if (this.checkdepartment[i] === '负责人姓名') {
            this.filterVal.push('staffName')
          } else if (this.checkdepartment[i] === '手机号') {
            this.filterVal.push('telephone')
          } else if (this.checkdepartment[i] === '部门级别') {
            this.filterVal.push('level')
          } else if (this.checkdepartment[i] === '上级部门') {
            this.filterVal.push('upperDepartmentNo')
          } else if (this.checkdepartment[i] === '支持业务线') {
            this.filterVal.push('supportBusiness')
          } else if (this.checkdepartment[i] === '部门类型') {
            this.filterVal.push('departmentType')
          } else if (this.checkdepartment[i] === '状态') {
            this.filterVal.push('status')
          } else if (this.checkdepartment[i] === '所在城市') {
            this.filterVal.push('cityName')
          } else if (this.checkdepartment[i] === '关联公司名称') {
            this.filterVal.push('companyName')
          }
        }
        return this.filterVal;
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      handleCheckAllChange(val) {
        this.checkdepartment = val ? departmentOptions : [];
        this.isIndeterminate = false;
      },
      handlecheckRolesChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.department.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.department.length;
      },
      /*selectDepartment到getCheckedDepartment是对应的部门操作*/
      selectDepartment() {//选择部门
        this.dialogVisibleDepartment = true;
      },
      clearDepartment() {//清除部门的值
        this.formInline.upperDepartmentName = '';
        this.formInline.upperDepartmentNo='';
      },
      loadNode(node, resolve) {
        var self = this;
        self.$http.get('department/buildTree2.do_', {
          params: null
        }).then((result) => {
          resolve([result.departmentDto]);
        }).catch(function (error) {

        });
      },
      renderContent(h, {node, data, store}) {
        // 这里编译器有红色波浪线不影响运行...
        if (data.status != 1) {
          return (
            < span
          style = "color:red" > {node.label} < /span>
        )
          ;
        } else {
          return (
            < span > {node.label} < /span>
        )
          ;
        }
      },
      handleClick(data, checked, node) {
        // 手动设置单选
        if (checked === true) {
          this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
          // 设置按钮是否可选（选中节点后调用两次handleClick，第一次checked为true，所以设置按钮写在这）
          if (data.status === 1) {
            this.operationBtnActive = false;
          } else {
            this.operationBtnActive = true;
          }
        } else {
          if (this.checkedId == data.id) {
            this.$refs.tree.setCheckedKeys([data.id]);
          }
        }
      },
      getCheckedDepartment() {

        this.formInline.upperDepartmentName = this.$refs.tree.getCheckedNodes()[0].departmentName;
        this.formInline.upperDepartmentNo = this.$refs.tree.getCheckedNodes()[0].departmentNo;
        this.dialogVisibleDepartment = false;
      },
      querySearchAsync(queryString, cb) {
        var list = this.list;
        var results = queryString ? list.filter(this.createStateFilter(queryString)) : list;

        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          cb(results);
        }, 3000 * Math.random());
      },
      createStateFilter(queryString) {
        return (state) => {
          return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      handleSelect(item) {
        console.log(item);
      }
    },
    mounted() {
      this.$http.get("/department/getCityList.do_").then((result) => {
        this.list.push({value:'全部'});
       result.cityList.map(item => {
         //对象转换为数组
         this.list.push({value: item});
      });
    });
      this.Search();

    }
  }
</script>
