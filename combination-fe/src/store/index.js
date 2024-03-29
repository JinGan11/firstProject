import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

var store = new Vuex.Store({
  state: {
    menuListState:[
      {name:'首页',path:'/index'}
    ],
    menuList:[],
    loginUsername: null,
    powerList:[],
    isFirstLogin: false,
  },
  mutations: {
    menuListStateAdd(state,obj){
      state.menuListState.push(obj);
    },
    menuListStateRemove(state,num){
      state.menuListState.splice(num,state.menuListState.length-num);
    }
  }

})
export default store;
