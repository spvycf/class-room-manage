<template>
  <el-header>
    <el-header style="text-align: right; font-size: 12px">
<!--      <el-dropdown>
        <i class="el-icon-setting" style="margin-right: 15px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>修改信息</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>-->
      <span style="color: black">welcome，{{role}} {{user}}</span>
      <el-button type="text" @click="loginOut" >
      <span>退出</span>
      </el-button>
    </el-header>
  </el-header>
</template>

<script>
  export default {
    data(){
      return{
        user:'',
        role:'',
      }
    },
    created(){
      this.user=window.localStorage.getItem('userName');
      var t= window.localStorage.getItem('type');
      if (t=='0'){
        this.role='管理员';
      }else if (t=='1'){
        this.role='教师';
      }else {
        this.role='学生';
      }
    },
    methods:{
      loginOut(){
        window.localStorage.removeItem('token');
        window.localStorage.removeItem('userName');
        //准备路由
        this.$router.replace('/login')
      }
    }
  }
</script>


<style>
/*  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }*/

</style>

