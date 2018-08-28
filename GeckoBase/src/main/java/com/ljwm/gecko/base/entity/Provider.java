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
 * 服务商表
 * </p>
 *
 * @author yuzhou
 * @since 2018-08-24
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("t_provider")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "服务商表", subTypes = {Provider.class})
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "入驻服务商")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "服务商名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "审核状态 0 待审核 1 审核通过 2 审核失败")
    @TableField("VALIDATE_STATE")
    private Integer validateState;

    @ApiModelProperty(value = "是否禁用")
    @TableField("DISABLED")
    private Boolean disabled;

    @ApiModelProperty(value = "服务商类型(个人：0，机构：1)")
    @TableField("TYPE")
    private Integer type;

    @ApiModelProperty(value = "创建人ID")
    @TableField("CREATER_ID")
    private Long createrId;

    @ApiModelProperty(value = "审核人ID")
    @TableField("VALIDATER_ID")
    private Integer validaterId;

    @ApiModelProperty(value = "审核时间")
    @TableField("VALIDATE_TIME")
    private Date validateTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty(value = "审核描述")
    @TableField("VALIDATE_TEXT")
    private String validateText;

    @ApiModelProperty(value = "省的编码")
    @TableField("PROV_CODE")
    private Integer provCode;

    @ApiModelProperty(value = "市的编码")
    @TableField("CITY_CODE")
    private Integer cityCode;

    @ApiModelProperty(value = "区的编码")
    @TableField("AREA_CODE")
    private Integer areaCode;

    @ApiModelProperty(value = "详细地址")
    @TableField("ADDRESS")
    private String address;


    public static final String ID = "ID";

    public static final String NAME = "NAME";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String VALIDATE_STATE = "VALIDATE_STATE";

    public static final String DISABLED = "DISABLED";

    public static final String TYPE = "TYPE";

    public static final String CREATER_ID = "CREATER_ID";

    public static final String VALIDATER_ID = "VALIDATER_ID";

    public static final String VALIDATE_TIME = "VALIDATE_TIME";

    public static final String UPDATE_TIME = "UPDATE_TIME";

    public static final String VALIDATE_TEXT = "VALIDATE_TEXT";

    public static final String PROV_CODE = "PROV_CODE";

    public static final String CITY_CODE = "CITY_CODE";

    public static final String AREA_CODE = "AREA_CODE";

    public static final String ADDRESS = "ADDRESS";

}