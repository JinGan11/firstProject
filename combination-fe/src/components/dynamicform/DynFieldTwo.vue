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
                        {{myVal.length}}个元素
                    </div>
                </el-col>
                <el-col :span="1">
                    <div @click='addRow' class="el-icon-circle-plus my-add-icon"></div>
                </el-col>
            </el-row>
            <div :class="'my-body-top-border'+(collspan?'':' my-hide')">
                <el-row v-for="(item, index) in myVal" :key="index" class="my-field-item">
                    <el-col :span="1">
                        <i class="el-icon-remove" @click="removeRow(index)"></i>
                    </el-col>
                    <el-col :span="23">
                        <el-input placeholder="请输入" v-model="myVal[index]"></el-input>
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
        name: 'DynFieldTwo',
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
                this.myVal.push('')
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
                    iArr = this.val;
                for(var i=0, len=iArr.length; i<len; i++){
                    if((typeof iArr[i])!=='string'){
                        arr[i] = '';
                    }else{
                        arr[i] = iArr[i];
                    }
                }
                this.myVal = arr;
            },
            isEmpty(){
                if(!this.myVal || this.myVal==0){
                    return true;
                }
                for(var i=0, len=this.myVal.length; i<len; i++){
                    if(!!this.myVal[i]){
                        return false;
                    }
                }
                return true;
            },
            isComplete(){
                if(!this.myVal || this.myVal.length==0){
                    return false;
                }
                for(var i=0, len=this.myVal.length; i<len; i++){
                    if(!this.myVal[i]){
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
  .my-add-icon{
    line-height: 32px;
    font-size:25px;
    margin-left:5px;
    color: #1d9705;
  }
  .my-add-icon:hover{
      color: #27d305
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
  .el-icon-info{
      font-size: 25px;
      line-height: 32px;
      margin-left: 6px;
      color:rgb(131, 133, 66);
  }
  .el-icon-remove{
      font-size: 25px;
      line-height: 32px;
      color:rgb(212, 0, 0);
  }
  .el-icon-remove:hover{
    color:rgb(255, 0, 0);
  }
  .el-icon-remove:active{
    color:rgb(212, 0, 0);
  }
  .my-body-top-border{
      border-top:1px solid rgb(238, 238, 238);
  }
  .my-collapse-icon{
    margin-left: 4px;
  }
</style>
