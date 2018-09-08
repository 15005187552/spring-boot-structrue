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
 * 个税计算表
 * </p>
 *
 * @author Levis
 * @since 2018-09-08
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_cal_tax`")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "个税计算表", subTypes = {CalTax.class})
public class CalTax implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "游客ID")
    @TableId(value = "`GUEST_ID`", type = IdType.INPUT)
    private String guestId;

    @ApiModelProperty(value = "城市编码")
    @TableField("`REGION_CODE`")
    private Integer regionCode;

    @ApiModelProperty(value = "税前收入")
    @TableField("`MONEY`")
    private BigDecimal money;

    @ApiModelProperty(value = "老个税扣除额")
    @TableField("`OLD_TAX`")
    private BigDecimal oldTax;

    @ApiModelProperty(value = "新个税扣除额")
    @TableField("`NEW_TAX`")
    private BigDecimal newTax;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;


    public static final String GUEST_ID = "`GUEST_ID`";

    public static final String REGION_CODE = "`REGION_CODE`";

    public static final String MONEY = "`MONEY`";

    public static final String OLD_TAX = "`OLD_TAX`";

    public static final String NEW_TAX = "`NEW_TAX`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

}
