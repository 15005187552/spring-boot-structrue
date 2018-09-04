package com.ljwm.gecko.admin.model.form;

import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateSaveForm {

  private Long id;

  private Long itemType;

  private Integer regionCode;

  private BigDecimal upperLimit;

  private BigDecimal lowerLimit;

  private BigDecimal companyPer;

  private BigDecimal personPer;

  private Integer perType;

  private Integer comType;

  private Integer sort;
}
