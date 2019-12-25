<template>
  <div>
    <el-form  ref="registForm" :model="registForm" label-width="80px" @submit.native.prevent >
      <el-form-item label="登录账号" prop="loginNo"
                    :rules="[
      { required: true, message: '登录账号不能为空'}
    ]"
      >
        <el-input v-model="registForm.loginNo" ></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="userName"
                    :rules="[
      { required: true, message: '姓名不能为空'}
    ]">
        <el-input v-model="registForm.userName"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="password"
                    :rules="[
      { required: true, message: '密码不能为空'}
    ]">
        <el-input v-model="registForm.password" show-password></el-input>
      </el-form-item>
      <el-form-item label="重复密码" prop="repassword"
                    :rules="[
      { required: true, message: '请重复输入密码'}
    ]">
        <el-input v-model="registForm.repassword" show-password></el-input>
      </el-form-item>

      <el-form-item label="身份">
        <el-radio v-model="registForm.type" label="2">学生</el-radio>
        <el-radio v-model="registForm.type" label="1">老师</el-radio>
      </el-form-item>

      <el-form-item label="性别">
        <el-radio v-model="registForm.sex" label="1">男</el-radio>
        <el-radio v-model="registForm.sex" label="0">女</el-radio>
      </el-form-item>

      <el-form-item label="年龄" prop="age"
                    :rules="[
      { type: 'number', message: '年龄必须为数字值'}
    ]">
        <el-input v-model.number="registForm.age"></el-input>
      </el-form-item>
      <el-form-item label="专业">
        <el-input v-model="registForm.profession" ></el-input>
      </el-form-item>
      <el-form-item label="班级" prop="classNO"
                    :rules="[
      { required: true, message: '班级不能为空'}
    ]">
        <el-input v-model="registForm.classNO" ></el-input>
      </el-form-item>
      <el-form-item label="手机" prop="phone"
                    :rules="[
      { required: true, message: '手机号不能为空'}
    ]">
        <el-input v-model="registForm.phone" ></el-input>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="onSubmit" >提交</el-button>
      </el-form-item>
      <el-form-item>
        <router-link to="/login">已有账号,返回登录</router-link>
      </el-form-item>

    </el-form>
  </div>
</template>



<script>
  import {registerUrl} from '@/api/api';


  export default {
    data() {
      return {
        registForm: {
          loginNo: '',
          userName: '',
          password: '',
          repassword: '',
          type: '2',
          sex: '1',
          age: '',
          profession: '',
          classNO: '',
          phone: ''

        }
      }
    },
    methods: {
      onSubmit() {
        let password = this.registForm.password;
        let repassword = this.registForm.repassword;
        if (password!=repassword){
          this.$message.error({
            message:"两次密码输入不一致",
            center:true
          });
          return;
        }

        registerUrl(this.registForm)
        .then(res=>{

          this.$message.success({
            message:res.message,
            center:true
          });
          this.$router.replace('/login')



        })
      }
    }
  }
</script>

<style scoped>
  .el-input{
    width: 280px;
  }
</style>
