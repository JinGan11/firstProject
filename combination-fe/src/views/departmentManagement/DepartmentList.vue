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
        :render-content="renderContent"
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
          id: 'id',
          status: 'status'
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
        var check_node = this.$refs.tree.getCheckedNodes()[0];
        if( this.$options.methods.checkHaveChildren(check_node) ){
          alert("无法删除包含下属部门的部门！");
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
    }
  };

</script>


<style scoped>

</style>
