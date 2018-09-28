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
@TableName("`t_provider_goods`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {ProviderGoods.class})
public class ProviderGoods implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "服务明细id")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "服务id,对应t_service表的主键")
    @TableField("`SERVICE_ID`")
    private Integer serviceId;

    @ApiModelProperty(value = "服务商id")
    @TableField("`PROVIDER_ID`")
    private Long providerId;

    @ApiModelProperty(value = "商品名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "商品副标题")
    @TableField("`SUBTITLE`")
    private String subtitle;

    @ApiModelProperty(value = "产品主图,url相对地址")
    @TableField("`MAIN_IMAGE`")
    private String mainImage;

    @ApiModelProperty(value = "商品详情")
    @TableField("`DETAIL`")
    private String detail;

    @ApiModelProperty(value = "价格,单位-元保留两位小数")
    @TableField("`PRICE`")
    private BigDecimal price;

    @ApiModelProperty(value = "商品状态.1-在售 2-下架 3-删除")
    @TableField("`STATUS`")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "首付比率")
    @TableField("`DOWN_PAYMENT_RATE`")
    private BigDecimal downPaymentRate;

    public static final String ID = "`ID`";

    public static final String SERVICE_ID = "`SERVICE_ID`";

    public static final String PROVIDER_ID = "`PROVIDER_ID`";

    public static final String NAME = "`NAME`";

    public static final String SUBTITLE = "`SUBTITLE`";

    public static final String MAIN_IMAGE = "`MAIN_IMAGE`";

    public static final String DETAIL = "`DETAIL`";

    public static final String PRICE = "`PRICE`";

    public static final String STATUS = "`STATUS`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

}
