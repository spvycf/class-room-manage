<template>
  <div class="modal-backdrop">
    <div class="modal" :style="mainStyles">
      <div class="modal-header">
        <h3>新增公告</h3>
      </div>
      <div class="modal-body">
        <el-form  ref="addNoticeForm" :model="addNoticeForm" label-width="80px" @submit.native.prevent >

          <el-form-item label="公告标题" prop="title"
                        :rules="[
      { required: true, message: '标题不能为空'}
    ]"
          >
            <el-input v-model="addNoticeForm.title" ></el-input>
          </el-form-item>

          <el-form-item prop="content"
                        :rules="[
      { required: true, message: '内容不能为空'}
    ]"
          >
            <el-input :autosize="{ minRows: 4, maxRows: 8}"
              type="textarea"
              placeholder="请输入内容"
              v-model="addNoticeForm.content"
              maxlength="600"
              show-word-limit
            >
            </el-input>
          </el-form-item>


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

  export default {
    name: 'AddNotice',
    props: {
    },
    data() {
      return {
        addNoticeForm:{
          title:'',
          content:'',
        }
      }
    },
    methods: {
      cancel() {
         this.addNoticeForm.title="";
         this.addNoticeForm.content="";
        this.$emit('on-cancel');
      },
      saveNotice(){
        saveNoticeUrl(this.addNoticeForm)
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
    justify-content: space-between;
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



