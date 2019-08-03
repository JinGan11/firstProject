<template>
  <home>
    <div style="width:85%; margin-left: 70px">
    <h3>基本信息</h3>
    <el-button type="primary" @click="fetchData">保存</el-button>
    <div style="width: 95%;">
      <el-form ref="form" :model="form" label-width="100px">
        <el-row>
          <el-col :span="5">
            <el-form-item label="公司名称：" >
              <el-input style="width:140px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="统一社会信用代码：" >
              <el-input style="width:140px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="5">
            <el-form-item label="类型：" >
              <el-select v-model="form.dataType" clearable placeholder="请选择">
                <el-option style="width:160px;"
                           v-for="item in form.dataTypeList"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="营业期限：" >
              <el-input style="width:140px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="5">
            <el-form-item label="住所：" >
              <el-input style="width:140px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="经营范围：" >
              <el-input style="width:140px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="5">
            <el-form-item label="法定代表人：" >
              <el-input style="width:140px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="注册资本：" >
              <el-input style="width:140px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="5">
            <el-form-item label="成立日期：" >
              <el-input style="width:140px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="登记机关：" >
              <el-input style="width:140px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="5">
            <el-form-item label="核准日期：" >
              <el-input style="width:140px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="登记状态：" >
              <el-input style="width:140px;" v-model="form.accountNo"></el-input>
            </el-form-item>
          </el-col>
        </el-row>


        <h3>附件信息</h3>
        <div>

        </div>


        <h3>发票信息</h3>
        <div>
          <el-row>
            <el-col :span="5">
              <el-form-item label="公司性质：" >
                <el-input style="width:140px;" v-model="form.accountNo"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="注册地址：" >
                <el-input style="width:140px;" v-model="form.accountNo"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <el-form-item label="开户银行：" >
                <el-input style="width:140px;" v-model="form.accountNo"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="账号：" >
                <el-input style="width:140px;" v-model="form.accountNo"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <el-form-item label="注册电话：" >
                <el-input style="width:140px;" v-model="form.accountNo"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="总公司标志：" >
                <el-input style="width:140px;" v-model="form.accountNo"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>


        <h3>其他信息</h3>
        <div>
          <el-row>
            <el-col :span="5">
              <el-form-item label="新建人：" >
                <el-input style="width:140px;" v-model="form.accountNo"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="新建时间：" >
                <el-input style="width:140px;" v-model="form.accountNo"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <el-form-item label="修改人：" >
                <el-input style="width:140px;" v-model="form.accountNo"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="修改时间：" >
                <el-input style="width:140px;" v-model="form.accountNo"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <el-form-item label="状态：" >
                <el-select v-model="form.dataType" clearable placeholder="请选择">
                  <el-option style="width:160px;"
                             v-for="item in form.dataTypeList"
                             :key="item.value"
                             :label="item.label"
                             :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <el-form-item label="备注：" >
                <el-input style="width:140px;" v-model="form.accountNo"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

      </el-form>


    </div>
    </div>
  </home>
</template>

<script>
  import commonUtils from '../../common/commonUtils'

  export default {
    data() {},
    activated() {
      commonUtils.Log("页面激活");
    },
    mounted() {
      commonUtils.Log("页面进来");
    },
    methods: { },
    created() {
      const self = this;
    },
  }


</script>
<style scoped>


</style>
