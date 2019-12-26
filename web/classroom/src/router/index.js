import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/page/login'
import register from '@/components/page/register'
import notice from '@/components/page/notice'
import user from '@/components/page/user'
import building from '@/components/page/building'
import course from '@/components/page/course'
import buildingManage from '@/components/page/buildingManage'
import courseManage from '@/components/page/courseManage'
import userManage from '@/components/page/userManage'
import noticeManage from '@/components/page/noticeManage'
import process from '@/components/page/process'
import roomManage from '@/components/page/roomManage'




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
    },
    {
      path: '/buildingManage',
      name: 'buildingManage',
      component: buildingManage
    },
    {
      path: '/courseManage',
      name: 'courseManage',
      component: courseManage
    },
    {
      path: '/userManage',
      name: 'userManage',
      component: userManage
    },
    {
      path: '/noticeManage',
      name: 'noticeManage',
      component: noticeManage
    },
    {
      path: '/process',
      name: 'process',
      component: process
    },
    {
      path: '/roomManage',
      name: 'roomManage',
      component: roomManage
    }


  ]
})
