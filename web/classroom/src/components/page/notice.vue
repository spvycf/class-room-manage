<template>
  <div>
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
        width="300">
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
  export default {
    name:'notice',
    data(){
      return{
        tableData:[]
      }

    },
    created() {
      listNoticeUrl(1,10)
      .then(res=>{
        let data = res.data.records;
        this.tableData=data;
        console.log(data);





      })

    }

  }

</script>
