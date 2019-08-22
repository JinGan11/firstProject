<template>
  <home>
    <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 20px ">
      <el-row :gutter="20">
        <el-col :span="5" style="color: #409EFF">
          申请信息
        </el-col>
        <el-col :span="2">
          <el-button type="primary" size="mini" @click="savaModifyRoleApply">保存</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" size="mini" @click="modifySaveCommitRoleApply">保存并提交</el-button>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" size="mini" @click="cancelRoleApply">取消</el-button>
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
              <el-input style="width:250px;"  placeholder="请选择角色" v-model="formRoleInfo.roleName" :disabled="true"></el-input>
              <el-button type="text" @click="selectRoleForRoleApply">选择</el-button>
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
        <el-col :span="2">
          <el-button type="primary" size="mini" @click="addAccountForApply()">添加</el-button>
        </el-col>
      </el-row>
    </div>
    <div style="margin-left: 40px;">
      <el-table ref="multipleTable" :data="tableDataAccount" border>
        <!--        <el-table-column type="selection" width="35"></el-table-column>-->
        <!--        <el-table-column prop="id" label="隐藏id"></el-table-column>-->
        <el-table-column prop="accountName" label="申请账号" width="150"></el-table-column>
        <el-table-column prop="staffName" label="关联员工姓名"width="150"></el-table-column>
        <el-table-column prop="staffNum" label="关联员工编号"  width="150"></el-table-column>
        <el-table-column prop="department" label="关联员工所属部门" width="200"></el-table-column>
        <el-table-column prop="applyOperation" label="申请操作" width="150" style="text-align: center">
          <template slot-scope="scope">
            <el-select v-model="scope.row.applyOperation" clearable style="width:100px;" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="operationOfDelete" label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="text"  @click="deleteSelect(scope.$index, scope.row)">删除</el-button>
          </template>
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
            <el-form-item label="申请时间">
              <el-input style="width:300px;" v-model="otherInfo.applyTime" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="申请人">
              <el-input style="width:300px;" v-model="otherInfo.applyStaffName" :disabled="true"></el-input>
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
        <el-row>
          <el-col :span="10">
            <el-form-item label="状态">
              <el-input style="width:300px;" v-model="otherInfo.applyStatus" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <div v-if="false">
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
        </div>
      </el-form>
    </div>





    <el-dialog :title='title1' :visible.sync="dialogVisibleRole"  :close-on-click-modal="false" width="85%">
      <div style="overflow: auto">
        <div>
          <el-form ref="form" :model="formSelectRole" label-width="100px">
            <el-row>
              <el-col :span="10">
                <el-form-item label="角色名称">
                  <el-input style="width:200px;" v-model="formSelectRole.name"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span ="10">
                <el-form-item>
                  <el-button type="primary" @click="fetchDataRole" style="width:100px">查询</el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div style="margin-left:20px;margin-bottom: 10px;">
          <el-button type="primary" @click="selectRole" :disabled="disabledSelectRole"  style="width:100px">确认选择</el-button>
          <el-button type="primary" @click="cancelSelectRole" style="width:100px">取消</el-button>
        </div>
        <el-table :data="tableDataRole" border   @selection-change="handleCurrentChangeRole" ><el-table-column label="选择" width="45">
          <template slot-scope="scope">
            <el-radio v-model="selectionRoleInfo" @change="selectInfoRow(scope.row)" :label="scope.row.roleId"><span width="0px;"></span></el-radio>
          </template>
        </el-table-column>
          <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
<!--          <el-table-column prop="roleId" label="角色ID" width="150"></el-table-column>-->
          <el-table-column prop="roleId" label="角色ID" width="120">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="ChooseOnDetail(scope.row.roleId)">{{scope.row.roleId}}</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="roleName" label="角色名称"width="150"></el-table-column>
          <el-table-column prop="accountNum" label="审批人账号" width="120"></el-table-column>
          <el-table-column prop="staffNum" label="审批人员工编号" ></el-table-column>
          <el-table-column prop="staffName" label="审批人姓名" width="200"></el-table-column>
          <el-table-column prop="departmentName" label="审批人所属部门" width="120"></el-table-column>
          <el-table-column prop="roleStatus" label="状态" width="100" style="text-align: center">
            <template slot-scope="scope">
              {{RoleStatusEnum[scope.row.roleStatus]}}
            </template>
          </el-table-column>
          <el-table-column prop="description" label="描述" width="120"></el-table-column>
        </el-table>
        <el-pagination background
                       @size-change="handleSizeChangeRole"
                       @current-change="handleCurrentChangeRole"
                       :current-page="currentPage"
                       :page-sizes="[10, 50, 100, 200]"
                       :page-size="pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="total">
        </el-pagination>
      </div>
    </el-dialog>


    <el-dialog :title='title2' :visible.sync="dialogVisibleAccount"  :close-on-click-modal="false" width="80%">
      <div style="overflow: auto">
        <div style="width: 95%;">
          <el-form ref="form" :model="form" label-width="100px">
            <el-row>
              <el-col :span="6">
                <el-form-item label="登陆账号" >
                  <el-input style="width:140px;" v-model="form.accountNo"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="员工编号">
                  <el-input style="width:160px;" v-model="form.staffNo"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="员工姓名">
                  <el-input style="width:180px;" v-model="form.name"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="数据权限类型" label-width="150px">
                  <el-select style="width:180px;" v-model="form.permissions" clearable placeholder="请选择">
                    <el-option
                      v-for="item in form.permissionsList"
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
                <el-form-item label="员工所属部门">
                  <el-input style="width:140px;" :disabled="true" v-model="form.department"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-button type="text" @click="chooseDepartment">选择</el-button>
                <el-button type="text">取消</el-button>
              </el-col>
              <el-col :span="6">
                <el-form-item label="是否关联员工">
                  <el-select style="width: 180px" v-model="form.isRelStaff" clearable placeholder="请选择">
                    <el-option
                      v-for="item in form.isRelStaffoptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="账号状态">
                  <el-select style="width:180px;" v-model="form.status" clearable placeholder="请选择">
                    <el-option
                      v-for="item in accountStatusList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <el-form ref="form" :model="form">
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
          <el-button type="primary" @click="selectAccountConfirm" :disabled="disabledSelectAccount" style="width:70px">确认选择</el-button>
          <el-button type="primary" @click="cancelSelectAccount" style="width:70px">取消</el-button>
        </div>
        <el-table ref="multipleTable" :data="tableData" border  @selection-change="handleSelectAccount">
          <el-table-column type="selection" width="35"></el-table-column>
          <el-table-column prop="id" v-if="false" label="隐藏id"></el-table-column>
          <el-table-column prop="accountName" label="登陆账号" style="width:auto"></el-table-column>
          <el-table-column prop="staffNum" label="员工编号" style="width:auto"></el-table-column>
          <el-table-column prop="staffName" label="员工姓名" style="width:auto"></el-table-column>
          <el-table-column prop="department" label="所属部门" style="width:auto"></el-table-column>
          <el-table-column prop="premissions" label="数据权限类型" style="width:auto">
            <template slot-scope="scope">
              {{form.permissionsEnum[scope.row.premissions]}}
            </template>
          </el-table-column>
          <el-table-column prop="accountState" label="账号状态" style="width:auto">
            <template slot-scope="scope">
              {{form.accountStatusEnum[scope.row.accountState]}}
            </template>
          </el-table-column>
          <el-table-column prop="modifyTime" label="操作时间" style="width:auto"></el-table-column>
          <el-table-column prop="modifyEmpName" label="操作人" style="width:auto"></el-table-column>
        </el-table>
        <el-pagination background
                       @size-change="handleSizeChangeAccount"
                       @current-change="handleCurrentChangeAccount"
                       :current-page="currentPage"
                       :page-sizes="[10, 50, 100, 200]"
                       :page-size="pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="total">
        </el-pagination>
      </div>
    </el-dialog>


    <el-dialog title="选择部门" :visible.sync="chooseDepartmentFlag" width="300px">
      <el-tree
        ref="tree"
        :props="defaultProps1"
        node-key="id"
        :load="loadNodeDepartment"
        lazy="true"
        check-strictly
        show-checkbox
        default-expanded-keys="[1]"
        @check-change="handleClick1">
      </el-tree>
    </el-dialog>


    <!--角色详情页弹窗-->
    <el-dialog title="角色详情信息" :visible.sync="roleInfoDetailFlag" :close-on-click-modal="false" width="900px">
      <!--      <el-form ref="form" :model="formDetail" label-width="80px">-->
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
                      <el-input style="width:200px;" :disabled="true" v-model="formInfo.roleStatus===1?'有效':'无效'"></el-input>

                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>










  </home>
</template>






<script>
  import commonUtils from '../../common/commonUtils'
  export default {
    name: "modifyRoleApply",
    data() {
      return {
        defaultProps1: {
          label: 'departmentName',
          children: 'children',
          isLeaf: 'nodeIsLeaf',
          id: 'id',
          no: 'departmentNo',
        },
        total: 0,
        currentPage: 1,
        pageSize: 10,
        title1: '选择角色',
        title2: '选择账户',
        dialogVisibleRole: false,
        dialogVisibleAccount: false,
        dialogCategory: '',//控制显示对应的具体弹窗
        multipleSelection: [],
        tableDataAccount: [],
        data: [],
        defaultProps: '',
        applyId: '',
        roleId:'',
        applyOperationEnum:{},
        accountIdList:[],
        accountLength:'',
        applyOperationList:[],
        accountChangesList:[],
        accountDuplicateList:[],
        chooseDepartmentFlag: false,
        roleStatus:'',
        disabledSelectRole:true,
        disabledSelectAccount:true,


        accountStatusList: [ {
          value: '1',
          label: '正常'
        }, {
          value: '2',
          label: '冻结'
        }],


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

        formSelectRole: {
          name: '',
        },
        tableDataRole: [],
        selectionRoleInfo: '',


        form: {//选择账户
          accountNo: null,
          staffNo: null,
          name: null,
          permissionsList: [],
          permissionsEnum: {},
          // accountStatusList: [],
          accountStatusEnum: {},
          permissions: null,
          department: null,
          isRelStaffoptions: [{
            value: '1',
            label: '是'
          }, {
            value: '0',
            label: '否'
          }],
          isRelStaff: null,
          status: null
        },

        tableData: [],
        selection: [],
        id: '',
        mineStatusValue: '',

        optionOfApply: '',
        options: [{
          value: 1,
          label: '添加'
        }, {
          value: 2,
          label: '移除'
        }],

        forms:{
          id:'',
          roleApplyNum:"",
          roleId:"",
          roleName:"",
          applyStatus:"",
          applyAccountName:"",
          applyStaffNum:"",
          applyStaffName:"",
          applyDepartmentName:"",
          applyTime:"",
          modifyStaffName:"",
          modifyTime:"",
          accountIdList:[],
          tableDataAccount:[],
          applyOperationList:[],
        },

        //角色详情
        roleInfoDetailFlag:false,
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

      }
    },
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
      this.applyId = sessionStorage.getItem('applyIdFromApply');
      console.log(this.applyId);

      this.roleId=sessionStorage.getItem('roleIdFromApply');
      this.roleStatus=sessionStorage.getItem('roleStatusFromApply');//角色状态
      this.formRoleInfo.roleName = sessionStorage.getItem('roleNameFromApply');//角色名字
      this.formRoleInfo.roleApplyNum=sessionStorage.getItem('applyNumFromApply');//申请编号
      this.formRoleInfo.approverStaffName = sessionStorage.getItem('approverStaffNameFromApply');//审批人
      this.formRoleInfo.businessLine = sessionStorage.getItem('businessLineFromApply');//业务线
      this.otherInfo.approverStaffName = sessionStorage.getItem('approverStaffNameFromApply');//审批人名字
      this.otherInfo.applyTime = sessionStorage.getItem('applyTimeFromApply');//申请时间
      this.otherInfo.modifyTime = sessionStorage.getItem('modifyTimeFromApply');//修改时间
      this.otherInfo.applyAccountName = sessionStorage.getItem('loginUsername');//申请人员工姓名
      this.otherInfo.modifyStaffName = sessionStorage.getItem('modifyEmpFromApply');//修改人员工姓名
      this.otherInfo.applyStatus='已新建';//状态
      this.showAccountListByApplyId();//查询改申请编号 包含的账号列表
      this.queryLoginInRoleApply();//申请人  具体信息
      sessionStorage.setItem('roleIdFromSelectRole', this.roleId);//角色id

    },
    methods: {
      loadNodeDepartment(node,resolve){
        var self = this;
        self.$http.get('department/buildTree2.do_')
          .then((result) => {
            resolve([result.departmentDto]);
          }).catch(function (error) {
        });
      },

      handleClick1(data,checked,node){
        // 手动设置单选
        if(checked === true) {
          this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
          this.form.departmentId = data.id;
          this.form.department = data.departmentName;
        } else {
          if (this.checkedId == data.id) {
            this.$refs.tree.setCheckedKeys([data.id]);
          }
        }
        this.closeChooseDepartment();
      },

      //弹出部门对话框
      chooseDepartment(){
        this.chooseDepartmentFlag = true;
      },
      handleSizeChangeRole(val) {
        this.pageSize = val;
        this.currentPage = 1;
        this.fetchDataRole(1, val);
      },
      handleCurrentChangeRole(val) {
        this.currentPage = val;
        this.fetchDataRole(val, this.pageSize);
      },


      handleSizeChangeAccount(val) {
        this.pageSize = val;
        this.currentPage = 1;
        this.fetchData(1, val);
      },
      handleCurrentChangeAccount(val) {
        this.currentPage = val;
        this.fetchData(val, this.pageSize);
      },


      selectRoleForRoleApply() {
        //点击选择，弹出选择角色对话框
        this.dialogVisibleRole = true;
        //显示所有角色列表
        this.fetchDataRole();
        //清除选中状态
        this.selectionRoleInfo=[];
        //
        this.disabledSelectRole=true;
      },

      fetchDataRole() { //获取数据
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          roleName: self.formSelectRole.name,
          flag: 1,
        };
        self.$http.get("roleManage/querylist.do_", {
          params: param
        }).then((result) => {
          self.tableDataRole = result.page.list;
          self.total = result.page.totalCount;
          self.RoleStatusEnum = result.RoleStatusEnum;
          self.roleDtoList = result.roleList;
        }).catch(function (error) {
          commonUtils.Log("roleManage/querylist.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },

      selectRole() {
        var self=this;
        var param={
          roleID: self.roleId,
        };
        self.$http.get('roleManage/getOneInf.do_', {
          params: param
        }).then((result) => {
          this.roleStatus=result.page.roleStatus;
          if(result.page.roleStatus==0){
            self.$message.info("角色已被删除，添加角色失败！请重新选择");
            this.formRoleInfo.roleName = '';
            this.formRoleInfo.approverStaffName = '';
            this.otherInfo.approverStaffName = '';
            this.formRoleInfo.businessLine = '';
          }
        }).catch(function (error) {
          commonUtils.Log("roleManage/getOtherOneInf.do_" + error);
          self.$message.error("获取数据错误");
        });
        //确认选择按钮 选择角色
        this.dialogVisibleRole = false;
      },
      selectInfoRow(row){
        //激活选择按钮
        this.disabledSelectRole=false;
        this.roleId = row.roleId;
        this.formRoleInfo.roleName = row.roleName;
        this.roleStatus = row.roleStatus;
        this.formRoleInfo.approverStaffName = row.staffName;
        this.otherInfo.approverStaffName = row.staffName;
        this.formRoleInfo.businessLine = row.businessLine;
      },
      cancelSelectRole() {
        //取消按钮
        this.dialogVisibleRole = false;
      },
      cancelRoleApply(){//取消 退回角色申请列表
        this.$router.replace('/roleManagement/apply')
      },


      addAccountForApply() {
        //点击添加按钮，进入账号选择页面
        this.dialogVisibleAccount = true;
        this.fetchData();
        //清楚表格所选的记录
        this.$refs.multipleTable.clearSelection();
      },

      fetchData() {//账户列表
        var self = this;
        var param = {
          page: self.currentPage,
          limit: self.pageSize,
          accountName: self.form.accountNo,
          staffNo: self.form.staffNo,
          name: self.form.name,
          permissions: self.form.permissions,
          department: self.form.departmentId,
          isRelStaff: self.form.isRelStaff,
          status: self.form.status,
          flag : 1,
        };
        self.$http.get('account/querylist.do_', {
          params: param
        }).then((result) => {
          self.tableData = result.page.list;
          self.form.permissionsList = result.permissionList;
          self.form.permissionsEnum = result.permissionEnum;
          self.form.accountStatusEnum = result.accountStatusEnum;
          // self.form.accountStatusList = result.accountStatusList;
          self.total = result.page.totalCount;
        }).catch(function (error) {
          commonUtils.Log("account/querylist.do_:" + error);
          self.$message.error("获取数据错误")
        });
      },
      cancelSelectAccount() {
        //取消按钮
        this.dialogVisibleAccount = false;
      },
      handleSelectAccount(val) {
        this.multipleSelection = val;
        this.disabledSelectAccount=false;
      },
      selectAccountConfirm() {//确定添加账户
        this.accountDuplicateList=[];
        for (let i = 0; i < this.multipleSelection.length; i++) {
          let flag = 0;
          for (let j = 0; j < this.accountChangesList.length; j++) {
            if (this.multipleSelection[i].id == this.accountChangesList[j].id) {
              flag = 1;
              this.accountDuplicateList.push(this.multipleSelection[i].accountName);
            }
          }
          if (flag == 0) {
            this.accountChangesList.push(this.multipleSelection[i]);
          }
        }
        if(this.accountDuplicateList.length>0){
          this.$message.info('账号    '+this.accountDuplicateList+'    已存在，不可重复添加');
        }
        this.tableDataAccount = this.accountChangesList;
        this.dialogVisibleAccount = false;
      },

      deleteSelect(index) { //移除添加的账户 删除行
        this.tableDataAccount.splice(index, 1)
      },


      showAccountListByApplyId() { //修改页面进来 显示账户列表信息
        var self = this;
        var param = {
          applyId: self.applyId,
        };
        self.$http.get('roleApply/showAccountListByApplyId.do_', {
          params: param
        }).then((result) => {


          //请求成功回调
          self.tableDataAccount = result.list;
          console.log( self.tableDataAccount)
          self.accountChangesList=result.list;

        }).catch(function (error) {
          //请求失败回调
          commonUtils.Log("roleApply/showAccountListByApplyId.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },

      queryLoginInRoleApply() {// 修改页面进来  获取当前登录账号 部门 员工
        var self = this;
        var applyAccountName = sessionStorage.getItem('loginUsername');
        var param = {
          applyAccountName: applyAccountName,
        };
        self.$http.get('roleApply/queryLoginInRoleApply.do_', {
          params: param
        }).then((result) => {
          self.otherInfo.applyAccountName = result.page.applyAccountName;
          self.otherInfo.applyStaffNum = result.page.applyStaffNum;
          self.otherInfo.applyStaffName = result.page.applyStaffName;
          self.otherInfo.applyDepartmentName = result.page.applyDepartmentName;
          self.otherInfo.modifyStaffName = result.page.modifyStaffName;
        }).catch(function (error) {
          commonUtils.Log("roleApply/queryLoginInRoleApply.do_" + error);
          self.$message.error("获取数据错误");
        });
      },

      savaModifyRoleApply(){// 保存修改
        for(let i=0;i<this.tableDataAccount.length;i++){//账号ID
          this.accountIdList.push(this.tableDataAccount[i].id);
        }
        for(let i=0;i<this.tableDataAccount.length;i++){//申请操作
          this.applyOperationList.push(this.tableDataAccount[i].applyOperation)
        }
        var self=this;
        self.forms.id=self.applyId;
        self.forms.roleApplyNum=self.formRoleInfo.roleApplyNum;
        self.forms.roleId=self.roleId;
        self.forms.roleName=self.formRoleInfo.roleName;
        self.forms.applyStatus='';
        self.forms.applyAccountName=self.otherInfo.applyAccountName;
        self.forms.applyStaffNum=self.otherInfo.applyStaffNum;
        self.forms.applyStaffName=self.otherInfo.applyStaffName;
        self.forms.applyDepartmentName=self.otherInfo.applyDepartmentName;
        self.forms.applyTime=self.otherInfo.applyTime;
        self.forms.modifyStaffName='';
        self.forms.modifyTime=self.otherInfo.modifyTime;
        self.forms.accountIdList=self.accountIdList;
        self.forms.applyOperationList=self.applyOperationList;

        if (!self.$options.methods.checkInput(self)){
          self.accountIdList=[];
          self.applyOperationList=[];
          return;
        }
        self.$http.post("roleApply/modifyRoleApply.do_",self.forms)
          .then(result => {
            self.$message.info("修改角色申请成功");
            self.$router.replace("/roleManagement/apply");
          }).catch(function (error) {
          commonUtils.Log("/roleManagement/apply"+error);
          self.$message.error("角色申请修改保存失败");
        })
      },

      modifySaveCommitRoleApply(){
        for(let i=0;i<this.tableDataAccount.length;i++){//账号ID
          this.accountIdList.push(this.tableDataAccount[i].id);
        }
        for(let i=0;i<this.tableDataAccount.length;i++){//申请操作
          this.applyOperationList.push(this.tableDataAccount[i].applyOperation)
        }
        var self=this;
        self.forms.id=self.applyId;
        self.forms.roleApplyNum=self.formRoleInfo.roleApplyNum;
        self.forms.roleId=self.roleId;
        self.forms.roleName=self.formRoleInfo.roleName;
        self.forms.applyStatus='';
        self.forms.applyAccountName=self.otherInfo.applyAccountName;
        self.forms.applyStaffNum=self.otherInfo.applyStaffNum;
        self.forms.applyStaffName=self.otherInfo.applyStaffName;
        self.forms.applyDepartmentName=self.otherInfo.applyDepartmentName;
        self.forms.applyTime=self.otherInfo.applyTime;
        self.forms.modifyStaffName='';
        self.forms.modifyTime=self.otherInfo.modifyTime;
        self.forms.accountIdList=self.accountIdList;
        self.forms.applyOperationList=self.applyOperationList;

        if (!self.$options.methods.checkInput(self)){
          self.accountIdList=[];
          self.applyOperationList=[];
          return;
        }
        self.$http.post("roleApply/modifySaveCommitRoleApply.do_",self.forms)
          .then(result => {
            self.$message.info("修改角色申请保存并提交成功");
            self.$router.replace("/roleManagement/apply");
          }).catch(function (error) {
          commonUtils.Log("/roleManagement/apply"+error);
          self.$message.error("角色申请修改保存并提交失败");
        })

      },

      checkInput(self) {
        if (self.formRoleInfo.roleName == '') {
          self.$message.info("申请角色为必填项，不允许为空");
          return false;
        }
        if(self.roleStatus==0){
          self.$message.info("申请角色已失效，请重新选择");
          return false;
        }
        if(self.tableDataAccount.length==0){
          self.$message.info("申请账号不允许为空");
          return false;
        }
        for(let i=0;i<self.tableDataAccount.length;i++){
          if(self.tableDataAccount[i].applyOperation!=1&&self.tableDataAccount[i].applyOperation!=2){
            self.$message.info('账号申请操作不允许为空');
            return false;
          }
        }
        return true;
      },

      //角色信息明细
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





    }
  }

</script>

<style scoped>

</style>
