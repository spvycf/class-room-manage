<template>
  <div>

    <el-row :gutter="10">
      <el-col :span="4"><div class="grid-content bg-purple">
        姓名:
      </div></el-col>
      <el-col :span="4" ><div class="grid-content bg-purple-light">
        <el-input class="heiget-input" v-model="name" placeholder="姓名"></el-input>
      </div></el-col>

      <el-col :span="6" style="padding-left: 115px"><div class="grid-content bg-purple">
        身份类型:
      </div></el-col>
      <el-col :span="4"><div class="grid-content bg-purple-light">
        <el-select v-model="type" placeholder="请选择">
          <el-option
            v-for="item in types"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </div></el-col>

      <el-col :span="4" style="padding-left: 115px"><div class="grid-content bg-purple heightd">
        性别:
      </div></el-col>
      <el-col :span="4" style="padding-left: 40px"><div class="grid-content bg-purple-light">
        <el-select v-model="sex" placeholder="请选择">
          <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </div></el-col>

      <el-col :span="4" style="padding-left: 150px"><div class="grid-content bg-purple heightd">
        手机:
      </div></el-col>
      <el-col :span="4"  style="padding-left: 50px"><div class="grid-content bg-purple-light">
        <el-input class="heiget-input" v-model="phone" placeholder="手机号"type="number" ></el-input>
      </div></el-col>



    </el-row>

    <el-row :gutter="10">
      <el-col :span="4"><div class="grid-content bg-purple">
        班级:
      </div></el-col>
      <el-col :span="4" ><div class="grid-content bg-purple-light">
        <el-input class="heiget-input" v-model="classNo" placeholder="班级"></el-input>
      </div></el-col>

      <el-col :span="4" style="padding-left: 115px">
        <div class="grid-content bg-purple heightd">
        专业:
      </div></el-col>
      <el-col :span="4" style="padding-left: 75px"><div class="grid-content bg-purple-light">
        <el-input class="heiget-input" v-model="profession" placeholder="专业"></el-input>
      </div></el-col>



      <el-col :span="4" style="padding-left: 210px;padding-right: 75px;">
        <el-button type="warning"  @click="cleanSearch" style="height: 36px" >清空</el-button>
      </el-col>

      <el-col :span="4" style="padding-left: 6px">
        <el-button type="primary"  @click="search" style="height: 36px">搜索</el-button>
      </el-col>

    </el-row>




    <el-table
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        fixed
        align="center"
        prop="loginNo"
        label="账号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="userName"
        align="center"
        label="姓名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="type"
        label="身份类型"
        align="center"
        :formatter="typeFormat"
        width="120">
      </el-table-column>
      <el-table-column
        prop="phone"
        label="手机"
        align="center"
        width="150">
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别"
        align="center"
        :formatter="sexFormat"
        width="100">
      </el-table-column>
      <el-table-column
        prop="age"
        label="年龄"
        align="center"
        width="100">
      </el-table-column>
      <el-table-column
        prop="classNO"
        label="班级"
        align="center"
        width="180">
      </el-table-column>
      <el-table-column
        prop="profession"
        label="专业"
        align="center"
        width="180">
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        align="center"
        :formatter="statusFormat"
        width="100">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        width="200">
        <template slot-scope="scope">
          <el-button  @click="handleClick(scope.row)" type="info" size="small">编辑</el-button>
          <el-button type="danger" size="small" v-if="scope.row.status=='0'" @click="handleForbidden(scope.row)">冻结</el-button>
          <el-button type="success" size="small" v-else @click="handleRelease(scope.row)">恢复</el-button>
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
  import AddNotice from "../modal/addNotice";
  import ShowNotice from "../modal/showNotice";
  import {listUserUrl} from '@/api/api';
  import {changeStatusUrl} from '@/api/api';

  export default {
    components: {},
    inject:['reload'],
    name:'userManage',
    data(){
      return{
        types:[
          {
            value:'1',
            label:'教师'
          },
          {
            value:'2',
            label:'学生'
          },
        ],
        sexs:[
          {
            value:'1',
            label:'男'
          },
          {
            value:'0',
            label:'女'
          },
        ],

        name:'',
        type:'',
        phone:'',
        sex:'',
        classNo:'',
        profession:'',
        tableData:[],
        totalNum:0,
        size:0
      }
    },
    comments:{


    },

    created() {
      listUserUrl(1,10,
        {
          'userName':this.name,
          'type':this.type,
          'sex':this.sex,
          'phone':this.phone,
          'classNO':this.classNo,
          'profession':this.profession
        }
      ).then(res=>{
        this.tableData=res.data.records;
        this.totalNum=res.data.total;
        this.size=res.data.size;

      });



    },
    methods:{
      typeFormat(row,coloum){
        return row.type=='1'?'教师':'学生';
      },
      sexFormat(row,coloum){
        return row.sex=='1'?'男':'女';
      },
      statusFormat(row,coloum){
        return row.status=='0'?'正常':'冻结';
      },
      //main
      currentChangeHandle(val){
        listUserUrl(val,10,
          {
            'userName':this.name,
            'type':this.type,
            'sex':this.sex,
            'phone':this.phone,
            'classNO':this.classNo,
            'profession':this.profession
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
        this.name='';
        this.type='';
        this.sex='';
        this.phone='';
        this.classNo='';
        this.profession='';
      },
      search(){
        listUserUrl(1,10,
          {
            'userName':this.name,
            'type':this.type,
            'sex':this.sex,
            'phone':this.phone,
            'classNO':this.classNo,
            'profession':this.profession
          }
        ).then(res=>{
          let data = res.data.records;
          this.tableData=data;
          this.totalNum=res.data.total;
          this.size=res.data.size;
        });
      },
      handleForbidden(row){
        this.$confirm('确定冻结该用户吗?冻结该用户后将无法登陆', '冻结用户', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          changeStatusUrl(
            {
              'id':row.id,
              'status':'1'
            }
          ).then(res => {
            this.$message.success({
              message: '冻结成功',
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
