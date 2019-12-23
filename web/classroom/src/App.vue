<template>
    <div id="app" >

  <div>


    <header-nav v-if="header_left_show"></header-nav>
    <el-container style="height: 100%; border: 2px ">
    <el-container>
      <left  v-if="header_left_show"></left>
      <el-main >
          <router-view v-if="isRouterAlive" v-on:header="header_left"></router-view>
    </el-main>
    </el-container>

    </el-container>

  </div>






  <!--    <router-view></router-view>-->
    </div>
</template>

<script>
  import header from './components/header.vue';
  import left from './components/left.vue';
    export default {
      components: {
        headerNav: header,
        left: left
      },
      provide(){
        return {
          reload: this.reload
        }
      },
      data(){
        return {
          isRouterAlive: true,
          header_left_show:true,
        }
      },
      methods:{
        //是否显示头部
        header_left:function (bool) {
          this.header_left_show = bool;
        },
        reload(){
          this.isRouterAlive = false;
          this.$nextTick(function () {
            this.isRouterAlive = true;

          })
        }
      }




    }
</script>


