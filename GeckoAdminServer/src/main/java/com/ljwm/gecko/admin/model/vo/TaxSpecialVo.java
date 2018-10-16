package com.ljwm.gecko.admin.model.vo;

import com.ljwm.gecko.base.entity.SpecialDeduction;
import com.ljwm.gecko.base.entity.TaxSpecial;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: xixil
 * Date: 2018/10/16 15:36
 * RUA
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TaxSpecialVo extends TaxSpecial {

  private SpecialDeduction specialDeduction;
}
