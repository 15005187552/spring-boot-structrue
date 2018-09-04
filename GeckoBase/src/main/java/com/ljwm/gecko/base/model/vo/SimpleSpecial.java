package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.SpecialDeduction;
import lombok.Data;

@Data
public class SimpleSpecial extends CommonSimple {

  public SimpleSpecial(SpecialDeduction specialDeduction) {
    this.code = specialDeduction.getId();
    this.name = specialDeduction.getName();
  }
}
