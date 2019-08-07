<template>
  <home>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="70px">
        <el-row>
          <el-col :span="5">
            <el-form-item label="国际代码：" label-width="100">
              <el-input style="width: 130px;" v-model="form.regionCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="区/县名称：" label-width="100">
              <el-input style="width: 130px;" v-model="form.regionName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="城市名称：" label-width="100">
              <el-input style="width: 130px;" v-model="form.upperRegion"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="省/直辖市名称：" label-width="100">
              <el-input style="width: 130px;" v-model="form.upperRegionTwice"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="状态：">
              <el-select v-model="form.regionStatus" clearable style="width:100px;" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="6">
            <el-form-item>
              <el-button type="primary" style="width: 100px" @click="fetchData" size="medium">查询</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="6" >
            <el-form-item>
              <el-button type="primary" style="width: 100px" @click="exportExcel" size="medium">导出</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column prop="regionCode" label="国际代码"  width="150px"></el-table-column>
      <el-table-column prop="regionAreaCode" label="区号"  width="100px"></el-table-column>
      <el-table-column prop="regionName" label="区/县名称"  width="150px"></el-table-column>
      <el-table-column prop="regionPinyin" label="名字拼音"  width="150px"></el-table-column>
      <el-table-column prop="upperRegion" label="所属城市"  width="150px"></el-table-column>

      <el-table-column prop="upperRegionTwice" label="所属省/直辖市"  width="200px"></el-table-column>

      <el-table-column prop="regionStatus" label="状态"  width="100px"></el-table-column>
      <el-table-column prop="mEmp" label="修改人"  width="150px"></el-table-column>
      <el-table-column prop="mTime" label="修改时间"  width="150px"></el-table-column>
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
<script>
  import commonUtils from '../../common/commonUtils'
  export default {
    data(){
      return{
        total:0,
        currentPage:1,
        pageSize:10,

        form:{
          regionCode:'',
          regionName:'',
          upperRegion:'',
          upperRegionTwice:'',
          regionStatus:''
        },

        tableData:[],
        countySearchList:[],

        regionCode:'' ,
        regionName:'',
        upperRegion:'',
        regionPinyin:'',
        regionAreaCode:'',
        regionStatus:'',
        mEmp:'',
        mTime:'',

        options:[{
          value:'',
          label:'全部'
        },{
          value:'1',
          label:'有效'
        },{
          value:'0',
          label:'无效'
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
          //根据查询条件获取数据
          fetchData() {
              var self=this;
              var param={
                  page:self.currentPage,
                  limit:self.pageSize,
                  regionCode:self.form.regionCode,
                  regionName:self.form.regionName,
                  upperRegion:self.form.upperRegion,
                  upperRegionTwice:self.form.upperRegionTwice,
                  regionStatus: self.form.regionStatus
              };
              self.$http.get('/regionManage/countySearch',{
                  params:param
              }).then((result)=>{
                  //对取回来的数据进行处理
                  self.tableData=result.page.list;
                  self.total = result.page.totalCount;
                  self.countySearchList = result.countySearchList;
                  //

              }).catch(function (error) {
                  commonUtils.Log("/regionManage/countySearch:" + error);
                  self.$message.error("获取数据错误");
              });

          },
          exportExcel() {
              require.ensure([], () => {
                  const {export_json_to_excel} = require('../../excel/Export2Excel');
                  const tHeader = ['国际代码','区号', '区县名称','名字拼音', '所属城市','所属省份', '状态', '修改人', '修改时间'];
                  // 上面设置Excel的表格第一行的标题
                  const filterVal = ['regionCode', 'regionAreaCode','regionName', 'regionPinyin', 'upperRegion', 'upperRegionTwice','regionStatus','mEmp','mTime'];
                  // 上面的'regionCode', 'regionName', 'regionPinyin', 'upperRegion', 'regionStatus' 里对象的属性
                  const list = this.countySearchList;  //把data里的tableData存到list
                  //console.log(list);
                  const data = this.formatJson(filterVal, list);
                  export_json_to_excel(tHeader, data, '区县搜索列表excel');
              })
          },
          formatJson(filterVal, jsonData) {
              return jsonData.map(v => filterVal.map(j => v[j]))
          }
      }

  }
</script>
