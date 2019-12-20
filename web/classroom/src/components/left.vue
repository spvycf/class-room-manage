<template>

<div class="left">



    <el-aside  width="200px" style="background-color: rgb(228, 228, 228)">


    <el-menu default-active="/notice" router>
      <el-menu-item index="/notice" >
        <i class="el-icon-news">
        </i>
        <span slot="title">公告中心</span>
      </el-menu-item>

        <el-menu-item index="/course" >
          <i class="el-icon-news">
          </i>
          <span slot="title">教学课程</span>
        </el-menu-item>



      <el-submenu index="1" >
        <template slot="title">
          <i class="el-icon-user-solid"></i>
          <span slot="title">个人中心</span>
        </template>


        <el-menu-item index="/user"><i class="el-icon-user"></i>个人信息</el-menu-item>
        <el-menu-item index="/my"><i class="el-icon-reading"></i>我的申请</el-menu-item>
      </el-submenu>


        <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">管理中心</span>
          </template>

          <el-submenu  index="3">
            <template slot="title"><i class="el-icon-school"></i>教室管理</template>
            <el-menu-item index="/buildingManage" ><i class="el-icon-office-building"></i>教学楼管理</el-menu-item>
            <el-menu-item index="/courseManage" ><i class="el-icon-document-copy"></i>课程管理</el-menu-item>
          </el-submenu>

          <el-menu-item index="/userManage" ><i class="el-icon-user-solid"></i>人员管理</el-menu-item>
          <el-menu-item index="/noticeManage" ><i class="el-icon-info"></i>公告管理</el-menu-item>
      </el-submenu>



      <el-menu-item index="/process">
        <i class="el-icon-s-grid"></i>
        <span slot="title">借还审批</span>
      </el-menu-item>



    </el-menu>
  </el-aside>



</div>


</template>
<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  .el-aside {
    color: #333;
    height: 100%;
  }
  .left{
    height: 100%;





  }
</style>

<script>
  export default {

  };
</script>
