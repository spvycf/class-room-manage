<template>
  <div class="modal-backdrop">
    <div class="modal" >
      <div class="modal-header">
        <h3>{{tt}}</h3>
      </div>
      <div class="modal-body">
        <el-form  ref="addBuildingForm" :model="addBuildingForm" label-width="100px" @submit.native.prevent >


          <input v-model="addBuildingForm.id" v-show="false"></input>



          <el-form-item label="教学楼编号" prop="buildingNo"
                        :rules="[
      { required: true, message: '教学楼编号不能为空'}
    ]"
          >
            <el-input v-model="addBuildingForm.buildingNo"
                      maxlength="20"
                      show-word-limit
            ></el-input>
          </el-form-item>

          <el-form-item label="教学楼名称" prop="buildingName"
                        :rules="[
      { required: true, message: '教学楼名称不能为空'}
    ]"
          >
            <el-input v-model="addBuildingForm.buildingName"
                      maxlength="20"
                      show-word-limit
            ></el-input>
          </el-form-item>




        </el-form>



      </div>
      <div class="modal-footer">
        <button type="button" class="btn-close" @click="cancel">取消</button>
        <button type="button" class="btn-confirm" @click="save">提交</button>
      </div>
    </div>

  </div>
</template>



<script>
  import {saveBuildingUrl} from '@/api/api';
  import {updateBuildingUrl} from '@/api/api';
  import {getbuildingTypeUrl} from "../../api/api";


  export default {
    name: 'AddBuildingType',
    props: {
    },
    data() {
      return {
        tt:'新增教学楼',
        addBuildingForm:{
          id:'',
          buildingNo:'',
          buildingName:'',

        }
      }
    },
    methods: {
      cancel() {
        this.addBuildingForm.buildingNo="";
        this.addBuildingForm.buildingName="";
        this.$emit('on-cancel-add');
      },
      save(){
        let updateId = this.addBuildingForm.id;
        if (Object.keys(updateId).length==0){
          //新增
          saveBuildingUrl(this.addBuildingForm)
            .then(res=>{
              this.$message.success({
                message: '保存成功',
                center:true,
              });
              this.cancel();
              this.$parent.reFresh();
            });

        }else {
          //编辑
          updateBuildingUrl(this.addBuildingForm)
            .then(res=>{
              this.$message.success({
                message: '修改成功',
                center:true,
              });
              this.cancel();
              this.$parent.reFresh();
            });


        }

      },
      //get
      getBuilding(id){
        this.tt='编辑教学楼',
          getbuildingTypeUrl({'id':id})
            .then(res=>{
              this.addBuildingForm.id=res.data.id;
              this.addBuildingForm.buildingNo=res.data.buildingNo;
              this.addBuildingForm.buildingName=res.data.buildingName;
              this.$parent.showAdd();

            });




      },


    }
  }
</script>


<style>
  .modal-backdrop {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(0,0,0,.3);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 100;
  }
  .modal {
    background-color: #fff;
    box-shadow: 2px 2px 20px 1px;
    overflow-x:auto;
    display: flex;
    flex-direction: column;
    border-radius: 16px;
    width: 700px;
  }
  .modal-header {
    border-bottom: 1px solid #eee;
    color: #313131;
    justify-content: center;
    padding: 15px;
    display: flex;
  }
  .modal-footer {
    border-top: 1px solid #eee;
    justify-content: flex-end;
    padding: 15px;
    display: flex;
  }
  .modal-body {
    position: relative;
    padding: 20px 10px;
  }
  .btn-close, .btn-confirm {
    border-radius: 8px;
    margin-left:16px;
    width:56px;
    height: 36px;
    border:none;
    cursor: pointer;
  }
  .btn-close {
    color: #313131;
    background-color:transparent;
  }
  .btn-confirm {
    color: #fff;
    background-color: #2d8cf0;
  }


</style>



