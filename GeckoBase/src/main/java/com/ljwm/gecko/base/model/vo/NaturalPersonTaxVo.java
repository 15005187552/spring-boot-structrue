package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.NaturalPerson;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/25 10:03
 */
@Data
public class NaturalPersonTaxVo extends NaturalPerson {

  private TaxVo taxVo;

}
