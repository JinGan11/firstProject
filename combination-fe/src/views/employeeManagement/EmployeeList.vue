<template>
  <home>
    <div style="width:85%; margin-left: 70px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="员工编号">
              <el-input placeholder="员工编号" prop="staffNum" style="width:150px;" v-model="form.staffNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="员工姓名">
              <el-input placeholder="员工姓名" style="width:150px;" v-model="form.staffName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="登陆账号">
              <el-input placeholder="登陆账号" :disabled="cloumnDisabled" style="width:150px;"
                        v-model="form.accountName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="是否离职">
              <el-select v-model="form.isDimission" clearable :disabled="cloumnDisabled" style="width:150px;"
                         placeholder="请选择">
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
              <el-input placeholder="所属部门" :disabled="true" style="width:200px;" v-model="searchDeptName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-button
              type="text"
              @click="searchDeptDialogVisible = true">选择
            </el-button>
            <el-button
              type="text"
              @click="searchDeptClear">清空
            </el-button>
          </el-col>
          <el-col :span="8">
            <el-form-item label="上级部门">
              <el-input placeholder="上级部门" :disabled="true" style="width:200px;"
                        v-model="searchDeptUpperName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-button
              type="text"
              @click="searchDeptUpperDialogVisible = true">选择
            </el-button>
            <el-button
              type="text"
              @click="searchDeptUpperClear">清空
            </el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <div v-if="!buttonDisabled">
                <el-button type="primary" @click="fetchData" style="width:100px">查询</el-button>
                <el-button type="primary" :disabled="empButtonPermission.exportPermission" style="width:100px"
                           @click="add">导出
                </el-button>
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
      <el-button type="primary" @click="createEmployee" v-if="!empButtonPermission.createPermission" style="width:70px">
        新建
      </el-button>
      <el-button type="primary" @click="modifyEmployee" v-if="!empButtonPermission.modifyPermission"
                 :disabled="isDiss" style="width:70px">修改
      </el-button>
      <el-button type="primary" @click="deleteEmployee" v-if="!empButtonPermission.deletePermission"
                 :disabled="disabled" style="width:70px">删除
      </el-button>
      <el-button type="primary" @click="quitEmployee" v-if="!empButtonPermission.quitPermission"
                 :disabled="quitDisabled" style="width:70px">离职
      </el-button>
      <el-button type="primary" @click="recovery" v-if="!empButtonPermission.recoveryPermission"
                 :disabled="recoveryDisabled" style="width:70px">恢复
      </el-button>
      <el-button type="primary" @click="distributionDepartment" v-if="!empButtonPermission.assignDepPermission"
                 :disabled="departmentDisabled" style="width:80px">分配部门
      </el-button>
    </div>
    <div style="margin-bottom: 10px" v-else>
      <el-button type="primary" @click="confirmChoice" style="width:70px">确认选择</el-button>
      <el-button type="primary" @click="cancelChoice" style="width:70px">取消</el-button>
    </div>
    <el-table ref="multipleTable" :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column label="选择" width="45">
        <template slot-scope="scope">
          <el-radio v-model="selection" :label="scope.row.id" @change="approvalInfo(scope.row)"><span
            width="0px;"></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="id" v-if="false" label="隐藏员工id"></el-table-column>
      <el-table-column prop="accountId" v-if="false" label="隐藏账户id"></el-table-column>
      <el-table-column prop="createTime" v-if="false" label="隐藏创建时间"></el-table-column>
      <el-table-column prop="createEmp" v-if="false" label="隐藏创建人"></el-table-column>
      <el-table-column prop="modifyTime" v-if="false" label="隐藏修改时间"></el-table-column>
      <el-table-column prop="modifyEmp" v-if="false" label="隐藏修改人"></el-table-column>
      <el-table-column prop="remark" v-if="false" label="隐藏备注"></el-table-column>
      <el-table-column prop="staffNum" label="员工编号" width="150">
        <template slot-scope="scope">
          <el-button type="text" @click="staffNumBtn(scope.row)">{{scope.row.staffNum}}</el-button>
        </template>
      </el-table-column>
      accountId
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

    <el-dialog :title="title" :visible.sync="distributionDepartmentFlag" :close-on-click-modal="false" width="800px">
      <div class="dialog-main">
        <el-form :inline="true" :rules="ruleDepartment" ref="formdiStributionDepartment"
                 :model="formdiStributionDepartment"  label-width="130px">
          <el-form-item label="分配部门"></el-form-item>
          <br>
          <el-form-item label="员工编号">
            <el-input v-model="formdiStributionDepartment.staffNum" disabled></el-input>
          </el-form-item>
          <el-form-item label="员工姓名">
            <el-input v-model="formdiStributionDepartment.staffName" disabled></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="formdiStributionDepartment.staffSex" disabled></el-input>
          </el-form-item>
          <el-form-item label="员工手机">
            <el-input v-model="formdiStributionDepartment.staffPhone" disabled></el-input>
          </el-form-item>
          <el-form-item label="当前归属部门">
            <el-input v-model="formdiStributionDepartment.staffBeforeDepartment" disabled></el-input>
          </el-form-item>
          <el-form-item label="调整后部门" prop="staffAfterDepartmentName">
            <el-input style="width: 170px;" v-model="formdiStributionDepartment.staffAfterDepartmentName"
                      disabled></el-input>
            <el-button style="width: 50px" typ="primary" @click="departmentVisible=true">选择</el-button>
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
        :default-expanded-keys="[1]"
        :render-content="renderContent"
        @check-change="handleClick">
      </el-tree>
      <el-button @click="getCheckedNodes" style="background-color: cornflowerblue">确定</el-button>
    </el-dialog>
    <el-dialog :title='createTitle' status-icon :visible.sync="createDialogVisible" :close-on-click-modal="false"
               width="900px">
      <el-form ref="createForm" :model="createForm" :rules="rulesCreate" label-width="80px">
        <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 10px ;">
          <div style="font-family: Consolas; font-size:20px ;margin-bottom: 20px;">员工信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="员工编号:" prop="staffNum" label-width="150px">
                <el-input style="width:200px;" v-model="createForm.staffNum"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工姓名:" prop="staffName" label-width="150px">
                <el-input style="width:200px;" v-model="createForm.staffName"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="性别:" prop="staffSex" label-width="150px">
                <template>
                  <el-radio v-model="createForm.staffSex" label="1">男</el-radio>
                  <el-radio v-model="createForm.staffSex" label="2">女</el-radio>
                </template>
              </el-form-item>

            </el-col>
            <el-col :span="12">
              <el-form-item label="员工手机号:" prop="staffTelephone" label-width="150px">
                <el-input style="width:200px;" v-model="createForm.staffTelephone"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="员工邮箱:" prop="staffEmail" label-width="150px">
                <el-input style="width:200px;" v-model="createForm.staffEmail"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="归属部门:" prop="departmentName" placeholder="请选择归属部门" label-width="150px">
                <el-input style="width:80px;" :disabled="true" v-model="createForm.departmentName"></el-input>
                <el-button type="text" @click="selectDepartment">选择</el-button>
                <el-button type="text" @click="clearDepartment">清空</el-button>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="是否离职:" prop="isDimission" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="this.isDimissionEnum[0]"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="登录账号:" prop="accountId" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="createForm.accountId"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>


        <div>
          <div style="font-family: Consolas; font-size:20px ; margin-left:50px;margin-bottom: 20px;padding-top: 20px">
            其他信息
          </div>
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
              <el-form-item label="备注:" prop="remark" label-width="150px">
                <el-input style="width:200px;" v-model="createForm.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <el-button type="primary" style="width:100px" @click="saveEmployee">保存</el-button>
              &nbsp&nbsp&nbsp&nbsp&nbsp
              <el-button type="primary" style="width:100px" @click="cancelEmployee">取消</el-button>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
    </el-dialog>
    <el-dialog :title='modifyTitle' :visible.sync="modifyDialogVisible" :close-on-click-modal="false" width="900px">
      <el-form ref="modifyForm" :model="modifyForm" :rules="rulesModify" label-width="80px">
        <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 10px ;">
          <div style="font-family: Consolas; font-size:20px ;margin-bottom: 20px;">员工信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="员工编号:" prop="staffNum" label-width="150px">
                <el-input style="width:200px;" v-model="modifyForm.staffNum"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工姓名:" prop="staffName" label-width="150px">
                <el-input style="width:200px;" v-model="modifyForm.staffName"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="性别:" label-width="150px">
                <el-radio-group v-model="modifyForm.staffSex">
                  <el-radio :label="1">男</el-radio>
                  <el-radio :label="2">女</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工手机号:" prop="staffTelephone" label-width="150px">
                <el-input style="width:200px;" v-model="modifyForm.staffTelephone"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="员工邮箱:" prop="staffEmail" label-width="150px">
                <el-input style="width:200px;" v-model="modifyForm.staffEmail"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="归属部门:" prop="departmentName" label-width="150px">
                <el-input style="width:80px;" :disabled="true" v-model="modifyForm.departmentName"></el-input>
                <el-button type="text" @click="selectDepartmentModify">选择</el-button>
                <el-button type="text" @click="clearDepartmentModify">清空</el-button>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="是否离职:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="modifyForm.isDimission"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="登录账号:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="modifyForm.accountName"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>


        <div>
          <div style="font-family: Consolas; font-size:20px ; margin-left:50px;margin-bottom: 20px;padding-top: 20px">
            其他信息
          </div>
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
              <el-form-item label="备注:" prop="remark" label-width="150px">
                <el-input style="width:200px;" v-model="modifyForm.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <el-row>
          <el-col style="text-align: center">
            <el-form-item>
              <el-button type="primary" style="width:100px" @click="saveUpdate">保存</el-button>
              &nbsp&nbsp&nbsp&nbsp&nbsp
              <el-button type="primary" style="width:100px" @click="cancelUpdate">取消</el-button>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
    </el-dialog>
    <el-dialog :title='titleDepartment' :visible.sync="dialogVisibleDepartment" :close-on-click-modal="false"
               width="50%">
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
          :default-expanded-keys="[1]"
          :render-content="renderContent"
          @check-change="handleClick">
        </el-tree>
        <br>
        <el-button type="primary" @click="getCheckedDepartment">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog :title='titleDepartment' :visible.sync="dialogVisibleDepartmentModify" :close-on-click-modal="false"
               width="50%">
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
          :default-expanded-keys="[1]"
          :render-content="renderContent"
          @check-change="handleClick">
        </el-tree>
        <br>
        <el-button type="primary" @click="getCheckedDepartmentModify">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog :title='contentTitle' append-to-body :visible.sync="contentDialogVisible" :close-on-click-modal="false" width="900px">
      <el-form ref="form" :model="contentForm" :disabled="true" label-width="80px">
        <div style="margin-left: 40px;border-bottom:1px solid gray;padding-bottom: 10px ;">
          <div style="font-family: Consolas; font-size:20px ;margin-bottom: 20px;">员工信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="员工编号:" label-width="150px">
                <el-input style="width:200px;" v-model="contentForm.staffNum"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工姓名:" label-width="150px">
                <el-input style="width:200px;" v-model="contentForm.staffName"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="性别:" label-width="150px">
                <el-radio-group v-model="contentForm.staffSex">
                  <el-radio :label="1">男</el-radio>
                  <el-radio :label="2">女</el-radio>
                </el-radio-group>
                <!--                <el-input style="width:200px;" v-model="contentForm.staffSex"></el-input>-->
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="员工手机号:" label-width="150px">
                <el-input style="width:200px;" v-model="contentForm.staffTelephone"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="员工邮箱:" label-width="150px">
                <el-input style="width:200px;" v-model="contentForm.staffEmail"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="归属部门:" label-width="150px">
                <el-input style="width:200px;" v-model="contentForm.departmentId"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="是否离职:" label-width="150px">
                <el-input style="width:200px;" v-model="contentForm.isDimission"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="登录账号:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="contentForm.accountId"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>


        <div>
          <div style="font-family: Consolas; font-size:20px ; margin-left:50px;margin-bottom: 20px;padding-top: 20px">
            其他信息
          </div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="新建时间:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="contentForm.createTime"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="新建人:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="contentForm.createEmp"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="修改时间:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="contentForm.modifyTime"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="修改人:" label-width="150px">
                <el-input style="width:200px;" :disabled="true" v-model="contentForm.modifyEmp"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="备注:" label-width="150px">
                <el-input style="width:200px;" v-model="contentForm.remark"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
      <template slot="footer">
        <div align="center">
          <el-button type="primary" @click="confirmInfo">确定</el-button>
        </div>
      </template>
    </el-dialog>


    <el-dialog title='选择当前部门' append-to-body :visible.sync="searchDeptDialogVisible" :close-on-click-modal="false" width="50%">
      <div>
        <el-tree
          ref="treeDept"
          :props="defaultPropsDept"
          node-key="id"
          :load="loadNodeDept"
          lazy="true"
          :default-expanded-keys="[1]"
          check-strictly
          show-checkbox
          @check-change="handleClickDept">
        </el-tree>
        <br>
        <el-button type="primary" @click="searchDeptCheck">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog title='选择上级部门' append-to-body :visible.sync="searchDeptUpperDialogVisible" :close-on-click-modal="false" width="50%">
      <div>
        <el-tree
          ref="treeDeptUpper"
          :props="defaultPropsDept"
          node-key="id"
          :load="loadNodeDept"
          lazy="true"
          :default-expanded-keys="[1]"
          check-strictly
          show-checkbox
          @check-change="handleClickDeptUpper">
        </el-tree>
        <br>
        <el-button type="primary" @click="searchDeptUpperCheck">确定</el-button>
      </div>
    </el-dialog>

  </home>

</template>

<script>
  import commonUtils from '../../common/commonUtils'

  const employeeOptions = ['员工编号', '登录账号', '员工姓名', '性别', '员工手机', '员工邮箱', '所属部门', '上级部门', '是否离职'];
  export default {
    name: "employee-list",
    inject: ['changeDialogVisible', 'chooseStaff'],
    props: {
      relAccount: 0
    },
    data() {
      var checkphone = (rule, value, callback) => {
        // let phoneReg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
        if (value == "") {
          callback(new Error("手机号为必填项，不允许为空"));
        } else if (!this.isCellPhone(value)) {//引入methods中封装的检查手机格式的方法
          callback(new Error("手机号不满足录入条件"));
        } else {
          callback();
        }
      };
      var checkNum = (rule, value, callback) => {
        // let phoneReg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
        if (value == "") {
          callback(new Error("员工编号为必填项，不允许为空"));
        } else if (!this.isNum(value)) {//引入methods中封装的检查手机格式的方法
          callback(new Error("员工编号不满足录入条件,需为1-20位数字"));
        }
        else if(this.staffNumList.indexOf(value) > -1) {
          callback(new Error("该员工编号已存在，不允许重复创建"))
        }
        else {
          callback();
        }
      };
      var checkUpdateNum = (rule, value, callback) => {
        // let phoneReg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
        if (value == "") {
          callback(new Error("员工编号为必填项，不允许为空"));
        } else if (!this.isNum(value)) {//引入methods中封装的检查手机格式的方法
          callback(new Error("员工编号不满足录入条件,需为1-20位数字"));
        }
        else if(this.staffNumList.indexOf(value) > -1 && this.updateStaffNum != value) {
          callback(new Error("该员工编号已存在，不允许重复创建"))
        }
        else {
          callback();
        }
      };
      return {
        isDiss:true,
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

        createForm: {
          staffNum: '',
          staffName: '',
          staffSex: '',
          staffTelephone: '',
          staffEmail: '',
          departmentId: '',
          departmentName: '',
          isDimission: '0',
          accountId: '',
          remark: '',
        },
        rulesCreate: {
          staffNum: [{required: true, validator: checkNum, trigger: 'blur'}],
          staffName: [{required: true, message: '员工姓名为必填项，不允许为空', trigger: 'blur'},
            {min: 1, max: 30, message: '员工姓名不满足录入条件，需为1-30个字符', trigger: 'blur'}],
          staffTelephone: [{required: true, validator: checkphone, trigger: "blur"}],
          staffEmail: [
            {type: 'email', message: '邮箱不满足录入条件', trigger: ['blur', 'change']}],
          departmentName: [{required: true, message: '归属部门为必填项，不允许为空'}],
        },
        modifyForm: {
          accountName: '',
          staffNum: '',
          staffName: '',
          staffSex: '',
          staffTelephone: '',
          staffEmail: '',
          departmentId: '',
          departmentName: '',
          createTime: '',
          createEmp: '',
          createEmpInit: '',
          remark: '',
        },
        modifyForm2:{
          accountName: '',
          staffNum: '',
          staffName: '',
          staffSex: '',
          staffTelephone: '',
          staffEmail: '',
          departmentId: '',
          departmentName: '',
          createTime: '',
          createEmp: '',
          createEmpInit: '',
          remark: '',
        },
        rulesModify: {
          staffNum: [{required: true, validator: checkUpdateNum, trigger: 'blur'}],
          staffName: [{required: true, message: '员工姓名为必填项，不允许为空', trigger: 'blur'},
            {min: 1, max: 30, message: '员工姓名不满足录入条件，需为1-30个字符', trigger: 'blur'}],

          staffTelephone: [{required: true, validator: checkphone, trigger: "blur"}],
          staffEmail: [
            {type: 'email', message: '邮箱不满足录入条件', trigger: ['blur', 'change']}],
          departmentName: [{required: true, message: '归属部门为必填项，不允许为空'}],
        },

        tableData: [],
        selection: '',
        id: '',
        staffName: '',
        accountId: '',
        accountName: '',
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
        modifyTitle: '员工信息修改页面',
        createDialogVisible: false,
        modifyDialogVisible: false,
        defaultProps: {
          label: 'departmentName',
          children: 'children',
          id: 'id',
          status: 'status'
        },
        titleDepartment: '选择部门',
        dialogVisibleDepartment: false,
        dialogVisibleDepartmentModify: false,

        dialogVisible: false,
        templateGroupName: '测试',
        description: '测试',
        staffDtoList: [],
        staffNumList:[],
        formdiStributionDepartment: {
          staffId: '',
          staffNum: '',
          staffName: '',
          staffSex: '',
          staffPhone: '',
          staffBeforeDepartment: '',
          staffAfterDepartment: '',
          staffAfterDepartmentName: '',
        },
        ruleDepartment: {
          staffAfterDepartmentName: [
            {required: true,message:'请选择部门',trigger:['blur','change']}
          ]
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
        quitDisabled: true,//离职按钮显示
        recoveryDisabled: true,//恢复按钮显示
        departmentDisabled:true,//分配部门按钮显示
        departmentVisible: false,
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
        },
        contentTitle: "员工详情页面",
        contentDialogVisible: false,
        contentForm: {
          staffNum: '',
          staffName: '',
          staffSex: '',
          staffTelephone: '',
          staffEmail: '',
          departmentId: '',
          isDimission: '',
          accountId: '',
          remark: '',
          createTime: '',
          createEmp: '',
          staffNumBtn: '',
          modifyEmp: '',
        },
        searchDeptDialogVisible: false,
        searchDeptUpperDialogVisible: false,
        searchDeptName: '',
        searchDeptUpperName: '',
        defaultPropsDept: {
          label: 'departmentName',
          children: 'children',
          isLeaf: 'nodeIsLeaf',
          id: 'id',
          no: 'departmentNo',
        },
        updateStaffNum:'',
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
      if (self.relAccount > 0) {
        if(self.relAccount==1) {
          self.form.isDimission = '0';
          self.cloumnDisabled = true;
        }
        self.buttonDisabled = true;
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
      } else {
        self.fetchData();
      }
      ;
    },
    methods: {
      judgmentAuthority() {
        const self = this;
        let permission = self.$store.state.powerList;
        permission.forEach(item => {
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
          self.staffNumList=result.staffNumList;
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

        this.createDialogVisible = true;

      },

      modifyEmployee() {//点击修改按钮，跳转到修改页面

        this.modifyDialogVisible = true;
      },
      saveEmployee() {

        const self = this;
        self.$refs["createForm"].validate(function (valid) {
          //if(self.$options.methods.checkInput(self)==false) return;
          if (valid) {
            self.$http.post("employee/insertStaff", self.createForm)
              .then((result) => {
                  self.createDialogVisible = false;
                  self.$message.success("新建用户成功");
                  self.fetchData();
              })
              .catch(function (error) {
                commonUtils.Log("employee/insertStaff:" + error);
                self.$message.error("新建用户失败");
              })
          } else {
            console.log('error submit!!');
            return false;
          }

        })

      },
      cancelEmployee() {
        this.createDialogVisible = false;
        this.$refs['createForm'].resetFields();
        this.createForm.accountId='';
        this.createForm.departmentId='';
        this.createForm.departmentName='';
        this.createForm.isDimission='';
        this.createForm.remark='';
        this.createForm.staffEmail='';
        this.createForm.staffName='';
        this.createForm.staffNum='';
        this.createForm.staffSex='';
        this.createForm.staffTelephone='';
      },
      saveUpdate() {
        console.log("=========")
        const self = this;
        let param = {
          id: self.id,
          accountId: self.modifyForm.accountId,
          staffNum: self.modifyForm.staffNum,
          staffName: self.modifyForm.staffName,
          staffSex: self.modifyForm.staffSex,
          staffEmail: self.modifyForm.staffEmail,
          staffTelephone: self.modifyForm.staffTelephone,
          departmentName: self.modifyForm.departmentName,
          departmentId: self.modifyForm.departmentId,
          remark: self.modifyForm.remark,
        }
        self.$refs["modifyForm"].validate(function (valid) {
          //if(self.$options.methods.checkInput(self)==false) return;
          if (valid) {
            self.$http.post("employee/updateStaff", param)
              .then(result => {

                self.modifyDialogVisible = false;
                self.$message.success("修改成功");
                self.fetchData();
              })
              .catch(function (error) {
                commonUtils.Log("employee/updateStaff:" + error);
                self.$message.error("修改用户信息失败");
              })
          } else {
            console.log('error submit!!');
            return false;
          }

        })

      },
      cancelUpdate() {
        this.modifyDialogVisible = false;
        this.$refs['modifyForm'].resetFields();
        this.modifyForm.id = this.modifyForm2.id;
        this.modifyForm.accountId = this.modifyForm2.accountId;
        this.modifyForm.accountName = this.modifyForm2.accountName;
        this.modifyForm.staffNum = this.modifyForm2.staffNum;
        this.modifyForm.staffName = this.modifyForm2.staffName;
        this.modifyForm.staffSex = this.modifyForm2.staffSex;
        this.modifyForm.staffEmail = this.modifyForm2.staffEmail;
        this.modifyForm.isDimission = this.modifyForm2.isDimission;
        this.modifyForm.staffTelephone = this.modifyForm2.staffTelephone;
        this.modifyForm.departmentName = this.modifyForm2.departmentName;
        this.modifyForm.departmentId = this.modifyForm2.departmentId;
        this.modifyForm.createTime = this.modifyForm2.createTime;
        this.modifyForm.remark = this.modifyForm2.remark;
        this.modifyForm.createEmpInit = this.modifyForm2.createEmp;
        this.modifyForm.modifyTime = this.modifyForm2.modifyTime
      },


      deleteEmployee() {//逻辑删除员工，员工状态改为无效，账户状态改为无效
        this.$confirm('此操作将删除该员工, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var self = this;
          var param = {
            id: self.id,
            accountId: self.accountId,
          };
          self.$http.get('employee/deleteEmployee', {
            params: param
          }).then((result) => {
            self.$message.success("成功删除");
            self.fetchData();
          }).catch(function (error) {
            commonUtils.Log("employee/deleteEmployee" + error);
            self.$message.error("获取数据错误");
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      //进行员工的离职操作
      quitEmployee() {
        this.$confirm('此操作将离职该员工, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var self = this;
          var param = {
            id: self.selection,
          };
          self.$http.get('employee/quitEmployee.do_', {
            params: param
          }).then((result) => {
            if (result.status == "success") {
              self.$message.success("成功离职");
              self.fetchData();
              self.quitDisabled = true;
              self.departmentDisabled=true;
              self.recoveryDisabled = false;
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
      //进行员工的恢复操作
      recovery() {
        this.$confirm('此操作将恢复该员工, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var self = this;
          var param = {
            id: self.selection,
          };
          self.$http.get('employee/recoverEmployee.do_', {
            params: param
          }).then((result) => {
            if (result.status == "success") {
              self.$message.success("恢复成功");
              self.fetchData();
              self.recoveryDisabled = true;
              self.quitDisabled = false;
              self.departmentDisabled=false;
            } else {
              self.$message.error("恢复失败")
            }

          }).catch(function (error) {
            commonUtils.Log("employee/recoverEmployee.do_" + error);
            //self.$message.error("恢复失败");
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
        this.departmentVisible = false;
      },
      saveDepartment() {
        var self=this;
        self.$refs["formdiStributionDepartment"].validate(function (valid) {
          if (valid) {
            self.$http.get('employee/updateDepartmentByEmployee.do_', {
              params: {
                id: self.selection,
                departmentId: self.formdiStributionDepartment.staffAfterDepartment,
              }
            }).then((result) => {
              if (result.status == "success") {
                self.$message.success(" 分配成功");
                self.fetchData();
              } else {
                self.$message.error("分配失败")
              }
            }).catch(function (error) {
              commonUtils.Log("employee/updateDepartmentByEmployee.do_" + error);
            });
            self.formdiStributionDepartment.staffAfterDepartmentName = '';
            self.formdiStributionDepartment.staffAfterDepartment = '';
            self.distributionDepartmentFlag = false;
          } else {
            console.log('error submit');
            return false;
          }
        });
        //$refs是父组件与表单中的REF属性相结合
      },
      cancelDepartment() {
        this.distributionDepartmentFlag = false;
        this.formdiStributionDepartment.staffAfterDepartmentName = '';
        this.formdiStributionDepartment.staffAfterDepartment = '';
      },
      confirmChoice() {
        const self = this;
        if (self.selection != '') {
          var staffData = {};
          for (var i = 0; ; i++) {
            staffData = self.tableData[i];
            if (self.selection == staffData.id) {
              break;
            }
          }
          this.chooseStaff(staffData);
          this.changeDialogVisible();
        } else {
          self.$message.info("请选择一名员工");
        }

      },
      cancelChoice() {
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
            export_json_to_excel(tHeader, data, '员工管理' + currentdate);
            this.$message({
              showClose: true,
              message: '文件导出成功',
              type: 'success'
            });
            this.dialogVisible = false;
            this.checkedemployees = [];
            this.filterVal = [];
            this.checkAll=false;
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
            this.filterVal.push('accountName')
          } else if (this.checkedemployees[i] === '员工姓名') {
            this.filterVal.push('staffName')
          } else if (this.checkedemployees[i] === '性别') {
            this.filterVal.push('staffSex')
          } else if (this.checkedemployees[i] === '员工手机') {
            this.filterVal.push('staffTelephone')
          } else if (this.checkedemployees[i] === '员工邮箱') {
            this.filterVal.push('staffEmail')
          } else if (this.checkedemployees[i] === '所属部门') {
            this.filterVal.push('departmentName')
          } else if (this.checkedemployees[i] === '上级部门') {
            this.filterVal.push('upperDepartmentName')
          } else if (this.checkedemployees[i] === '是否离职') {
            this.filterVal.push('isDimission')
          }
        }
        return this.filterVal;
      },
      /*  approvalInfo(val){
          this.disabled = false;
          this.id = val.id;
          this.accountId = val.accountId;
        },*/
      loadNode(node, resolve) {
        var self = this;
        self.$http.get('department/buildTree2.do_', {
          params: null
        }).then((result) => {
          resolve([result.departmentDto]);
        }).catch(function (error) {

        });
      },
      getCheckedNodes() {
        // 用于演示获取部门id的方式
        this.formdiStributionDepartment.staffAfterDepartmentName = this.$refs.tree.getCheckedNodes()[0].departmentName;
        this.formdiStributionDepartment.staffAfterDepartment = this.$refs.tree.getCheckedNodes()[0].id;
        this.departmentVisible = false;
        //alert(this.$refs.tree.getCheckedNodes()[0].id);
      },
      handleClick(data, checked, node) {
        // 手动设置单选
        if (checked === true) {
          this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
          // 设置按钮是否可选（选中节点后调用两次handleClick，第一次checked为true，所以设置按钮写在这）
          if (data.status === 1) {
            this.operationBtnActive = false;
          } else {
            this.operationBtnActive = true;
          }
        } else {
          if (this.checkedId == data.id) {
            this.$refs.tree.setCheckedKeys([data.id]);
          }
        }
      },
      deleteDept() {
        var check_node = this.$refs.tree.getCheckedNodes()[0];
        if (this.$options.methods.checkHaveChildren(check_node)) {
          alert("删除失败，请先删除该部门的下级部门");
          return;
        }
        var params = {
          id: check_node.id
        };
        this.$http.post('department/deleteDepartment.do_', params);
        this.dialogVisible = false;
        // 删除节点后前端显示跟着修改，避免刷新整个页面
        check_node.status = 0;
        var tmpNode = {
          departmentName: "tmp",
          children: [],
          id: 99999999,
          status: 0
        };
        this.$refs.tree.insertBefore(tmpNode, check_node.id);
        this.$refs.tree.remove(check_node.id);
        this.$refs.tree.insertAfter(check_node, tmpNode.id);
        this.$refs.tree.remove(tmpNode.id);
        this.operationBtnActive = true;
        alert("部门删除成功");
      },
      checkHaveChildren(data) {
        for (var i = 0; i < data.children.length; i++) {
          if (data.children[i].status === 1) {
            return true;
          }
        }
        return false;
      },
      renderContent(h, {node, data, store}) {
        // 这里编译器有红色波浪线不影响运行...
        if (data.status != 1) {
          return (
            < span
          style = "color:red" > {node.label} < /span>
        )
          ;
        } else {
          return (
            < span > {node.label} < /span>
        )
          ;
        }
      },
      changeUpper() {
        window.localStorage.setItem("dept_id", this.$refs.tree.getCheckedNodes()[0].id);
        window.localStorage.setItem("dept_name", this.$refs.tree.getCheckedNodes()[0].departmentName);
        this.$router.replace('/departmentManagement/changeUpperDepartment');
      },
      approvalInfo(val) {
        this.disabled = false;
        if(val.isDimission===0) {
          this.isDiss = false;
        }else if(val.isDimission===1){
          this.isDiss = true;
        }
        this.id = val.id;
        this.accountId = val.accountId;
        this.modifyForm.id = val.id;
        this.modifyForm2.id = val.id;
        this.modifyForm.accountId = val.accountId;
        this.modifyForm2.accountId = val.accountId;
        this.modifyForm.accountName = val.accountName;
        this.modifyForm2.accountName = val.accountName;
        this.modifyForm.staffNum = val.staffNum;
        this.modifyForm2.staffNum = val.staffNum;
        this.updateStaffNum = val.staffNum;
        this.modifyForm.staffName = val.staffName;
        this.modifyForm2.staffName = val.staffName;
        this.modifyForm.staffSex = val.staffSex;
        this.modifyForm2.staffSex = val.staffSex;
        this.modifyForm.staffEmail = val.staffEmail;
        this.modifyForm2.staffEmail = val.staffEmail;
        this.modifyForm.isDimission = this.isDimissionEnum[val.isDimission];
        this.modifyForm2.isDimission = this.isDimissionEnum[val.isDimission];
        this.modifyForm.staffTelephone = val.staffTelephone;
        this.modifyForm2.staffTelephone = val.staffTelephone;
        this.modifyForm.departmentName = val.departmentName;
        this.modifyForm2.departmentName = val.departmentName;
        this.modifyForm.departmentId = val.departmentId;
        this.modifyForm2.departmentId = val.departmentId;
        this.modifyForm.createTime = val.createTime;
        this.modifyForm2.createTime = val.createTime;
        this.modifyForm.remark = val.remark;
        this.modifyForm2.remark = val.remark;
        this.modifyForm.createEmpInit = val.createEmp;
        this.modifyForm2.createEmpInit = val.createEmp;
        this.modifyForm.modifyTime = val.modifyTime;
        this.modifyForm2.modifyTime = val.modifyTime;
        this.modifyForm = this.modifyForm2;


        this.formdiStributionDepartment.staffNum = val.staffNum;
        this.formdiStributionDepartment.staffAfterDepartment = val.staffAfterDepartment;
        this.formdiStributionDepartment.staffPhone = val.staffTelephone;
        this.formdiStributionDepartment.staffSex = this.SexEnum[val.staffSex];
        this.formdiStributionDepartment.staffBeforeDepartment = val.departmentName;
        this.formdiStributionDepartment.staffName = val.staffName;

        //回填创建人和修改人
        var self = this;
        var param = {
          staffId: val.id,
          createEmp: val.createEmp,
          modifyEmp: val.modifyEmp
        };
        self.$http.get('employee/otherInfo.do_', {
          params: param
        }).then((result) => {
          self.modifyForm.createEmp = result.list.createEmpName;
          self.modifyForm.modifyEmp = result.list.modifyEmpName;

        }).catch(function (error) {
          commonUtils.Log("employee/otherInfo.do_:" + error);
          //self.$message.error("获取数据错误");
        });
        //恢复，离职显示按钮
        if (val.isDimission == 0) {
          self.quitDisabled = false;
          self.departmentDisabled=false;
          self.recoveryDisabled = true;
        } else {
          self.quitDisabled = true;
          self.departmentDisabled=true;
          self.recoveryDisabled = false;
        }
      },
      selectDepartment() {//选择部门
        this.dialogVisibleDepartment = true;
      },
      clearDepartment() {//清除部门的值
        this.createForm.departmentName = '';
      },
      loadNode(node, resolve) {
        var self = this;
        self.$http.get('department/buildTree2.do_', {
          params: null
        }).then((result) => {
          resolve([result.departmentDto]);
        }).catch(function (error) {

        });
      }, renderContent(h, {node, data, store}) {
        // 这里编译器有红色波浪线不影响运行...
        if (data.status != 1) {
          return (
            < span
          style = "color:red" > {node.label} < /span>
        )
          ;
        } else {
          return (
            < span > {node.label} < /span>
        )
          ;
        }
      },
      handleClick(data, checked, node) {
        // 手动设置单选
        if (checked === true) {
          this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
          // 设置按钮是否可选（选中节点后调用两次handleClick，第一次checked为true，所以设置按钮写在这）
          if (data.status === 1) {
            this.operationBtnActive = false;
          } else {
            this.operationBtnActive = true;
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
        this.createForm.departmentName = this.$refs.tree.getCheckedNodes()[0].departmentName;
        this.createForm.departmentId = this.$refs.tree.getCheckedNodes()[0].id;
        this.dialogVisibleDepartment = false;
      },
      selectDepartmentModify() {//选择部门
        this.dialogVisibleDepartmentModify = true;
      },
      clearDepartmentModify() {//清除部门的值
        this.modifyForm.departmentName = '';
      },
      getCheckedDepartmentModify() {
        // 获取部门 回填到文本框中
        // alert(this.$refs.tree.getCheckedNodes()[0].departmentName);
        this.modifyForm.departmentName = this.$refs.tree.getCheckedNodes()[0].departmentName;
        this.modifyForm.departmentId = this.$refs.tree.getCheckedNodes()[0].id;
        this.dialogVisibleDepartmentModify = false;
      },
      //检查手机号
      isCellPhone(val) {
        if (!/^1(3|4|5|6|7|8)\d{9}$/.test(val)) {
          return false;
        } else {
          return true;
        }
      },
      isNum(val){
        if(!/^\d{1,20}$/.test(val)){
          return false;
        }
        else {
          return true;
        }

      },
      staffNumBtn(val) {
        this.contentDialogVisible = true;
        this.contentForm.accountId = val.accountId;
        this.contentForm.staffNum = val.staffNum;
        this.contentForm.staffName = val.staffName;
        this.contentForm.staffSex = val.staffSex;
        this.contentForm.staffEmail = val.staffEmail;
        this.contentForm.isDimission = this.isDimissionEnum[val.isDimission];
        this.contentForm.staffTelephone = val.staffTelephone;
        this.contentForm.departmentId = val.departmentName;
        this.contentForm.createTime = val.createTime;
        this.contentForm.createEmp = val.createEmp;
        this.contentForm.modifyEmp = val.modifyEmp;
        this.contentForm.modifyTime = val.modifyTime;
        this.contentForm.remark = val.remark;

        //回填创建人和修改人
        var self = this;
        var param = {
          staffId: val.id,
        };
        self.$http.get('employee/otherInfo.do_', {
          params: param
        }).then((result) => {
          self.contentForm.createEmp = result.list.createEmpName;
          self.contentForm.modifyEmp = result.list.modifyEmpName;
        }).catch(function (error) {
          commonUtils.Log("employee/otherInfo.do_:" + error);
          self.$message.error("获取数据错误");
        });
      },
      confirmInfo() {
        this.contentDialogVisible = false;
      },
      loadNodeDept(node, resolve) {
        var self = this;
        self.$http.get('department/buildTree2.do_')
          .then((result) => {
            resolve([result.departmentDto]);
          }).catch(function (error) {

        });
      },
      handleClickDept(data, checked, node) {
        // 手动设置单选
        if (checked === true) {
          this.checkedId = data.id;
          this.$refs.treeDept.setCheckedKeys([data.id]);
        } else {
          if (this.checkedId == data.id) {
            this.$refs.treeDept.setCheckedKeys([data.id]);
          }
        }
      },
      handleClickDeptUpper(data, checked, node) {
        // 手动设置单选
        if (checked === true) {
          this.checkedId = data.id;
          this.$refs.treeDeptUpper.setCheckedKeys([data.id]);
        } else {
          if (this.checkedId == data.id) {
            this.$refs.treeDeptUpper.setCheckedKeys([data.id]);
          }
        }
      },
      searchDeptCheck() {
        //alert("选择部门");
        this.searchDeptName = this.$refs.treeDept.getCheckedNodes()[0].departmentName;
        this.form.departmentId = this.$refs.treeDept.getCheckedNodes()[0].id;
        this.searchDeptDialogVisible = false;
      },
      searchDeptClear() {
        //alert("清空部门");
        this.searchDeptName = "";
        this.form.departmentId = "";
      },
      searchDeptUpperCheck() {
        //alert("选择上级部门");
        this.searchDeptUpperName = this.$refs.treeDeptUpper.getCheckedNodes()[0].departmentName;
        this.form.upperDepartmentNo = this.$refs.treeDeptUpper.getCheckedNodes()[0].departmentNo;
        this.searchDeptUpperDialogVisible = false;
      },
      searchDeptUpperClear() {
        //alert("清空上级部门");
        this.searchDeptUpperName = "";
        this.form.upperDepartmentNo = "";
      }
    }
  }
</script>

<style scoped>


</style>
