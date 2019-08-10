<template>
  <home>
    <el-form :inline="true" label-width="100px" :model="formInline" class="demo-form-inline">
      <el-form-item label="部门名称">
        <el-input v-model="formInline.departmentName">
        </el-input>
      </el-form-item>
      <el-form-item label="负责人ID">
        <el-input v-model="formInline.StaffId"></el-input>
      </el-form-item>
      <el-form-item label="负责人姓名">
        <el-input v-model="formInline.StaffName"></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="formInline.telePhone"></el-input>
      </el-form-item>
      <el-form-item label="部门所在城市">
        <el-input v-model="formInline.cityName"></el-input>
      </el-form-item>
      <el-form-item label="部门级别">
        <el-input v-model="formInline.level">
        </el-input>
      </el-form-item>
      <el-form-item label="上级部门">
        <el-input v-model="formInline.upperDepartmentNo"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-input v-model="formInline.status"></el-input>
      </el-form-item>
      <el-form-item label="部门类型">
        <el-select v-model="formInline.departmentType" value="0">
          <el-option label="全部" value="0"></el-option>
          <el-option label="门店" value="1"></el-option>
          <el-option label="停车场" value="2"></el-option>
          <el-option label="交车中心" value="3"></el-option>
          <el-option label="维修厂" value="4"></el-option>
        </el-select>
      </el-form-item>
      <br>
      <el-form-item size="100px">
        <el-button type="primary" @click="Search">查询</el-button>
        <el-button @click="exportDepartment">导出</el-button>
      </el-form-item>
    </el-form>
    <el-table ref="multipleTable" :data="tableData" border @selection-change='handleSelectionChange'>
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id" @change="ChooseOnDetail"><span width="0px;"></span>
          </el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="departmentNo" label="部门编号" width="140"></el-table-column>
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
      <el-table-column prop="companyName" label="关联公司名称" width="140"></el-table-column>
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
        <hr>
        <div>
          <div style="font-family:Consolas;font-size:20px;margin-left: 50px;margin-bottom: 20px;">申请信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="部门编号" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.departmentNo" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="部门名称" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.departmentName" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="负责人ID" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.staffId" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="负责人姓名" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.staffName" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="手机号" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.telephone" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.email" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="座机号" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.landline" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所在城市" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.cityName" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="详细地址" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.address" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.departmentNo" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="经度" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.longitude" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="纬度" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.latitude" placeholder=""></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <hr>
        <div>
          <div style="font-family:Consolas;font-size:20px;margin-left: 50px;margin-bottom: 20px;">业务信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="部门级别" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.level" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="上级部门" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.upperDepartmentNo" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="支持业务线" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.supportBusiness" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="部门类型" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.departmentType" placeholder="">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="办公点标识" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.workplace" placeholder=""></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <hr>
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

        <div>
          <hr>
          <div style="font-family:Consolas;font-size:20px;margin-left: 50px;margin-bottom: 20px;">其他信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="新建时间" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.createTime" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="新建人" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.createEmp" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="修改时间" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.modifyTime" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="修改人" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.modifyEmp" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态" label-width="150px">
                <el-input style="width: 200px;" v-model="formDetail.status" placeholder=""></el-input>
              </el-form-item>
            </el-col>
            <br>
            <el-col :span="12">
              <el-form-item label="备注" label-width="150px">
                <el-input style="width: 300px;" v-model="formDetail.remark" placeholder=""></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
    </el-dialog>
    <el-dialog title='excelTitle' :visible.sync="exportDialogVisible" :close-on-click-modal="false" width="600px">
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
  </home>
</template>
<script>
  const departmentOptions = ['部门编号', '办公点标识', '部门名称', '负责人ID', '负责人姓名', '手机号', '部门级别', '上级部门', '支持业务线', '部门类型', '状态', '所在城市', '关联公司名称'];
  export default {
    data() {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        totalCompany: 0,
        currentPageCompany: 1,
        pageSizeCompany: 5,
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
          modifyTime: '',
          modifyEmp: '',
          status: '',
          remark: '',
          longitude: '',
          latitude: '',
        },
        formInline: {
          departmentName: '',
          StaffId: '',
          StaffName: '',
          telePhone: '',
          cityName: '',
          level: '',
          upperDepartmentNo: '',
          status: '',
          departmentType: ''
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
        exportDialogVisible: false,
        checkAll: false,
        checkdepartment: [],
        department: departmentOptions,
        isIndeterminate: true,
        filterVal: [],
        list: [],
        disabled: true,
        departmentList: [],
      }
    },
    methods: {
      ChooseOnDetail() {
        // alert(this.row.tableData[0].departmentNo);
        var param = {
          id: this.selection,
          page: this.currentPageCompany,
          limit: this.pageSizeCompany,
        };

        this.$http.get("/department/selectDepartment.do_", {
          params: param
        }).then((resultss) => {
          this.formDetail = resultss.department;
          this.totalCompany = resultss.totalCount;
          this.tableCity = resultss.page.list;
          this.CompanyStatusEnum = resultss.CompanyStatusEnum;
          this.CompanyTypeEnum = resultss.CompanyTypeEnum;
          this.CompanyMarkEnum = resultss.CompanyMarkEnum;
          this.CompanyNatureEnum = resultss.CompanyNatureEnum;
          //枚举类型转换
          this.formDetail.departmentType = resultss.DepartmentTypeEnum[this.formDetail.departmentType];
          this.formDetail.level = resultss.LevelEnum[this.formDetail.level];
          this.formDetail.status = resultss.DepartmentStatusEnum[this.formDetail.status];
          console.log(this.tableCity);
        }).catch(function (error) {
          console.log('department/selectDepartment.do_' + error);
          this.$message.error("获取数据错误");
        });
        this.dialogVisibleDetail = true;
      },
      handleSelectionChange(val) {
        this.selection = val;
      },
      Search() {
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          departmentName: self.formInline.departmentName,
          staffName: self.formInline.staffName,
          staffId: self.formInline.staffId,
          telePhone: self.formInline.telePhone,
          cityName: self.formInline.cityName,
          level: self.formInline.level,
          upperDepartmentNo: self.formInline.upperDepartmentNo,
          status: self.formInline.status,
          departmentType: self.formInline.departmentType,
        };
        self.$http.get('/department/searchDepartment.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.DepartmentTypeEnum = result.DepartmentTypeEnum;
          self.StatusEnum = result.StatusEnum;
          self.total = result.page.totalCount;
          self.LevelEnum = result.LevelEnum;
          self.departmentList = self.tableData;
          console.log(self.tableData);
        }).catch(function (error) {
          console.log('department/searchDepartment.do_' + error);
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

            }
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '员工管理列表excel');
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
    },
  }
</script>
