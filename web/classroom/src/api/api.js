import { get, post } from './index';

//登录
export const loginUrl = p => post('/api/user/login', p);

//注册
export const registerUrl = p => post('/api/user/register', p);

//公告
export const listNoticeUrl =(page,row) => post('/api/notice/list/'+page+'/'+row,'{}');
export const deleteNoticeUrl =p => post('/api/notice/delete',p);
export const saveNoticeUrl =p => post('/api/notice/save',p);
export const updateNoticeUrl =p => post('/api/notice/update',p);
export const getNoticeUrl =p => post('/api/notice/get',p);

//用户
export const listUserUrl =(page,row,p) => post('/api/user/list/'+page+'/'+row,p);
export const changeStatusUrl =(p) => post('/api/user/changeStatus',p);
export const getUserUrl =(p) => post('/api/user/get',p);
export const updateUserUrl =(p) => post('/api/user/update',p);

//教学楼
export const listbuildingTypeUrl =(page,row,p) => post('/api/buildingType/list/'+page+'/'+row,p);
export const changebuildingTypeStatusUrl =(p) => post('/api/buildingType/delete',p);
export const getbuildingTypeUrl =(p) => post('/api/buildingType/get',p);
export const updateBuildingUrl =(p) => post('/api/buildingType/update',p);
export const saveBuildingUrl =(p) => post('/api/buildingType/save',p);

//教室
export const listRoomUrl =(page,row,p) => post('/api/buildingRoom/list/'+page+'/'+row,p);
export const saveRoomUrl =(p) => post('/api/buildingRoom/save',p);
export const updateRoomUrl =(p) => post('/api/buildingRoom/update',p);
export const deleteRoomUrl =(p) => post('/api/buildingRoom/delete',p);
export const getRoomUrl =(p) => post('/api/buildingRoom/get',p);


//课程
export const listCourseUrl =(p) => post('/api/course/list/',p);






