<template>

  <div>
    <el-button type="primary"  @click="showAdd">新增公告</el-button>
    <AddNotice v-show="showModal" @on-cancel="cancel"></AddNotice>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="title"
        label="标题"
        width="300px"
        align="center">
      </el-table-column>
      <el-table-column
        prop="content"
        label="内容"
        width="300px"
        align="center">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="发布时间"
        width="300px"
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
            查看详情
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
  </div>
</template>

<script>
  import {listNoticeUrl} from '@/api/api';
  import {deleteNoticeUrl} from '@/api/api';
  import {getNoticeUrl} from '@/api/api';
  import AddNotice from "../modal/addNotice";
  export default {
    components: {AddNotice},
    inject:['reload'],
    name:'noticeManage',
    data(){
      return{
        tableData:[],
        showModal:false
      }
    },
    comments:{
      'AddNotice':AddNotice
    },
    created() {
      listNoticeUrl(1,10)
        .then(res=>{
          let data = res.data.records;
          this.tableData=data;
        })
    },
    methods:{
      handleSee(index,row){
        console.log(row.id);
        //请求详情
        getNoticeUrl({'id':row.id})
      },

      handleDelete(index,row){
        console.log(row.id);
        deleteNoticeUrl({'id':row.id})
        .then(res=>{
          this.$message.success({
            message: '删除成功',
            center: true,
          });
          this.reload();
        });
      },

      //新增
      showAdd(){
        this.showModal=!this.showModal;
      },

      cancel(){
        this.showModal=false;
      },

      reFresh(){
        this.reload();
      }




    },

  }

</script>
