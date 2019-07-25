<template>
  <el-col :span="24">
    <el-row v-for="field in cfg.fieldCfg" :key="field.code" :class="field.isHide?'my-hide':''">
        <dyn-field-one v-if="field.type==1" :ref="field.code" :cfg="field" :val="val[field.code]" @valChange='onValChange'></dyn-field-one>
        <dyn-field-two v-else-if="field.type==2" :ref="field.code" :cfg="field" :val="val[field.code]" @valChange='onValChange'></dyn-field-two>
        <dyn-field-three v-else-if="field.type==3" :ref="field.code" :cfg="field" :val="val[field.code]" @valChange='onValChange' @toSearch='toSearch'></dyn-field-three>
        <dyn-field-four v-else-if="field.type==4" :ref="field.code" :cfg="field" :val="val[field.code]" @valChange='onValChange'></dyn-field-four>
        <div v-else>解析错误！不能识别属性:{{field.code}},类型{{field.type}}</div>
    </el-row>
  </el-col>
</template>
<script>
  import DynFieldOne from './DynFieldOne'
  import DynFieldTwo from './DynFieldTwo'
  import DynFieldThree from './DynFieldThree'
  import DynFieldFour from './DynFieldFour'
  import Vue from 'vue'
  export default {
    name: 'DynForm',
    props: {
      cfg:{
        type:Object,
        default(){
          return {}
        }
      },
      val:{
        type:Object,
        default(){
          return {}
        }
      },
      hideProjectVariable:{
        type:Boolean,
        default(){
          return false;
        }
      }
    },
    data(){
      return {needInitVisiblity:true}
    },
    components:{
      DynFieldOne,DynFieldTwo,DynFieldThree,DynFieldFour
    },
    methods: {
      onValChange(field,value){
        if(this.val[field.code]!=value){
          Vue.set(this.val, field.code, value)
        }
      },
      initData(){
        var fieldMap = {};
        for(var i=0,len=this.cfg.fieldCfg.length; i<len; i++){
          fieldMap[this.cfg.fieldCfg[i].code] = 1;
        }
        for(var valKey in this.val){
          this.val.hasOwnProperty(valKey) && !fieldMap.hasOwnProperty(valKey) && Vue.delete(this.val, valKey)
        }
      },
      toSearch(fieldCfg){
        this.$emit("toSearch", fieldCfg);
      },
      isEmpty(){
        for(var i=0,len=this.cfg.fieldCfg.length; i<len; i++){
          if(this.$refs[this.cfg.fieldCfg[i].code] && !this.$refs[this.cfg.fieldCfg[i].code][0].isEmpty()){
            return false;
          }
        }
        return true;
      },
      initVisiblity(isHide){
        if(!this.needInitVisiblity){
          return;
        }
        var field, isHide = isHide===undefined ? this.hideProjectVariable : isHide;
        for(var i=0,len=this.cfg.fieldCfg.length; i<len; i++){
          field = this.cfg.fieldCfg[i];
          if(!isHide){
            Vue.set(field, 'isHide', false);
            continue;
          }
          if(field.id && this.$refs[field.code] && this.$refs[field.code][0].isComplete()){
            Vue.set(field, 'isHide', true);
          }
        }
        this.needInitVisiblity = false;
      }
    },
    watch:{
      hideProjectVariable(){
        this.needInitVisiblity = true;
        this.initVisiblity();
      },
      cfg(){
        this.needInitVisiblity = true;
      }
    },
    updated(){
      this.initVisiblity();
    }
  }
</script>

<style scoped>
.el-row{
  margin-bottom: 10px;
}
.my-hide{
  display: none;
}
</style>
