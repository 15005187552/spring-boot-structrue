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
 * @author Levis
 * @since 2018-10-08
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_order_pay_info`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {OrderPayInfo.class})
public class OrderPayInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "会员id")
    @TableField("`MEMBER_ID`")
    private Long memberId;

    @ApiModelProperty(value = "订单编号")
    @TableField("`ORDER_NO`")
    private String orderNo;

    @ApiModelProperty(value = "微信订单号")
    @TableField("`WX_ORDER_NUM`")
    private String wxOrderNum;

    @ApiModelProperty(value = "支付状态  0  未支付  1  已支付")
    @TableField("`STATUS`")
    private Integer status;

    @ApiModelProperty(value = "类型  0  首付款   1 尾款")
    @TableField("`TYPE`")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "支付金额")
    @TableField("`PAY_AMOUNT`")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "支付时间")
    @TableField("`PAYMENT_TIME`")
    private Date paymentTime;


    public static final String ID = "`ID`";

    public static final String MEMBER_ID = "`MEMBER_ID`";

    public static final String ORDER_NO = "`ORDER_NO`";

    public static final String WX_ORDER_NUM = "`WX_ORDER_NUM`";

    public static final String STATUS = "`STATUS`";

    public static final String TYPE = "`TYPE`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String PAY_AMOUNT = "`PAY_AMOUNT`";

}
