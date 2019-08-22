<template>
  <home>
    <div style="width:95%;">
      <el-form ref="form" :model="form" label-width="auto">
        <el-row>
          <el-col :span="6">
            <el-form-item label="角色名称">
              <el-input placeholder="角色名称"  v-model="form.roleName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="支持业务线">
              <el-select placeholder="所有"  v-model="form.businessLine"  >
                <el-option label="所有" value=" "></el-option>
                <el-option label="买买车" value="买买车"></el-option>
                <el-option label="闪贷" value="闪贷"></el-option>
                <el-option label="租车" value="租车"></el-option>
                <el-option label="专车" value="专车"></el-option>
                <el-option label="保险" value="保险"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="审批人账号">
              <el-input placeholder="审批人账号"  v-model="form.approverAccountName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="审批人员工编号">
              <el-input placeholder="审批人员工编号"  v-model="form.approverStaffNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="审批人姓名">
              <el-input placeholder="审批人姓名" v-model="form.approverStaffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="审批人所属部门" label-width="auto">
              <el-input  v-model="form.approverDepartmentName" style="width: 60%"></el-input>
              <el-button type="text" @click="selectDepartment" >选择</el-button>
              <el-button type="text" @click="clearDepartment" style="margin-left: 0px;">清空</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="角色状态">
              <el-select placeholder="所有" v-model="form.roleStatus" >
                <el-option label="所有" value=" "></el-option>
                <el-option label="无效" value="0"></el-option>
                <el-option label="有效" value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="权限名称">
              <el-input placeholder="权限名称"  v-model="form.powerName" style="width: 60%"></el-input>
              <el-button type="text" @click="selectPermissions">选择</el-button>
              <el-button type="text" @click="clearPermission" style="margin-left: 0px;">清空</el-button>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <div v-if="!BtnPermission.exportPermission">
                <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
                <el-button type="primary" style="width:100px" @click="exportRolePower">导出</el-button>
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
    </div>
    <div style="margin-bottom: 10px" v-else>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <!--<el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>-->
      <el-table-column prop="roleId" label="角色ID" width="auto">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="ChooseOnDetail(scope.row.roleId)">{{scope.row.roleId}}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="roleName" label="角色名称" width="auto"></el-table-column>
      <el-table-column prop="businessLine" label="支持业务线" width="auto"></el-table-column>
      <el-table-column prop="approverAccountName" label="审批人账号" width="auto"></el-table-column>
      <el-table-column prop="approverStaffNum" label="审批人员工编号" width="auto"></el-table-column>
      <el-table-column prop="approverStaffName" label="审批人姓名" width="auto"></el-table-column>
      <el-table-column prop="approverDepartmentName" label="审批人所属部门" width="auto"></el-table-column>
      <el-table-column prop="roleStatus" label="角色状态" width="auto">
        <template slot-scope="scope">
          {{RoleStatusEnum[scope.row.roleStatus]}}
        </template>
      </el-table-column>
      <el-table-column prop="powerName" label="权限名称" width="auto">
        <template slot-scope="scope">
          <el-popover
            placement="bottom"
            width="200"
            trigger="click"
            :content="scope.row.powerName">
            <el-button slot="reference" type="text">{{scope.row.powerName|ellipsis}}</el-button>
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
    <el-dialog :title="roleAssignPermissionTitle" :visible.sync="roleAssignPermissionFlag" :close-on-click-modal="false"
               width="700px">
      <div class="dialog-main" >
        <el-form>
          <el-row type="flex" justify="center" style="width: 100%;">
            <el-col :span="11">
              <el-scrollbar style="width: 400px">
                <el-tree
                  style="float: left;margin-left: 100px;height: 350px"
                  ref="permTree"
                  :props="PermissionProps"
                  node-key="powerId"
                  :load="loadNodePermTree"
                  lazy="true"
                  show-checkbox
                  check-strictly
                  @check-change="handleCheckChange">
                </el-tree>
              </el-scrollbar>
            </el-col>
          </el-row>
          <div style="height: 80px;display: flex;align-items: center;margin-left: 80%">
            <el-button type="primary" style="margin-right: 10px" @click="getCheckedPermission">确定</el-button>
          </div>
        </el-form>
      </div>
    </el-dialog>

    <el-dialog title="角色详情信息" :visible.sync="roleInfoDetailFlag" :close-on-click-modal="false" width="900px">
      <!--<el-form ref="form" :model="formDetail" label-width="80px">-->
        <div>
          <div style="margin-bottom: 10px">
            <p>角色信息</p>
          </div>
          <div style="width:85%; margin-left: 70px">
            <el-form ref="form" :model="formInfo" label-width="110px">
              <el-row>
                <el-col :span="10">
                  <el-form-item label="角色ID">
                    <el-input style="width:200px;" :disabled="true" v-model="formInfo.roleID"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="角色名称">
                    <el-input style="width:200px;" :disabled="true" v-model="formInfo.roleName"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="10">
                  <el-form-item label="审批人账号">
                    <el-input style="width:200px;" :disabled="true" v-model="formInfo.accountNum"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="审批人员工编号">
                    <el-input style="width:200px;" :disabled="true" v-model="formInfo.staffNum"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="10">
                  <el-form-item label="审批人姓名">
                    <el-input style="width:200px;" :disabled="true" v-model="formInfo.staffName"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label="审批人所属部门">
                    <el-input style="width:200px;" :disabled="true" v-model="formInfo.departmentName"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="15">
                  <el-form-item label="支持业务线">
                    <!--<template v-for="item in chks">
                      <input type="checkbox" name="hobby" :value="item.id"
                           :checked="form.loopsss.indexOf(item.id) > -1"/>{{item.name}}
                    </template>
                    -->
                    <input type="checkbox" :disabled="true" v-model="formInfo.businessLine" value="买买车">买买车
                    <input type="checkbox" :disabled="true" v-model="formInfo.businessLine" value="租车">租车
                    <input type="checkbox" :disabled="true" v-model="formInfo.businessLine" value="闪贷">闪贷
                    <input type="checkbox" :disabled="true" v-model="formInfo.businessLine" value="专车">专车
                    <input type="checkbox" :disabled="true" v-model="formInfo.businessLine" value="保险">保险
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="10">
                  <el-form-item label="描述">
                    <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" :disabled="true" v-model="formInfo.description"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <div>
            <p>其它信息</p>
            <div>
              <div style="width:85%; margin-left: 70px">
                <el-form ref="otherForm" :model="otherForm" label-width="110px">
                  <el-row>
                    <el-col :span="10">
                      <el-form-item label="新建人">
                        <el-input style="width:200px;" :disabled="true" v-model="otherForm.createEmp"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="10">
                      <el-form-item label="新建时间">
                        <el-input style="width:200px;" :disabled="true" v-model="otherForm.createTime"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="10">
                      <el-form-item label="修改人">
                        <el-input style="width:200px;" :disabled="true" v-model="otherForm.modifyEmp"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="10">
                      <el-form-item label="修改时间">
                        <el-input style="width:200px;" :disabled="true" v-model="otherForm.modifyTime"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="1">
                      <el-form-item label="状态">
                        <el-select v-model="formInfo.roleStatus" :disabled="true" style="width:150px;">
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
                </el-form>
              </div>
            </div>
          </div>
          <div style="text-align: center">
            <el-button type="primary" @click="cancelRoleInfo" style="width:70px">取消</el-button>
          </div>
        </div>
     <!-- </el-form>-->
    </el-dialog>
    <el-dialog :title='excelTitle' :visible.sync="rolePowerDialogVisible" :close-on-click-modal="false" width="600px">
      <template>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="checkRolePowers" @change="handleCheckRolePowerChange">//单选框
          <el-checkbox v-for="rolePower in rolePowers" :label="rolePower" :key="rolePower">{{rolePower}}</el-checkbox>
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
  const rolePowersOptions = ['角色ID', '角色名称','支持业务线', '审批人账号', '审批人员工编号', '审批人姓名', '审批人所属部门', '角色状态', '权限名称'];
  export default {
    data() {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        form: {
          roleId:'',
          roleName:'',
          businessLine:'',
          approverAccountName:'',
          approverStaffNum:'',
          approverStaffName:'',
          approverDepartmentName:'',
          roleStatus:'',
          powerName:'',
          powerId:''
        },
        defaultProps: {
          label: 'departmentName',
          children: 'children',
          id: 'id',
          status: 'status'
        },
        tableData: [],
        dialogVisibleDepartment:false,
        buttonDisabled: false,
        titleDepartment:'选择部门',
        excelTitle: '请选择需要导出的字段',
        isIndeterminate: true,
        checkAll: false,
        rolePowerDialogVisible:false,
        checkRolePowers: [],
        rolePowers:rolePowersOptions,
        rolePowerDtoList:[],
        filterVal: [],
        BtnPermission: {
          exportPermission: true,
        },
        roleAssignPermissionFlag: false,
        powerSelectedList: [],
        selected: [],
        PermissionProps: {
          label: 'powerName',
          children: 'children',
          id: 'powerId'
        },
        selectedNodes: [],
        myRole: [],
        roleAssignPermissionTitle: '选择权限',
        powerTree: [],
        checkStrictly: false,
        roleInfoDetailFlag: false,

        formInfo: {
          roleID: '',
          roleName: '',
          businessLine: [],
          roleStatus: '',
          accountNum: '',
          staffNum: '',
          staffName: '',
          departmentName: '',
          description: '',
        },

        otherForm:{
          createEmp:'',
          createEmpNum:'',
          createEmpName:'',
          createEmpTime:'',
          modifyEmp:'',
          modifyEmpNum:'',
          modifyEmpName:'',
          modifyEmpTime:'',
        },

        RoleStatusEnum: {},
        options: [
          {
            value: 0,
            label: '无效',
          }, {
            value: 1,
            label: '有效',
          }],
      }
    },
    filters: {
      ellipsis(value) {
        if (!value) return "";
        if (value.length > 30) {
          return value.slice(0, 30) + "...";
        }
        return value;
      }
    },
    activated() {
      commonUtils.Log("页面激活");
     },
     mounted() {
       commonUtils.Log("页面进来");
       this.judgmentAuthority();
       /*this.fetchData();*/
    },

    methods: {
      judgmentAuthority() {
        const self = this;
        let permission = self.$store.state.powerList;
        permission.forEach(item=>{
          if (item === 56) {
            self.BtnPermission.exportPermission = false
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
          roleId:self.form.roleId,
          roleName:self.form.roleName,
          businessLine:self.form.businessLine,
          approverAccountName:self.form.approverAccountName,
          approverStaffNum:self.form.approverStaffNum,
          approverStaffName:self.form.approverStaffName,
          approverDepartmentName:self.form.approverDepartmentName,
          roleStatus:self.form.roleStatus,
          powerName:self.form.powerName,
          powerId:self.form.powerId,
        };
        self.$http.post('power/queryRolePowerlist.do_', param)
          .then((result) => {
          self.tableData = result.page.list;
          self.total = result.page.totalCount;
          self.rolePowerDtoList = result.rolePowerDtoList;
          self.RoleStatusEnum = result.RoleStatusEnum;
        }).catch(function (error) {
          commonUtils.Log("queryRolePowerlist.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },


      loadNode(node,resolve){
        var self = this;
        self.$http.get('department/buildTree2.do_', {
          params: null
        }).then((result) => {
          resolve([result.departmentDto]);
        }).catch(function (error) {

        });
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
      getCheckedDepartment() {
        // 获取部门 回填到文本框中
        // alert(this.$refs.tree.getCheckedNodes()[0].departmentName);
        this.form.approverDepartmentName=this.$refs.tree.getCheckedNodes()[0].departmentName;
        this.dialogVisibleDepartment=false;
      },
      selectDepartment(){//选择部门
        this.dialogVisibleDepartment=true;
      },
      clearDepartment(){//清除部门的值
        this.form.approverDepartmentName='';
      },
      exportRolePower(){
        this.rolePowerDialogVisible = true;
      },
      handleCheckAllChange(val) {
        this.checkRolePowers = val ? rolePowersOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckRolePowerChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.rolePowers.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.rolePowers.length;
      },

      selectPermissions() {
        const self = this;
        self.roleAssignPermissionFlag = true;
      },
      getCheckedPermission() {
        this.form.powerName=this.$refs.permTree.getCheckedNodes()[0].powerName;
        this.roleAssignPermissionFlag=false;
      },
      //获取树数据
      loadNodePermTree(node, resolve) {
        var self = this;
        self.$http.get('power/getPowerList', {
          params: null
        }).then((result) => {
          resolve([result.powerTree]);
        }).catch(function (error) {
          commonUtils.Log("account/querylist.do_:" + error);
          self.$message.error("获取数据错误")
        });

      },
      handleCheckChange (data,checked,node) {
        // 手动设置单选
        if(checked === true) {
          this.form.powerId = data.powerId;
          this.checkedId = data.powerId;
          this.$refs.permTree.setCheckedKeys([data.powerId]);
        } else {
          if (this.checkedId == data.powerId) {
            this.$refs.permTree.setCheckedKeys([data.powerId]);
          }
        }
      },
      clearPermission(){//清除权限的值
        this.form.powerName='';
      },

      exportExcel() {
        if(this.checkRolePowers.length ===0){
          this.$message({
            showClose: false,
            message: '请选择需要导出的字段',
            type: 'error'
          });
        }else{
          require.ensure([], () => {
            const {export_json_to_excel} = require('../../excel/Export2Excel');
            const tHeader = this.checkRolePowers;
            // 上面设置Excel的表格第一行的标题

            const filterVal = this.exportField(this.checkRolePowers);
            // 上面的staffNum、accountId、staffName是tableData里对象的属性
            const list = this.rolePowerDtoList;  //把data里的tableData存到list
            for (let i = 0; i < list.length; i++) {
              list[i].roleStatus = this.RoleStatusEnum[list[i].roleStatus];
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
            export_json_to_excel(tHeader, data, '角色权限'+currentdate);
            this.$message({
              showClose: true,
              message: '文件导出成功',
              type: 'success'
            });
            this.rolePowerDialogVisible=false;
            this.checkRolePowers=[];
            this.filterVal=[];
          })
        }
      },
      exportField(val) {
        for (let i = 0; i < val.length; i++) {
          if (this.checkRolePowers[i] === '角色ID') {
            this.filterVal.push('roleId')
          } else if (this.checkRolePowers[i] === '角色名称') {
            this.filterVal.push('roleName')
          } else if (this.checkRolePowers[i] === '支持业务线') {
            this.filterVal.push('businessLine')
          } else if (this.checkRolePowers[i] === '审批人账号') {
            this.filterVal.push('approverAccountName')
          } else if (this.checkRolePowers[i] === '审批人员工编号') {
            this.filterVal.push('approverStaffNum')
          } else if (this.checkRolePowers[i] === '审批人姓名') {
            this.filterVal.push('approverStaffName')
          } else if (this.checkRolePowers[i] === '审批人所属部门') {
            this.filterVal.push('approverDepartmentName')
          } else if (this.checkRolePowers[i] === '角色状态') {
            this.filterVal.push('roleStatus')
          } else if (this.checkRolePowers[i] === '权限名称') {
            this.filterVal.push('powerName')
          }
        }
        return this.filterVal;
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      cancel() {
        this.rolePowerDialogVisible = false;
      },
      ChooseOnDetail(roleId) {
        const self = this;
        self.roleInfoDetailFlag = true;
        var param = {
          roleID: roleId,
        };
        self.$http.get('roleManage/getOtherOneInf.do_', {
          params: param
        }).then((result) => {
          self.otherForm = result.page;
          self.otherForm.createEmp = `${self.otherForm.createEmpNum}(${self.otherForm.createEmpName})`;
          self.otherForm.modifyEmp = `${self.otherForm.modifyEmpNum}(${self.otherForm.modifyEmpName})`;
        }).catch(function (error) {
          commonUtils.Log("roleManage/getOtherOneInf.do_" + error);
          self.$message.error("获取数据错误");
        });
        self.$http.get('roleManage/getOneInf.do_', {
          params: param
        }).then((result) => {
          self.formInfo=result.page;
          self.RoleStatusEnum = result.RoleStatusEnum;
          self.formInfo.businessLine=self.formInfo.businessLine.split(';');
        }).catch(function (error) {
          commonUtils.Log("roleManage/getOneInf.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },
      cancelRoleInfo() {
        this.roleInfoDetailFlag = false;
      }
    }
  }
</script>

<style scoped>


</style>

