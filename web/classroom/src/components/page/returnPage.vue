<template>
  <div>
    <!--    <UpdateBuildingType v-show="showUpdateBuildingModal" @on-close="cancel" ref="updateUser"></UpdateBuildingType>
        <AddBuildingRoom v-show="showAddRoomModal" @on-close="cancel" ref="updateUser"></AddBuildingRoom>
    <AddBuildingType v-show="showModal" @on-cancel-add="cancelAdd" ref="addBuilding"></AddBuildingType>-->

    <!--
        <el-row :gutter="10">
          <el-col :span="4"><div class="grid-content bg-purple">
            编号:
          </div></el-col>
          <el-col :span="4" ><div class="grid-content bg-purple-light">
            <el-input class="heiget-input" v-model="buildingNo" placeholder="教学楼编号"></el-input>
          </div></el-col>

          <el-col :span="4" style="padding-left: 115px">
            <div class="grid-content bg-purple heightd">
              名称:
            </div></el-col>
          <el-col :span="4" style="padding-left: 75px"><div class="grid-content bg-purple-light">
            <el-input class="heiget-input" v-model="buildingName" placeholder="教学楼名称"></el-input>
          </div></el-col>
    -->

    <el-row :gutter="10">


      <el-col :span="4" style="padding-left: 20px">
        <el-button type="primary"   style="height: 36px">归还教室处理</el-button>
      </el-col>

    </el-row>
    <!--    <el-button type="primary"  @click="showAdd">新增教学楼</el-button>-->




    <el-table
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        fixed
        align="center"
        prop="buildingNo"
        label="教学楼编号"
        width="200">
      </el-table-column>
      <el-table-column
        prop="buildingName"
        align="center"
        label="教学楼名称"
        width="200">
      </el-table-column>
      <el-table-column
        prop="userName"
        label="用户名称"
        align="center"
        width="150">
      </el-table-column>
      <el-table-column
        prop="courseName"
        label="课程名称"
        align="center"
        width="150">
      </el-table-column>
      <el-table-column
        prop="type"
        :formatter="typeFormat"
        label="课程安排"
        align="center"
        width="150">
      </el-table-column>
      <el-table-column
        prop="startTime,endTime"
        label="课程时间"
        align="center"
        width="150">
        <template slot-scope="scope"> {{scope.row.startTime}}-{{scope.row.endTime}} </template>
      </el-table-column>

      <el-table-column
        prop="status"
        label="状态"
        align="center"
        :formatter="statusFormat"
        width="150">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="申请时间"
        align="center"
        width="200">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        width="300">
        <template slot-scope="scope">
          <!--         <el-button  @click="handleUpdate(scope.row)" type="info" size="small">编辑</el-button>-->
          <el-button  @click="handlePassRoom(scope.row)" type="primary" size="small">通过</el-button>
          <!--<el-button  @click="handleDeainedRoom(scope.row)" type="warning" size="small">拒绝</el-button>-->
          <!--          <el-button type="danger" size="small" v-if="scope.row.status=='0'" @click="handleForbidden(scope.row)">删除</el-button>
                    <el-button type="success" size="small" v-else @click="handleRelease(scope.row)">恢复</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      style=""
      :hide-on-single-page="false"
      layout="total,prev, pager, next"
      :total=totalNum
      :page-size=size
      class="pagination"
      @current-change="currentChangeHandle"


    >
    </el-pagination>
  </div>



</template>

<script>
  import {listProcessUrl} from '@/api/api';
  import {returnUrl} from '@/api/api';

  export default {
    inject:['reload'],
    name:'returnPage',
    data(){
      return{
        id:'',
        buildingNo:'',
        buildingName:'',
        status:'',
        createTime:'',
        tableData:[],
        totalNum:0,
        size:0,
        showModal:false
      }
    },


    created() {
      listProcessUrl(1,10,
        {
          'status':'4',

        }
      ).then(res=>{
        this.tableData=res.data.records;
        this.totalNum=res.data.total;
        this.size=res.data.size;

      });



    },
    methods:{
      statusFormat(row,coloum){
        if (row.status==='2'){
          return '申请待通过';
        }else if (row.status==='4'){
          return '归还待通过';
        }


      },
      typeFormat(row,coloum){
        if (row.type==='0'){
          return '每天';
        }else if (row.type==='1'){
          return '每周一';
        }else if (row.type==='2'){
          return '每周二';
        }else if (row.type==='3'){
          return '每周三';
        }else if (row.type==='4'){
          return '每周四';
        }else if (row.type==='5'){
          return '每周五';
        }else if (row.type==='6'){
          return '每周刘';
        }else if (row.type==='7'){
          return '每周日';
        }else if (row.type==='8'){
          return '临时';
        }

      },
      //main
      currentChangeHandle(val){
        listProcessUrl(val,10,
          {
            'status':'4'

          }
        ).then(res=>{
          let data = res.data.records;
          this.tableData=data;
          this.totalNum=res.data.total;
          this.size=res.data.size;
        });
      },



      handlePassRoom(row){
        returnUrl(
          {
            'id':row.id

          }
        ).then(res => {
          this.$message.success({
            message: '通过成功',
            center: true,
          });
          this.reload();
        });
      },
      handleDeainedRoom(row){
        applyUrl(
          {
            'id':row.id,
            'status':'5'
          }
        ).then(res => {
          this.$message.success({
            message: '已拒绝申请',
            center: true,
          });
          this.reload();
        });
      },
      dealReturn(){
        this.status='4';
        listProcessUrl(1,10,
          {
            'status':'4',

          }
        ).then(res=>{
          this.tableData=res.data.records;
          this.totalNum=res.data.total;
          this.size=res.data.size;

        });

      },
      dealProcess(){
        this.status='2';
        listProcessUrl(1,10,
          {
            'status':'2',

          }
        ).then(res=>{
          this.tableData=res.data.records;
          this.totalNum=res.data.total;
          this.size=res.data.size;

        });

      },







      handleUpdate(row){
        this.$refs.addBuilding.getBuilding(row.id);

      },
      showAdd(){
        this.showModal=true;
      },
      cancelAdd(){
        this.showModal=false;
      },
      reFresh(){
        this.reload();
      },
      handleSeeRoom(row){
        this.$router.replace({
          name:'roomManage',
          params:{
            id:row.id
          }
        });
      }







    },
  }

</script>

<style>
  .pagination{
    text-align: center;
    margin-top: 50px;
  }
  .el-input__inner{
    height: 36px;
  }
  .heightd{
    width: 80px;
  }
  .el-row {
    margin-bottom: 5px;
  &:last-child {
     margin-bottom: 0;
   }
  }
  .el-col {
    border-radius: 4px;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .bg-purple {

    margin-top: 8px;
    margin-left: auto;
    /*background: #d3dce6;*/
  }
  .bg-purple-light {
    width: 150px;
  }
  .el-col-4 {
    width: 60px;
  }
  .el-col-6 {
    width: 190px;
  }



</style>
