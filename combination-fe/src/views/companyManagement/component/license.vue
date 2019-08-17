<template>
  <div>
    <div class="license-info">
      <div class="license-img">
        <img :src="fileUrl+license.id" height="150px" width="200px"/>
      </div>
      <div class="actions">
        <el-button type="danger" @click="deleteLicense" size="small">删除</el-button>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "license",
    props: ['license'],
    data() {
      return {
        licenseUrl: '',
        fileUrl:"http://localhost:8081/combination/company/getLicense?id=",
      }
    },
    methods: {
      deleteLicense() {
        this.$emit('delete-license', this.license.id);
      }
    }
  }
</script>

<style lang="scss" scoped>
  .license-info {
    margin: 5px;
    float: left;
    width: 205px;
    height:190px;
    border-radius:3px;
    .license-img {
    }
    .actions {
        padding-left:50px ;
    }
  }

</style>
