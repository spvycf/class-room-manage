package com.yaoqun.classroom.controller;

import com.yaoqun.classroom.common.Result;
import com.yaoqun.classroom.common.ResultUtil;
import com.yaoqun.classroom.entity.User;
import com.yaoqun.classroom.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        Object object = userService.updateUser(user);
        return ResultUtil.Success("修改成功", object);
    }

    @PostMapping("/list/{page}/{row}")
    public Result list(@PathVariable("page")int page,@PathVariable("row")int row, @RequestBody User user) {
        Object object = userService.listUsers(page,row,user);
        return ResultUtil.Success("查询成功", object);
    }
}
