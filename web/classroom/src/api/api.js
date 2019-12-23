import { get, post } from './index';

//登录
export const loginUrl = p => post('/api/user/login', p);

//注册
export const registerUrl = p => post('/api/user/register', p);
//列取公告
export const listNoticeUrl =(page,row) => post('/api/notice/list/'+page+'/'+row,'{}');

//列取公告
export const deleteNoticeUrl =p => post('/api/notice/delete',p);
export const saveNoticeUrl =p => post('/api/notice/save',p);
export const updateNoticeUrl =p => post('/api/notice/update',p);
export const getNoticeUrl =p => post('/api/notice/get',p);
