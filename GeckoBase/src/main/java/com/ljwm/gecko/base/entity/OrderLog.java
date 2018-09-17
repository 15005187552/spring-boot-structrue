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
 * 订单日志表
 * </p>
 *
 * @author Livis
 * @since 2018-09-17
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_order_log`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "订单日志表", subTypes = {OrderLog.class})
public class OrderLog implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "订单日志ID")
    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "日志内容")
    private String text;

    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    private Date createTime;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作人对应用户，不一定有")
    @TableField("`operator_id`")
    private Long operatorId;

    @ApiModelProperty(value = "操作后状态")
    @TableField("`to_status`")
    private Integer toStatus;

    @ApiModelProperty(value = "对应订单")
    @TableField("`order_id`")
    private String orderId;


    public static final String ID = "`id`";

    public static final String TEXT = "`text`";

    public static final String CREATE_TIME = "`create_time`";

    public static final String OPERATOR = "`operator`";

    public static final String OPERATOR_ID = "`operator_id`";

    public static final String TO_STATUS = "`to_status`";

    public static final String ORDER_ID = "`order_id`";

}
