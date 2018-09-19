package com.ljwm.gecko.base.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Janiffy
 * @date 2018/9/15 16:59
 */
@Data
public class CompanySpecialVo {

  @ApiModelProperty(value = "公司专项扣除表ID")
  private Long id;

  @ApiModelProperty(value = "专项扣除ID")
  private Long specialId;

  @ApiModelProperty(value = "公司扣除百分比")
  private BigDecimal companyPer;

  @ApiModelProperty(value = "个人扣除百分比")
  private BigDecimal personPer;
}