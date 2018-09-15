package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 中国行政区划表
 * </p>
 *
 * @author yuzhou
 * @since 2018-08-24
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("t_location")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "中国行政区划表", subTypes = {Location.class})
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "区划代码")
    @TableId(value = "CODE", type = IdType.INPUT)
    private Integer code;

    @ApiModelProperty(value = "区划名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "父级区域代码")
    @TableField("PCODE")
    private Integer pcode;

    @ApiModelProperty(value = "区划级别 省 0 市 1 区县 2")
    @TableField("LEVEL")
    private Integer level;

    @ApiModelProperty(value = "是否禁用")
    @TableField("DISABLED")
    private Boolean disabled;


    public static final String CODE = "CODE";

    public static final String NAME = "`NAME`";

    public static final String PCODE = "PCODE";

    public static final String LEVEL = "LEVEL";

    public static final String DISABLED = "DISABLED";

}
