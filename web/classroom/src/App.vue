<template>
    <div id="app" >

  <div>

    <header-nav></header-nav>
    <left></left>
    <router-view></router-view>
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
      }


    }
</script>


