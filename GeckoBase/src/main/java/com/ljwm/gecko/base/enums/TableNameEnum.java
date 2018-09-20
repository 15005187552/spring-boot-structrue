package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/20 17:23
 */
@Getter
public enum TableNameEnum {

  T_INCOME_TYPE(0, "t_income_type"),
  T_OTHER_REDUCE(1, "t_other_reduce"),
  T_SPECIAL_DEDUCTION(2, "t_special_deduction"),
  T_ADD_SPECIAL(3, "t_add_special");

  private Integer code;

  private String name;

  TableNameEnum(Integer code, String name){
    this.code = code;
    this.name = name;
  }

}
