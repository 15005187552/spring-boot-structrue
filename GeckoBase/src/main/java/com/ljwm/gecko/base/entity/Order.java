package com.ljwm.gecko.base.entity;

import java.math.BigDecimal;
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
 * 
 * </p>
 *
 * @author Levis
 * @since 2018-09-10
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_order`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {Order.class})
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "订单id")
    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单号")
    @TableField("`order_no`")
    private Long orderNo;

    @ApiModelProperty(value = "用户id")
    @TableField("`member_id`")
    private Long memberId;
    @TableField("`shipping_id`")
    private Long shippingId;

    @ApiModelProperty(value = "实际付款金额,单位是元,保留两位小数")
    private BigDecimal payment;

    @ApiModelProperty(value = "支付类型,1-在线支付")
    @TableField("`payment_type`")
    private Integer paymentType;

    @ApiModelProperty(value = "订单状态:0-已取消,5-待服务定价,10-未付款,20-已付款,50-交易成功,60-交易关闭")
    private Integer status;

    @ApiModelProperty(value = "支付时间")
    @TableField("`payment_time`")
    private Date paymentTime;

    @ApiModelProperty(value = "交易完成时间")
    @TableField("`end_time`")
    private Date endTime;

    @ApiModelProperty(value = "交易关闭时间")
    @TableField("`close_time`")
    private Date closeTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`update_time`")
    private Date updateTime;


    public static final String ID = "`id`";

    public static final String ORDER_NO = "`order_no`";

    public static final String MEMBER_ID = "`member_id`";

    public static final String SHIPPING_ID = "`shipping_id`";

    public static final String PAYMENT = "`payment`";

    public static final String PAYMENT_TYPE = "`payment_type`";

    public static final String STATUS = "`status`";

    public static final String PAYMENT_TIME = "`payment_time`";

    public static final String END_TIME = "`end_time`";

    public static final String CLOSE_TIME = "`close_time`";

    public static final String CREATE_TIME = "`create_time`";

    public static final String UPDATE_TIME = "`update_time`";

}
