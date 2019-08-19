<template>
  <home>

    <div style="width: 20%; margin-left: 70px ; float: left;height: 100%"  >
      <div  style="width:100%; margin-left: 70px;height: 10%">
        <el-button type="primary" v-if="!BtnPermission.newPermission" :disabled="createDisable" @click="createRegionBtn" >新建</el-button>
        <el-button type="primary" v-if="!BtnPermission.modifyPermission" :disabled="modifyDisable" @click="modifyRegionBtn" >修改</el-button>
      </div>
      <el-tree
        ref="regionTree"
        class="tree"
        :props="props"
        :load="loadNode"
        lazy
        check-strictly
        node-key="cityID"
        :expand-on-click-node="false"
        :render-content="renderContent"
        @node-click="nodeClicked"
      >
      </el-tree>
    </div>
    <div style="width:45%; height: 100%;float: left"  >

      <div style="width:100%;margin-top: 70px;height: 100%">
        <el-form :model="form" label-width="150px" :disabled="formDisable" status-icon :rules="rules" ref="form">

          <el-row :span="12">
            <el-form-item label="国际代码" prop="regionCode">
              <el-input v-model.number="form.regionCode"  style="width: 200px" maxlength="6"></el-input>
            </el-form-item>
          </el-row>

          <el-row :span="12">
            <el-form-item label="行政区划名称" prop="regionName">
              <el-input v-model="form.regionName" style="width: 200px" maxlength="50"></el-input>
            </el-form-item>
          </el-row>

          <el-row :span="12">
            <el-form-item label="名字拼音" prop="regionPinyin">
              <el-input v-model="form.regionPinyin"  style="width: 200px" maxlength="255"></el-input>
            </el-form-item>
          </el-row>

          <el-row :span="12">
            <el-form-item label="上级区划"  >
              <el-input v-model="form.upperRegion" style="width: 200px" disabled></el-input>
            </el-form-item>
          </el-row>

          <el-row :span="12">
            <el-form-item label="区划类型" >
              <el-select v-model="form.regionLevel"  style="width:110px;" placeholder="请选择" disabled>
                <el-option
                  v-for="item in optionsLevel"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>

          <el-row>
            <el-form-item label="状态" prop="regionStatus">
              <el-select v-model="form.regionStatus"  style="width:100px;" placeholder="请选择" >
                <el-option
                  v-for="item in optionsStatus"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>

          <el-row :span="12">
            <el-form-item label="新建人" >
              <el-input v-model="form.createEmpName" autocomplete="off" disabled style="width: 200px"></el-input>
            </el-form-item>
          </el-row>

          <el-row :span="12">
            <el-form-item label="新建时间" >
              <el-input v-model="form.createTime" autocomplete="off" disabled style="width: 200px"></el-input>
            </el-form-item>
          </el-row>

          <el-row :span="12">
            <el-form-item label="修改人" >
              <el-input v-model="form.modifyEmpName" autocomplete="off" disabled style="width: 200px"></el-input>
            </el-form-item>
          </el-row>

          <el-row :span="12">
            <el-form-item label="修改时间" >
              <el-input v-model="form.modifyTime" autocomplete="off" disabled  style="width: 200px"></el-input>
            </el-form-item>
          </el-row>
          <el-col  :offset="8">
            <el-form-item v-if="isModify">
              <el-button size="small" type="primary" @click="modifyRegionSave">修改保存</el-button>
            </el-form-item>
            <el-form-item v-if="isCreate">
              <el-button size="small" type="primary" @click="createRegionSave">新建保存</el-button>
            </el-form-item>
          </el-col>

        </el-form>

      </div>

    </div>
  </home>
</template>
<script>
    import commonUtils from '../../common/commonUtils'
    export default {
        data() {
            var checkRegionCode = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('国际代码不能为空'));
                }
                setTimeout(() => {
                    if (!Number.isInteger(value)) {
                        callback(new Error('请输入数字值'));
                    } else {
                            callback();
                    }
                }, 100);
            };

            var checkRegionName = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('区划名称不能为空'));
                }
                else{
                    return callback();
                }
            };
            var checkRegionStatus = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('区划状态不能为空'));
                }
                else{
                    return callback();
                }
            };
            var checkRegionPinyin = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('区划拼音不能为空'));
                }
                else{
                    return callback();
                }
            };

            return{
                rules: {
                    regionCode: [
                        { validator: checkRegionCode, trigger: 'blur' }
                    ],
                    regionName: [
                        { validator: checkRegionName, trigger: 'blur' }
                    ],
                    regionStatus: [
                        { validator: checkRegionStatus, trigger: 'blur' }
                    ],
                    regionPinyin: [
                        { validator: checkRegionPinyin, trigger: 'blur' }
                    ],

                },

                optionsStatus:[ {
                    value: '1',
                    label: '有效'
                },{
                    value:'0',
                    label:'无效'
                }],
                optionsLevel:[ {
                    value: '1',
                    label: '省/直辖市'
                },{
                    value:'2',
                    label:'城市'
                },{
                    value:'3',
                    label:'区县'
                }],
                timer:'',
                form:{
                    cityID:'',
                    countryCode:'CN',
                    regionAreaCode:'',
                    upperRegionID:'0',
                    upperRegionCode:'0',
                    remark:'无',
                    createEmpName:'',
                    createTime:'',
                    regionCode:'',
                    regionName:'',
                    regionPinyin:'',
                    regionLevel:'',
                    regionStatus:'',
                    upperRegion:'中国',
                    modifyEmpName:'',
                    modifyTime:'',
                    upperRegionTwice:''
                },
                formTemp:{
                    cityID:'',
                    countryCode:'CN',
                    regionAreaCode:'',
                    upperRegionID:'0',
                    upperRegionCode:'0',
                    remark:'无',
                    createEmpName:'',
                    createTime:'',
                    regionCode:'',
                    regionName:'',
                    regionPinyin:'',
                    regionLevel:'',
                    regionStatus:'',
                    upperRegion:'中国',
                    modifyEmpName:'',
                    modifyTime:'',
                    upperRegionTwice:''
                },
                //树
                props: {
                    label: 'regionName',
                    children:'children',
                    cityID:'cityID',
                    regionCode:'regionCode',
                    isLeaf:'regionLeaf',
                    regionLevel:'regionLevel',
                    regionStatus:'regionStatus'

                },

                createDisable:false,
                modifyDisable:true,
                formDisable:true,
                isModify:false,
                isCreate:false,


                BtnPermission: {
                  newPermission: true,
                  modifyPermission: true,
                },

            }
        },
        activated() {
            commonUtils.Log("页面激活");
        },
        mounted() {
            this.judgmentAuthority();
            commonUtils.Log("页面进来");
            this.getCurrentTime();
        },
        methods: {
          judgmentAuthority() {
            const self = this;
            let permission = self.$store.state.powerList;
            permission.forEach(item=>{
              if (item === 71) {
                self.BtnPermission.newPermission = false
              }
              if (item === 72) {
                self.BtnPermission.modifyPermission = false
              }
            });
          },
            //树
            loadNode(node, resolve) {
                var self=this;
                if (node.level === 0) {
                    var regionLevel=node.level+1;
                    var param={
                        regionLevel: regionLevel
                    };
                    self.$http.get('/regionManage/getRegionList',{
                        params:param
                    }).then((result)=>{
                        //对取回来的数据进行处理
                        return resolve(result.regionList);
                    }).catch(function (error) {
                        commonUtils.Log("/regionManage/getRegionList:" + error);
                        self.$message.error("获取数据错误");
                    });
                }

                var hasChild;
                if (node.level > 2){ hasChild=false; return resolve([]);}
                else hasChild = true;

                if(node.data!=null){
                    setTimeout(() => {
                        var data;
                        if (hasChild) {
                            var regionLevel=node.level+1;
                            var cityID=node.data.cityID;
                            var regionCode=node.data.regionCode;
                            var param={
                                cityID:cityID,
                                regionLevel: regionLevel,
                                regionCode:regionCode,
                            };
                            self.$http.get('/regionManage/getRegionList',{
                                params:param
                            }).then((result)=>{
                                //对取回来的数据进行处理
                                data=result.regionList;
                                return resolve(data);
                            }).catch(function (error) {
                                commonUtils.Log("/regionManage/getRegionList:" + error);
                                self.$message.error("获取数据错误");
                            });
                        } else {
                            data = [];
                            resolve(data);
                        }

                    }, 200);
                }

            },
            renderContent (h, {node, data, store}) {
                const id = 'authTreeRadio_' + data.cityID;

                // const styleContext = data.parentId ? '' : 'display:none;'
                const styleContext = '';
                let styleColor = '';
                if (data.status === 0) {
                    styleColor = 'color:#f56c6c' ;// 红
                }
                /* else if (data.status === 1) {
                        if (data.elementType === 2) {
                               styleColor = 'color:#409eff' // 蓝
                       } else {
                               styleColor = 'color:#67c23a' // 绿
                               }
                      }*/
                // console.log(id);
                return (
                    <span>
                    <input type="radio" style={styleContext} name="authTreeRadio" id={id} value={data.cityID}/>
                <span style={styleColor}>{node.label}</span>
                    </span>
            );
            },
            nodeClicked(region, node, regionTree) {
                var checkedKey = this.$refs.regionTree.getCurrentKey();
                // console.log(checkedKey);
                document.getElementById('authTreeRadio_' + checkedKey).checked = true;
                if(checkedKey === this.currentKey){
                    this.destroyed();
                    //第二次点击,应取消
                    var radio = document.getElementById('authTreeRadio_' + checkedKey);
                    radio.checked = false;
                    this.currentKey = null;
                    this.$refs.regionTree.setCurrentKey(null);
                    this.setDefault(this);
                    var regionLevel=1;
                    this.setCreateBtn(this,region,regionLevel);

                    return;
                }else{
                    //第一次点击,应选中
                    this.destroyed();
                    this.currentKey = checkedKey;
                    this.fetchRegionDetails(this,region);
                    this.formDisable=true;
                    this.modifyDisable=false;
                    var regionLevel=region.regionLevel;
                    this.setCreateBtn(this,region,regionLevel);
                    return;
                }
            },
            setDefault(self){
                self.form={
                    cityID:'',
                    countryCode:'CN',
                    regionAreaCode:'',
                    upperRegionID:'0',
                    upperRegionCode:'0',
                    remark:'无',
                    createEmpName:'',
                    createTime:'',
                    regionCode:'',
                    regionName:'',
                    regionPinyin:'',
                    regionLevel:'',
                    regionStatus:'',
                    upperRegion:'中国',
                    modifyEmpName:'',
                    modifyTime:'',
                    upperRegionTwice:''
                };
                self.formTemp={
                    cityID:'',
                    countryCode:'CN',
                    regionAreaCode:'',
                    upperRegionID:'0',
                    upperRegionCode:'0',
                    remark:'无',
                    createEmpName:'',
                    createTime:'',
                    regionCode:'',
                    regionName:'',
                    regionPinyin:'',
                    regionLevel:'',
                    regionStatus:'',
                    upperRegion:'中国',
                    modifyEmpName:'',
                    modifyTime:'',
                    upperRegionTwice:''
                };
                self.formDisable=true;
                self.modifyDisable=true;
                self.isCreate=false;
                self.isModify=false;
            },

            //让表单所有选项为不能选中
            setFormDisable(){
                this.formDisable=true;
            },


            //其他信息栏：获取时间
            dealWithTime (data) {
                let formatDateTime;
                let Y = data.getFullYear();
                let M = data.getMonth() + 1;
                let D = data.getDate();
                let H = data.getHours();
                let Min = data.getMinutes();
                let S = data.getSeconds();
                let W = data.getDay();
                H = H < 10 ? ('0' + H) : H;
                Min = Min < 10 ? ('0' + Min) : Min;
                S = S < 10 ? ('0' + S) : S;
                formatDateTime = Y + '年' + M + '月' + D + '日 ' + H + ':' + Min + ':' + S;
                return formatDateTime;
            },
            destroyed () {
                // 结束时清除定时器
                if (this.timer) {
                    clearInterval(this.timer);
                }
            },
            //取得现在的时间
            getCurrentTime(){
                this.form.modifyEmpName=window.sessionStorage.getItem("loginUsername");
                // 页面加载完显示当前时间
                this.form.modifyTime = this.dealWithTime(new Date());
                // 定时器，定时修改显示的时间
                let _this = this;
                this.timer = setInterval(function () {
                    _this.form.modifyTime = _this.dealWithTime(new Date())
                }, 1000);
            },
            //获取选择节点的详细信息
            fetchRegionDetails(self,region){

                var regionLevel=region.regionLevel;
                var cityID=region.cityID;
                var regionCode=region.regionCode;
                var regionName=region.regionName;
                // console.log(regionLevel+"-"+cityID+"-"+regionCode);
                var param={
                    cityID:cityID,
                    regionLevel: regionLevel,
                    regionCode:regionCode,
                    regionName:regionName
                };
                self.$http.get('/regionManage/getRegionDetails',{
                    params:param
                }).then((result)=>{
                    //对取回来的数据进行处理
                    self.form=result.regionDetails;
                    self.regionStatus=self.form.regionStatus;
                    this.formTemp=result.regionDetails;

                    if (self.form.regionStatus === 0) {
                        self.form.regionStatus = '无效'
                    } else if (self.form.regionStatus === 1) {
                        self.form.regionStatus = '有效'
                    }

                    if (self.form.regionLevel === 1) {
                        self.form.regionLevel = '省/直辖市'
                    } else if (self.form.regionLevel === 2) {
                        self.form.regionLevel = '城市'
                    }else if (self.form.regionLevel === 3) {
                        self.form.regionLevel = '区县'
                    }

                    // console.log(self.form);
                }).catch(function (error) {
                    commonUtils.Log("/regionManage/getRegionDetails:" + error);
                    self.$message.error("获取数据错误");
                });
            },

            //设置新建按钮可否使用
            setCreateBtn(self,region,regionLevel){
                // console.log("self.form.regionStatus : "+self.regionStatus);

                if(region.regionStatus=== 0)
                    self.createDisable=true;
                else if(regionLevel===1 ||regionLevel==2)
                    self.createDisable=false;
                else if(regionLevel===3 )
                    self.createDisable=true;
                else
                    self.createDisable=false;
            },

            //修改按钮
            modifyRegionBtn(){
                this.formDisable=false;
                this.isModify=true;
                this.isCreate=false;
                this.form=this.formTemp;
                this.getCurrentTime();
            },
            createRegionBtn(){

                this.formDisable=false;
                this.isModify=false;
                this.isCreate=true;
                this.form={
                    cityID:'',
                    countryCode:'CN',
                    regionAreaCode:'',
                    upperRegionID:'0',
                    upperRegionCode:'0',
                    remark:'无',
                    createEmpName:'',
                    createTime:'',
                    regionCode:'',
                    regionName:'',
                    regionPinyin:'',
                    regionLevel:'',
                    regionStatus:'1',
                    upperRegion:'中国',
                    modifyEmpName:'',
                    modifyTime:'',
                    upperRegionTwice:''
                };



                this.getCurrentTime();
                this.form.createEmpName=this.form.modifyEmpName;
                this.form.createTime=this.form.modifyTime;

                var regionLevel='1';
                if (this.formTemp.regionLevel === '省/直辖市') {
                    regionLevel = '2'
                } else if (this.formTemp.regionLevel === '城市') {
                    regionLevel = '3'
                }
                this.form.regionLevel=regionLevel;

                if(regionLevel==='1'){
                    this.formTemp={
                        cityID:'0',
                        countryCode:'CN',
                        regionAreaCode:'',
                        upperRegionID:'0',
                        upperRegionCode:'0',
                        remark:'无',
                        createEmpName:'',
                        createTime:'',
                        regionCode:'0',
                        regionName:'中国',
                        regionPinyin:'',
                        regionLevel:'',
                        regionStatus:'1',
                        upperRegion:'中国',
                        modifyEmpName:'',
                        modifyTime:'',
                        upperRegionTwice:''
                    };
                }

                this.form.upperRegion=this.formTemp.regionName;
                this.form.upperRegionCode=this.formTemp.regionCode;
                this.form.upperRegionID=this.formTemp.cityID;
                this.form.upperRegionTwice=this.formTemp.upperRegion;


            },

            //修改区域信息(修改保存)
            modifyRegionSave(){
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        var self=this;
                        var regionStatus;
                        var regionLevel;
                        if (self.form.regionStatus === '无效') {
                            regionStatus = 0
                        } else if (self.form.regionStatus === '有效') {
                            regionStatus = 1
                        }else{
                            regionStatus=self.form.regionStatus;
                        }

                        if (self.form.regionLevel === '省/直辖市') {
                            regionLevel = 1
                        } else if (self.form.regionLevel === '城市') {
                            regionLevel = 2
                        }else if (self.form.regionLevel === '区县') {
                            regionLevel = 3
                        }else{
                            regionLevel =self.form.regionLevel;
                        }

                        var param={
                            cityID:self.form.cityID,
                            regionCode:self.form.regionCode,
                            regionName:self.form.regionName,
                            regionPinyin:self.form.regionPinyin,
                            regionStatus: regionStatus,
                            modifyTime:self.form.modifyTime,
                        };
                        // console.log(param);
                        self.$http.get('/regionManage/modifyRegionSave',{
                            params:param
                        }).then((result)=>{
                            //对取回来的数据进行处理
                            // console.log(result);
                            self.$alert(result);

                        }).catch(function (error) {
                            commonUtils.Log("/regionManage/modifyRegionSave:" + error);
                            self.$message.error("获取数据错误");
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });


            },

            //创建新的区域（新建保存）
            createRegionSave(){
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        var self=this;
                        console.log(self.formTemp);
                        console.log(self.form);
                        var param={
                            regionCode:self.form.regionCode,
                            regionName:self.form.regionName,
                            regionPinyin:self.form.regionPinyin,
                            regionLevel:self.form.regionLevel,
                            regionStatus: self.form.regionStatus,
                            modifyTime:self.form.modifyTime,
                            upperRegion:self.form.upperRegion,
                            upperRegionCode:self.form.upperRegionCode,
                            upperRegionID:self.form.upperRegionID

                        };
                        self.$http.get('/regionManage/createRegion',{
                            params:param
                        }).then((result)=>{
                            //对取回来的数据进行处理
                            // console.log(result);
                            self.$alert(result);

                        }).catch(function (error) {
                            commonUtils.Log("/regionManage/createRegion:" + error);
                            self.$message.error("获取数据错误");
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });


            },


        }
    }

</script>
<style scoped>

</style>
