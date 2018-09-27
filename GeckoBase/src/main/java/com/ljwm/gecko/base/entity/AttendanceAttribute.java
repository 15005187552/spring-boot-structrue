package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 考勤字段表
 * </p>
 *
 * @author xixil
 * @since 2018-09-27
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_attendance_attribute`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "考勤字段表", subTypes = {AttendanceAttribute.class})
public class AttendanceAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "描述")
    @TableField("`DESC`")
    private String desc;

    @ApiModelProperty(value = "是否前台输入 0-不需要 1-需要")
    @TableField("`IS_NEED_ENTER`")
    private Boolean isNeedEnter;
    @TableField("`CREATE_TIME`")
    private Date createTime;
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "禁用/启用")
    @TableField("`DISABLED`")
    private Boolean disabled;

    @ApiModelProperty(value = "排序")
    @TableField("`SORT`")
    private Integer sort;


    public static final String ID = "`ID`";

    public static final String NAME = "`NAME`";

    public static final String DESC = "`DESC`";

    public static final String IS_NEED_ENTER = "`IS_NEED_ENTER`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String DISABLED = "`DISABLED`";

    public static final String SORT = "`SORT`";

}
