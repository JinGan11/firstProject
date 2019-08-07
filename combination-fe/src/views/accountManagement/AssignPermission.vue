<template>
  <div>
    <el-form>
      <div style="height: 80px;display: flex;align-items: center;margin-left: 80%">
        <el-button type="primary" style="margin-right: 10px" @click="preservePower">保存</el-button>
        <el-button type="primary" @click="cencel">取消</el-button>
      </div>
      <div style="height: 50px;">
        <label style="margin-left: 5%; font-size: 22px; color: cornflowerblue;">所拥有角色</label>
        <label style="margin-left: 45%; font-size: 22px; color: cornflowerblue;">所拥有特殊权限</label>
      </div>
      <el-row type="flex" justify="center" style="width: 100%;">
        <el-col :span="11" style="width: 20%">
          <el-table ref="currAvaiVarRef" class="total-variable" height="350" border @cell-click="currCellClick"
                    @selection-change="selectionChangeLeft" :data="roleList">
            <el-table-column type="selection" width="44"></el-table-column>
            <el-table-column prop="roleName" label="可分配角色"></el-table-column>
          </el-table>
        </el-col>
        <el-col :span="2" class="button-col" style="display: flex; align-items: center;">
          <div class="button-group">
            <el-button type="primary" :disabled="leftButtonDisable" style="margin-left: 7px;margin-bottom: 15px"
                       class="button-select el-icon-arrow-right" round @click="selectVariable"></el-button>
            <el-button type="primary" :disabled="rightButtonDisable" style="margin-left: 7px"
                       class="button-select el-icon-arrow-left" round @click="abandonVariable"></el-button>
          </div>
        </el-col>
        <el-col :span="11" style="width: 20%">
          <el-table ref="selectVarRef" class="selected-variable" height="350" :data="selected" border
                    @cell-click="selectCellClick"
                    @selection-change="selectionChangeRight">
            <el-table-column type="selection"></el-table-column>
            <el-table-column prop="roleName" label="已拥有角色"></el-table-column>
          </el-table>
        </el-col>
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
        self.$http.get('account/getRoleList.do_').then((result) => {
          self.roleList = result.notOwnedRole.list;
          self.selected = result.ownedRole.list;
          console.log(result)
        }).catch(function (error) {
          commonUtils.Log("account/getRoleList.do_" + error);
          self.$message.error("获取数据错误")
        });
        self.$http.get('power/getAccountPower.do_').then((result) => {
          self.selectedNodes = result.accountPower;
          console.log(result)
        }).catch(function (error) {
          commonUtils.Log("power/getAccountPower.do_" + error);
          self.$message.error("获取数据错误")
        });
      },
      selectionChangeLeft(val) {
        this.leftSelected = val
      },
      selectionChangeRight(val) {
        this.rightSelected = val
      },
      currCellClick(row, column, cell, event){
        this.$refs.currAvaiVarRef.toggleRowSelection(row)
      },
      selectCellClick(row, column, cell, event){
        this.$refs.selectVarRef.toggleRowSelection(row);
      },
      // 点击选择变量
      selectVariable() {
        let self = this;
        // 右边设值
        self.leftSelected.forEach((item) => self.selected.push(item));
        // 左边删除变量
        self.roleList = self.roleList.filter((item) => {
          return self.selected.indexOf(item) === -1
        });
        self.leftSelected = [];
        // 重排
        self.selected.sort((a, b) => {
          return a.id - b.id
        })
      },
      // 点击舍弃变量
      abandonVariable() {
        let self = this;
        // 右边删除该元素，左边添加此元素
        self.rightSelected.forEach((item) => self.roleList.push(item));
        self.selected = self.selected.filter((item) => {
          return self.rightSelected.indexOf(item) === -1
        })
        self.rightSelected = []
        self.roleList.sort((a, b) => {
          return a.id - b.id
        })
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
      handleClick(data,checked,node){
        if(checked === true) {
          this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
        } else {
          if (this.checkedId === data.id) {
            this.$refs.tree.setCheckedKeys([data.id]);
          }
        }
      },
      handleCheckChange(data, checked, indeterminate) {
        const self = this;
        console.log(self.$refs.tree.getCheckedNodes())
        // if (checked === true) {
        //   self.powerSelectedList.push({powerId:data.powerId});
        //   console.log(self.powerSelectedList)
        // }else {
        //   self.powerSelectedList.pop({powerId:data.powerId});
        //   console.log(self.powerSelectedList)
        // }
      },
      preservePower() {
        const self = this;
        var param1 = {
          roleList : self.selected
        };
        console.log(param1)
        var param2 = {
          powerList : self.$refs.tree.getCheckedNodes()
        };
        self.$http.post("power/modifyAccountRole", param1)
          .then((result) => {
            console.log("success!")
          })
          .catch(function (error) {

          });
        console.log(param2)
        self.$http.post("power/modifySpecialPower", param2)
          .then((result) => {
            console.log("success!")
          })
          .catch(function (error) {

          });
        self.$router.replace("/accountManagement")
      },
      cencel(){
        this.$router.replace("/accountManagement")
      },

    }
  }
</script>

<style scoped>

</style>
