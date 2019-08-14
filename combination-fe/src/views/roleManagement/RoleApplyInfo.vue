<template>
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
        <el-table-column type="selection" width="35"></el-table-column>
        <el-table-column prop="id" label="隐藏id"></el-table-column>
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
        <el-button type="primary" @click="" style="width:70px">取消</el-button>
      </div>

    </div>
  </home>
</template>

<script>
    export default {
      data() {
        return {
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
          tableDataRole: [],



        }
      },

    }
</script>

<style scoped>

</style>
