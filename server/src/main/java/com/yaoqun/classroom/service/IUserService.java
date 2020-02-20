package com.yaoqun.classroom.service;

import com.yaoqun.classroom.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IUserService extends IService<User> {

    Object login(User user);

    Object register(User user);

    Object updateUser(User user);

    Object listUsers(int page, int row, User user);

    Object changeStatus(User user);

    Object getUser(User user);

    String checkUserTpye(String uId);
}
