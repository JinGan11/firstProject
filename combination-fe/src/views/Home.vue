<template>
  <div>
    <slot>
      <div class="content"></div>
    </slot>
  </div>
</template>

<script>
  export default {
    name: "home",
    props: {
      isHome: {
        type: Boolean,
        default: true
      }
    },
    data() {
      return {
      }
    },
    mounted() {
    },
    components: {},
    methods: {}
  }
</script>

<style scoped="scoped">
  .content{
    position: absolute;
    overflow: hidden;
    height: 100%;
    width: 100%;
    background-size: cover;
    background-repeat: no-repeat;
    background-image: url("../../static/img/home.jpg");
    background-position: center 0;
  }
  .my-second-subject .my-text-ident{
    text-indent:60px;
  }
  .my-title .el-collapse-item__header{
    font-size: 15px;
    padding-left: 30px;
    font-weight: bold;
  }
  .my-title.my-second-subject .el-collapse-item__header{
    padding-left: 60px;
  }
</style>
