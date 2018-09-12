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
 * @since 2018-09-11
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
    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "会员id")
    @TableField("`member_id`")
    private Long memberId;
    @TableField("`order_no`")
    private String orderNo;

    @ApiModelProperty(value = "服务id")
    @TableField("`service_id`")
    private Integer serviceId;

    @ApiModelProperty(value = "商品服务明细id")
    @TableField("`product_service_id`")
    private Long productServiceId;

    @ApiModelProperty(value = "商品名称")
    @TableField("`service_name`")
    private String serviceName;

    @ApiModelProperty(value = "商品图片地址")
    @TableField("`service_image`")
    private String serviceImage;

    @ApiModelProperty(value = "生成订单时的商品单价，单位是元,保留两位小数")
    @TableField("`current_unit_price`")
    private BigDecimal currentUnitPrice;

    @ApiModelProperty(value = "商品数量")
    private Integer quantity;

    @ApiModelProperty(value = "商品总价,单位是元,保留两位小数")
    @TableField("`total_price`")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "服务订单备注")
    @TableField("`service_content`")
    private String serviceContent;
    @TableField("`create_time`")
    private Date createTime;
    @TableField("`update_time`")
    private Date updateTime;

    @ApiModelProperty(value = "子订单号")
    @TableField("`order_item_no`")
    private String orderItemNo;

    @ApiModelProperty(value = "订单状态:0-已取消,5-待服务定价,10-未付款,20-已付款,50-交易成功,60-交易关闭")
    @TableField("`order_item_status`")
    private Integer orderItemStatus;


    public static final String ID = "`id`";

    public static final String MEMBER_ID = "`member_id`";

    public static final String ORDER_NO = "`order_no`";

    public static final String SERVICE_ID = "`service_id`";

    public static final String PRODUCT_SERVICE_ID = "`product_service_id`";

    public static final String SERVICE_NAME = "`service_name`";

    public static final String SERVICE_IMAGE = "`service_image`";

    public static final String CURRENT_UNIT_PRICE = "`current_unit_price`";

    public static final String QUANTITY = "`quantity`";

    public static final String TOTAL_PRICE = "`total_price`";

    public static final String SERVICE_CONTENT = "`service_content`";

    public static final String CREATE_TIME = "`create_time`";

    public static final String UPDATE_TIME = "`update_time`";

    public static final String ORDER_ITEM_NO = "`order_item_no`";

    public static final String ORDER_ITEM_STATUS = "`order_item_status`";

}
