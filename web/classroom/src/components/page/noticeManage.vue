<template>

  <div>
    <el-button type="primary"  @click="showAdd">新增公告</el-button>
    <AddNotice v-show="showModal" @on-cancel="cancel" ref="addNotice"></AddNotice>
    <ShowNotice v-show="showNoticeModal" @on-close="closeNotice" ref="showNotice"></ShowNotice>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="title"
        label="标题"
        width="500px"
        :show-overflow-tooltip="true"
        align="center">
      </el-table-column>
<!--      <el-table-column
        prop="content"
        label="内容"
        width="500px"
        align="center">
      </el-table-column>-->
      <el-table-column
        prop="createTime"
        label="发布时间"
        width="500px"
        align="center">

      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="300"
        align="center">
        <template slot-scope="scope">
          <el-button
            @click.native.prevent="handleSee(scope.$index, scope.row)"
            type="text"
            size="small">
            查看
          </el-button>
          <el-button
            @click.native.prevent="handleUpdate(scope.$index, scope.row)"
            type="text"
            size="small">
            编辑
          </el-button>
          <el-button
            @click.native.prevent="handleDelete(scope.$index, scope.row)"
            type="text"
            size="small">
            删除
          </el-button>
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
  import {listNoticeUrl} from '@/api/api';
  import {deleteNoticeUrl} from '@/api/api';
  import {getNoticeUrl} from '@/api/api';
  import AddNotice from "../modal/addNotice";
  import ShowNotice from "../modal/showNotice";

  export default {
    components: {AddNotice,ShowNotice},
    inject:['reload'],
    name:'noticeManage',
    data(){
      return{
        tableData:[],
        showModal:false,
        showNoticeModal:false,
        totalNum:0,
        size:0
      }
    },
    comments:{
      'AddNotice':AddNotice,
      'ShowNotice':ShowNotice,
    },
    created() {

      listNoticeUrl(1,10)
        .then(res=>{
          let data = res.data.records;
          this.tableData=data;
          this.totalNum=res.data.total;
          this.size=res.data.size;
        })
    },
    methods:{
      handleSee(index,row){
        //console.log(row.id);
        //请求详情
        //this.$refs.addNotice.getNotice(row.id);

        this.$refs.showNotice.getNotice(row.id);

      },
      handleUpdate(index,row){
        //console.log(row.id);
        //请求详情
        this.$refs.addNotice.getNotice(row.id);

      },

      handleDelete(index,row){
        this.$confirm('确定删除该公告吗?', '删除公告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteNoticeUrl({'id': row.id})
            .then(res => {
            this.$message.success({
              message: '删除成功',
              center: true,
            });
            this.reload();
          });
        });
      },
      currentChangeHandle(val){
        listNoticeUrl(val,10)
          .then(res=>{
            let data = res.data.records;
            this.tableData=data;
            this.totalNum=res.data.total;
            this.size=res.data.size;
          });
      },

      //新增
      showAdd(){
        this.showModal=!this.showModal;
      },

      cancel(){
        this.showModal=false;
      },

      closeNotice(){
        this.showNoticeModal=false;
      },

      open(){
        this.showModal=true;
      },
      openShow(){
        this.showNoticeModal=true;
      },

      reFresh(){
        this.reload();
      }




    },

  }

</script>

<style>
  .pagination{
    text-align: center;
    margin-top: 50px;
  }

</style>
