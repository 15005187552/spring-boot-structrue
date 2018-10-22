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
 * 系统自定义协议表
 * </p>
 *
 * @author xixil
 * @since 2018-10-22
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_protocol`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "系统自定义协议表", subTypes = {Protocol.class})
public class Protocol implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "协议代码")
    @TableField("`CODE`")
    private String code;

    @ApiModelProperty(value = "协议名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "协议一句话描述")
    @TableField("`DESCRIPTION`")
    private String description;

    @ApiModelProperty(value = "协议主题内容")
    @TableField("`CONTENT`")
    private String content;

    @ApiModelProperty(value = "禁用/启用")
    @TableField("`DISABLED`")
    private Boolean disabled;


    public static final String ID = "`ID`";

    public static final String CODE = "`CODE`";

    public static final String NAME = "`NAME`";

    public static final String DESCRIPTION = "`DESCRIPTION`";

    public static final String CONTENT = "`CONTENT`";

    public static final String DISABLED = "`DISABLED`";

}
