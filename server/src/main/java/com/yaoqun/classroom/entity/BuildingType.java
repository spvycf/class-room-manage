package com.yaoqun.classroom.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("building_type")
public class BuildingType implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    /**
     * 教学楼代码
     */
    @TableField("buildingNo")

    private String buildingNo;

    /**
     * 教学楼名称
     */
    @TableField("buildingName")

    private String buildingName;

    /**
     * 0正常1冻结
     */

    private String status;

    @TableField(exist = false)
    private int num;


}
