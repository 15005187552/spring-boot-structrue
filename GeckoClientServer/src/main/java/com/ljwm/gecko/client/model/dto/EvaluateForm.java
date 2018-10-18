package com.ljwm.gecko.client.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Janiffy
 * @date 2018/10/18 13:42
 */
@Data
public class EvaluateForm {

  private List<IncomeDto> incomeList;

  private List<IncomeDto> specialList;

  private List<IncomeDto> specialAddList;

  @Data
  public class IncomeDto{

    private Long id;

    private BigDecimal money;
  }
}
