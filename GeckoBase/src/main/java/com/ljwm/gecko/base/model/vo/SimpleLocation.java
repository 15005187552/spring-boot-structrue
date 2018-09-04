package com.ljwm.gecko.base.model.vo;

import lombok.Data;

@Data
public class SimpleLocation {

  private Integer code;

  private String name;

  private Integer parentCode;

  private String parentName;
}
