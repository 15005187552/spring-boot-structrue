package com.ljwm.gecko.base.enums;

import com.ljwm.gecko.base.listener.CommonEnum;
import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/20 17:23
 */
@Getter
public enum TableNameEnum implements CommonEnum {

  T_INCOME_TYPE(0, "t_income_type"),
  T_OTHER_REDUCE(1, "t_other_reduce"),
  T_SPECIAL_DEDUCTION(2, "t_special_deduction"),
  T_ADD_SPECIAL(3, "t_add_special"),
  T_MEMBER(4,"t_member"),
  T_ADMIN(5,"t_admin"),
  T_PROVIDER(6,"t_provider"),
  T_ATTENDANCE(7, "t_attendance_attibute");
  private Integer code;

  private String name;


  TableNameEnum(Integer code, String name){
    this.code = code;
    this.name = name;
  }

}
