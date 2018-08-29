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
 * 其它扣除减免分类表
 * </p>
 *
 * @author Levis
 * @since 2018-08-29
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_other_reduce`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "其它扣除减免分类表", subTypes = {OtherReduce.class})
public class OtherReduce implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "其它扣除减免分类")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分类名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "排序")
    @TableField("`SORT`")
    private String sort;

    @ApiModelProperty(value = "描述")
    @TableField("`DESCRIPTION`")
    private String description;


    public static final String ID = "`ID`";

    public static final String NAME = "`NAME`";

    public static final String SORT = "`SORT`";

    public static final String DESCRIPTION = "`DESCRIPTION`";

}
