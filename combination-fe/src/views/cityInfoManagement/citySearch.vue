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
              <el-select v-model="form.isAvailable" clearable style="width:200px;" placeholder="请选择">
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
              <el-button type="primary" style="width: 100px" size="medium">导出</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column prop="regionCode" label="国际代码"  width="200px"></el-table-column>
      <el-table-column prop="regionAreaCode" label="区号"  width="200px"></el-table-column>
      <el-table-column prop="regionName" label="城市名称"  width="200px"></el-table-column>
      <el-table-column prop="regionPinyin" label="名字拼音"  width="200px"></el-table-column>
      <el-table-column prop="upperRegion" label="上级区划"  width="200px"></el-table-column>
      <el-table-column prop="regionStatus" label="状态"  width="200px"></el-table-column>
      <el-table-column prop="modifyEmp" label="修改人"  width="200px"></el-table-column>
      <el-table-column prop="modifyTime" label="修改时间"  width="200px"></el-table-column>
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
          isAvailable:''
        },

        tableData:[],

        regionCode:'' ,
        regionName:'',
        upperRegion:'',
        regionPinyin:'',
        regionAreaCode:'',
        regionStatus:'',
        modifyEmp:'',
        modifyTime:'',

        options:[{
          value:'1',
          label:'有效'
        },{
          value:'0',
          label:'无效'
        }]

      }
    }

  }
</script>
