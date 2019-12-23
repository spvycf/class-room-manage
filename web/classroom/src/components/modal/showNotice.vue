<template>
    <div class="modal-backdrop">
      <div class="modal" :style="mainStyles">
        <div class="modal-header">
          <h3 >{{title}}</h3>
        </div>
        <div class="modal-body">
          <p>{{content}}</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn-confirm" @click="closeNotice">关闭</button>
        </div>

      </div>
    </div>

</template>

<script>
  import {getNoticeUrl} from '@/api/api';
  export default {
    data(){
      return{
        title:'',
        content:'',

      }
    },
    methods:{
      getNotice(id){
          getNoticeUrl({'id':id})
            .then(res=>{
              console.log(res.data);
              this.title=res.data.title;
              this.content=res.data.content;
              this.$parent.openShow();

            });
      },
      closeNotice(){
        this.$emit('on-close');

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
    text-align: center;
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
</style>
