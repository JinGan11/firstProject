<template>
  <div>
    <el-aside width="auto" class="aside">
      <el-menu :default-active="$route.path"
               router
               class="el-menu-vertical-demo"
               :collapse="isCollapse">
        <div class="aside-title"><img style="width: 130px;vertical-align: middle;" :src="sysTitleShow"></img></div>
        <template v-for="item in data">
          <el-menu-item v-if="item.children === null" :index="item.url">
            <i :class="item.icon"></i>
            <span slot="title">{{item.title}}</span>
          </el-menu-item>
          <el-submenu v-else :index="item.url">
            <template slot="title">
              <i :class="item.icon"></i>
              <span slot="title">{{item.title}}</span>
            </template>
            <el-menu-item-group>
              <MenuItem :data="item.children"></MenuItem>
            </el-menu-item-group>
          </el-submenu>
        </template>
        <!--<MenuItem :data="data"></MenuItem>-->
      </el-menu>
    </el-aside>
  </div>
</template>

<script>
  import MenuItem from './MenuItem'
  export default {
    name: "AsideMenu",
    props: {
      sysTitle:"",
      sysTitleShow:"",
      isCollapse: false,
      data: Array
    },
    components: {MenuItem},
    watch:{
      isCollapse:(n,o) =>{
      }
    }
  }
</script>

<style scoped>

</style>
