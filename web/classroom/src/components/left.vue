<template>



    <el-aside  width="200px" style="background-color: rgb(228, 228, 228)">
    <el-menu default-active="1">
      <el-menu-item index="/notice" router="true" >
        <i class="el-icon-news">
          <span slot="title">公告</span>
        </i>
      </el-menu-item>




        <el-submenu index="2">
        <template slot="title"><i class="el-icon-menu"></i>管理中心</template>

          <el-submenu>
            <template slot="title"><i class="el-icon-school"></i>教室管理</template>
            <el-menu-item index="2-1"><i class="el-icon-office-building"></i>教学楼管理</el-menu-item>
            <el-menu-item index="2-1"><i class="el-icon-document-copy"></i>课程管理</el-menu-item>
          </el-submenu>

          <el-menu-item index="2-1"><i class="el-icon-user-solid"></i>人员管理</el-menu-item>
          <el-menu-item index="2-1"><i class="el-icon-info"></i> 公告管理</el-menu-item>
      </el-submenu>

        <el-menu-item index="/" router="true" >
          <i class="el-icon-news">
            <span slot="title">借还审批</span>
          </i>
        </el-menu-item>



    </el-menu>
  </el-aside>






</template>
<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  .el-aside {
    color: #333;
  }
</style>

<script>
  export default {
    data() {
      const item = {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      };
      return {
        tableData: Array(20).fill(item)
      }
    }
  };
</script>
