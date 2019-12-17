import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/page/login'
import register from '@/components/page/register'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',

      component: login
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/register',
      name: 'register',
      component: register
    }

  ]
})
