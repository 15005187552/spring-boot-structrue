package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.Location;
import lombok.Data;

import java.util.List;

@Data
public class LocationRateVo extends Location{

  private List<RateVo> rates;
}
