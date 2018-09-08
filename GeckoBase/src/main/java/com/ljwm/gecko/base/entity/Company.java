package com.ljwm.gecko.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 入驻企业表
 * </p>
 *
 * @author Levis
 * @since 2018-09-07
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_company`")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "入驻企业表", subTypes = {Company.class})
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "入驻企业")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "企业名称")
    @TableField("`NAME`")
    private String name;

    @ApiModelProperty(value = "企业类型")
    @TableField("`TYPE`")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "认证状态：0：未认证，1：认证通过，2:认证失败")
    @TableField("`VALIDATE_STATE`")
    private Integer validateState;

    @ApiModelProperty(value = "是否禁用")
    @TableField("`DISABLED`")
    private Integer disabled;

    @ApiModelProperty(value = "手机号码")
    @TableField("`PHONE_NUM`")
    private String phoneNum;

    @ApiModelProperty(value = "企业纳税代码")
    @TableField("`CODE`")
    private String code;

    @ApiModelProperty(value = "创建人ID")
    @TableField("`CREATER_ID`")
    private Long createrId;

    @ApiModelProperty(value = "证照图片路径")
    @TableField("`PIC_PATH`")
    private String picPath;

    @ApiModelProperty(value = "审核人ID")
    @TableField("`VALIDATOR_ID`")
    private Long validatorId;

    @ApiModelProperty(value = "审核时间")
    @TableField("`VALIDATE_TIME`")
    private Date validateTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "审核意见")
    @TableField("`VALIDATE_TEXT`")
    private String validateText;

    @ApiModelProperty(value = "省级代码")
    @TableField("`PROV_CODE`")
    private Integer provCode;

    @ApiModelProperty(value = "市级代码")
    @TableField("`CITY_CODE`")
    private Integer cityCode;

    @ApiModelProperty(value = "区级代码")
    @TableField("`AREA_CODE`")
    private Integer areaCode;

    @ApiModelProperty(value = "详细地址")
    @TableField("`ADDRESS`")
    private String address;


    public static final String ID = "`ID`";

    public static final String NAME = "`NAME`";

    public static final String TYPE = "`TYPE`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String VALIDATE_STATE = "`VALIDATE_STATE`";

    public static final String DISABLED = "`DISABLED`";

    public static final String PHONE_NUM = "`PHONE_NUM`";

    public static final String CODE = "`CODE`";

    public static final String CREATER_ID = "`CREATER_ID`";

    public static final String PIC_PATH = "`PIC_PATH`";

    public static final String VALIDATOR_ID = "`VALIDATOR_ID`";

    public static final String VALIDATE_TIME = "`VALIDATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String VALIDATE_TEXT = "`VALIDATE_TEXT`";

    public static final String PROV_CODE = "`PROV_CODE`";

    public static final String CITY_CODE = "`CITY_CODE`";

    public static final String AREA_CODE = "`AREA_CODE`";

    public static final String ADDRESS = "`ADDRESS`";

}
