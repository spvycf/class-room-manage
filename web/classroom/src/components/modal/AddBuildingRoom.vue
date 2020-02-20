<template>
  <div class="modal-backdrop">
    <div class="modal" >
      <div class="modal-header">
        <h3>{{tt}}</h3>
      </div>
      <div class="modal-body">

        <input v-model="buildingId" v-show="false"></input>

        <el-form  ref="addBuildingRoomForm" :model="addBuildingRoomForm" label-width="100px" @submit.native.prevent >


          <input v-model="addBuildingRoomForm.id" v-show="false"></input>




          <el-form-item label="教室编号" prop="roomNO"
                        :rules="[
      { required: true, message: '教室编号不能为空'}
    ]"
          >
            <el-input v-model="addBuildingRoomForm.roomNO"
                      maxlength="20"
                      show-word-limit
            ></el-input>
          </el-form-item>

          <el-form-item label="教室容量" prop="roomSpace"
                        :rules="[
      { required: true, message: '教室名称不能为空'}
    ]"
          >
            <el-input v-model="addBuildingRoomForm.roomSpace" type="number"
                      maxlength="20"
                      show-word-limit
            ></el-input>
          </el-form-item>

          <el-form-item label="多媒体" prop="hasMedia"
                        :rules="[
      { required: true,message: '多媒体情况不能为空'}]"
          >
            <el-select v-model="addBuildingRoomForm.hasMedia" placeholder="请选择">
              <el-option
                v-for="item in types"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
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
 import {saveRoomUrl} from '@/api/api';
 import {getRoomUrl} from '@/api/api';
 import {updateRoomUrl} from '@/api/api';

  export default {

    inject:['reload'],
    name: 'AddBuildingRoom',
    props: [
      "buildingId"
    ],
    data() {
      return {
        types:[
          {
            value:'1',
            label:'有'
          },
          {
            value:'0',
            label:'无'
          },
        ],
        //buildingId:'',
        tt:'新增教室',
        addBuildingRoomForm:{
          id:'',
          buildingId:'',
          roomNO:'',
          roomSpace:'',
          hasMedia:''

        }
      }
    },
    methods: {
      cancel() {
        this.addBuildingRoomForm.roomNO="";
        this.addBuildingRoomForm.roomSpace="";
        this.addBuildingRoomForm.hasMedia="";
        this.$emit('on-cancel-add');
      },
      save(){
        let updateId = this.addBuildingRoomForm.id;
        if (Object.keys(updateId).length==0){
          this.addBuildingRoomForm.buildingId=this.buildingId;
          //新增
          saveRoomUrl(this.addBuildingRoomForm)
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
          updateRoomUrl(this.addBuildingRoomForm)
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
      getRoom(id){
        this.tt='编辑教室',
          getRoomUrl({'id':id})
            .then(res=>{
              this.addBuildingRoomForm.id=res.data.id;
              this.addBuildingRoomForm.roomNO=res.data.roomNO;
              this.addBuildingRoomForm.roomSpace=res.data.roomSpace;
              this.addBuildingRoomForm.hasMedia=res.data.hasMedia;
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



