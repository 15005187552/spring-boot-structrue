package com.ljwm.gecko.admin.model.vo;

import com.ljwm.gecko.base.entity.IncomeType;
import com.ljwm.gecko.base.entity.TaxIncome;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: xixil
 * Date: 2018/10/16 15:35
 * RUA
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TaxIncomeVo extends TaxIncome {

  private IncomeType incomeType;
}
