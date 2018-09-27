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
 * @author Livis
 * @since 2018-09-26
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_spec_services_price`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {SpecServicesPrice.class})
public class SpecServicesPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "`ID`", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "规格键名")
    @TableField("`KEY`")
    private String key;

    @ApiModelProperty(value = "规格键名中文")
    @TableField("`KEY_NAME`")
    private String keyName;

    @ApiModelProperty(value = "价格")
    @TableField("`PRICE`")
    private BigDecimal price;

    @ApiModelProperty(value = "首付比率")
    @TableField("`DOWN_PAYMENT_RATE`")
    private BigDecimal downPaymentRate;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "服务商id")
    @TableField("`PROVIDER_ID`")
    private Long providerId;

    @ApiModelProperty(value = "是否启用   0  启用  1 不启用")
    @TableField("`DISABLED`")
    private Integer disabled;

    @ApiModelProperty(value = "商品明细id")
    @TableField("`GOOD_ID`")
    private Long goodId;


    public static final String ID = "`ID`";

    public static final String KEY = "`KEY`";

    public static final String KEY_NAME = "`KEY_NAME`";

    public static final String PRICE = "`PRICE`";

    public static final String DOWN_PAYMENT_RATE = "`DOWN_PAYMENT_RATE`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String PROVIDER_ID = "`PROVIDER_ID`";

    public static final String DISABLED = "`DISABLED`";

    public static final String GOOD_ID = "`GOOD_ID`";

}
