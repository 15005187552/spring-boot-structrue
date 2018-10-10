package com.ljwm.gecko.base.entity;

import java.math.BigDecimal;
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
 * @author xixil
 * @since 2018-09-28
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
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单号")
    @TableField("`ORDER_NO`")
    private String orderNo;

    @ApiModelProperty(value = "用户id")
    @TableField("`MEMBER_ID`")
    private Long memberId;
    @TableField("`SHIPPING_ID`")
    private Long shippingId;

    @ApiModelProperty(value = "实际付款金额,单位是元,保留两位小数")
    @TableField("`PAYMENT`")
    private BigDecimal payment;

    @ApiModelProperty(value = "支付类型,1-在线支付")
    @TableField("`PAYMENT_TYPE`")
    private Integer paymentType;

    @ApiModelProperty(value = "订单状态:0-已取消,5-待服务定价,10-未付款,20-已付款,50-交易成功,60-交易关闭")
    @TableField("`STATUS`")
    private Integer status;

    @ApiModelProperty(value = "交易完成时间")
    @TableField("`END_TIME`")
    private Date endTime;

    @ApiModelProperty(value = "交易关闭时间")
    @TableField("`CLOSE_TIME`")
    private Date closeTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "首付金额")
    @TableField("`DOWN_PAYMENT_AMOUNT`")
    private BigDecimal downPaymentAmount;

    @ApiModelProperty(value = "剩余金额")
    @TableField("`REMIAN_AMOUNT`")
    private BigDecimal remianAmount;


    public static final String ID = "`ID`";

    public static final String ORDER_NO = "`ORDER_NO`";

    public static final String MEMBER_ID = "`MEMBER_ID`";

    public static final String SHIPPING_ID = "`SHIPPING_ID`";

    public static final String PAYMENT = "`PAYMENT`";

    public static final String PAYMENT_TYPE = "`PAYMENT_TYPE`";

    public static final String STATUS = "`STATUS`";

    public static final String END_TIME = "`END_TIME`";

    public static final String CLOSE_TIME = "`CLOSE_TIME`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String DOWN_PAYMENT_AMOUNT = "`DOWN_PAYMENT_AMOUNT`";

    public static final String REMIAN_AMOUNT = "`REMIAN_AMOUNT`";

}
