import { get, post } from './index';

//登录
export const loginUrl = p => post('/api/user/login', p);

//注册
export const registerUrl = p => post('/api/user/register', p);
