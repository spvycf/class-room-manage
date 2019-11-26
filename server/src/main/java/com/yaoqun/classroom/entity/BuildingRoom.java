package com.yaoqun.classroom.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("building_room")
public class BuildingRoom implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    /**
     * 教学楼简码
     */
    @TableField("buildingNo")

    private String buildingNo;

    /**
     * 教室编号
     */
    @TableField("roomNO")

    private String roomNO;

    /**
     * 容量
     */
    @TableField("roomSpace")

    private Integer roomSpace;

    /**
     * 1有多媒体0没有
     */
    @TableField("hasMedia")

    private String hasMedia;

    /**
     * 0正常1冻结
     */

    private String status;


}
