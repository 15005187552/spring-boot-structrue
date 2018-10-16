package com.ljwm.gecko.admin.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.admin.serializer.TaxCompute;
import com.ljwm.gecko.base.entity.Tax;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Author: xixil
 * Date: 2018/10/16 15:29
 * RUA
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TaxVo extends Tax {

  private List<TaxOtherVo> taxOthers;

  @JSONField(serializeUsing = TaxCompute.IncomeCompute.class)
  private List<TaxIncomeVo> taxIncomes;

  private List<TaxSpecialVo> taxSpecials;

  private List<TaxSpecialAddVo> taxSpecialAdds;
}
