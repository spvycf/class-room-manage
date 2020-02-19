package com.yaoqun.classroom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author doger.wang
 * @date 2019/12/14 10:32
 */
@Data
public class CourseArrangeDTO implements Comparable<CourseArrangeDTO>, Serializable {

    private static final long serialVersionUID = -6078778226379468841L;

    /**
     * id
     */

    private String id;

    /**
     * 教室id
     */
    @TableField("roomId")

    private String roomId;

    /**
     * 课程或预约名称
     */
    @TableField("courseName")

    private String courseName;

    /**
     * 0每天1每周一.....234567每周日8临时预约
     */

    private String type;

    /**
     * 日期，每周每天则为空
     */

    private LocalDate date;

    /**
     * 开始时间
     */
    @TableField("startTime")

    private LocalTime startTime;

    /**
     * 结束时间
     */
    @TableField("endTime")

    private LocalTime endTime;

    /**
     * 所属人
     */
    @TableField("userId")

    private String userId;

    /**
     * 0正常1失效
     */

    private String status;

    private String roomNo;
    private String buildingNo;
    private String buildingName;
    private String userName;
    private String profession;
    private String classNO;
    private String phone;
    private String uType;

    @Override
    public int compareTo(CourseArrangeDTO o) {
        return this.startTime.isBefore(o.getStartTime())==true?-1:1;
    }
}
