import { get, post } from './index';

//登录
export const loginUrl = p => post('/api/user/login', p);

//注册
export const registerUrl = p => post('/api/user/register', p);
//列取公告
export const listNoticeUrl =(page,row) => post('/api/notice/list/'+page+'/'+row,'{}');
