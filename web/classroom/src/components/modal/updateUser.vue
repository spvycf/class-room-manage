<template>
  <div class="modal-backdrop">
    <div class="modal" >
      <div class="modal-header">
        <h3>编辑用户</h3>
      </div>
      <div class="modal-body">
        <el-form  ref="userForm" :model="userForm" label-width="80px" @submit.native.prevent >
          <input v-model="userForm.id" v-show="false"></input>


          <el-form-item label="姓名" prop="userName"
                        :rules="[
      { required: true, message: '姓名不能为空'}
    ]"
          >
            <el-input v-model="userForm.userName"
                      maxlength="20"
                      show-word-limit
            ></el-input>
          </el-form-item>

          <el-form-item label="性别" prop="sex"
                        :rules="[
      { required: true, message: '性别不能为空'}
    ]"
          >
            <el-radio v-model="userForm.sex" label="0">女</el-radio>
            <el-radio v-model="userForm.sex" label="1">男</el-radio>

          </el-form-item>

          <el-form-item label="身份" prop="type"
                        :rules="[
      { required: true, message: '身份不能为空'}
    ]"
          >
            <el-radio v-model="userForm.type" label="1">教师</el-radio>
            <el-radio v-model="userForm.type" label="2">学生</el-radio>

          </el-form-item>

          <el-form-item label="手机" prop="phone"
                        :rules="[
      { required: false, message: ''}
    ]"
          >
            <el-input v-model="userForm.phone"
            ></el-input>
          </el-form-item>

          <el-form-item label="年龄" prop="age"
                        :rules="[
      { required: false, message: ''}
    ]"
          >
            <el-input v-model="userForm.age" type="number"
            ></el-input>
          </el-form-item>

          <el-form-item label="班级" prop="classNO"
                        :rules="[
      { required: false, message: ''}
    ]"
          >
            <el-input v-model="userForm.classNO"
            ></el-input>
          </el-form-item>

          <el-form-item label="专业" prop="profession"
                        :rules="[
      { required: false, message: ''}
    ]"
          >
            <el-input v-model="userForm.profession"
            ></el-input>
          </el-form-item>


        </el-form>


      </div>
      <div class="modal-footer">
        <button type="button" class="btn-close" @click="cancel">取消</button>
        <button type="button" class="btn-confirm" @click="save">保存</button>
      </div>
    </div>

  </div>

</template>

<script>
  import {getUserUrl} from '@/api/api';
  import {updateUserUrl} from '@/api/api';
  export default {
    name:'updateUser',
    data(){
      return{
        userForm:{
          loginNo:'',
          userName:'',
          sex:'',
          age:'',
          profession:'',
          classNO:'',
          phone:''
        }

      }
    },

    methods:{
      getUser(id){
        getUserUrl({'id':id})
        .then(res=>{
          this.userForm=res.data;
          this.$parent.open();

        })
      },
      cancel(){

        this.$emit('on-close');

      },
      save(){
/*        this.userForm.createTime='';
        this.userForm.updateTime='';*/
        updateUserUrl(this.userForm)
        .then(res=>{
            this.$message.success({
              message: '保存成功',
              center:true,
            });
            this.cancel();
            this.$parent.reFresh();
          });
      },



    }


  }
</script>

<style>
  .modal-backdrop {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(0,0,0,.3);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 100;
  }
  .modal {
    background-color: #fff;
    box-shadow: 2px 2px 20px 1px;
    overflow-x:auto;
    display: flex;
    flex-direction: column;
    border-radius: 16px;
    width: 700px;
  }
  .modal-header {
    border-bottom: 1px solid #eee;
    color: #313131;
    justify-content: center;
    padding: 15px;
    display: flex;
  }
  .modal-footer {
    border-top: 1px solid #eee;
    justify-content: flex-end;
    padding: 15px;
    display: flex;
  }
  .modal-body {
    position: relative;
    padding: 20px 10px;
  }
  .btn-close, .btn-confirm {
    border-radius: 8px;
    margin-left:16px;
    width:56px;
    height: 36px;
    border:none;
    cursor: pointer;
  }
  .btn-close {
    color: #313131;
    background-color:transparent;
  }
  .btn-confirm {
    color: #fff;
    background-color: #2d8cf0;
  }
</style>
