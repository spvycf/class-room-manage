<template>
  <div class="modal-backdrop">
    <div class="modal" >
      <div class="modal-header">
        <h3>{{tt}}</h3>
      </div>
      <div class="modal-body">
        <el-form  ref="addNoticeForm" :model="addCourseForm" label-width="80px" @submit.native.prevent >


          <input v-model="addCourseForm.id" v-show="false"></input>



          <el-form-item label="课程名称" prop="title"
                        :rules="[
      { required: true, message: '名称不能为空'}
    ]"
          >
            <el-input v-model="addCourseForm.title"
                      maxlength="20"
                      show-word-limit
            ></el-input>
          </el-form-item>

          <el-form-item label="课程时间" prop="type"
                        :rules="[
      { required: true, message: '课程时间不能为空'}
    ]"
          >
            <el-radio v-model="addCourseForm.type" label="0" @change="typeChange">每天</el-radio>
            <el-radio v-model="addCourseForm.type" label="1" @change="typeChange">每周一</el-radio>
            <el-radio v-model="addCourseForm.type" label="2" @change="typeChange">每周二</el-radio>
            <el-radio v-model="addCourseForm.type" label="3" @change="typeChange">每周三</el-radio>
            <el-radio v-model="addCourseForm.type" label="4" @change="typeChange">每周四</el-radio>
            <el-radio v-model="addCourseForm.type" label="5" @change="typeChange">每周五</el-radio>
            <el-radio v-model="addCourseForm.type" label="6" @change="typeChange">每周六</el-radio>
            <el-radio v-model="addCourseForm.type" label="7" @change="typeChange">每周日</el-radio>
            <el-radio v-model="addCourseForm.type" label="8" @change="typeChange">自定义</el-radio>

          </el-form-item>
          <div class="block" v-show="isShow">
            <span class="demonstration"></span>
            <el-date-picker
              v-model="value1"
              type="datetime"
              placeholder="选择日期时间">
            </el-date-picker>
          </div>




        </el-form>



      </div>
      <div class="modal-footer">
        <button type="button" class="btn-close" @click="cancel">取消</button>
        <button type="button" class="btn-confirm" @click="saveNotice">提交</button>
      </div>
    </div>

  </div>
</template>



<script>
  import {saveNoticeUrl} from '@/api/api';
  import {updateNoticeUrl} from '@/api/api';
  import {getNoticeUrl} from '@/api/api';


  export default {
    name: 'AddNotice',
    props: {
    },
    data() {
      return {
        tt:'新增课程',
        addCourseForm:{
          id:'',
          title:'',
          type:'',

        },
        isShow:false,
      }
    },
    methods: {
      cancel() {
        this.addCourseForm.id="";
        this.addCourseForm.title="";
        this.addCourseForm.content="";
        this.$emit('on-cancel');
      },
      saveNotice(){
        let updateId = this.addCourseForm.id;
        if (Object.keys(updateId).length==0){
          //新增
          saveNoticeUrl(this.addCourseForm)
            .then(res=>{
              this.$message.success({
                message: '保存成功',
                center:true,
              });
              this.cancel();
              this.$parent.reFresh();
            });

        }else {
          //编辑
          updateNoticeUrl(this.addCourseForm)
            .then(res=>{
              this.$message.success({
                message: '修改成功',
                center:true,
              });
              this.cancel();
              this.$parent.reFresh();
            });


        }

      },
      //get
      getNotice(id){
        this.tt='编辑课程',
          getNoticeUrl({'id':id})
            .then(res=>{
              this.addCourseForm.id=res.data.id;
              this.addCourseForm.title=res.data.title;
              this.addCourseForm.content=res.data.content;
              this.$parent.open();

            });




      },
      typeChange(val){
        this.isShow=false;
        if ('8'===val){
          this.isShow=true;
        }
      }


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



