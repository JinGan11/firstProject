<template>
  <home style="margin-left: 20px">
    <br>
    <div>
      <el-button type="primary">新建子部门</el-button>
      <el-button type="primary">修改</el-button>
      <el-button type="primary" @click="dialogVisible = true">删除</el-button>
      <el-button type="primary">修改上级部门</el-button>
      <el-button type="primary">关联公司</el-button>
    </div>
    <br>
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
        @check-change="handleClick">
      </el-tree>
      <br>
      <el-button @click="getCheckedNodes">点击弹出选中的部门的ID</el-button>
    </div>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%">
      <br>
      <span>确定要删除该部门吗?</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteDept">确 定</el-button>
      </span>
    </el-dialog>


  </home>
</template>

<script>
  export default {
    data() {
      return {
        defaultProps: {
          label: 'departmentName',
          children: 'children',
          id: 'id'
        },
        dialogVisible: false
      };
    },
    methods: {
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
        alert(this.$refs.tree.getCheckedNodes()[0].id);
      },
      handleClick(data,checked,node){
        // 手动设置单选
        if(checked === true) {
          this.checkedId = data.id;
          this.$refs.tree.setCheckedKeys([data.id]);
        } else {
          if (this.checkedId == data.id) {
            this.$refs.tree.setCheckedKeys([data.id]);
          }
        }
      },
      deleteDept(){
        if( this.$refs.tree.getCheckedNodes()[0].children.length > 0 ){
          alert("无法删除包含下属部门的部门！");
          return;
        }
        var params = {
          id: this.$refs.tree.getCheckedNodes()[0].id
        };
        this.$http.post('department/deleteDepartment.do_',params);
        this.dialogVisible = false;
        this.$refs.tree.remove(params.id);
      }
    }
  };

</script>


<style scoped>

</style>
