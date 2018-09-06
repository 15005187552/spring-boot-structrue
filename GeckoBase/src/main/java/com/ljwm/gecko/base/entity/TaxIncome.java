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
 * 报税数据收入表
 * </p>
 *
 * @author Levis
 * @since 2018-09-05
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_tax_income`")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "报税数据收入表", subTypes = {TaxIncome.class})
public class TaxIncome implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "报税数据收入")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "报税数据ID")
    @TableField("`TAX_ID`")
    private Long taxId;

    @ApiModelProperty(value = "收入分类ID")
    @TableField("`INCOME_TYPE_ID`")
    private Long incomeTypeId;

    @ApiModelProperty(value = "创建时间")
    @TableField("`CREATE_TIME`")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`UPDATE_TIME`")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField("`UPDATER`")
    private Long updater;

    @ApiModelProperty(value = "收入金额")
    @TableField("`INCOME`")
    private String income;


    public static final String ID = "`ID`";

    public static final String TAX_ID = "`TAX_ID`";

    public static final String INCOME_TYPE_ID = "`INCOME_TYPE_ID`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String UPDATER = "`UPDATER`";

    public static final String INCOME = "`INCOME`";

}
