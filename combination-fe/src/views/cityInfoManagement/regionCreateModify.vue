<template>
  <home>

    <div style="width: 20%; margin-left: 70px ; float: left;height: 100%"  >
      <div  style="width:100%; margin-left: 70px;height: 10%">
        <el-button type="primary" :disabled="createDisable" @click="" >新建</el-button>
        <el-button type="primary" :disabled="modifyDisable" @click="modifyRegion" >修改</el-button>
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
        <el-form :model="form" label-width="150px" :disabled="formDisable" >

           <el-row :span="12">
              <el-form-item label="国际代码" >
                <el-input v-model="form.regionCode"  ></el-input>
              </el-form-item>
           </el-row>

          <el-row :span="12">
              <el-form-item label="省/直辖市名称" >
                <el-input v-model="form.regionName"  ></el-input>
              </el-form-item>
           </el-row>

          <el-row :span="12">
              <el-form-item label="名字拼音" >
                <el-input v-model="form.regionPinyin"  ></el-input>
              </el-form-item>
          </el-row>

          <el-row :span="12">
              <el-form-item label="上级区划"  >
                <el-input v-model="form.upperRegion" ></el-input>
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
            <el-form-item label="状态" >
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
              <el-input v-model="form.createEmpName" autocomplete="off" disabled ></el-input>
            </el-form-item>
          </el-row>

          <el-row :span="12">
            <el-form-item label="新建时间" >
              <el-input v-model="form.createTime" autocomplete="off" disabled ></el-input>
            </el-form-item>
          </el-row>

          <el-row :span="12">
            <el-form-item label="修改人" >
              <el-input v-model="form.modifyEmpName" autocomplete="off" disabled ></el-input>
              </el-form-item>
          </el-row>

          <el-row :span="12">
              <el-form-item label="修改时间" >
                <el-input v-model="form.modifyTime" autocomplete="off" disabled ></el-input>
              </el-form-item>
          </el-row>
        </el-form>
      </div>

    </div>
  </home>
</template>
<script>
    import commonUtils from '../../common/commonUtils'
    const formDefault={
            countryCode:'CN',
            regionAreaCode:'',
            upperRegionID:'',
            remark:'无',
            createEmpName:'',
            createTime:'',
            regionCode:'',
            regionName:'',
            regionPinyin:'',
            regionLevel:'',
            regionStatus:'',
            upperRegion:'',
            modifyEmpName:'',
            modifyTime:''
        }
    export default {
        data() {
            return{
                form:formDefault,
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

                //树
                props: {
                    label: 'regionName',
                    children:'children',
                    cityID:'cityID',
                    regionCode:'regionCode',
                    isLeaf:'regionLeaf'

                },

                createDisable:true,
                modifyDisable:true,
                formDisable:true,
                regionStatus:2,



            }
        },
        activated() {
            commonUtils.Log("页面激活");
        },
        mounted() {

            commonUtils.Log("页面进来");
            // this.getCurrentTime();
        },
        methods: {

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
                var regionLevel=node.level;
                console.log(checkedKey);
                document.getElementById('authTreeRadio_' + checkedKey).checked = true;
                if(checkedKey === this.currentKey){
                    //第二次点击,应取消
                    var radio = document.getElementById('authTreeRadio_' + checkedKey);
                    radio.checked = false;
                    this.currentKey = null;
                    this.$refs.regionTree.setCurrentKey(null);
                    this.form=formDefault;
                    this.formDisable=true;
                    this.modifyDisable=true;
                    this.setCreateBtn(this,3);

                    return;
                }else{
                    //第一次点击,应选中
                    this.currentKey = checkedKey;
                    this.fetchRegionDetails(this,region,regionLevel);
                    this.formDisable=true;
                    this.modifyDisable=false;
                    this.setCreateBtn(this,regionLevel);

                    return;
                }
            },

            //让表单所有选项为不能选中
            setFormDisable(){
                this.formDisable=true;
            },

            //创建新的区域
            createRegion(){
                var self=this;
                var param={
                    regionCode:self.form.regionCode,
                    regionName:self.form.regionName,
                    regionPinyin:self.form.regionPinyin,
                    regionLevel:self.form.regionLevel,
                    upperRegion:self.form.upperRegion,
                    regionStatus: self.form.regionStatus,
                    modifyTime:self.form.modifyTime
                };
                self.$http.get('/regionManage/createProvince',{
                    params:param
                }).then((result)=>{
                    //对取回来的数据进行处理
                    console.log(result);

                }).catch(function (error) {
                    commonUtils.Log("/regionManage/createProvince:" + error);
                    self.$message.error("获取数据错误");
                });
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
                this.form.modifyEmp=window.sessionStorage.getItem("loginUsername");
                // 页面加载完显示当前时间
                this.form.modifyTime = this.dealWithTime(new Date());
                // 定时器，定时修改显示的时间
                let _this = this;
                this.timer = setInterval(function () {
                    _this.form.modifyTime = _this.dealWithTime(new Date())
                }, 1000);
            },
            //获取选择节点的详细信息
            fetchRegionDetails(self,region,regionLevel){

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

            //设置新建按钮
            setCreateBtn(self,regionLevel){
                // console.log("self.form.regionStatus : "+self.regionStatus);
                if(self.regionStatus=== 2)
                    self.createDisable=true;
                else if(regionLevel===1 ||regionLevel==2)
                    self.createDisable=false;
                else
                    self.createDisable=true;
            },

            //修改按钮
            modifyRegion(){
                this.formDisable=false;
            },
            createRegion(){

            }




        }
    }

</script>
<style scoped>

</style>
