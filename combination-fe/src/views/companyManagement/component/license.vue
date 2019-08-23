<template>
  <div>
    <div class="license-info">
      <div class="license-img">
        <img :src="fileUrl+license.id" height="150px" width="200px"/>
      </div>
      <div class="actions">
        <el-button v-if="isShow" type="danger" @click="deleteLicense" size="small">删除</el-button>
        <el-button type="info" @click="previewLicense" size="small">预览</el-button>
      </div>
    </div>
  </div>
</template>
<script>
  import global from '../../../common/global.js'
  export default {
    name: "license",
    props: ['license','isShow'],
    data() {
      return {
        licenseUrl: '',
        fileUrl:"http://"+global.host+":8081/combination/company/getLicense?id=",
      }
    },
    methods: {
      deleteLicense() {
        this.$emit('delete-license', this.license.id);
      },
      previewLicense() {
        this.$emit('preview-license', this.license.id);
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
     position: center;
    }
  }

</style>
