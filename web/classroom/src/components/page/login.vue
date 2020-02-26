

<template>
  <div id="logo">

    <h2 class="title">教室信息管理系统</h2>

    <el-form ref="form" :model="form" label-width="80px" size="medium" class="loginForm">

      <el-form-item label="登录名">
        <el-input v-model="form.loginNo"></el-input>
      </el-form-item>


      <el-form-item label="密码">
        <el-input v-model="form.password" show-password></el-input>
      </el-form-item>


      <el-form-item>
        <el-button  @click="toRegister">注册</el-button>
        <el-button type="primary" @click="onSubmit">登录</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
  import {loginUrl} from '@/api/api';
  export default {
    name: 'login',

    data() {
      return {
        form: {
          loginNo: '',
          password: ''

        },
        note: {
          backgroundImage: "url(" + require("../../source/classroom.jpg") + ")",
          backgroundRepeat: "no-repeat",
          backgroundSize: '100% 100%',
          backgroundPosition: "fixed",
        }
      }
    },
    created() {
      this.$emit('header', false);

    },
    methods: {
      onSubmit() {
        loginUrl(this.form)
        .then(res=>{
          let id = res.data.id;
          let name = res.data.userName;
          let type = res.data.type;
          this.$message.success({
            message: '欢迎您,'+name,
            center:true,
          });
          window.localStorage.setItem('token',id);
          window.localStorage.setItem('userName',name);
          window.localStorage.setItem('type',type);
          //准备路由
          this.$router.replace('/notice')

        }
      )
      },
      toRegister(){
        this.$router.replace('/register');
      }
    }
  }
</script>


<style scoped>
  .el-form-item__label{color:red}
  .el-input{
    width: 280px;
  }
  #logo{
    background: url("../../source/classroom.jpg");
    background-size: 100% 100%;
    height: 100%;
    position: fixed;
    width: 100%;
    top: 0px;
    left: 0px;
  }
  .loginForm{
    padding-left: 810px;
    padding-top: 128px;
  }
  .title{
    padding-left: 900px;
    padding-top: 100px;
  }

</style>
