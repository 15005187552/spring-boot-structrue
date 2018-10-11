package com.ljwm.gecko.provider.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProviderDetailDto {

  @ApiModelProperty("服务商id")
  private Long id;

  @ApiModelProperty("详情")
  private String detail;
}
