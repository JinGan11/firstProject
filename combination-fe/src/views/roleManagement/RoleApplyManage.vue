<template>
  <home>
    <div style="width:96%; margin-left: 40px">
      <el-form ref="form" :model="form" label-width="110px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="角色申请编号">
              <el-input style="width:150px;" v-model="form.roleApplyNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请角色ID">
              <el-input style="width:150px;" v-model="form.roleId"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请角色名称">
              <el-input style="width:150px;" v-model="form.roleName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请人登录账号">
              <el-input style="width:150px;" v-model="form.applyAccountName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="申请人员工编号">
              <el-input style="width:150px;" v-model="form.applyStaffNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请人员工姓名">
              <el-input style="width:150px;" v-model="form.applyStaffName">
              </el-input>

            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请人所属部门">
              <el-input style="width:80px;" v-model="form.applyDepartmentName"></el-input>
              <el-button type="text" @click="selectDepartment">选择</el-button>
              <el-button type="text" @click="clearDepartment">清空</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态">
              <el-select v-model="form.applyStatus" clearable  style="width:150px;" placeholder="请选择">
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
          <el-col :span="50">
            <el-form-item label="申请时间">
              <el-date-picker
                v-model="form.applyTime"
                type="datetimerange"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="50">
            <el-form-item label="操作时间">
              <el-date-picker
                v-model="form.modifyTime"
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
      </el-form>
    </div>
    <div style="margin-bottom: 10px; margin-top:20px; margin-left:40px">
      <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
      <el-button type="primary" @click="createRoleApply"   style="width:70px">新建</el-button>
      <el-button type="primary" @click="modifyRoleApply" style="width:70px" :disabled="disabledDelete">修改</el-button>
      <el-button type="primary" @click="deleteRoleApply" style="width:70px" :disabled="disabledDelete">删除</el-button>
      <el-button type="primary" @click="commitRoleApply" style="width:70px" :disabled="disabledDelete">提交审核</el-button>
      <el-button type="primary" style="width:100px" @click="out">导出</el-button>
    </div>

    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" @change="selectRow(scope.row)" :label="scope.row.id" ><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <!--      <el-table-column prop="id"  label="隐藏id"></el-table-column>-->
      <el-table-column prop="roleApplyNum" label="角色申请编号" width="150">
        <template slot-scope="scope">
          <el-button type="text" @click="roleApplyNumBtn(scope.row)">{{scope.row.roleApplyNum}}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="roleId" label="申请角色ID"width="150">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="cellTrigger(scope.row.roleId)">{{scope.row.roleId}}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="roleName" label="申请角色名称"  width="150"></el-table-column>
      <el-table-column prop="approverStaffName" label="审批负责人" width="150"></el-table-column>
      <el-table-column prop="businessLine" label="角色支持业务线" width="150"></el-table-column>
      <el-table-column prop="applyAccountName" label="申请人登录账号" width="150"></el-table-column>
      <el-table-column prop="applyStaffNum" label="申请人员工编号" width="150"></el-table-column>
      <el-table-column prop="applyStaffName" label="申请人员工姓名" width="150"></el-table-column>
      <el-table-column prop="applyDepartmentName" label="申请人所属部门" width="150"></el-table-column>
      <el-table-column prop="applyTime" label="申请时间"width="150"></el-table-column>
      <el-table-column prop="applyStatus" label="状态" width="150" style="text-align: center">
        <template slot-scope="scope">
          {{ApplyStatusEnum[scope.row.applyStatus]}}
        </template>
      </el-table-column>
      <el-table-column prop="modifyEmp" label="操作人" width="150"></el-table-column>
      <el-table-column prop="modifyTime" label="操作时间" width="150"></el-table-column>
      <el-table-column prop="rejectReason" label="拒绝原因" width="150"></el-table-column>
      <el-table-column prop="approveTime" v-if="false" label="审批时间" width="150"></el-table-column>
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
    <el-dialog :title='excelTitle' :visible.sync="dialogVisibleRole" :close-on-click-modal="false" width="600px">
      <template>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="checkedRoles" @change="handleCheckedRoleChange">
          <el-checkbox v-for="role in roles" :label="role" :key="role">{{role}}</el-checkbox>
        </el-checkbox-group>
      </template>
      <template slot="footer">
        <el-button type="primary" @click="exportExcel">确定导出</el-button>
        <el-button @click="cancel">取 消</el-button>
      </template>
    </el-dialog>


    <el-dialog :title='titleDepartment' :visible.sync="dialogVisibleDepartment"  :close-on-click-modal="false" width="50%">
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

    <el-dialog :title='titleRoleApplyInfo' :visible.sync="dialogVisibleRoleApplyInfo"  :close-on-click-modal="false" width="80%">
      <home>
        <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 20px ">
          <el-row :gutter="20">
            <el-col :span="5" style="color: #409EFF">
              申请信息
            </el-col>
          </el-row>
        </div>
        <div style="margin-top: 20px;margin-left: 40px;margin-bottom:20px;">
          <br>
          <el-form ref="form" :model="formRoleInfo" label-width="110px">
            <el-row>
              <el-col :span="10">
                <el-form-item label="角色申请编号">
                  <el-input style="width:300px;" v-model="formRoleInfo.roleApplyNum" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="申请角色">
                  <el-input style="width:250px;"  v-model="formRoleInfo.roleName" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="角色审批人">
                  <el-input style="width:300px;" v-model="formRoleInfo.approverStaffName" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="角色支持业务线">
                  <el-input style="width:300px;" v-model="formRoleInfo.businessLine" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div style="margin-left: 40px;margin-bottom:20px;border-bottom:1px solid gray;padding-bottom: 20px">
          <el-row :gutter="20">
            <el-col :span="5" style="color: #409EFF">
              申请账号明细
            </el-col>
          </el-row>
        </div>
        <div style="margin-left: 40px;">
          <el-table ref="multipleTable" :data="tableDataAccount" border>
            <!--            <el-table-column type="selection" width="35"></el-table-column>-->
            <!--            <el-table-column prop="id" label="隐藏id"></el-table-column>-->
            <el-table-column prop="accountName" label="申请账号" width="150"></el-table-column>
            <el-table-column prop="staffName" label="关联员工姓名"width="150"></el-table-column>
            <el-table-column prop="staffNum" label="关联员工编号"  width="150"></el-table-column>
            <el-table-column prop="department" label="关联员工所属部门" width="200"></el-table-column>
            <el-table-column prop="applyOperation" label="申请操作" width="150" style="text-align: center">
            </el-table-column>
          </el-table>
        </div>

        <div style="margin-left: 40px;margin-top:20px;margin-bottom:20px;border-bottom:1px solid gray;padding-bottom: 20px">
          <el-row :gutter="20">
            <el-col :span="5" style="color: #409EFF">
              其他信息
            </el-col>
          </el-row>
        </div>
        <div style="margin-top: 20px;margin-left: 40px;">
          <br>
          <el-form ref="form" :model="otherInfo" label-width="110px">
            <el-row>
              <el-col :span="10">
                <el-form-item label="申请人登录账号">
                  <el-input style="width:300px;" v-model="otherInfo.applyAccountName" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="申请人员工编号">
                  <el-input style="width:300px;" v-model="otherInfo.applyStaffNum" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="申请人员工姓名">
                  <el-input style="width:300px;" v-model="otherInfo.applyStaffName" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="申请人所属部门">
                  <el-input style="width:300px;" v-model="otherInfo.applyDepartmentName" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="申请时间">
                  <el-input style="width:300px;" v-model="otherInfo.applyTime" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="申请状态">
                  <el-input style="width:300px;" v-model="otherInfo.applyStatus" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="修改时间">
                  <el-input style="width:300px;" v-model="otherInfo.modifyTime" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="修改人">
                  <el-input style="width:300px;" v-model="otherInfo.modifyStaffName" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10">
                <el-form-item label="审批时间">
                  <el-input style="width:300px;" v-model="otherInfo.approveTime" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="审批人">
                  <el-input style="width:300px;" v-model="otherInfo.approverStaffName" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div style="text-align: center">
            <el-button type="primary" @click="cancalShowApplyDetails" style="width:70px">取消</el-button>
          </div>
        </div>
        <br/>
      </home>
    </el-dialog>











  </home>
</template>
<script>
  import commonUtils from '../../common/commonUtils'
  const roleOptions = ['角色申请编号', '申请角色ID', '申请角色名称', '审批负责人', '角色支持业务线', '申请人登录账号', '申请人员工姓名', '申请人所属部门', '申请时间','状态','操作人','操作时间','拒绝理由'];
  export default {
    data() {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        disabled: true,
        disabledDelete:true,
        titleDepartment:'选择部门',
        dialogVisibleDepartment:false,
        dialogVisibleRoleApplyInfo:false,
        titleRoleApplyInfo:'角色申请详情',



        multipleSelection: [],
        tableDataAccount: [],
        applyId:'',
        applyStatusSwitch:'',

        applyTimeStart:'',
        applyTimeEnd:'',
        modifyTimeStart:'',
        modifyTimeEnd:'',
        formRoleInfo: {//申请信息
          roleApplyNum: '',
          roleName: '',
          approverStaffName: '',
          businessLine: ''
        },

        otherInfo: {//其他信息
          applyAccountName: '',
          applyStaffNum: '',
          applyStaffName: '',
          applyDepartmentName: '',
          applyStatus: '',
          applyTime: '',
          modifyTime: '',
          modifyStaffName: '',
          approveTime: '',
          approverStaffName: '',
        },

        form: {
          roleApplyNum:'',
          roleId: '',
          roleName: '',
          applyAccountName:'',
          applyStaffNum:'',
          applyStaffName:'',
          applyDepartmentName:'',
          applyStatus:'',
          applyTime:[],
          modifyTime:[],
        },
        tableData: [],
        selection: [],
        radio:'',
        id:'',
        roleApplyNum: '',
        roleId: '',
        roleName: '',
        approverStaffName: '',
        BusinessLineEnum:{},
        businessLine: '',
        applyAccountName: '',
        applyStaffNum: '',
        applyStaffName: '',
        applyDepartmentName: '',
        applyTime: '',
        ApplyStatusEnum:{},
        applyStatus: '',
        modifyEmp: '',
        modifyTime: '',
        rejectReason: '',
        value:'',
        beginDateScope:'',
        excelTitle: '请选择需要导出的字段',
        dialogVisibleRole: false,
        roleDtoList: [],
        checkAll: false,
        checkedRoles: [],
        roles: roleOptions,
        isIndeterminate: true,
        filterVal: [],
        list:[],
        options: [{
          value: '',
          label: '全部'
        }, {
          value: '1',
          label: '已新建'
        }, {
          value: '2',
          label: '待审批'
        }, {
          value: '3',
          label: '审批通过'
        }, {
          value: '4',
          label: '审批拒绝'
        },{
          value:'5',
          label:'已删除'
        }],

        defaultProps: {
          label: 'departmentName',
          children: 'children',
          id: 'id',
          status: 'status'
        },
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
      cancel() {
        this.dialogVisibleRole = false;
      },

      cellTrigger(val) {//角色详情页
        this.$router.push({path: '/RoleInf', query: {roleID: val}});
      },

      fetchData() { //获取数据
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          roleApplyNum:self.form.roleApplyNum,
          roleId:self.form.roleId,
          roleName:self.form.roleName,
          applyAccountName: self.form.applyAccountName,
          applyStaffNum:self.form.applyStaffNum ,
          applyStaffName: self.form.applyStaffName,
          applyDepartmentName:self.form.applyDepartmentName,
          applyStatus: self.form.applyStatus,
          applyTimeStart:self.form.applyTime[0],
          applyTimeEnd:self.form.applyTime[1],
          modifyTimeStart: self.form.modifyTime[0],
          modifyTimeEnd: self.form.modifyTime[1],
          type:'角色申请'
        };
        self.$http.get('roleApply/querylist.do_', {
          params: param
        }).then((result) => {
          //请求成功回调
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          // self.BusinessLineEnum = result.BusinessLineEnum;
          self.ApplyStatusEnum = result.applyStatusEnum;
          self.roleDtoList=result.roleDtoList;
        }).catch(function (error) {
          //请求失败回调
          commonUtils.Log("roleApply/querylist.do_:"+error);
          self.$message.error("获取数据错误");
        });
      },
      createRoleApply(){//新建角色申请
        this.$router.replace('/createRoleApply')
      },


      out(){//点击导出按钮触发的函数
        this.dialogVisibleRole =true;
      },
      exportExcel() {
        if(this.checkedRoles.length ===0){
          this.$message({
            showClose: false,
            message: '请选择需要导出的字段',
            type: 'error'
          });
        }else{
          require.ensure([], () => {
            const {export_json_to_excel} = require('../../excel/Export2Excel');
            const tHeader = this.checkedRoles;
            // 上面设置Excel的表格第一行的标题

            const filterVal = this.exportField(this.checkedRoles);
            // 上面的staffNum、accountId、staffName是tableData里对象的属性
            const list = this.roleDtoList;  //把data里的tableData存到list
            for (let i = 0; i < list.length; i++) {
              switch(list[i].applyStatus){
                case 1: list[i].applyStatus='已新建';
                  break;
                case 2: list[i].applyStatus='待审批';
                  break;
                case 3: list[i].applyStatus='审批通过';
                  break;
                case 4: list[i].applyStatus='审批拒绝';
                  break;
                case 5: list[i].applyStatus='已删除';
                  break;
              }
            }
            console.log(list);
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
            export_json_to_excel(tHeader, data, '角色申请'+currentdate);
            this.$message({
              showClose: true,
              message: '文件导出成功',
              type: 'success'
            });
            this.dialogVisibleRole=false;
            this.checkedRoles=[];
            this.filterVal=[];
          })
        }
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      handleCheckAllChange(val) {
        this.checkedRoles = val ? roleOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckedRoleChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.roles.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.roles.length;
      },
      exportField(val) {
        for (let i = 0; i < val.length; i++) {
          if (this.checkedRoles[i] === '角色申请编号') {
            this.filterVal.push('roleApplyNum')
          } else if (this.checkedRoles[i] === '申请角色ID') {
            this.filterVal.push('roleId')
          } else if (this.checkedRoles[i] === '申请角色名称') {
            this.filterVal.push('roleName')
          } else if (this.checkedRoles[i] === '审批负责人') {
            this.filterVal.push('approverStaffName')
          } else if (this.checkedRoles[i] === '角色审批业务线') {
            this.filterVal.push('businessLine')
          } else if (this.checkedRoles[i] === '申请人登录账号') {
            this.filterVal.push('applyAccountName')
          } else if (this.checkedRoles[i] === '申请人员工编号') {
            this.filterVal.push('applyStaffNum')
          } else if (this.checkedRoles[i] === '申请人员工姓名') {
            this.filterVal.push('applyStaffName')
          } else if (this.checkedRoles[i] === '申请人所属部门') {
            this.filterVal.push('applyDepartmentName')
          }else if (this.checkedRoles[i] === '申请时间') {
            this.filterVal.push('applyTime')
          }else if (this.checkedRoles[i] === '状态') {
            this.filterVal.push('applyStatus')
          }else if (this.checkedRoles[i] === '操作人') {
            this.filterVal.push('modifyEmp')
          }else if (this.checkedRoles[i] === '操作时间') {
            this.filterVal.push('modifyTime')
          }else if (this.checkedRoles[i] === '拒绝原因') {
            this.filterVal.push('rejectReason')
          }
        }
        return this.filterVal;
      },


      deleteRoleApply() {//删除角色信息
        this.$confirm('此操作将删除该角色申请, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var self = this;
          var param = {
            selection:self.selection,
          };
          self.$http.get('roleApply/deleteRoleApply.do_', {
            params: param
          }).then(() => {
            self.$message.success("成功删除");
            self.fetchData();
            self.disabledDelete=true;
            self.selection=[];
          }).catch(function (error) {
            commonUtils.Log("roleApply/deleteRoleApply.do_:" + error);
            self.$message.error("获取数据错误");
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },

      commitRoleApply(){//提交审核
        this.$confirm('此操作将提交该角色申请, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var self = this;
          var param = {
            selection:self.selection,
          };
          self.$http.get('roleApply/commitRoleApply.do_', {
            params: param
          }).then(() => {
            self.$message.success("提交角色申请成功");
            self.fetchData();
            self.disabledDelete=true;
            self.selection=[];
          }).catch(function (error) {
            commonUtils.Log("roleApply/commitRoleApply.do_:" + error);
            self.$message.error("获取数据错误");
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消提交'
          });
        });
      },

      loadNode(node,resolve){
        var self = this;
        self.$http.get('department/buildTree.do_', {
          params: null
        }).then((result) => {
          resolve([result.departmentDto]);
        }).catch(function (error) {

        });
      },
      getCheckedDepartment() {
        // 获取部门 回填到文本框中
        // alert(this.$refs.tree.getCheckedNodes()[0].departmentName);
        this.form.applyDepartmentName=this.$refs.tree.getCheckedNodes()[0].departmentName;
        this.dialogVisibleDepartment=false;
      },
      handleClick(data,checked,node){
        // 手动设置单选
        if(checked === true) {
          this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
          // 设置按钮是否可选（选中节点后调用两次handleClick，第一次checked为true，所以设置按钮写在这）
          if(data.status === 1){
            this.operationBtnActive=false;
          }else{
            this.operationBtnActive=true;
          }
        } else {
          if (this.checkedId == data.id) {
            this.$refs.tree.setCheckedKeys([data.id]);
          }
        }
      },
      renderContent(h, { node, data, store }) {
        // 这里编译器有红色波浪线不影响运行...
        if(data.status != 1){
          return (
            <span style="color:red">{node.label}</span>
        );
        }else{
          return (
            <span>{node.label}</span>
        );
        }
      },
      selectDepartment(){//选择部门
        this.dialogVisibleDepartment=true;
      },
      clearDepartment(){//清除部门的值
        this.form.applyDepartmentName='';
      },





      modifyRoleApply(){//修改角色申请
        this.$router.replace('/modifyRoleApply')
      },

      selectRow(row){//选中某行
        this.disabled = false;
        if(row.applyStatus === 1){
          this.disabledDelete=false;
        }else{
          this.disabledDelete=true;
        }

        sessionStorage.setItem('applyIdFromApply', row.id);
        sessionStorage.setItem('roleIdFromApply', row.roleId);
        sessionStorage.setItem('applyNumFromApply',row.roleApplyNum);//角色申请编号
        sessionStorage.setItem('roleNameFromApply', row.roleName);//角色名字
        sessionStorage.setItem('approverStaffNameFromApply', row.approverStaffName);//审批人
        sessionStorage.setItem('businessLineFromApply', row.businessLine);
        sessionStorage.setItem('applyTimeFromApply', row.applyTime);//申请人
        sessionStorage.setItem('applyTimeFromApply', row.applyTime);//申请人
        sessionStorage.setItem('modifyTimeFromApply', row.modifyTime);//修改时间
        sessionStorage.setItem('modifyEmpFromApply', row.modifyEmp);//修改人
      },


      roleApplyNumBtn(row){ //弹出框 角色申请详情
        this.dialogVisibleRoleApplyInfo=true;
        this.applyId= row.id;
        this.formRoleInfo.roleName = row.roleName;//角色名字
        this.formRoleInfo.roleApplyNum=row.roleApplyNum;//申请编号
        this.otherInfo.approverStaffName = row.approverStaffName;//审批人名字
        this.formRoleInfo.businessLine = row.businessLine;//业务线
        this.otherInfo.applyAccountName =row.applyAccountName;//申请人登录账号
        this.otherInfo.applyStaffNum=row.applyStaffNum;//员工编号
        this.otherInfo.applyStaffName=row.applyStaffName;//员工姓名
        this.otherInfo.applyDepartmentName=row.applyDepartmentName;//部门
        this.otherInfo.applyTime = row.applyTime;//申请时间
        this.otherInfo.modifyTime = row.modifyTime;//修改时间
        this.otherInfo.modifyStaffName =row.modifyEmp;//修改人员工姓名
        this.otherInfo.approveTime=row.approveTime;//审批时间
        this.formRoleInfo.approverStaffName = row.approverStaffName;//审批人
        switch (row.applyStatus) {
          case 1: this.otherInfo.applyStatus='已新建'; break;
          case 2: this.otherInfo.applyStatus='待审批'; break;
          case 3: this.otherInfo.applyStatus='审批通过';break;
          case 4: this.otherInfo.applyStatus='审批拒绝'; break;
          case 5: this.otherInfo.applyStatus='已删除'; break;
          default: this.otherInfo.applyStatus=null;
        }
        this.showAccountListByApplyId();//查询改申请编号 包含的账号列表
      },

      showAccountListByApplyId() {
        var self = this;
        var param = {
          applyId: self.applyId,
        };
        self.$http.get('roleApply/showAccountListByApplyId.do_', {
          params: param
        }).then((result) => {
          //请求成功回调
          self.tableDataAccount = result.list;
          for(let i=0;i<self.tableDataAccount.length;i++){
            if(self.tableDataAccount[i].applyOperation===1){
              self.tableDataAccount[i].applyOperation='添加';
            }
            if(self.tableDataAccount[i].applyOperation===2){
              self.tableDataAccount[i].applyOperation='移除';
            }
          }

        }).catch(function (error) {
          //请求失败回调
          commonUtils.Log("roleApply/showAccountListByApplyId.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },

      cancalShowApplyDetails(){
        this.dialogVisibleRoleApplyInfo=false;
      }
    }

  }
</script>

<style scoped>

</style>
