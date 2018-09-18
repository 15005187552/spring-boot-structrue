package com.ljwm.gecko.client.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CompanySpecialForm{
  @ApiModelProperty(value = "公司专项扣除表ID")
  @TableId(value = "`ID`", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty(value = "专项扣除ID")
  @TableField("`SPECIAL_ID`")
  private Long specialId;

  @ApiModelProperty(value = "公司扣除百分比")
  @TableField("`COMPANY_PER`")
  private BigDecimal companyPer;

  @ApiModelProperty(value = "个人扣除百分比")
  @TableField("`PERSON_PER`")
  private BigDecimal personPer;
}
