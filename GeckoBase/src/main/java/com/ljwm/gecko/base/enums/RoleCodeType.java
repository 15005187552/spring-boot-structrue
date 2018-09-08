package com.ljwm.gecko.base.enums;

import lombok.Getter;

/**
 * @author Janiffy
 * @date 2018/9/8 16:30
 */
@Getter
public enum RoleCodeType {
  CREATOR(1, "创建人", 1),
  ADMIN(2, "管理员", 1),
  ITIN(3, "申报人", 1);

  private Integer digit;

  private String role;

  private Integer value;

  RoleCodeType(Integer digit, String role, Integer value) {
    this.digit = digit;
    this.role = role;
    this.value = value;
  }
}
