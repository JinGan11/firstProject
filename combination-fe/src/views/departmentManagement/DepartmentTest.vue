<template>
  <home>

    <h1>啊啊啊</h1>

    <el-tree
      :props="props"
      :load="loadNode"
      lazy
      show-checkbox>
    </el-tree>

  </home>
</template>

<script>
  export default {
    data() {
      return {
        props: {
          label: 'departmentName',
          value: 'departmentNo',
          children: 'zones',
          isLeaf: 'leaf'
        },
      };
    },
    methods: {
      loadNode(node, resolve) {
        var self = this;

        if (node.level === 0) {
          return resolve([{
            departmentNo: 'Z000001',
            departmentName: '总公司'
          }]);
        }
        if (node.level > 1) return resolve([]);

        setTimeout(() => {
          var params = {
            department_no: 'Z000001'
          }
          self.$http.get('department/searchChildDepartment.do_', {
            params: params
          }).then((result) => {
            resolve(result.departments);
          }).catch(function (error) {
            commonUtils.Log("department/querylist.do_:"+error);
            self.$message.error("获取数据错误");
          });

        }, 500);
      }
    }
  };
</script>

<style scoped>

</style>
