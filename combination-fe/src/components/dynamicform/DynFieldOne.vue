<template>
  <el-row>
    <el-col :span="6" class="label">
      {{cfg.name}}({{cfg.code}})：
    </el-col>
    <el-col :span="17">
      <el-input placeholder="请输入" v-model="myVal"></el-input>
    </el-col>
    <el-col :span="1">
      <el-tooltip :tabindex="-1" effect="light" :content="cfg.description" placement="bottom" :disabled="!cfg.description">
        <i :class="'el-icon-info'+(!cfg.description?' my-color-disabled':'')"></i>
      </el-tooltip>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: "DynFieldOne",
    props: {
      cfg: {
        type: Object,
        default() {
          return {}
        }
      },
      val: {}
    },
    data() {
      return {myVal: undefined}
    },
    methods: {
      initData() {
        if (this.val === undefined || (typeof this.val) !== 'string') {
          this.myVal = undefined;
        } else {
          this.myVal = this.val;
        }
      },
      isEmpty() {
        return !this.myVal
      },
      isComplete() {
        return !!this.myVal
      },
      valExist() {
        this.$emit('valExist', this.cfg, this.isComplete());
      }
    },
    created() {
      this.initData();
    },
    watch: {
      myVal(newV, oldV) {
        this.valExist();
        this.$emit("valChange", this.cfg, newV, oldV);
      },
      val(newV, oldV) {
        this.initData();


      }
    }
  }
</script>

<style scoped>
  .label {
    text-align: right;
    line-height: 32px;
  }

  .el-row {
    margin-bottom: 0;
  }

  .el-icon-info {
    font-size: 25px;
    line-height: 32px;
    margin-left: 6px;
    color: rgb(131, 133, 66);
  }
</style>
