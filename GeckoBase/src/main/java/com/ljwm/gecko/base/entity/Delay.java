package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 延迟事件表
 * </p>
 *
 * @author Levis
 * @since 2018-10-19
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_delay`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "延迟事件表", subTypes = {Delay.class})
public class Delay implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "延迟事件的ID")
    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "延迟事件的类型")
    private Integer type;

    @ApiModelProperty(value = "目标源ID")
    @TableField("`target_id`")
    private Long targetId;

    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    private Date createTime;

    @ApiModelProperty(value = "下次触法时间")
    @TableField("`next_time`")
    private Date nextTime;

    @ApiModelProperty(value = "已经发送了的次数")
    private Integer times;

    @ApiModelProperty(value = "状态")
    private Integer status;


    public static final String ID = "`id`";

    public static final String TYPE = "`type`";

    public static final String TARGET_ID = "`target_id`";

    public static final String CREATE_TIME = "`create_time`";

    public static final String NEXT_TIME = "`next_time`";

    public static final String TIMES = "`times`";

    public static final String STATUS = "`status`";

}
