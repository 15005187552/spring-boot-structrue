package com.ljwm.gecko.admin.model.vo;

import com.ljwm.gecko.base.entity.AddSpecial;
import com.ljwm.gecko.base.entity.TaxSpecialAdd;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: xixil
 * Date: 2018/10/16 15:37
 * RUA
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TaxSpecialAddVo extends TaxSpecialAdd {

  private AddSpecial specialAdd;
}
