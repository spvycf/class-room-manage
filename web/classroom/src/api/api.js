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


