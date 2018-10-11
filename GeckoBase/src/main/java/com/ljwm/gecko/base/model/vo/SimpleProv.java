package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SimpleProv {

  private String code;

  private String name;

  public SimpleProv(Location location){
    this.code = location.getCode();
    this.name = location.getName();
  }
}
