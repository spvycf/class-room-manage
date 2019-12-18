<template>
  <div class="left-nav">
    <ul>
      <li>
        <div>通知</div>
      </li>

      <li>
        <div>课程信息</div>
      </li>

      <li>
        <div>我的预约</div>
      </li>

      <li>
        <div>个人信息</div>
      </li>



      <li>

        <div>统计</div>
      </li>


    </ul>
  </div>
</template>

<script>
  export default {

    data () {
      return {

      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .left-nav{
    color:#fff;
    font-size:20px;
    height:100%;
    background-color: #1D8ce0;
    float:left;
    width:15%;
  }
  .iconfont{
    font-size:24px;
  }
  .left-nav ul{
    padding:0px;
    margin: 0px;
  }
  .left-nav li{
    list-style: none;
    text-align: center;
    border-bottom:1px solid #20a0ff;
    padding:10px;
  }
</style>
