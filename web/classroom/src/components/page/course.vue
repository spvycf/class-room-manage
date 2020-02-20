<template>
  <div>
    <!--    <UpdateBuildingType v-show="showUpdateBuildingModal" @on-close="cancel" ref="updateUser"></UpdateBuildingType>
        <AddBuildingRoom v-show="showAddRoomModal" @on-close="cancel" ref="updateUser"></AddBuildingRoom>-->
    <AddBuildingType v-show="showModal" @on-cancel-add="cancelAdd" ref="addBuilding"></AddBuildingType>

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



      <el-col :span="4" style="padding-left: 210px;padding-right: 75px;">
        <el-button type=""  @click="cleanSearch" style="height: 36px" >清空</el-button>
      </el-col>

      <el-col :span="4" style="padding-left: 6px">
        <el-button type="primary"  @click="search" style="height: 36px">搜索</el-button>
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
        prop="num"
        label="教室数量"
        align="center"
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
        prop="createTime"
        label="创建时间"
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
          <el-button  @click="handleSeeRoom(scope.row)" type="warning" size="small">教室详情</el-button>
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
      :size=size
      class="pagination"
      @current-change="currentChangeHandle"


    >
    </el-pagination>
  </div>



</template>

<script>
  import AddBuildingType from "../modal/AddBuildingType";
  import {listbuildingTypeUrl} from '@/api/api';
  import {changebuildingTypeStatusUrl} from '@/api/api';

  export default {
    components: {AddBuildingType},
    comments:{
      'AddBuildingType':AddBuildingType

    },
    inject:['reload'],
    name:'buildingManage',
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
      listbuildingTypeUrl(1,10,
        {
          'buildingNo':this.buildingNo,
          'buildingName':this.buildingName
        }
      ).then(res=>{
        this.tableData=res.data.records;
        this.totalNum=res.data.total;
        this.size=res.data.size;

      });



    },
    methods:{
      statusFormat(row,coloum){
        return row.status=='0'?'正常':'冻结';
      },
      //main
      currentChangeHandle(val){
        listbuildingTypeUrl(val,10,
          {
            'buildingNo':this.buildingNo,
            'buildingName':this.buildingName
          }
        ).then(res=>{
          let data = res.data.records;
          this.tableData=data;
          this.totalNum=res.data.total;
          this.size=res.data.size;
        });
      },
      //清空搜索框内容
      cleanSearch(){
        this.buildingNo='';
        this.buildingName='';

      },
      search(){
        listbuildingTypeUrl(1,10,
          {
            'buildingNo':this.buildingNo,
            'buildingName':this.buildingName
          }
        ).then(res=>{
          let data = res.data.records;
          this.tableData=data;
          this.totalNum=res.data.total;
          this.size=res.data.size;
        });
      },
      handleForbidden(row){
        this.$confirm('确定删除该教学楼吗?', '删除教学楼 ', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          changebuildingTypeStatusUrl(
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
      handleRelease(row){
        changeStatusUrl(
          {
            'id':row.id,
            'status':'0'
          }
        ).then(res => {
          this.$message.success({
            message: '恢复成功',
            center: true,
          });
          this.reload();
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
