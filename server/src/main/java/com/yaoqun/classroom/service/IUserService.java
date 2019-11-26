package com.yaoqun.classroom.service;

import com.yaoqun.classroom.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dogerWang
 * @since 2019-11-26
 */
public interface IUserService extends IService<User> {

    Object login(User user);

    Object register(User user);
}
