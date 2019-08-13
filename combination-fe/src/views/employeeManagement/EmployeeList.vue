<template>
  <home>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="员工编号">
              <el-input placeholder="员工编号" style="width:200px;" v-model="form.staffNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工姓名">
              <el-input placeholder="员工姓名" style="width:200px;" v-model="form.staffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="登陆账号">
              <el-input placeholder="登陆账号" :disabled="cloumnDisabled" style="width:200px;" v-model="form.accountName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item  label="是否离职">
              <el-select v-model="form.isDimission" clearable :disabled="cloumnDisabled" style="width:200px;" placeholder="请选择">
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
          <el-col :span="8">
            <el-form-item label="所属部门">
              <el-input placeholder="所属部门" :disabled="true" style="width:300px;" v-model="form.departmentId"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-button
              type="text"
              @click="">选择
            </el-button>
            <el-button
              type="text"
              @click="">清空
            </el-button>
          </el-col>
          <el-col :span="8">
            <el-form-item label="上级部门">
              <el-input placeholder="上级部门" :disabled="true" style="width:300px;"
                        v-model="form.upperDepartmentNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-button
              type="text"
              @click="">选择
            </el-button>
            <el-button
              type="text"
              @click="">清空
            </el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <div v-if="!buttonDisabled">
                <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
                <el-button type="primary" :disabled="empButtonPermission.exportPermission" style="width:100px" @click="add">导出</el-button>
              </div>
              <div v-else>
                <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-bottom: 10px" v-if="!buttonDisabled">
      <el-button type="primary" @click="createEmployee" :disabled="empButtonPermission.createPermission" style="width:70px">新建</el-button>
      <el-button type="primary" @click="modifyEmployee" :disabled="disabled || empButtonPermission.modifyPermission" style="width:70px">修改</el-button>
      <el-button type="primary" @click="deleteEmployee" :disabled="disabled || empButtonPermission.deletePermission" style="width:70px">删除</el-button>
      <el-button type="primary" @click="quitEmployee" :disabled="disabled || empButtonPermission.quitPermission" style="width:70px">离职</el-button>
      <el-button type="primary" @click="recovery" :disabled="disabled || empButtonPermission.recoveryPermission" style="width:70px">恢复</el-button>
      <el-button type="primary" @click="distributionDepartment" :disabled="disabled || empButtonPermission.assignDepPermission" style="width:80px">分配部门</el-button>
    </div>
    <div style="margin-bottom: 10px" v-else>
      <el-button type="primary" @click="confirmChoice" style="width:70px">确认选择</el-button>
      <el-button type="primary" @click="cancelChoice" style="width:70px">取消</el-button>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id" @change="approvalInfo(scope.row)"><span width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏员工id"></el-table-column>
      <el-table-column prop="accountId" v-if="false" label="隐藏账户id"></el-table-column>
      <el-table-column prop="createTime" v-if="false" label="隐藏创建时间"></el-table-column>
      <el-table-column prop="createEmp" v-if="false" label="隐藏创建人"></el-table-column>
      <el-table-column prop="remark" v-if="false" label="隐藏备注"></el-table-column>
      <el-table-column prop="staffNum" label="员工编号" width="150"></el-table-column>accountId
      <el-table-column prop="accountName" label="登陆账号" width="150"></el-table-column>
      <el-table-column prop="staffName" label="员工姓名" width="120"></el-table-column>
      <el-table-column prop="staffSex" label="性别" width="50" style="text-align: center">
        <template slot-scope="scope">
          {{SexEnum[scope.row.staffSex]}}
        </template>
      </el-table-column>
      <el-table-column prop="staffTelephone" label="员工手机">
        <template slot-scope="scope">
          {{scope.row.staffTelephone|protect}}
        </template>
      </el-table-column>
      <el-table-column prop="staffEmail" label="员工邮箱" width="200"></el-table-column>
      <el-table-column prop="departmentName" label="所属部门" width="120"></el-table-column>
      <el-table-column prop="upperDepartmentName" label="上级部门" width="150"></el-table-column>
      <el-table-column prop="isDimission" label="是否离职" width="100">
        <template slot-scope="scope">
          {{isDimissionEnum[scope.row.isDimission]}}
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

    <el-dialog :title="title" :visible.sync="distributionDepartmentFlag" :close-on-click-modal="false" width="700px">
      <div class="dialog-main">
        <el-form :inline="true" :model="formdiStributionDepartment" class="demo-form-inline" label-width="100px">
          <el-form-item label="分配部门"></el-form-item>
          <br>
          <el-form-item label="员工编号">
            <el-input v-model="formdiStributionDepartment.staffId" placeholder="请输入员工编号"></el-input>
          </el-form-item>
          <el-form-item label="员工姓名">
            <el-input v-model="formdiStributionDepartment.staffName" placeholder="请输入员工姓名"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="formdiStributionDepartment.staffSex" placeholder="请输入性别"></el-input>
          </el-form-item>
          <el-form-item label="员工手机">
            <el-input v-model="formdiStributionDepartment.staffPhone" placeholder="请输入员工手机"></el-input>
          </el-form-item>
          <el-form-item label="当前归属部门">
            <el-input v-model="formdiStributionDepartment.staffBeforeDepartment" placeholder="请输入当前部门"></el-input>
          </el-form-item>
          <el-form-item label="调整后部门">
            <el-input style="width: 170px;" v-model="formdiStributionDepartment.staffAfterDepartment" placeholder="请输入归属部门"></el-input><el-button style="width: 40px" typ="primary" @click="departmentVisible=true">选择</el-button>
          </el-form-item>
        </el-form>
      </div>
      <template slot="footer">
        <el-button type="primary" @click="saveDepartment">保存</el-button>
        <el-button @click="cancelDepartment">取消</el-button>
      </template>
    </el-dialog>
    <el-dialog :title='excelTitle' :visible.sync="dialogVisible" :close-on-click-modal="false" width="600px">
      <template>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="checkedemployees" @change="handleCheckedEmployeesChange">
          <el-checkbox v-for="city in employees" :label="city" :key="city">{{city}}</el-checkbox>
        </el-checkbox-group>
      </template>
      <template slot="footer">
        <el-button type="primary" @click="exportExcel">确定导出</el-button>
        <el-button @click="cancel">取 消</el-button>
      </template>
    </el-dialog>
    <el-dialog title="请选择部门" :visible.sync="departmentVisible" :close-on-click-modal="false" width="600px">
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
      <el-button @click="getCheckedNodes">点击弹出选中的部门的ID</el-button>
    </el-dialog>
    <el-dialog :title='createTitle' :visible.sync="createDialogVisible" :close-on-click-modal="false" width="900px">
      <el-form ref="form" :model="createForm" label-width="80px">
        <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 10px ;">
          <div style="font-family: Consolas; font-size:20px ;margin-bottom: 20px;">员工信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="员工编号:" label-width="150px">
                <el-input style="width:200px;"  v-model="createForm.staffNum"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工姓名:" label-width="150px">
                <el-input style="width:200px;" v-model="createForm.staffName"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="性别:" label-width="150px">
                <el-input style="width:200px;" v-model="createForm.staffSex"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工手机号:" label-width="150px">
                <el-input style="width:200px;" v-model="createForm.staffTelephone"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="员工邮箱:" label-width="150px">
                <el-input style="width:200px;" v-model="createForm.staffEmail"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="归属部门:" label-width="150px">
                <el-input style="width:200px;" v-model="createForm.departmentId"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="是否离职:" label-width="150px">
                <el-input style="width:200px;" v-model="createForm.isDimission"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="登录账号:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="createForm.accountId"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>



        <div>
          <div style="font-family: Consolas; font-size:20px ; margin-left:50px;margin-bottom: 20px;padding-top: 20px">其他信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="新建时间:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="createForm.createTime"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="新建人:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="createForm.createEmp"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="修改时间:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="createForm.modifyTime"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="修改人:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="createForm.modifyEmp"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="备注:" label-width="150px">
                <el-input style="width:200px;" v-model="createForm.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <el-button type="primary" style="width:100px" @click="saveEmployee" >保存</el-button>
              &nbsp&nbsp&nbsp&nbsp&nbsp
              <el-button type="primary" style="width:100px" @click="cancelEmployee">取消</el-button>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
    </el-dialog>
    <el-dialog :title='modifyTitle' :visible.sync="modifyDialogVisible" :close-on-click-modal="false" width="900px">
      <el-form ref="form" :model="modifyForm" label-width="80px">
        <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 10px ;">
          <div style="font-family: Consolas; font-size:20px ;margin-bottom: 20px;">员工信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="员工编号:" label-width="150px">
                <el-input style="width:200px;"  v-model="modifyForm.staffNum"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工姓名:" label-width="150px">
                <el-input style="width:200px;" v-model="modifyForm.staffName"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="性别:" label-width="150px">
                <el-input style="width:200px;" v-model="modifyForm.staffSex"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工手机号:" label-width="150px">
                <el-input style="width:200px;" v-model="modifyForm.staffTelephone"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="员工邮箱:" label-width="150px">
                <el-input style="width:200px;" v-model="modifyForm.staffEmail"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="归属部门:" label-width="150px">
                <el-input style="width:200px;" v-model="modifyForm.departmentId"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="是否离职:" label-width="150px">
                <el-input style="width:200px;" v-model="modifyForm.isDimission"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="登录账号:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="modifyForm.accountId"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>



        <div>
          <div style="font-family: Consolas; font-size:20px ; margin-left:50px;margin-bottom: 20px;padding-top: 20px">其他信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="新建时间:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="modifyForm.createTime"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="新建人:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="modifyForm.createEmp"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="修改时间:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="modifyForm.modifyTime"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="修改人:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="modifyForm.modifyEmp"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="备注:" label-width="150px">
                <el-input style="width:200px;" v-model="modifyForm.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <el-button type="primary" style="width:100px" @click="saveUpdate" >保存</el-button>
              &nbsp&nbsp&nbsp&nbsp&nbsp
              <el-button type="primary" style="width:100px" @click="cancelUpdate">取消</el-button>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
    </el-dialog>

  </home>

</template>

<script>
  import commonUtils from '../../common/commonUtils'

  const employeeOptions = ['员工编号', '登录账号', '员工姓名', '性别', '员工手机', '员工邮箱', '所属部门', '上级部门', '是否离职'];
  export default {
    name: "employee-list",
    inject:['changeDialogVisible','chooseStaff'],
    props: {
      relAccount: 0
    },
    data() {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        excelForm: {
          staffNum: '',
          staffName: '',
          accountId: '',
          staffSex: '',
          staffTelephone: '',
          staffEmail: '',
          departmentId: '',
          upperDepartmentNo: '',
          isDimission: '',
        },
        form: {
          staffNum: '',
          staffName: '',
          accountName: '',
          isDimission: '',
          departmentId: '',
          upperDepartmentNo: '',
        },
        createForm:{
          staffNum:'',
          staffName:'',
          staffSex:'',
          staffTelephone:'',
          staffEmail:'',
          departmentId:'',
          isDimission:'',
          accountId:'',
          remark:'',

        },
        modifyForm:{
          staffNum:'',
          staffName:'',
          staffSex:'',
          staffTelephone:'',
          staffEmail:'',
          createTime:'',
          createEmp:'',
          remark:'',
        },

        tableData: [],
        selection: '',
        id: '',
        staffName: '',
        accountId: '',
        accountName:'',
        staffSex: '',
        SexEnum: {},
        staffTelephone: '',
        staffEmail: '',
        departmentName: '',
        upperDepartmentName: '',
        isDimission: '',
        isDimissionEnum: {},
        title: '模板',
        excelTitle: '请选择需要导出的字段',
        createTitle: '员工新建页面',
        modifyTitle:'员工信息修改页面',
        createDialogVisible:false,
        modifyDialogVisible:false,

        dialogVisible: false,
        templateGroupName: '测试',
        description: '测试',
        staffDtoList: [],
        formdiStributionDepartment: {
          staffId: '',
          staffName: '',
          staffSex: '',
          staffPhone: '',
          staffBeforeDepartment: '',
          staffAfterDepartment: '',
        },
        distributionDepartmentFlag: false,
        deleteEmployeeFlag: false,
        options: [{
          value: '2',
          label: '全部'
        }, {
          value: '0',
          label: '在职'
        }, {
          value: '1',
          label: '离职'
        }],
        cloumnDisabled: false,
        buttonDisabled: false,
        checkAll: false,
        checkedemployees: [],
        employees: employeeOptions,
        isIndeterminate: true,
        filterVal: [],
        list: [],
        disabled: true,
        departmentVisible:false,
        defaultProps: {
          label: 'departmentName',
          children: 'children',
          id: 'id',
          status: 'status'
        },
        operationBtnActive: true,

        empButtonPermission: {
          createPermission: true,
          modifyPermission: true,
          deletePermission: true,
          quitPermission: true,
          recoveryPermission: true,
          assignDepPermission: true,
          exportPermission: true
        }
      }
    },
    filters: {
      protect: function (value) {
        if (!value) {
          return ''
        }
        return value.substr(0, 3) + "****" + value.substr(-4);
      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
    },
    created() {
      var self = this;
      self.judgmentAuthority();
      if(self.relAccount == 1) {
        self.form.isDimission = '0';
        self.buttonDisabled = true;
        self.cloumnDisabled = true;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          isDimission: self.form.isDimission,
          relAccount: self.relAccount,
        };

        self.$http.get('employee/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          self.SexEnum = result.SexEnum;
          self.isDimissionEnum = result.isDismissionEnum;
          self.staffDtoList = result.staffDtoList;
        }).catch(function (error) {
          commonUtils.Log("employee/querylist.do_:" + error);
          self.$message.error("获取数据错误");
        })
      }else {
        self.fetchData();
      }
      ;
    },
    methods: {
      judgmentAuthority() {
        const self = this;
        let permission = self.$store.state.powerList;
        permission.forEach(item=>{
          if (item === 15) {
            self.empButtonPermission.createPermission = false
          }
          if (item === 16) {
            self.empButtonPermission.modifyPermission = false
          }
          if (item === 17) {
            self.empButtonPermission.deletePermission = false
          }
          if (item === 18) {
            self.empButtonPermission.quitPermission = false
          }
          if (item === 19) {
            self.empButtonPermission.recoveryPermission = false
          }
          if (item === 20) {
            self.empButtonPermission.assignDepPermission = false
          }
          if (item === 66) {
            self.empButtonPermission.exportPermission = false
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
      fetchData() { //获取数据
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          staffNum: self.form.staffNum,
          staffName: self.form.staffName,
          departmentId: self.form.departmentId,
          isDimission: (self.form.isDimission === '2') ? '' : self.form.isDimission,
          accountName: self.form.accountName,
          upper_department_no: self.form.upperDepartmentNo,
          relAccount: self.relAccount,
        };
        self.$http.get('employee/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          self.SexEnum = result.SexEnum;
          self.isDimissionEnum = result.isDismissionEnum;
          self.staffDtoList = result.staffDtoList;
        }).catch(function (error) {
          commonUtils.Log("employee/querylist.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },
      save() {
        this.dialogVisible = false;
      },
      cancel() {
        this.dialogVisible = false;
      },
      add() {
        this.dialogVisible = true;
      },
      createEmployee() {//点击新建按钮，弹出新建窗口

        this.createDialogVisible=true;

      },

      modifyEmployee() {//点击修改按钮，跳转到修改页面

        this.modifyDialogVisible=true;
      },
      saveEmployee(){

        const self=this;
        self.$http.post("employee/insertStaff",self.createForm)
          .then(result => {
            this.createDialogVisible=false;
            self.$message.success("新建用户成功");
          })
          .catch(function (error) {
            commonUtils.Log("employee/insertStaff:"+error);
            self.$message.error("新建用户失败");
          })

      },
      cancelEmployee(){
        this.createDialogVisible=false;
      },
      saveUpdate(){
        console.log("=========")
        const self=this;

        self.$http.post("employee/updateStaff",self.modifyForm)
          .then(result => {

            this.modifyDialogVisible=false;
            self.$message.success("修改成功");
          })
          .catch(function (error) {
            commonUtils.Log("employee/updateStaff:"+error);
            self.$message.error("修改用户信息失败");
          })
        console.log("6666")
      },
      cancelUpdate(){
        this.modifyDialogVisible=false;
      },


      deleteEmployee() {//逻辑删除员工，员工状态改为无效，账户状态改为无效
        this.$confirm('此操作将删除该员工, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var self = this;
          var param = {
            id:self.id,
            accountId:self.accountId,
          };
          self.$http.get('employee/updateStatus.do_', {
            params: param
          }).then((result) => {
            self.$message.success("成功删除");
          }).catch(function (error) {
            commonUtils.Log("employee/updateStatus.do_" + error);
            self.$message.error("获取数据错误");
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      quitEmployee(){
        this.$confirm('此操作将离职该员工, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var self = this;
          var param = {
            id:self.selection,
          };
          self.$http.get('employee/quitEmployee.do_', {
            params: param
          }).then((result) => {
            if (result.status=="success"){
              self.$message.success("成功离职");
            } else {
              self.$message.error("离职失败")
            }

          }).catch(function (error) {
            commonUtils.Log("employee/quitEmployee.do_" + error);
            self.$message.error("获取数据错误");
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消离职'
          });
        });
      },
      recovery(){
        this.$confirm('此操作将恢复该员工, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var self = this;
          var param = {
            id:self.selection,
          };
          self.$http.get('employee/recoverEmployee.do_', {
            params: param
          }).then((result) => {
            if (result.status=="success"){
              self.$message.success("恢复成功");
            } else {
              self.$message.error("恢复失败")
            }
          }).catch(function (error) {
            commonUtils.Log("employee/recoverEmployee.do_" + error);
            self.$message.error("恢复失败");
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消恢复'
          });
        });
      },
      distributionDepartment() {
        this.distributionDepartmentFlag = true;
        this.departmentVisible=false;
      },
      saveDepartment() {
        this.distributionDepartmentFlag=false;
      },
      cancelDepartment() {
        this.distributionDepartmentFlag=false;
        alert("已取消");
      },
      confirmChoice(){
        const self = this;
        if(self.selection!=''){
          var staffData = {};
          for(var i = 0;;i++){
            staffData=self.tableData[i];
            if(self.selection==staffData.id){
              break;
            }
          }
          this.chooseStaff(staffData);
          this.changeDialogVisible();
        }else {
          self.$message.info("请选择一名员工");
        }

      },
      cancelChoice(){
        this.changeDialogVisible();
      },
      exportExcel() {
        if (this.checkedemployees.length === 0) {
          this.$message({
            showClose: false,
            message: '请选择需要导出的字段',
            type: 'error'
          });
        } else {
          require.ensure([], () => {
            const {export_json_to_excel} = require('../../excel/Export2Excel');
            const tHeader = this.checkedemployees;
            // 上面设置Excel的表格第一行的标题

            const filterVal = this.exportField(this.checkedemployees);
            // 上面的staffNum、accountId、staffName是tableData里对象的属性
            const list = this.staffDtoList;  //把data里的tableData存到list
            for (let i = 0; i < list.length; i++) {
              if (list[i].isDimission === 0) {
                list[i].isDimission = '在职'
              } else if (list[i].isDimission === 1) {
                list[i].isDimission = '离职'
              }
              if (list[i].staffSex === 1) {
                list[i].staffSex = '男'
              } else if (list[i].staffSex === 2) {
                list[i].staffSex = '女'
              }
            }
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '员工管理列表excel');
            this.$message({
              showClose: true,
              message: '文件导出成功',
              type: 'success'
            });
            this.dialogVisible = false;
            this.checkedemployees = [];
            this.filterVal = [];
          })
        }
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      handleCheckAllChange(val) {
        this.checkedemployees = val ? employeeOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckedEmployeesChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.employees.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.employees.length;
      },
      exportField(val) {
        for (let i = 0; i < val.length; i++) {
          if (this.checkedemployees[i] === '员工编号') {
            this.filterVal.push('staffNum')
          } else if (this.checkedemployees[i] === '登录账号') {
            this.filterVal.push('accountId')
          } else if (this.checkedemployees[i] === '员工姓名') {
            this.filterVal.push('staffName')
          } else if (this.checkedemployees[i] === '性别') {
            this.filterVal.push('staffSex')
          } else if (this.checkedemployees[i] === '员工手机') {
            this.filterVal.push('staffTelephone')
          } else if (this.checkedemployees[i] === '员工邮箱') {
            this.filterVal.push('staffEmail')
          } else if (this.checkedemployees[i] === '所属部门') {
            this.filterVal.push('departmentId')
          } else if (this.checkedemployees[i] === '上级部门') {
            this.filterVal.push('upperDepartmentNo')
          } else if (this.checkedemployees[i] === '是否离职') {
            this.filterVal.push('isDimission')
          }
        }
        return this.filterVal;
      },
      approvalInfo(val){
        this.disabled = false;
        this.id = val.id;
        this.accountId = val.accountId;

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
      getCheckedNodes() {
        // 用于演示获取部门id的方式
        this.formdiStributionDepartment.staffAfterDepartment=this.$refs.tree.getCheckedNodes()[0].id;
        this.departmentVisible=false;
        //alert(this.$refs.tree.getCheckedNodes()[0].id);
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
      deleteDept(){
        var check_node = this.$refs.tree.getCheckedNodes()[0];
        if( this.$options.methods.checkHaveChildren(check_node) ){
          alert("删除失败，请先删除该部门的下级部门");
          return;
        }
        var params = {
          id: check_node.id
        };
        this.$http.post('department/deleteDepartment.do_',params);
        this.dialogVisible = false;
        // 删除节点后前端显示跟着修改，避免刷新整个页面
        check_node.status = 0;
        var tmpNode = {
          departmentName: "tmp",
          children: [],
          id: 99999999,
          status: 0
        };
        this.$refs.tree.insertBefore(tmpNode,check_node.id);
        this.$refs.tree.remove(check_node.id);
        this.$refs.tree.insertAfter(check_node,tmpNode.id);
        this.$refs.tree.remove(tmpNode.id);
        this.operationBtnActive=true;
        alert("部门删除成功");
      },
      checkHaveChildren (data) {
        for(var i = 0;i<data.children.length;i++){
          if(data.children[i].status === 1){
            return true;
          }
        }
        return false;
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
      changeUpper() {
        window.localStorage.setItem("dept_id",this.$refs.tree.getCheckedNodes()[0].id);
        window.localStorage.setItem("dept_name",this.$refs.tree.getCheckedNodes()[0].departmentName);
        this.$router.replace('/departmentManagement/changeUpperDepartment');
      },
      approvalInfo(val){
        this.disabled = false;
        this.id = val.id;
        this.accountId = val.accountId;
        this.modifyForm.id=val.id;
        this.modifyForm.accountId=val.accountId;
        this.modifyForm.staffNum=val.staffNum;
        this.modifyForm.staffName=val.staffName;
        this.modifyForm.staffSex=val.staffSex;
        this.modifyForm.staffEmail=val.staffEmail;
        this.modifyForm.isDimission=val.isDimission;
        this.modifyForm.staffTelephone=val.staffTelephone;
        this.modifyForm.departmentId=val.departmentId;
        this.modifyForm.createTime=val.createTime;
        this.modifyForm.createEmp=val.createEmp;
        this.modifyForm.remark=val.remark;
      }

    }
  }
</script>

<style scoped>


</style>
