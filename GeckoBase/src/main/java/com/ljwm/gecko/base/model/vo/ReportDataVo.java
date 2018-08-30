package com.ljwm.gecko.base.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ReportDataVo {

  private List<IncomeTypeVo> incomeTypeVoList;

  private List<AddSpecialVo> addSpecialVoList;

  private List<SpecialDeductionVo> specialDeductionVoList;

  private List<OtherReduceVo> otherReduceVoList;
}
