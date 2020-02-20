<template>
  <div>

    <AddBuildingRoom v-show="showModal" @on-cancel-add="cancelAdd" ref="addBuildingRoom" v-bind:buildingId="buildingId"></AddBuildingRoom>

    <el-row :gutter="10">
      <el-col :span="4"><div class="grid-content bg-purple">
        教室编号:
      </div></el-col>
      <el-col :span="4" ><div class="grid-content bg-purple-light">
        <el-input class="heiget-input" v-model="roomNO" placeholder="编号"></el-input>
      </div></el-col>

      <el-col :span="4" style="padding-left: 95px">
        <div class="grid-content bg-purple heightd">
          容量:
        </div></el-col>
      <el-col :span="4" style="padding-left: 40px"><div class="grid-content bg-purple-light">
        <el-input class="heiget-input" v-model="roomSpace" placeholder="容量"></el-input>
      </div></el-col>

      <el-col :span="6" style="padding-left: 120px"><div class="grid-content bg-purple">
        多媒体:
      </div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple-light">
        <el-select v-model="hasMedia" placeholder="请选择">
          <el-option
            v-for="item in types"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </div></el-col>



      <el-col :span="4" style="padding-left: 110px;padding-right: 75px;">
        <el-button type=""  @click="cleanSearch" style="height: 36px" >清空</el-button>
      </el-col>

      <el-col :span="4" style="padding-left: 6px">
        <el-button type="primary"  @click="search" style="height: 36px">搜索</el-button>
      </el-col>

    </el-row>
    <el-button v-if="isAdmin()" type="primary"  @click="showAdd">新增教室</el-button>




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
        prop="roomNO"
        align="center"
        label="教室编号"
        width="200">
      </el-table-column>
      <el-table-column
        prop="roomSpace"
        label="教室容量"
        align="center"
        width="150">
      </el-table-column>

      <el-table-column
        prop="hasMedia"
        label="多媒体"
        align="center"
        :formatter="hasMediaFormat"
        width="150">
      </el-table-column>

      <el-table-column
        prop="status"
        label="状态"
        align="center"
        :formatter="statusFormat"
        width="150">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        width="300">
        <template slot-scope="scope">
          <el-button  @click="courseDetail(scope.row)" type="primary" size="small">课程安排</el-button>
          <el-button v-if="isAdmin()"  @click="handleUpdate(scope.row)" type="info" size="small">编辑</el-button>
          <el-button v-show="isAdmin()" type="danger" size="small" v-if="scope.row.status=='0'" @click="handleForbidden(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      style=""
      :hide-on-single-page="false"
      layout="total,prev, pager, next"
      :total=totalNum
      :size=size
      class="pagination"
      @current-change="currentChangeHandle"


    >
    </el-pagination>
  </div>



</template>


<script>
  import {listRoomUrl} from '@/api/api';
  import AddBuildingRoom from "../modal/AddBuildingRoom";
  import {deleteRoomUrl} from "../../api/api";

  export default {

    components: {AddBuildingRoom},
    comments:{
      'AddBuildingRoom':AddBuildingRoom

    },
    inject:['reload'],

    data() {
      return {
        types:[
          {
            value:'1',
            label:'有'
          },
          {
            value:'0',
            label:'无'
          },
        ],

        buildingId: '',
        buildingNo: '',
        roomNO: '',
        roomSpace: '',
        hasMedia: '',
        tableData:[],
        totalNum:0,
        size:0,
        showModal:false

      }
    },
    created() {
      this.buildingId=this.$route.params.id;
      listRoomUrl(1,10,{
        'buildingId':this.buildingId
      }).then(res=>{
        this.tableData=res.data.records;
        this.totalNum=res.data.total;
        this.size=res.data.size;
      })

    },
    methods:{
      statusFormat(row,coloum){
        return row.status=='0'?'正常':'冻结';
      },
      hasMediaFormat(row,coloum){
        return row.hasMedia=='1'?'有':'无';
      },
      currentChangeHandle(val){
        listRoomUrl(val,10,
          {
            'buildingId':this.buildingId,
            'roomNO':this.roomNO,
            'roomSpace':this.roomSpace,
            'hasMedia':this.hasMedia
          }
        ).then(res=>{
          let data = res.data.records;
          this.tableData=data;
          this.totalNum=res.data.total;
          this.size=res.data.size;
        });
      },
      handleUpdate(row){
        this.$refs.addBuildingRoom.getRoom(row.id);

      },
      handleForbidden(row){
        this.$confirm('确定删除该教室吗?', '删除教室 ', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          deleteRoomUrl (
            {
              'id':row.id
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
        listRoomUrl(1,10,
          {
            'buildingId':this.buildingId,
            'roomNO':this.roomNO,
            'roomSpace':this.roomSpace,
            'hasMedia':this.hasMedia
          }
        ).then(res=>{
          let data = res.data.records;
          this.tableData=data;
          this.totalNum=res.data.total;
          this.size=res.data.size;
        });
      },
      cleanSearch(){
        this.roomNO='';
        this.roomSpace='';
        this.hasMedia='';

      },


      reFresh(){
        this.reload();
      },
      showAdd(){
        this.showModal=true;
      },
      cancelAdd(){
        this.showModal=false;
      },
      courseDetail(row){
        this.$router.push({
          path: '/courseManage',
          query: {
            roomId:row.id
          }
        })

      },
      isAdmin(){
        let type = window.localStorage.getItem('type');
        return (type=='0');
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
    width: 85px;
  }
  .el-col-6 {
    width: 190px;
  }

</style>
