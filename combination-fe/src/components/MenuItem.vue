<template>
  <div>
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
  </div>
</template>

<script>
    export default {
      name:'MenuItem',
      props: {
        data: Array
      }
    }
</script>

<style scoped>

</style>
