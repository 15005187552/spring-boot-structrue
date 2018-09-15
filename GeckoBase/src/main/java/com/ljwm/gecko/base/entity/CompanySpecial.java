package com.ljwm.gecko.base.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2018-09-15
 */
@Data
@SuppressWarnings("ALL")
@Accessors(chain = true)
@TableName("`t_company_special`")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "", subTypes = {CompanySpecial.class})
public class CompanySpecial implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "公司专项扣除表ID")
    @TableId(value = "`ID`", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "公司ID")
    @TableField("`COMPANY_ID`")
    private Long companyId;

    @ApiModelProperty(value = "专项扣除ID")
    @TableField("`SPECIAL_ID`")
    private Long specialId;

    @ApiModelProperty(value = "公司扣除百分比")
    @TableField("`COMPANY_PER`")
    private BigDecimal companyPer;

    @ApiModelProperty(value = "个人扣除百分比")
    @TableField("`PERSON_PER`")
    private BigDecimal personPer;


    public static final String ID = "`ID`";

    public static final String COMPANY_ID = "`COMPANY_ID`";

    public static final String SPECIAL_ID = "`SPECIAL_ID`";

    public static final String COMPANY_PER = "`COMPANY_PER`";

    public static final String PERSON_PER = "`PERSON_PER`";

}
