package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.CityItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RateVo extends CityItem {

  private String typeName;
}
