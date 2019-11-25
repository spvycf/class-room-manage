package com.yaoqun.classroom.config;

import com.yaoqun.classroom.common.ResultCode;
import com.yaoqun.classroom.common.ResultException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.REException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author doger.wang
 * @date 2019/11/25 15:54
 */
@ControllerAdvice
public class GlobalException {



        @ResponseBody
        @ExceptionHandler(ResultException.class)
        public Object handleException(ResultException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", ResultCode.FAIL);
            map.put("message", e.getMessage());
            return map;
        }


}
