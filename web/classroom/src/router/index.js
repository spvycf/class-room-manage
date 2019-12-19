import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/page/login'
import register from '@/components/page/register'
import home from '@/components/page/home'
import notice from '@/components/page/notice'
import user from '@/components/page/user'
import building from '@/components/page/building'
import course from '@/components/page/course'

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
    },
    {
      path: '/home',
      name: 'home',
      component: home
    },
    {
      path: '/notice',
      name: 'notice',
      component: notice
    },
    {
      path: '/user',
      name: 'user',
      component: user
    },
    {
      path: '/building',
      name: 'building',
      component: building
    },
    {
      path: '/course',
      name: 'course',
      component: course
    }

  ]
})
