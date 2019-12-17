<template>
  <div>
    <el-form  ref="registForm" :model="registForm" label-width="80px" @submit.native.prevent>
      <el-form-item label="登录账号">
        <el-input v-model="registForm.loginNo"></el-input>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="registForm.userName"></el-input>
      </el-form-item>

      <el-form-item label="密码">
        <el-input v-model="registForm.password" show-password></el-input>
      </el-form-item>
      <el-form-item label="重复密码">
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

      <el-form-item label="年龄">
        <el-input v-model="registForm.age"></el-input>
      </el-form-item>
      <el-form-item label="专业">
        <el-input v-model="registForm.profession" ></el-input>
      </el-form-item>
      <el-form-item label="班级">
        <el-input v-model="registForm.classNO" ></el-input>
      </el-form-item>
      <el-form-item label="手机">
        <el-input v-model="registForm.phone" ></el-input>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="onSubmit" >提交</el-button>
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
        registerUrl(this.registForm)
        .then(res=>{

          this.$message.success({
            message:res.data.message,
            center:true
          });

        })
      }
    }
  }
</script>
