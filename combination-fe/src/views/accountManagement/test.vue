<template>
  <home>
    <el-tree
      ref="tree"
      highlight-current
      node-key="id"
      :props="props"
      :load="loadNode"
      lazy
      show-checkbox
      @check-change="handleCheckChange">
    </el-tree>
  </home>
</template>
<script>
  export default {
    data() {
      return {
        props: {
          id: 'id',
          no: 'departmentNo',
          label: 'label',
          children: 'children',
          isLeaf: 'leaf'
        },
        aa: true,
        data1:[{
          id: 12,
          label: 'region',
          leaf: false
        }],
        data: [{
          id: 21,
          label: '一级 1',
          leaf: false,
          children: [{
            id: 22,
            label: '二级 1-1',
            leaf: false,
            children: [{
              id: 23,
              label: '三级 1-1-1',
              leaf: true
            }]
          }]
        }, {
          id: 20,
          label: '一级 2',
          leaf: false,
          children: [{
            id: 25,
            label: '二级 2-1',
            leaf: false,
            children: [{
              id: 27,
              label: '三级 2-1-1',
              leaf: true
            }]
          }, {
            id: 30,
            label: '二级 2-2',
            leaf: false,
            children: [{
              id: 33,
              label: '三级 2-2-1',
              leaf: true
            }]
          }]
        }, {
          id: 63,
          label: '一级 3',
          leaf: false,
          children: [{
            id: 11,
            label: '二级 3-1',
            leaf: false,
            children: [{
              id: 12,
              label: '三级 3-1-1',
              leaf: true
            }]
          }, {
            id: 13,
            label: '二级 3-2',
            leaf: false,
            children: [{
              id: 14,
              label: '三级 3-2-1',
              leaf: true
            }]
          }]
        }],
      };
    },
    methods: {
      loadNode(node, resolve) {
        if (node.level === 0) {
          return resolve(this.data1);
        }
       if (node.level > 1){
         var id = node.data.id;
         resolve(tt(id,this.data));
       }
        if (node.level === 1){
            resolve(this.data);
        }
        function tt(id,datas) {
          var d = null;
          for(var i = 0;i<datas.length;i++){
             var data = datas[i];
             if (data.id != id && data.leaf == false){
              d = tt(id,data.children);
              if (d != null ){
                return d;
              }
            }else if(data.id==id){
              return data.children;
            }
          }
          return d;
        }
      },
      handleCheckChange(data){
        this.currTreeId=data.id
        setTimeout(() => {
          let currtData = this.$refs.tree.getCheckedNodes(true)
          this.labelCheckedList = currtData;
        }, 100);
      },
    }
  };
</script>
