<template>
    <el-row>
        <el-col :span='24' class="my-card">
            <el-row>
                <el-col :span="6" class="label">
                    <div @click="collspanSwitch">  
                        <div class="my-collspan-icon-container">
                            <i :class='"my-collspan-icon el-collapse-item__arrow el-icon-arrow-right"+(collspan?" is-active":"")'></i>
                        </div>{{cfg.name}}({{cfg.code}})：
                    </div>
                </el-col>
                <el-col :span="17" class='field-val-readonly'>
                    <div @click="collspanSwitch">   
                        <el-col :span="23">
                            结构 ({{cfg.refStructure.name}})
                        </el-col>
                    </div>
                    <el-col :span="1">
                        <i :class='"el-icon-search"+(cfg.showSearch?"":" my-hide")' @click="toSearch"></i>
                    </el-col>
                </el-col>
                <el-col :span="1">
                    <el-tooltip :tabindex="-1" effect="light" :content="cfg.description" placement="bottom" :disabled="!cfg.description">
                        <i :class="'el-icon-info'+(!cfg.description?' my-color-disabled':'')"></i>
                    </el-tooltip>
                </el-col>
            </el-row>
            <div :class="'my-body-top-border'+(collspan?'':' my-hide')">
                <el-row v-for="subField in cfg.refStructure.fields" :key="subField.code" class="my-field-item">
                    <dyn-field-one :ref="'formField_'+subField.code" v-if="subField.type==1" :cfg="subField" :val="myVal[subField.code]" @valChange="onValChange"></dyn-field-one>
                    <dyn-field-two :ref="'formField_'+subField.code" v-else-if="subField.type==2" :cfg="subField" :val="myVal[subField.code]" @valChange="onValChange"></dyn-field-two>
                    <dyn-field-three-sub :ref="'formField_'+subField.code" v-else-if="subField.type==3" :cfg="subField" :val="myVal[subField.code]" @valChange="onValChange"></dyn-field-three-sub>
                    <dyn-field-four :ref="'formField_'+subField.code" v-else-if="subField.type==4" :cfg="subField" :val="myVal[subField.code]" @valChange="onValChange"></dyn-field-four>    
                </el-row>
            </div>
        </el-col>
    </el-row>
</template>

<script>
    import DynFieldOne from './DynFieldOne'
    import DynFieldTwo from './DynFieldTwo'
    import DynFieldThreeSub from './DynFieldThreeSub'
    import DynFieldFour from './DynFieldFour'
    import Vue from 'vue'
    export default {
        name: 'DynFieldThree',
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
            return{
                myVal:this.val,
                collspan:true
            }
        },
        components:{
            DynFieldOne,DynFieldTwo,DynFieldThreeSub,DynFieldFour
        },
        methods:{
            onValChange(field,value){
                Vue.set(this.myVal, field.code, value)
            },
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
                var myData = {};
                for(var i=0,len=this.cfg.refStructure.fields.length; i<len; i++){
                    myData[this.cfg.refStructure.fields[i].code] = this.val[this.cfg.refStructure.fields[i].code];
                }
                this.myVal = myData;
            },
            toSearch(){
                this.$emit("toSearch", this.cfg);
            },
            isEmpty(){
                var refField;
                for(var i=0,len=this.cfg.refStructure.fields.length; i<len; i++){
                    refField = this.$refs["formField_"+this.cfg.refStructure.fields[i].code][0];
                    if(refField && !refField.isEmpty()){
                        return false;
                    }
                }
                return true;
            },
            isComplete(){
                var refField;
                for(var i=0,len=this.cfg.refStructure.fields.length; i<len; i++){
                    refField = this.$refs["formField_"+this.cfg.refStructure.fields[i].code][0];
                    if(refField && !refField.isComplete()){
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
    margin-bottom: 0;
  }
  .field-val-readonly{
      line-height: 32px;
      padding: 0px 10px;
  }
  .my-card{
    border: 1px solid rgb(211, 211, 211);
    background-color: rgb(248, 248, 248);
  }
  .my-collspan-icon-container{
      float:left;
      line-height:32px;
      padding-left:10px;
  }
  .my-collspan-icon{
      margin-left:4px;
  }
  .el-icon-info{
      font-size: 25px;
      line-height: 32px;
      margin-left: 6px;
      color:rgb(131, 133, 66);
  }
  .my-field-item{
      margin: 5px 0px;
  }
  .my-body-top-border{
    border-top:1px solid rgb(238, 238, 238);
  }
  .el-icon-search{
    line-height: 32px;
    font-size:25px;
    margin-left:5px;
    font-weight: bold;
    color: #1d9705;
  }
  .el-icon-search:hover{
      color: #27d305
  }
  .el-icon-search:active{
      color: #1d9705;
  }
</style>
