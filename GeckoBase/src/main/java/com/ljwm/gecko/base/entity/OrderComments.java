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
 * 订单评价表
 * </p>
 *
 * @author Livis
 * @since 2018-09-18
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_order_comments`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "订单评价表", subTypes = {OrderComments.class})
public class OrderComments implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "商品评价ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关联订单ID")
    @TableField("`ORDER_ID`")
    private Long orderId;

    @ApiModelProperty(value = "关联的订单明细ID")
    @TableField("`ORDER_ITEM_ID`")
    private Long orderItemId;

    @ApiModelProperty(value = "评价的用户")
    @TableField("`MEMBER_ID`")
    private Long memberId;

    @ApiModelProperty(value = "用户名/昵称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "评价打分")
    @TableField("`STAR`")
    private Integer star;

    @ApiModelProperty(value = "评价创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;


    public static final String ID = "`ID`";

    public static final String ORDER_ID = "`ORDER_ID`";

    public static final String ORDER_ITEM_ID = "`ORDER_ITEM_ID`";

    public static final String MEMBER_ID = "`MEMBER_ID`";

    public static final String NAME = "`NAME`";

    public static final String STAR = "`STAR`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

}
