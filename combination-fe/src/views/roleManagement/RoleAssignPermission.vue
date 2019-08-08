<template>
  <div>
    <el-form>
      <div style="height: 80px;display: flex;align-items: center;margin-left: 80%">
        <el-button type="primary" style="margin-right: 10px" @click="preservePower">保存</el-button>
        <el-button type="primary" @click="cencel">取消</el-button>
      </div>
      <el-row type="flex" justify="center" style="width: 100%;">
        <el-col :span="11">
          <el-scrollbar style="width: 400px">
            <el-tree
              style="float: left;margin-left: 100px;height: 350px"
              ref="tree"
              :props="defaultProps"
              node-key="powerId"
              :load="loadNode"
              lazy="true"
              :default-expanded-keys="[1]"
              :default-checked-keys="selectedNodes"
              show-checkbox
              @check-change="handleCheckChange">
            </el-tree>
          </el-scrollbar>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
  import commonUtils from "../../common/commonUtils";

  export default {
    data() {
      return {
        roleList: [],
        roleSelectedList: [],
        powerSelectedList: [],
        selected: [],
        leftSelected: [],
        rightSelected: [],
        isLazy: true,
        defaultProps: {
          label: 'powerName',
          children: 'children',
          id: 'powerId'
        },
        selectedNodes:[]

      }
    },
    mounted() {
      this.init()
    },
    computed: {
      leftButtonDisable()
      {
        return this.leftSelected.length === 0
      },
      rightButtonDisable()
      {
        return this.rightSelected.length === 0
      }
    },
    methods: {
      //初始化角色
      init() {
        const self = this;
        var param = {
          roleInfoId: 1000
        }
        self.$http.post('roleManage/getRolePower.do_', param).then((result) => {
          self.selectedNodes = result.rolePowerList;
          console.log(result)
        }).catch(function (error) {
          commonUtils.Log("roleManage/getRolePower.do_" + error);
          self.$message.error("获取数据错误")
        });
      },
      //获取树数据
      loadNode(node,resolve){
        var self = this;
        self.$http.get('power/getPowerList', {
          params: null
        }).then((result) => {
          resolve([result.powerTree]);
        }).catch(function (error) {

        });
      },
      handleCheckChange(data, checked, indeterminate) {
        const self = this;
        console.log(self.$refs.tree.getCheckedNodes())
      },
      preservePower() {
        const self = this;
        var param = {
          roleInfoId: 9999,
          powerList : self.$refs.tree.getCheckedNodes()
        };
        self.$http.post("roleManage/assignPermission", param)
          .then((result) => {
            console.log("success!")
          })
          .catch(function (error) {

          });
        self.$router.replace("/roleManagement/roleManagement")
      },
      cencel(){
        this.$router.replace("/roleManagement/roleManagement")
      },
    }
  }
</script>

<style scoped>

</style>
