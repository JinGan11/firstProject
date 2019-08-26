<template>
  <home style="margin: 10px 20px">
    <div>
      <span style="margin-right: 100px">正在修改的部门为：{{ dept_name }}</span>
      <el-button type="primary" v-bind:disabled="cannotSave" @click="save">保存</el-button>
      <el-button type="primary" @click="cancle">取消</el-button>
    </div>
    <br/>
    <div>
      <span style="color: red;">红色：无效部门</span><br/>
<!--      <span style="color: #CDAD00">黄色：本部门、本部门下级部门、业务线不包含本部门</span></span>-->
      <br/><br/>
      <span>请选择该部门新的上级部门</span>
      </div>
    <br/>
    <el-tree
      ref="tree"
      :props="defaultProps"
      node-key="id"
      :load="loadNode"
      lazy="true"
      :default-expanded-keys="[1]"
      check-strictly
      :expand-on-click-node="false"
      :render-content="renderContent"
      @node-click="handleNodeClick">
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
          status: 'status',
          canChoose: 'canChoose'
        },
        cannotSave: true,
        checkedNo: 'Z000001'
      }
    },
    mounted() {
      this.dept_id=window.localStorage.getItem("dept_id");
      this.dept_name=window.localStorage.getItem("dept_name");
    },
    methods: {
      loadNode(node,resolve){
        var self = this;
        var params = {
          id: window.localStorage.getItem("dept_id"),
          forIEFresh: new Date().getTime()
        };
        self.$http.get('department/buildUpperTree.do_', {
          params: params
        }).then((result) => {
          resolve([result.departmentDto]);
        }).catch(function (error) {

        });
      },
      renderContent(h, { node, data, store }) {
        // 这里编译器有红色波浪线不影响运行...
        if(data.status != 1) {
          return (
            <span style = "color:red">{node.label}</span>
          );
        }
        if(data.canChoose == false){
          return (
            <span>{node.label}</span>
        );
        }
        return (
          <span><input type="radio" name="departmentCheck" id={data.id} value={data.id}/>{node.label}</span>
        );
      },
      handleNodeClick(data){
        if(data.canChoose){
          console.log(data);
          var dept_options = document.getElementsByName("departmentCheck");
          for(var i = 0;i<dept_options.length;i++){
            if(dept_options[i].value==data.id){
              dept_options[i].checked=true;
              this.checkedNo=data.departmentNo;
              this.cannotSave=false;
              break;
            }
          }
        }
      },
      save(){
        var self = this;
        var params = {
          id: '',
          upperDepartmentNo: '',
          forIEFresh: new Date().getTime()
        };
        params.id=self.dept_id;
        params.upperDepartmentNo=self.checkedNo;
        self.$http.post("department/updateUpperDepartment.do_",params)
          .then(result => {
            if(result.result==false){
              self.$message.error(result.msg);
              return false;
            }
            self.$message.success("修改成功！");
            self.$router.replace("/departmentManagement/showDepartment");
          })
          .catch(function (error) {

          })
      },
      cancle(){
        this.$router.replace("/departmentManagement/showDepartment");
      }
    }
  }
</script>
