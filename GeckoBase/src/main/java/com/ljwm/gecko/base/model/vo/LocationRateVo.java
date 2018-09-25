package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class LocationRateVo extends Location{

  private List<RateVo> rates;
}
