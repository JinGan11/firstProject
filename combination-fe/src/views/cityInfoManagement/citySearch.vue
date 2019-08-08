<template>
  <home>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="70px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="国际代码：" label-width="130">
              <el-input style="width: 200px;" v-model="form.regionCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="城市名称：" label-width="130">
              <el-input style="width: 200px;" v-model="form.regionName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="省/直辖市名称：" label-width="130">
              <el-input style="width: 200px;" v-model="form.upperRegion"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态：">
              <el-select v-model="form.regionStatus" clearable style="width:200px;" placeholder="请选择">
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
              <el-button type="primary" style="width: 100px" @click="exportVisible = true" size="medium">导出</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column prop="regionCode" label="国际代码"  width="200px"></el-table-column>
      <el-table-column prop="regionName" label="城市名称"  width="200px"></el-table-column>
      <el-table-column prop="regionPinyin" label="名字拼音"  width="200px"></el-table-column>
      <el-table-column prop="upperRegion" label="所属省份"  width="200px"></el-table-column>
      <el-table-column prop="regionStatus" label="状态"  width="200px"></el-table-column>
      <el-table-column prop="mEmp" label="修改人"  width="200px"></el-table-column>
      <el-table-column prop="mTime" label="修改时间"  width="200px"></el-table-column>
    </el-table>
    <el-pagination background
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   :current-page="currentPage"
                   :page-sizes="[10, 20, 50, 100]"
                   :page-size="pageSize"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total">
    </el-pagination>

    <!--导出弹窗-->
    <el-dialog :title='excelTitle' :visible.sync="exportVisible" :close-on-click-modal="false" width="600px">
      <template>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="checkedRegionProps" @change="handleCheckedRegionPropsChange">
          <el-checkbox v-for="props in regionProps" :label="props" :key="props">{{props}}</el-checkbox>
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
  import commonUtils from '../../common/commonUtils'
  const regionPropsEnums = ['国际代码','城市名字','名字拼音', '所属省市', '状态', '修改人', '修改时间'];
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
          regionStatus:''
        },

        tableData:[],
        citySearchList:[],

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
        }],

          //导出文件
          exportVisible:false,
          isIndeterminate: true,
          checkAll:false,
          excelTitle: '请选择需要导出的字段',
          checkedRegionProps:[],
          regionProps: regionPropsEnums,
          filterVal: [],

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
          regionStatus: self.form.regionStatus
        };
        self.$http.get('/regionManage/citySearch',{
          params:param
        }).then((result)=>{
          //对取回来的数据进行处理
          self.tableData=result.page.list;
          self.total = result.page.totalCount;
            self.citySearchList=result.citySearchList;
            self.RegionStatus=result.RegionStatus;
          //

        }).catch(function (error) {
          commonUtils.Log("/regionManage/citySearch:" + error);
          self.$message.error("获取数据错误");
        });

      },
        cancel() {
            this.exportVisible = false;
        },
        handleCheckAllChange(val) {
            this.checkedRegionProps = val ? regionPropsEnums : [];
            this.isIndeterminate = false;
        },
        handleCheckedRegionPropsChange(value) {
            let checkedCount = value.length;
            this.checkAll = checkedCount === this.regionProps.length;
            this.isIndeterminate = checkedCount > 0 && checkedCount < this.regionProps.length;
        },
        exportExcel() {
            if (this.checkedRegionProps.length === 0) {
                this.$message({
                    showClose: false,
                    message: '请选择需要导出的字段',
                    type: 'error'
                });
            } else {
                require.ensure([], () => {
                    const {export_json_to_excel} = require('../../excel/Export2Excel');
                    const tHeader = this.checkedRegionProps;
                    // 上面设置Excel的表格第一行的标题

                    const filterVal = this.exportField(this.checkedRegionProps);
                    // 上面的staffNum、accountId、staffName是tableData里对象的属性
                    const list = this.citySearchList;  //把data里的tableData存到list
                    for (let i = 0; i < list.length; i++) {
                        if (list[i].regionStatus === 0) {
                            list[i].regionStatus = '无效'
                        } else if (list[i].regionStatus === 1) {
                            list[i].regionStatus = '有效'
                        }
                    }
                    const data = this.formatJson(filterVal, list);
                    export_json_to_excel(tHeader, data, '省市管理列表excel');
                    this.$message({
                        showClose: true,
                        message: '文件导出成功',
                        type: 'success'
                    });
                    this.exportVisible = false;
                    this.checkedRegionProps = [];
                    this.filterVal = [];
                })
            }
        },
        formatJson(filterVal, jsonData) {
            return jsonData.map(v => filterVal.map(j => v[j]))
        },
        exportField(val) {
            for (let i = 0; i < val.length; i++) {
                if (this.checkedRegionProps[i] === '国际代码') {
                    this.filterVal.push('regionCode')
                } else if (this.checkedRegionProps[i] === '城市名字') {
                    this.filterVal.push('regionName')
                } else if (this.checkedRegionProps[i] === '名字拼音') {
                    this.filterVal.push('regionPinyin')
                } else if (this.checkedRegionProps[i] === '所属省市') {
                    this.filterVal.push('upperRegion')
                } else if (this.checkedRegionProps[i] === '状态') {
                    this.filterVal.push('regionStatus')
                } else if (this.checkedRegionProps[i] === '修改人') {
                    this.filterVal.push('mEmp')
                } else if (this.checkedRegionProps[i] === '修改时间') {
                    this.filterVal.push('mTime')
                }
            }
            return this.filterVal;
        },

    }
  }
</script>
