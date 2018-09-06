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
 * 报税数据专项扣除表
 * </p>
 *
 * @author Levis
 * @since 2018-09-06
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_tax_special`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "报税数据专项扣除表", subTypes = {TaxSpecial.class})
public class TaxSpecial implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "报税数据专项扣除表")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "报税数据ID")
    @TableField("`TAX_ID`")
    private Long taxId;

    @ApiModelProperty(value = "专项扣除分类ID")
    @TableField("`SPECIAL_DEDU_ID`")
    private Long specialDeduId;

    @ApiModelProperty(value = "个人缴纳金额")
    @TableField("`PERSONAL_MONEY`")
    private String personalMoney;

    @ApiModelProperty(value = "单位纳税金额")
    @TableField("`COMPANY_MONEY`")
    private String companyMoney;

    @ApiModelProperty(value = "个人缴纳比例")
    @TableField("`PERSONAL_PERCENT`")
    private String personalPercent;

    @ApiModelProperty(value = "单位缴纳比例")
    @TableField("`COMPANY_PERCENT`")
    private String companyPercent;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField("`UPDATER`")
    private Long updater;


    public static final String ID = "`ID`";

    public static final String TAX_ID = "`TAX_ID`";

    public static final String SPECIAL_DEDU_ID = "`SPECIAL_DEDU_ID`";

    public static final String PERSONAL_MONEY = "`PERSONAL_MONEY`";

    public static final String COMPANY_MONEY = "`COMPANY_MONEY`";

    public static final String PERSONAL_PERCENT = "`PERSONAL_PERCENT`";

    public static final String COMPANY_PERCENT = "`COMPANY_PERCENT`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String UPDATER = "`UPDATER`";

}
