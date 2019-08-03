<template>
  <home>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="企业名称">
              <el-input style="width:200px;" v-model="form.companyName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="统一社会信用代码">
              <el-input style="width:200px;" v-model="form.creditCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
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
          <el-col :span="6">
            <el-form-item label="公司性质">
              <el-select v-model="form.companyNature" clearable  style="width:200px;" placeholder="请选择">
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
            <el-form-item label="状态">
              <el-select v-model="form.status" clearable  style="width:200px;" placeholder="请选择">
                <el-option
                  v-for="item in options3"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
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
      <el-button type="primary" @click="" style="width:70px">新建</el-button>
      <el-button type="primary" @click="" style="width:70px">修改</el-button>
    </div>

    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange" >
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id" ><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
      <el-table-column prop="id" label="编号" width="150"></el-table-column>
      <el-table-column prop="companyName" label="公司名称"width="150"></el-table-column>
      <el-table-column prop="creditCode" label="统一社会信用代码" width="120"></el-table-column>
      <el-table-column prop="companyType" label="类型" width="50" style="text-align: center">
        <template slot-scope="scope">
          {{CompanyTypeEnum[scope.row.companyType]}}
        </template>
      </el-table-column>
      <el-table-column prop="companyNature" label="公司性质" width="50" style="text-align: center">
        <template slot-scope="scope">
          {{CompanyNatureEnum[scope.row.companyNature]}}
        </template>
      </el-table-column>
      <el-table-column prop="companyMark" label="总公司标志" width="50" style="text-align: center">
        <template slot-scope="scope">
          {{CompanyMarkEnum[scope.row.companyMark]}}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="50" style="text-align: center">
        <template slot-scope="scope">
          {{CompanyStatusEnum[scope.row.status]}}
        </template>
      </el-table-column>
      <el-table-column prop="modifyTime" label="修改时间" width="120"></el-table-column>
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
  </home>
</template>

</template>

<script>
  import commonUtils from '../../common/commonUtils'
  export default {
    data() {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        form: {
          companyName: '',
          creditCode: '',
          companyType: '',
          companyNature: '',
          status: '',

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
        companyMarkEnum:{},
        status: '',
        CompanyStatusEnum:{},
        modifyTime: '',
        modifyEmp: '',
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
        options3: [{
          value: '',
          label: '全部'
        }, {
          value: '1',
          label: '有效'
        }, {
          value: '2',
          label: '无效'
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
        this.selection = val;
      },
      fetchData() { //获取数据
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          staffNum:self.form.companyName,
          creditCode:self.form.creditCode,
          companyType:self.form.companyType,
          companyNature:self.form.companyNature,
          status:self.form.status,
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

        }).catch(function (error) {
          commonUtils.Log("company/querylist.do_:"+error);
          self.$message.error("获取数据错误");
        });
        },
      },
  }

</script>
<style scoped>


</style>
