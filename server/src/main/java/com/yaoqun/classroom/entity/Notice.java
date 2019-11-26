package com.yaoqun.classroom.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author dogerWang
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    /**
     * 标题
     */

    private String title;

    /**
     * 内容
     */

    private String content;

    @TableField("createTime")

    private LocalDateTime createTime;

    /**
     * 0正常1删除
     */

    private String status;


}
