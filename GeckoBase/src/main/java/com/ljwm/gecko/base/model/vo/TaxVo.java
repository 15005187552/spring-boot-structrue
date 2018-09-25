package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.Tax;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/25 10:23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TaxVo extends Tax {

  List<TaxIncomeVo> incomeVoList;

  List<TaxOtherReduceVo> otherReduceVoList;

  List<TaxSpecialVo> specialVoList;

  List<TaxSpecialAddVo> specialAddVoList;
}
