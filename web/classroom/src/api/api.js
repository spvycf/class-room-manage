import { get, post } from './index';

export const loginUrl = p => post('/api/user/login', p);
