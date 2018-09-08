package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 服务分类表
 * </p>
 *
 * @author Levis
 * @since 2018-09-08
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_service`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "服务分类表", subTypes = {Service.class})
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "服务分类名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "父级服务分类ID")
    @TableField("`PID`")
    private Integer pid;

    @ApiModelProperty(value = "分类级别 一级 0 二级 1 ")
    @TableField("`LEVEL`")
    private Integer level;

    @ApiModelProperty(value = "是否禁用")
    @TableField("`DISABLED`")
    private Boolean disabled;


    public static final String ID = "`ID`";

    public static final String NAME = "`NAME`";

    public static final String PID = "`PID`";

    public static final String LEVEL = "`LEVEL`";

    public static final String DISABLED = "`DISABLED`";

}
