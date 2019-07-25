import Vue from 'vue'
import Router from 'vue-router'

import Demo from '@/views/Demo'



Vue.use(Router)

export default new Router({
  routes: [{
    path: '/demo1',
    name: 'Demo',
    component: Demo
  }
  ]
})
