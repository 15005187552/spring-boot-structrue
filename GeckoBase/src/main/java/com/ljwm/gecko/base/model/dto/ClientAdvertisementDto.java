package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ClientAdvertisementDto {
  @ApiModelProperty(value = "0 查看启用 1 查看禁用，  不传值全部",hidden = true)
  private Integer disabled;

  @ApiModelProperty(value = "设备类型")
  private Integer equipType;
}
