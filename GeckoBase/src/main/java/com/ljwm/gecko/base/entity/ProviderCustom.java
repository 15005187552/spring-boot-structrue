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
@TableName("`t_provider_custom`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {ProviderCustom.class})
public class ProviderCustom implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "服务明细id")
    @TableId(value = "`id`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "服务id,对应t_service表的主键")
    @TableField("`service_id`")
    private Long serviceId;

    @ApiModelProperty(value = "服务商id")
    @TableField("`provider_id`")
    private Long providerId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品副标题")
    private String subtitle;

    @ApiModelProperty(value = "产品主图,url相对地址")
    @TableField("`main_image`")
    private String mainImage;

    @ApiModelProperty(value = "图片地址,json格式,扩展用")
    @TableField("`sub_images`")
    private String subImages;

    @ApiModelProperty(value = "商品详情")
    private String detail;

    @ApiModelProperty(value = "价格,单位-元保留两位小数")
    private BigDecimal price;

    @ApiModelProperty(value = "商品状态.1-在售 2-下架 3-删除")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`update_time`")
    private Date updateTime;


    public static final String ID = "`id`";

    public static final String SERVICE_ID = "`service_id`";

    public static final String PROVIDER_ID = "`provider_id`";

    public static final String NAME = "`name`";

    public static final String SUBTITLE = "`subtitle`";

    public static final String MAIN_IMAGE = "`main_image`";

    public static final String SUB_IMAGES = "`sub_images`";

    public static final String DETAIL = "`detail`";

    public static final String PRICE = "`price`";

    public static final String STATUS = "`status`";

    public static final String CREATE_TIME = "`create_time`";

    public static final String UPDATE_TIME = "`update_time`";

}
