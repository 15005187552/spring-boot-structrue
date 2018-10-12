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
 * 报税数据专项附加扣除表
 * </p>
 *
 * @author Levis
 * @since 2018-10-11
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_tax_special_add`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "报税数据专项附加扣除表", subTypes = {TaxSpecialAdd.class})
public class TaxSpecialAdd implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "报税数据专项附加扣除")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "报税数据ID")
    @TableField("`TAX_ID`")
    private Long taxId;

    @ApiModelProperty(value = "专项附加扣除分类ID")
    @TableField("`SPECIAL_ADD_ID`")
    private Long specialAddId;

    @ApiModelProperty(value = "个人缴纳金额")
    @TableField("`TAX_MONEY`")
    private BigDecimal taxMoney;

    @ApiModelProperty(value = "缴纳证明附件路径")
    @TableField("`TAX_DOC_PATH`")
    private String taxDocPath;

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

    public static final String SPECIAL_ADD_ID = "`SPECIAL_ADD_ID`";

    public static final String TAX_MONEY = "`TAX_MONEY`";

    public static final String TAX_DOC_PATH = "`TAX_DOC_PATH`";

    public static final String CREATE_TIME = "`CREATE_TIME`";

    public static final String UPDATE_TIME = "`UPDATE_TIME`";

    public static final String UPDATER = "`UPDATER`";

}
