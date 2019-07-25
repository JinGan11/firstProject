<template>
  <el-row>
    <el-col :span="6" class="label">
      {{cfg.name}}({{cfg.code}})：
    </el-col>
    <el-col :span="17" class="my-card">
      <el-row >
        <el-col :span="23">
          <div @click="collspanSwitch" class="my-line-height">
            <i :class='"my-collapse-icon el-collapse-item__arrow el-icon-arrow-right"+(collspan?" is-active":"")'></i>
            {{myVal.length}}个元素({{cfg.refStructure.name}})
          </div>
        </el-col>
        <el-col :span="1">
          <div @click='addRow' class="el-icon-circle-plus my-add-icon"></div>
        </el-col>
      </el-row>
      <el-row v-for="(valItem, index) in myVal" :key="index" :class="'my-array-item'+(collspan?'':' my-hide')">
          <el-col :span="1">
            <i class="el-icon-remove" @click="removeRow(index)"></i>
          </el-col>
          <el-col :span="23">
            <el-row v-for="item in cfg.refStructure.fields" :key="item.code" class='my-field-item'>
              <el-col :span="6" class="label">{{item.name}}({{item.code}})：</el-col>
              <el-col :span="17">
                <el-input placeholder="请输入" v-model="valItem[item.code]"></el-input>
              </el-col>
              <el-col :span="1">
                <el-tooltip :tabindex="-1" effect="light" :content="item.description" placement="bottom" :disabled="!item.description">
                  <i :class="'el-icon-info'+(!item.description?' my-color-disabled':'')"></i>
                </el-tooltip>
              </el-col>
            </el-row>
          </el-col>
      </el-row>
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
    name: 'FeDynFieldFour',
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
        collspan:true,
      }
    },
    methods:{
      addRow(){
        this.myVal.push({})
      },            
      removeRow(index){
        this.myVal.splice(index,1);
      },
      collspanSwitch(){
        this.collspan = !this.collspan;
      },
      initData(){
        if(this.val!==undefined && this.val===this.myVal){
          return;
        }
        if(!Array.isArray(this.val)){
          this.myVal = [];
          return;
        }
        var arr = [], 
            iArr = this.val,
            valItem,
            valItemInput, 
            sField;
        for(var i=0, len=iArr.length; i<len; i++){
          valItemInput = iArr[i];
          valItem = arr[i] = {}
          if(!valItemInput){
            continue;
          }
          for(var j=0, jlen=this.cfg.refStructure.fields.length; j<jlen; j++){
            sField = this.cfg.refStructure.fields[j];
            if((typeof valItemInput[sField.code])==='string'){
              valItem[sField.code] = valItemInput[sField.code];
            }
          }
        }
        this.myVal = arr;
      },
      isEmpty(){
        if(!this.myVal){
          return true;
        }
        var valItem,
            sField;
        for(var i=0, len=this.myVal.length; i<len; i++){
          valItem = this.myVal[i];
          for(var j=0, jlen=this.cfg.refStructure.fields.length; j<jlen; j++){
            sField = this.cfg.refStructure.fields[j];
            if(!!valItem[sField.code]){
              return false;
            }
          }
        }
        return true;
      },
      isComplete(){
        if(!this.myVal || this.myVal.length==0){
          return false;
        }
        var valItem,
            sField;
        for(var i=0, len=this.myVal.length; i<len; i++){
          valItem = this.myVal[i];
          for(var j=0, jlen=this.cfg.refStructure.fields.length; j<jlen; j++){
            sField = this.cfg.refStructure.fields[j];
            if(!valItem[sField.code]){
              return false;
            }
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
  .my-add-icon{
    line-height: 32px;
    font-size:25px;
    margin-left:5px;
    color: #28ac0d;
  }
  .my-add-icon:hover{
    color: #47c22f
  }
  .my-add-icon:active{
    color: #1d9705;
  }
  .my-card{
    padding: 0px 10px 0px 10px; 
    border: 1px solid rgb(211, 211, 211);
    background-color: rgb(248, 248, 248);
  }
  .my-field-item{
    margin: 5px 0px 5px 0px;
  }
  .my-array-item{
    border-top:1px solid rgb(238, 238, 238);
  }
  .el-icon-info{
    font-size: 25px;
    line-height: 32px;
    margin-left: 6px;
    color:rgb(131, 133, 66);
  }
  .el-icon-remove{
    font-size: 25px;
    line-height: 32px;
    color:rgb(224, 0, 0);
  }
  .el-icon-remove:hover{
    color:rgb(255, 51, 51);
  }
  .el-icon-remove:active{
    color:rgb(196, 0, 0);
  }
  .my-collapse-icon{
    margin-left: 4px;
  }
</style>
