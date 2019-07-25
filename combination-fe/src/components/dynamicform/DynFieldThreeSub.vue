<template>
  <el-row>
    <el-col :span="6" class="label">
      {{cfg.name}}({{cfg.code}})：
    </el-col>
    <el-col :span="17" class="my-card">
      <el-row>
        <div @click="collspanSwitch" class="my-line-height">
          <i :class='"my-collapse-icon el-collapse-item__arrow el-icon-arrow-right"+(collspan?" is-active":"")'></i>
          结构({{cfg.refStructure.name}})
        </div>
      </el-row>
      <div :class="'my-body-top-border' +(collspan?'':' my-hide')">
        <el-row v-for="item in cfg.refStructure.fields" :key="item.code" :class="'my-array-item'">
          <el-col :span="6" class="label">{{item.name}}({{item.code}})：</el-col>
          <el-col :span="17">
            <el-input placeholder="请输入" v-model="myVal[item.code]"></el-input>
          </el-col>
          <el-col :span="1">
            <el-tooltip :tabindex="-1" effect="light" :content="item.description" placement="bottom" :disabled="!item.description">
              <i :class="'el-icon-info'+(!item.description?' my-color-disabled':'')"></i>
            </el-tooltip>
          </el-col>
        </el-row>
      </div>
    </el-col>
    <el-col :span="1">
      <el-tooltip :tabindex="-1" effect="light" :content="cfg.description" placement="bottom" :disabled="!cfg.description">
        <i :class="'el-icon-info'+(!cfg.description?' my-color-disabled':'')"></i>
      </el-tooltip>
    </el-col>
  </el-row>
</template>

<script>
  import Vue from 'vue'
  export default {
    name: 'DynFieldThreeSub',
    props: {
      cfg:{
        type:Object,
        default(){
          return {}
        }
      },
      val:{}
    },
    data(){
      return {
        myVal:undefined,
        collspan:true
      }
    },
    methods:{
      collspanSwitch(){
        this.collspan = !this.collspan;
      },
      initData(){
        if(this.val!==undefined && this.myVal===this.val){
          return;
        }
        if(Object.prototype.toString.call(this.val) !== '[object Object]'){
          this.myVal={}
          return;
        }
        var myData = {}, refField; 
        for(var i=0,len=this.cfg.refStructure.fields.length; i<len; i++){
          refField = this.cfg.refStructure.fields[i];
          if((typeof this.val[refField.code])==='string'){
            myData[refField.code] = this.val[refField.code]
          }
        }
        this.myVal = myData;
      },
      isEmpty(){
        if(!this.myVal){
          return true;
        }
        var myData = {}, refField; 
        for(var i=0,len=this.cfg.refStructure.fields.length; i<len; i++){
          refField = this.cfg.refStructure.fields[i];
          if(!!this.myVal[refField.code]){
            return false;
          }
        }
        return true;
      },
      isComplete(){
        if(!this.myVal){
          return false;
        }
        var myData = {}, refField; 
        for(var i=0,len=this.cfg.refStructure.fields.length; i<len; i++){
          refField = this.cfg.refStructure.fields[i];
          if(!this.myVal[refField.code]){
            return false;
          }
        }
        return true;
      }
    },
    watch:{
      myVal(newV,oldV){
        this.$emit("valChange", this.cfg, newV, oldV);
      },
      val(){
        this.initData();
      }
    },
    created(){
      this.initData();
    }
  }
</script>

<style scoped>
  .label {
    text-align: right;
    line-height: 32px;
  }
  .el-row {
    margin-bottom: 0px;
  }
  .my-line-height{
    line-height: 32px;
  }
  .my-card{
    padding: 0px 10px 0px 10px; 
    border: 1px solid rgb(211, 211, 211);
    background-color: rgb(248, 248, 248);
  }
  .my-array-item{
    margin: 5px 0px 5px 0px;
  }
  .el-icon-info{
    font-size: 25px;
    line-height: 32px;
    margin-left: 6px;
    color:rgb(131, 133, 66);
  }
  .my-body-top-border{
    border-top:1px solid rgb(238, 238, 238);
  }
  .my-collapse-icon{
    margin-left: 4px;
  }
</style>
