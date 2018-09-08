package com.ljwm.gecko.client.model.vo;

import com.ljwm.gecko.base.model.vo.TaxIncomeVo;
import com.ljwm.gecko.base.model.vo.TaxOtherReduceVo;
import com.ljwm.gecko.base.model.vo.TaxSpecialAddVo;
import com.ljwm.gecko.base.model.vo.TaxSpecialVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/6 14:36
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TaxVo {

  List<TaxIncomeVo> incomeVoList;

  List<TaxOtherReduceVo> otherReduceVoList;

  List<TaxSpecialVo> specialVoList;

  List<TaxSpecialAddVo> specialAddVoList;
}
