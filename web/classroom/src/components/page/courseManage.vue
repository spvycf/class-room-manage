<template>
  <div class="class-table">
    <addCourse v-show="showModal" @on-cancel="cancelAdd" ref="addCourse"></addCourse>

    <div class="block">
      <span class="demonstration">选择时间:</span>
      <el-date-picker
        v-model="weekTime"
        type="week"
        format="yyyy 第 WW 周"
        value-format="yyyy-MM-dd"
        :picker-options="{'firstDayOfWeek': 1}"
        placeholder="选择周">
      </el-date-picker>
        <el-button type="primary"   @click="search" style="width:50px;height:38px" >查询</el-button>
        <el-button type="primary"   v-if="isAdmin()" @click="showAddCourse" style="width:80px;height:38px" >新增课程</el-button>
        <el-button type="primary"   v-else @click="showAddCourse" style="width:80px;height:38px" >申请课程</el-button>
      </div>
    <div class="table-wrapper">
      <div class="tabel-container">
        <table>
          <thead>
          <tr>
            <th style="width:100px;">时间</th>
            <th v-for="(lesson, lessonIndex) in classTableData.lessons" :key="lessonIndex">{{lesson}}</th>
          </tr>
          </thead>
          <tbody>
          <!--<tr v-for="(lesson, lessonIndex) in classTableData.lessons" :key="lessonIndex">-->
          <tr v-for="(weekNum, weekIndex) in classTableData.weeks" :key="weekIndex">
            <td>
              <p>{{'周' + weekNum}}</p>
              <p>{{courseDate[weekIndex].date}}</p>
            </td>
            <td class="courses" v-for="(lesson, lessonIndex) in classTableData.lessons" :key="lessonIndex">
            <div    v-for="(course, courseIndex) in courseDate[weekIndex].courses" :class="{bgColor:courseIndex%2===1}" :key="courseIndex" class="course" @click="detail(course.id)">
              <div v-if="(lessonIndex*6+6)<=parseInt(course.startTime.substr(0,2))&&parseInt(course.startTime.substr(0,2))<lessonIndex*6+12">
                <p style="font-size: 11px;">{{course.courseName}}</p>
                <!--<p style="font-size: 11px;">{{lessonIndex*6+6}}</p>
                <p style="font-size: 11px;">{{parseInt(course.startTime.substr(0,2))}}</p>
                <p style="font-size: 11px;">{{lessonIndex*6+12}}</p>-->
                <p style="font-size: 11px;">{{course.userName}}</p>
                <p style="font-size: 8px;color: #eee">{{course.startTime}}-{{course.endTime}}</p>
              </div>

            </div>
            <!--{{classTableData.courses[courseIndex][weekIndex] || '-'}}-->
          </td>

          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>

</template>

<script>
  import addCourse from "../modal/addCourse";


  import {listCourseUrl} from '@/api/api';
  import {deleteCourseUrl} from '@/api/api';


  export default {
    components: {addCourse},
    comments:{
      'addCourse':addCourse

    },

    inject:['reload'],
    name:'courseManage',
    data(){
      return{
        showModal:false,
        weekTime: '',
        roomId:'',
        classTableData: {
          lessons: [
            '上午',
            '下午',
            '晚上'
          ],
          weeks: [
            '一',
            '二',
            '三',
            '四',
            '五',
            '六',
            '日'
          ],



        },
        courseDate: [

        ],






        }
    },
    created() {
      this.roomId=this.$route.query.roomId;
      listCourseUrl({
        'roomId':this.roomId
      })
        .then(res=>{
            this.courseDate=res.data;

          }

        );
    },
    methods:{
      listCs(){
        listCourseUrl({
          'roomId':this.roomId
        })
        .then(res=>{
          this.courseDate=res.data;
          }

        );
      },
      detail(id){
        this.$confirm('确认删除课程吗?', '删除课程', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          deleteCourseUrl(
            {
              'id':id

            }
          ).then(res => {
            this.$message.success({
              message: '删除成功',
              center: true,
            });
            this.reload();
          });
        });
      },
      search(){
        listCourseUrl({
          'roomId':this.roomId,
          'date':this.weekTime
        })
          .then(res=>{
            this.courseDate=res.data;
          }
          );
      },
      cancelAdd(){
        this.showModal=false;
      },
      showAddCourse(){
        this.showModal=true;
      },
      isAdmin(){
        let type = window.localStorage.getItem('type');
        return (type=='0');
      }


    },



  }



</script>

<style scoped>
  *{
    margin: 0;
    padding: 0;
  }
  .table-wrapper {
    width: 100%;
    overflow: auto;
    margin-bottom: 60px;
  }
  table {
    table-layout: fixed;
    width: 100%;
  }

  thead {
    background-color: #547c99;
  }
  th {
    color: #fff;
    line-height: 15px;
    font-weight: normal;
  }
  tbody {
    background-color: #f9f9f9;
  }
  td {
    color: #677998;
    line-height: 15px;
  }
  th, td {
    padding: 8px 2px;
    font-size: 15px;
    text-align: center;
  }
  tbody tr:nth-child(2n){
    background-color: #ecf4f4;
  }

  tr td:first-child {
    color: #333;
  }
  .course{
    background-color: #ebbbbb;
    color: #fff;
    display: inline-block;
    border-radius: 3px;
    width: 47%;
    margin: 1px 1%;
  }
  .bgColor{
    background-color: #89B2E5;
  }
</style>
