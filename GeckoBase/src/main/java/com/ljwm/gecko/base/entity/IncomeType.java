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
 * 收入类型表
 * </p>
 *
 * @author Levis
 * @since 2018-08-29
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_income_type`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "收入类型表", subTypes = {IncomeType.class})
public class IncomeType implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "类别ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分类名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "排序")
    @TableField("`SORT`")
    private String sort;

    @ApiModelProperty(value = "是否前台输入 0-不需要 1-需要")
    @TableField("`IS_NEED_ENTER`")
    private Integer isNeedEnter;

    @ApiModelProperty(value = "父分类ID")
    @TableField("`P_ID`")
    private Long pId;

    @ApiModelProperty(value = "分类描述")
    @TableField("`CLASS_DESC`")
    private String classDesc;

    @ApiModelProperty(value = "级别 0-一级 1-二级")
    @TableField("`LEVEL`")
    private Integer level;


    public static final String ID = "`ID`";

    public static final String NAME = "`NAME`";

    public static final String SORT = "`SORT`";

    public static final String IS_NEED_ENTER = "`IS_NEED_ENTER`";

    public static final String P_ID = "`P_ID`";

    public static final String CLASS_DESC = "`CLASS_DESC`";

    public static final String LEVEL = "`LEVEL`";

}
