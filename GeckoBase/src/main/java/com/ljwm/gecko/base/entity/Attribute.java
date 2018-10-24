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
 * 公司人事模板定制项表
 * </p>
 *
 * @author xixil
 * @since 2018-10-24
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_attribute`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "公司人事模板定制项表", subTypes = {Attribute.class})
public class Attribute implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "自增ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关联哪张表")
    @TableField("`TABLE_NAME`")
    private Integer tableName;

    @ApiModelProperty(value = "关联表的ID")
    @TableField("`ITEM_ID`")
    private Long itemId;

    @ApiModelProperty(value = "名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "排序")
    @TableField("`SORT`")
    private Integer sort;


    public static final String ID = "`ID`";

    public static final String TABLE_NAME = "`TABLE_NAME`";

    public static final String ITEM_ID = "`ITEM_ID`";

    public static final String NAME = "`NAME`";

    public static final String SORT = "`SORT`";

}
