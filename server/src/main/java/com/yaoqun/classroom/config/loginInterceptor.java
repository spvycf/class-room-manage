package com.yaoqun.classroom.config;

import com.yaoqun.classroom.common.Constatnt;
import com.yaoqun.classroom.common.ResultCode;
import com.yaoqun.classroom.common.ResultException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author doger.wang
 * @date 2019/12/14 10:46
 */
@Component
public class loginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
/*        String requestURI = request.getRequestURI();
        if (requestURI.contains("login")){
            return true;
        }*/
        String uId = request.getHeader(Constatnt.uId);
        if (StringUtils.isEmpty(uId)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"用户登录信息为空");
        }
        return true;

    }
}
