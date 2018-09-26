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
 * 
 * </p>
 *
 * @author Livis
 * @since 2018-09-26
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_spec_item`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {SpecItem.class})
public class SpecItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "规格类型id")
    @TableField("`SPEC_ID`")
    private Integer specId;

    @ApiModelProperty(value = "规格项")
    @TableField("`ITEM`")
    private String item;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "排序")
    @TableField("`SORT`")
    private Integer sort;


    public static final String ID = "`ID`";

    public static final String SPEC_ID = "`SPEC_ID`";

    public static final String ITEM = "`ITEM`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String SORT = "`SORT`";

}
