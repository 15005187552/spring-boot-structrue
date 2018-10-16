package com.ljwm.gecko.admin.model.vo;

import com.ljwm.gecko.base.entity.OtherReduce;
import com.ljwm.gecko.base.entity.TaxOtherReduce;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: xixil
 * Date: 2018/10/16 15:40
 * RUA
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TaxOtherVo extends TaxOtherReduce {

  private OtherReduce otherReduce;
}
