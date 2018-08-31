package com.ljwm.gecko.base.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class LocationRateDto {

  private String provinceName;

  private String cityName;

  private List<LocationRateDetailDto> locationRateDetailDtoList;
}
