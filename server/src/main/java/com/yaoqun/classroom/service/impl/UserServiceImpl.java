package com.yaoqun.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaoqun.classroom.common.ResultCode;
import com.yaoqun.classroom.common.ResultException;
import com.yaoqun.classroom.entity.User;
import com.yaoqun.classroom.mapper.UserMapper;
import com.yaoqun.classroom.service.IUserService;
import com.yaoqun.classroom.util.CommonUtils;
import com.yaoqun.classroom.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.zip.CheckedInputStream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dogerWang
 * @since 2019-11-26
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Object login(User user) {
        String loginNo = user.getLoginNo();
        String password = user.getPassword();
        if (StringUtils.isEmpty(loginNo)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"登录账号为空");
        }
        if (StringUtils.isEmpty(password)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"密码为空");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getLoginNo,loginNo);
        User one = getOne(wrapper);
        if (null==one){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"用户不存在");
        }
        password= MD5Util.getMD5ofStr(password,1);
        if (!password.equals(one.getPassword())){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"密码错误");
        }
        if (!"0".equals(one.getStatus())){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"用户已被冻结");
        }
        return one;



    }

    @Override
    public Object register(User user) {
        String uuid = IdWorker.get32UUID();
        String loginNo = user.getLoginNo();
        String password = user.getPassword();
        Integer age = user.getAge();
        String classNO = user.getClassNO();
        String phone = user.getPhone();
        String profession = user.getProfession();
        String sex = user.getSex();
        String type = user.getType();
        String userName = user.getUserName();
        if (StringUtils.isEmpty(loginNo)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"登录账号为空");
        }
        if (StringUtils.isEmpty(password)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"密码为空");
        }
        if (StringUtils.isEmpty(type)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"注册类型为空");
        }
        if (StringUtils.isEmpty(userName)){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"用户名称为空");
        }
        checkLoginNo(loginNo);
        LocalDateTime now = LocalDateTime.now();
        User save = new User();
        save.setId(uuid);
        save.setType(type);
        save.setLoginNo(loginNo);
        save.setUserName(userName);
        save.setPassword(MD5Util.getMD5ofStr(password,1));
        save.setSex(sex);
        save.setAge(age);
        save.setProfession(profession);
        save.setClassNO(classNO);
        save.setPhone(phone);
        save.setStatus("0");
        save.setCreateTime(now);
        save.setUpdateTime(now);
        return this.save(save);

    }

    private void checkLoginNo(String loginNo) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getLoginNo,loginNo);
        User one = getOne(wrapper);
        if (null!=one){
            throw new ResultException(ResultCode.PARAMER_EXCEPTION,"登录账号已存在");
        }
    }

    public static void main(String[] args) {
        CommonUtils.listSet(User.class,"save");
    }
}
