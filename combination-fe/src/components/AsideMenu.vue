<template>
  <div>
    <el-aside width="auto" style="height:100%;">
      <el-menu :default-active="$route.path"
               router
               style="height:100%;"
               class="el-menu-vertical-demo"
               :collapse="isCollapse"
               unique-opened>
        <el-header>
          <div class="aside-title" style="width:100% ;position: absolute;right:0px ;background: #1E90FF ;font-size: 28px;font-family: STKaiti">优车组织结构</div>
        </el-header>
        <el-aside width="auto" style="position: absolute;left:0px;top:65px ;bottom: 0px; right:0px">
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
        </el-aside>
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
