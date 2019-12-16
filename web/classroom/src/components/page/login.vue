

<template>
  <div>
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="登录名">
        <el-input v-model="form.loginNo"></el-input>
      </el-form-item>


        <el-form-item label="密码">
          <el-input v-model="form.password" show-password></el-input>
        </el-form-item>


      <el-form-item>
        <el-button>注册</el-button>
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
    methods: {
      onSubmit() {
        let params = new URLSearchParams();
        for(var key in this.form){
          params.append(key,this.form[key])
        }

        console.log(params.get('loginNo'));
        console.log(params.get('password'));

        loginUrl(
          params
        ).then(res=>{
          console.log(res)
        })

      }
    }
  }
</script>


<style scoped>

</style>
