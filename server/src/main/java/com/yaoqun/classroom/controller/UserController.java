package com.yaoqun.classroom.controller;

import com.yaoqun.classroom.common.Result;
import com.yaoqun.classroom.common.ResultUtil;
import com.yaoqun.classroom.entity.User;
import com.yaoqun.classroom.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author doger.wang
 * @date 2019/11/25 14:24
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        Object object = userService.login(user);
        return ResultUtil.Success("登录成功", object);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        Object object = userService.register(user);
        return ResultUtil.Success("注册成功", object);
    }
}
