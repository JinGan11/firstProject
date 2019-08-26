<template>
  <home style="margin-left: 20px">
    <br>
    <div>
      <el-button type="primary" v-if="!depButtonPermission.createPermission" v-bind:disabled="operationBtnActive" @click="addDept">新建子部门</el-button>
      <el-button type="primary" v-if="!depButtonPermission.modifyPermission" v-bind:disabled="operationBtnActive" @click="updateDept">修改</el-button>
      <el-button type="primary" @click="dialogVisible = true" v-if="!depButtonPermission.deletePermission" v-bind:disabled="operationBtnActive || operationBtnActive2">删除</el-button>
      <el-button type="primary" v-if="!depButtonPermission.modifyUpDepPermission" v-bind:disabled="operationBtnActive || operationBtnActive2" @click="changeUpper">修改上级部门</el-button>
      <el-button type="primary" @click="relationCompanyBtn" v-if="!depButtonPermission.relCompanyPermission" :disabled="operationBtnActive">关联公司</el-button>
    </div>
    <br>
    <div>
      <span style="color: red;">红色：无效部门</span>
      <br/><br/>
      <span>选择要操作的部门</span>
      <br><br>
      <el-tree
        ref="tree"
        :props="defaultProps"
        node-key="id"
        :load="loadNode"
        lazy="true"
        :default-expanded-keys="[1]"
        :expand-on-click-node="false"
        check-strictly
        :render-content="renderContent"
        @node-click="handleNodeClick">
      </el-tree>
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
        checkedNode:{
          id: 1,
          no: 'Z000001',
          children: '',
          name: ''
        },
        dialogVisible: false,
        operationBtnActive: true,
        operationBtnActive2: true,
        depButtonPermission: {
          createPermission: true,
          modifyPermission: true,
          deletePermission: true,
          modifyUpDepPermission: true,
          relCompanyPermission: true,
        }
      };
    },
    created() {
      this.judgmentAuthority();
    },
    methods: {
      judgmentAuthority() {
        const self = this;
        let permission = self.$store.state.powerList;
        permission.forEach(item=>{
          if (item === 10) {
            self.depButtonPermission.createPermission = false
          }
          if (item === 11) {
            self.depButtonPermission.modifyPermission = false
          }
          if (item === 12) {
            self.depButtonPermission.deletePermission = false
          }
          if (item === 13) {
            self.depButtonPermission.modifyUpDepPermission = false
          }
          if (item === 14) {
            self.depButtonPermission.relCompanyPermission = false
          }
        });
      },
      loadNode(node,resolve){
        var self = this;
        var param = {
          forIEFresh: new Date().getTime()
        };
        self.$http.post('department/buildTree.do_', {
          params: param
        }).then((result) => {
          resolve([result.departmentDto]);
      }).catch(function (error) {

        });
      },
      handleNodeClick(data){
        var dept_options = document.getElementsByName("departmentCheck");
        for(var i = 0;i<dept_options.length;i++){
          if(dept_options[i].value==data.id){
            dept_options[i].checked=true;

            this.checkedNode.id=data.id;
            this.checkedNode.no=data.departmentNo;
            this.checkedNode.name=data.departmentName;
            this.checkedNode.children=data.children;

            this.operationBtnActive=false;
            // 总公司不能删除
            if(data.departmentNo=="Z000001")
              this.operationBtnActive2=true;
            else
              this.operationBtnActive2=false;
            break;
          }
        }
      },
      // handleClick(data,checked,node){
      //   // 手动设置单选
      //   if(checked === true) {
      //     this.checkedId = data.id;
      //     this.$refs.tree.setCheckedKeys([data.id]);
      //     // 设置按钮是否可选（选中节点后调用两次handleClick，第一次checked为true，所以设置按钮写在这）
      //     if(data.status === 1){
      //       this.operationBtnActive=false;
      //     }else{
      //       this.operationBtnActive=true;
      //     }
      //     // 总公司不能删除
      //     if(data.departmentNo=="Z000001")
      //       this.operationBtnActive2=true;
      //     else
      //       this.operationBtnActive2=false;
      //   } else {
      //     if (this.checkedId == data.id) {
      //       this.$refs.tree.setCheckedKeys([data.id]);
      //     }
      //   }
      // },
      deleteDept(){
        var self = this;
        if( this.$options.methods.checkHaveChildren(self.checkedNode.children) ){
          this.$message.error("删除失败，请先删除该部门的下级部门");
          return;
        }
        var params = {
          id: self.checkedNode.id,
          forIEFresh: new Date().getTime()
        };
        self.$http.get('department/deleteDepartment.do_', {
          params: params
        }).then((result) => {
          if(!result.result){
            self.$message.error(result.msg);
          }else{
            self.$message.success("删除成功！");
         }
        }).catch(function (error) {

        });
        this.dialogVisible = false;
        // 删除节点后前端显示跟着修改，避免刷新整个页面
        // var deletedNode = self.$refs.tree.getNode(self.checkedNode.id);
        // deletedNode.status = 0;
        // var tmpNode = {
        //   departmentName: "tmp",
        //   children: [],
        //   id: 99999999,
        //   status: 0
        // };
        // this.$refs.tree.insertBefore(tmpNode,deletedNode.id); // bug
        // this.$refs.tree.remove(deletedNode.id);
        // this.$refs.tree.insertAfter(deletedNode,tmpNode.id);
        // this.$refs.tree.remove(tmpNode.id);
        // this.operationBtnActive=true;

        //self.$router.replace('/departmentManagement/showDepartment');
        self.$router.go(0);

        //self.reload();
      },
      checkHaveChildren (data) {
        if(data==null || data=='') return false;
        for(var i = 0;i<data.length;i++){
          if(data[i].status == 1){
            return true;
          }
        }
        return false;
      },
      renderContent(h, { node, data, store }) {
        // 这里编译器有红色波浪线不影响运行...
        if(data.status != 1) {
          return (
            <span style = "color:red">{node.label}</span>
          );
        }
        return (
          <span><input type="radio" name="departmentCheck" id={data.id} value={data.id}/>{node.label}</span>
        );
      },
      changeUpper() {
        window.localStorage.setItem("dept_id",this.checkedNode.id);
        window.localStorage.setItem("dept_name",this.checkedNode.name);
        this.$router.replace('/departmentManagement/changeUpperDepartment');
      },
      addDept() {
        window.localStorage.setItem("dept_no",this.checkedNode.no);
        window.localStorage.setItem("dept_name",this.checkedNode.name);
        this.$router.replace('/departmentManagement/addDepartment');
      },
      updateDept() {
        window.localStorage.setItem("dept_id",this.checkedNode.id);
        this.$router.replace('/departmentManagement/updateDepartment');
      },
      relationCompanyBtn(){
        window.localStorage.setItem("departmentRelId",this.checkedNode.id);
        this.$router.replace('/relationCompanyList');
      },

    }
  };
</script>


<style scoped>

</style>
