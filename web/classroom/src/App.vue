<template>
    <div id="app" >

  <div>


    <header-nav></header-nav>
    <el-container style="height: 100%; border: 2px ">
    <el-container>
      <left></left>
      <el-main >
          <router-view v-if="isRouterAlive"></router-view>
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
          isRouterAlive: true
        }
      },
      methods:{
        reload(){
          this.isRouterAlive = false;
          this.$nextTick(function () {
            this.isRouterAlive = true;

          })
        }
      }




    }
</script>


