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
@TableName("`t_order_item`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {OrderItem.class})
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "订单子表id")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;
    @TableField("`ORDER_NO`")
    private String orderNo;

    @ApiModelProperty(value = "服务id")
    @TableField("`SERVICE_ID`")
    private Integer serviceId;

    @ApiModelProperty(value = "商品服务明细id")
    @TableField("`GOOD_ID`")
    private Long goodId;

    @ApiModelProperty(value = "生成订单时的商品单价，单位是元,保留两位小数")
    @TableField("`CURRENT_UNIT_PRICE`")
    private BigDecimal currentUnitPrice;

    @ApiModelProperty(value = "商品数量")
    @TableField("`QUANTITY`")
    private Integer quantity;

    @ApiModelProperty(value = "商品总价,单位是元,保留两位小数")
    @TableField("`TOTAL_PRICE`")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "服务订单备注")
    @TableField("`SERVICE_CONTENT`")
    private String serviceContent;
    @TableField("`CREATE_TIME`")
    private Date createTime;
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "子订单号")
    @TableField("`ORDER_ITEM_NO`")
    private String orderItemNo;

    @ApiModelProperty(value = "订单状态:0-已取消,5-待服务定价,10-未付款,20-已付款,50-交易成功,60-交易关闭")
    @TableField("`ORDER_ITEM_STATUS`")
    private Integer orderItemStatus;

    @ApiModelProperty(value = "服务商id")
    @TableField("`PROVIDER_ID`")
    private Long providerId;

    @ApiModelProperty(value = "首付比率")
    @TableField("`DOWN_PAYMENT_RATE`")
    private BigDecimal downPaymentRate;

    @ApiModelProperty(value = "首付金额")
    @TableField("`DOWN_PAYMENT_AMOUNT`")
    private BigDecimal downPaymentAmount;

    @ApiModelProperty(value = "剩余金额")
    @TableField("`REMAIN_AMOUNT`")
    private BigDecimal remainAmount;

    @ApiModelProperty(value = "会员id")
    @TableField("`MEMBER_ID`")
    private Long memberId;


    public static final String ID = "`ID`";

    public static final String ORDER_NO = "`ORDER_NO`";

    public static final String SERVICE_ID = "`SERVICE_ID`";

    public static final String GOOD_ID = "`GOOD_ID`";

    public static final String CURRENT_UNIT_PRICE = "`CURRENT_UNIT_PRICE`";

    public static final String QUANTITY = "`QUANTITY`";

    public static final String TOTAL_PRICE = "`TOTAL_PRICE`";

    public static final String SERVICE_CONTENT = "`SERVICE_CONTENT`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String ORDER_ITEM_NO = "`ORDER_ITEM_NO`";

    public static final String ORDER_ITEM_STATUS = "`ORDER_ITEM_STATUS`";

    public static final String PROVIDER_ID = "`PROVIDER_ID`";

    public static final String DOWN_PAYMENT_RATE = "`DOWN_PAYMENT_RATE`";

    public static final String DOWN_PAYMENT_AMOUNT = "`DOWN_PAYMENT_AMOUNT`";

    public static final String REMAIN_AMOUNT = "`REMAIN_AMOUNT`";

    public static final String MEMBER_ID = "`MEMBER_ID`";

}
