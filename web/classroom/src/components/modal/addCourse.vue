<template>
  <div class="modal-backdrop">
    <div class="modal" >
      <div class="modal-header">
        <h3>{{tt}}</h3>
      </div>

      <div class="modal-body">
        <input v-model="roomId" v-show="true"></input>
        <p>{{roomId}}1</p>
        <el-form  ref="addNoticeForm" :model="addCourseForm" label-width="80px" @submit.native.prevent >




          <el-form-item label="课程名称" prop="courseName"
                        :rules="[
      { required: true, message: '名称不能为空'}
    ]"
          >
            <el-input v-model="addCourseForm.courseName"
                      maxlength="20"
                      show-word-limit
            ></el-input>
          </el-form-item>

          <el-form-item label="课程计划" prop="type"
                        :rules="[
      { required: true, message: '课程计划不能为空'}
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




          <el-form-item v-show="isShow" label="课程计划" prop="date"
                        :rules="[
      { required: true, message: '时间不能为空'}
    ]"
          >
            <div class="block" >
              <el-date-picker
                v-model="addCourseForm.date"
                type="date"
                value-format="yyyy-MM-dd"
                :picker-options="pickerOptions"
                placeholder="选择日期">
              </el-date-picker>

            </div>
          </el-form-item>


          <el-form-item label="课程时间" prop="time"
                        :rules="[
      { required: true, message: '时间不能为空'}
    ]"
          >
            <el-time-picker
              is-range
              v-model="addCourseForm.time"
              value-format="HH:mm:ss"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              placeholder="选择时间范围">
            </el-time-picker>
          </el-form-item>





        </el-form>



      </div>
      <div class="modal-footer">
        <button type="button" class="btn-close" @click="cancel">取消</button>
        <button type="button" class="btn-confirm" @click="saveCourse">提交</button>
      </div>
    </div>

  </div>
</template>



<script>
  import {saveCourseUrl} from '@/api/api';



  export default {
    name: 'addCourse',
    props: [
      "roomId"
    ],
    data() {
      return {
        tt:'新增课程',
        //roomId:'',
        addCourseForm:{
          courseName:'',
          type:'',
          date:'',
          time:'',
          roomId:''

        },
        isShow:false,
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() < Date.now() - 24 * 60 * 60 * 1000
          }
        }
      }
    },
    methods: {
      cancel() {
        this.addCourseForm.id="";
        this.addCourseForm.courseName="";
        this.addCourseForm.type="";
        this.$emit('on-cancel');
      },
      saveCourse(){
         this.addCourseForm.roomId=this.roomId;


          //新增
          saveCourseUrl({
            'roomId':this.addCourseForm.roomId,
            'courseName':this.addCourseForm.courseName,
            'type':this.addCourseForm.type,
            'date':this.addCourseForm.date,
            'startTime':this.addCourseForm.time[0],
            'endTime':this.addCourseForm.time[1]


          })
            .then(res=>{
              this.$message.success({
                message: '保存成功',
                center:true,
              });
              this.cancel();
              this.$parent.reFresh();
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



