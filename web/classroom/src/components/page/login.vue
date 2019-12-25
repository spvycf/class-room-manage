

<template>
  <div>
    <el-form ref="form" :model="form" label-width="80px" size="medium  ">
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
  .el-input{
    width: 280px;
  }

</style>
