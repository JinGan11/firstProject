<template>
  <home style="margin: 10px 20px">
    <div>
      <span style="margin-right: 100px">要修改的部门为：{{ dept_name }}</span>
      <el-button type="primary">保存</el-button>
      <el-button type="primary">取消</el-button>
    </div>
    <br/>
    <div>
      <span>请选择该部门新的上级部门</span>
    </div>
    <br/>
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

  </home>
</template>

<script>
  export default{
    data () {
      return {
        dept_id: 0,
        dept_name:'null',
        defaultProps: {
          label: 'departmentName',
          children: 'children',
          id: 'id',
          status: 'status'
        },
      }
    },
    mounted() {
      this.dept_id=window.localStorage.getItem("dept_id");
      this.dept_name=window.localStorage.getItem("dept_name");
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
  }
</script>
