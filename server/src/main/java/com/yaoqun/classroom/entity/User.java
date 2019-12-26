package com.yaoqun.classroom.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    /**
     * 身份类型
     */

    private String type;

    /**
     * 学号，工号，登录号
     */
    @TableField("loginNo")

    private String loginNo;

    /**
     * 名字
     */
    @TableField("userName")

    private String userName;

    /**
     * 密码md5加密
     */

    private String password;

    /**
     * 性别0女1男
     */

    private String sex;

    /**
     * 年龄
     */

    private Integer age;

    /**
     * 专业
     */

    private String profession;

    /**
     * 班级
     */
    @TableField("classNO")

    private String classNO;

    /**
     * 手机
     */

    private String phone;

    /**
     * 状态0正常1冻结
     */

    private String status;

    @TableField("createTime")
    private LocalDateTime createTime;


    @TableField("updateTime")
    private LocalDateTime updateTime;


}
