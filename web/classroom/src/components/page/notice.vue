<template>
  <div>
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
        width="300px"
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
        width="300px"
        align="center">
        <template slot-scope="scope">
          <el-button
            @click.native.prevent="handleSee(scope.$index, scope.row)"
            type="text"
            size="small">
            查看详情
          </el-button>
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>

<script>
  import {listNoticeUrl} from '@/api/api';
  import AddNotice from "../modal/addNotice";
  import ShowNotice from "../modal/showNotice";
  export default {
    components: {ShowNotice},

    name:'notice',
    data(){
      return{
        showNoticeModal:false,
        tableData:[]
      }

    },
    comments:{
      'ShowNotice':ShowNotice,
    },
    created() {

      listNoticeUrl(1,10)
      .then(res=>{
        let data = res.data.records;
        this.tableData=data;
        console.log(data);





      })

    },
    methods:{
      handleSee(index,row){
        //console.log(row.id);
        //请求详情
        //this.$refs.addNotice.getNotice(row.id);
        this.$refs.showNotice.getNotice(row.id);

      },

      openShow(){
        this.showNoticeModal=true;
      },
      closeNotice(){
        this.showNoticeModal=false;
      },
    },

  }

</script>
