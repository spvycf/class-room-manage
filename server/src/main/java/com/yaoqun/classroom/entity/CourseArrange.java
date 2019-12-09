package com.yaoqun.classroom.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("course_arrange")
public class CourseArrange implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 开始时间
     */
    @TableField("startTime")

    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("endTime")

    private LocalDateTime endTime;

    /**
     * 所属人
     */
    @TableField("userId")

    private String userId;

    /**
     * 0正常1失效
     */

    private String status;


}
