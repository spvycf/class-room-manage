package com.yaoqun.classroom.controller;

import com.yaoqun.classroom.common.Result;
import com.yaoqun.classroom.common.ResultCode;
import com.yaoqun.classroom.common.ResultException;
import com.yaoqun.classroom.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author doger.wang
 * @date 2019/11/25 14:24
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public Result listAreasMp() {
        Object object = null;
        throw new ResultException(ResultCode.PARAMER_EXCEPTION,"出错了啊");
        //return ResultUtil.Success("登录成功", object);
    }
}
