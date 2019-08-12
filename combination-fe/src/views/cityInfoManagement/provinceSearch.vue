<template>
  <home>
    <!--搜索-->
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="70px">


        <el-row class="demo-autocomplete">
          <el-col :span="8">
            <div class="sub-title">国际代码：</div>
            <el-autocomplete
              class="inline-input"
              v-model="form.regionCode"
              valueKey="regionCode"
              value="regionCode"
              :fetch-suggestions="querySearchRegionCode"
              placeholder="请输入国际代码"
              @select="handleSelectRegionCode"
            ></el-autocomplete>
          </el-col>
          <el-col :span="12">
            <div class="sub-title">省/直辖市</div>
            <el-autocomplete
              class="inline-input"
              v-model="form.regionName"
              valueKey="regionName"
              value="regionName"
              :fetch-suggestions="querySearch"
              placeholder="请输入省市"
              @select="handleSelect"
            ></el-autocomplete>
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
          <el-col :span="3" :offset="3">
            <el-form-item>
              <el-button type="primary" style="width: 100px" @click="fetchData" size="medium">查询</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="3" >
            <el-form-item>
              <el-button type="primary" style="width: 100px" @click="exportVisible = true" :disabled="provinceBtnPermission.exportPermission" size="medium">导出</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="3" >
            <el-form-item>
              <el-button type="primary" style="width: 100px" @click="createFormVisible = true" :disabled="provinceBtnPermission.createPermission" size="medium">新建</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="3" >
            <el-form-item>
              <el-button type="primary" style="width: 100px" :disabled="provinceBtnPermission.modifyPermission" size="medium">修改</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="3" >
            <el-form-item>
              <el-button type="primary" style="width: 100px"   size="medium">查询</el-button>
            </el-form-item>
          </el-col>
        </el-row>


      </el-form>
    </div>
    <!--查询结果表格-->
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column prop="regionCode" label="国际代码"  width="200px"></el-table-column>
      <el-table-column prop="regionName" label="省/直辖市"  width="200px"></el-table-column>
      <el-table-column prop="regionPinyin" label="名字拼音"  width="200px"></el-table-column>
      <el-table-column prop="upperRegion" label="上级区划"  width="200px"></el-table-column>
      <el-table-column prop="regionStatus" label="状态"  width="200px">
        <template slot-scope="scope">
          {{RegionStatus[scope.row.regionStatus]}}
        </template>
      </el-table-column>
      <el-table-column prop="mEmp" label="修改人"  width="200px"></el-table-column>
      <el-table-column prop="mTime" label="修改时间"  width="200px"></el-table-column>
    </el-table>
    <!--分栏-->
    <el-pagination background
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   :current-page="currentPage"
                   :page-sizes="[10, 20, 50, 100]"
                   :page-size="pageSize"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total">
    </el-pagination>

    <!--新建窗口-->
    <el-dialog title="新建省/直辖市" :visible.sync="createFormVisible">
      <el-form :model="createForm">
        <el-form-item label="国际代码" >
          <el-input v-model="createForm.regionCode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="省/直辖市名称" >
          <el-input v-model="createForm.regionName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="名字拼音" >
          <el-input v-model="createForm.regionPinyin" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="上级区划" >
          <el-input v-model="createForm.upperRegion" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" >
        <el-input v-model="createForm.regionStatus" autocomplete="off"></el-input>
      </el-form-item>
        <el-form-item label="修改人" >
          <el-input v-model="createForm.mEmp" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="修改时间" >
          <el-input v-model="createForm.mTime" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="createFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="createRegion" >确 定</el-button>
      </div>
    </el-dialog>

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

  const regionPropsEnums = ['国际代码','省/直辖市','名字拼音', '上级区划', '状态', '修改人', '修改时间'];
  export default {
    data(){
      return{
        //  分页
        total:0,
        currentPage:1,
        pageSize:10,
        //搜索输入框
        form:{
          regionCode:'',
          regionName:'',
          regionStatus:''
        },
        //返回数据表格
        tableData:[],
        //返回的数据
        provinceSearchList:[],
        //表格每列的初始数据
        regionCode:'' ,
        regionName:'',
        regionPinyin:'',
        upperRegion:'',
        regionStatus:'',
        mEmp:'',
        mTime:'',
        RegionStatus:{},
        //新建页面的表单
        createFormVisible:false,
        createForm:{
            regionCode:'',
            regionName:'',
            regionPinyin:'',
            regionStatus:'',
            upperRegion:'',
            mEmp:'',
            mTime:''
        },
        //表单 form 中的下拉栏
        options:[{
          value:'',
          label:'全部'
        }, {
          value: '1',
          label: '有效'
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
        //输入校验


          //带输入建议的选择框
          state1:'',
          provinceSuggest:[],

        provinceBtnPermission: {
          createPermission: true,
          modifyPermission: true,
          exportPermission: true
        }

      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      this.judgmentAuthority();
      commonUtils.Log("页面进来");
      this.fetchData();
        // this.provinceSuggest=this.provinceSearchList;
    },
    methods: {
      judgmentAuthority() {
        const self = this;
        let permission = self.$store.state.powerList;
        permission.forEach(item=>{
          if (item === 67) {
            self.provinceBtnPermission.createPermission = false
          }
          if (item === 68) {
            self.provinceBtnPermission.modifyPermission = false
          }
          if (item === 62) {
            self.provinceBtnPermission.exportPermission = false
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
          regionStatus: self.form.regionStatus
        };
        self.$http.get('/regionManage/provinceSearch',{
          params:param
        }).then((result)=>{
          //对取回来的数据进行处理
          self.tableData=result.page.list;
          self.total = result.page.totalCount;
          self.provinceSearchList=result.provinceSearchList;
          self.RegionStatus=result.RegionStatus;
          //
            if(self.flags=='1'){
                self.provinceSuggest=self.provinceSearchList;
                self.flags='0';
            }

        }).catch(function (error) {
          commonUtils.Log("/regionManage/provinceSearch:" + error);
          self.$message.error("获取数据错误");
        });
          // for (let i = 0; i < provinceSearchList.length; i++) {
          //     this.provinceSuggest.push("value",provinceSearchList.regionName);
          // }
      },
      // 导出execl
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
                    const list = this.provinceSearchList;  //把data里的tableData存到list
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
                } else if (this.checkedRegionProps[i] === '省/直辖市') {
                    this.filterVal.push('regionName')
                } else if (this.checkedRegionProps[i] === '名字拼音') {
                    this.filterVal.push('regionPinyin')
                } else if (this.checkedRegionProps[i] === '上级区划') {
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
      //输入校验
        // 只能输入汉字、英文、数字
        btKeyDown(e) {
            e.target.value = e.target.value.replace(/[^\u4E00-\u9FA5]/g,"");
        },
        //限制输入特殊字符
        btKeyUp(e) {
            e.target.value = e.target.value.replace(/[`~!@#$%^&*()_\-+=<>?:"{}|,.\/;'\\[\]·~！@#￥%……&*（）——\-+={}|《》？：“”【】、；‘’，。、]/g,"");
        },


      //创建新的区域
      createRegion(){
          var self=this;
          self.createFormVisible = false;
          var param={
              regionCode:self.createForm.regionCode,
              regionName:self.createForm.regionName,
              regionPinyin:self.createForm.regionPinyin,
              regionStatus:self.createForm.regionStatus,
              upperRegion:self.createForm.upperRegion,
              mEmp:self.createForm.mEmp,
              mTime:self.createForm.mTime
          };
          self.$http.get('/regionManage/createProvince',{
              params:param
          }).then((result)=>{
              console.log("chuangjianchenggong");
          }).catch(function (error) {
              commonUtils.Log("/regionManage/provinceSearch:" + error);
              self.$message.error("获取数据错误");
          });
      },

      //带建议的输入：省份名字
        querySearch(queryString, cb) {
            var provinceSuggests = this.provinceSearchList;
            var results = queryString ? provinceSuggests.filter(this.createFilter(queryString)) : provinceSuggests;

            // 调用 callback 返回建议列表的数据
            cb(results);
        },
        createFilter(queryString) {
            return (province) => {
                return (province.regionName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
            };
        },

        handleSelect(item) {
            console.log(item);
        },


        //带建议的输入：国际代码
        querySearchRegionCode(queryString, cb) {
            var provinceSuggests = this.provinceSearchList;
            var results = queryString ? provinceSuggests.filter(this.createFilterRegionCode(queryString)) : provinceSuggests;
            // 调用 callback 返回建议列表的数据
            cb(results);
        },
        createFilterRegionCode(queryString) {
            return (province) => {
                return (province.regionCode.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
            };
        },

        handleSelectRegionCode(item) {
            console.log(item);
        }



    }
  }
</script>
