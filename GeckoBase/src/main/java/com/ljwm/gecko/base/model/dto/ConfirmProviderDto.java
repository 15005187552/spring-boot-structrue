package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ConfirmProviderDto {

  @ApiModelProperty("服务商id")
  private Long id;

  @ApiModelProperty(value = "认证人",hidden = true)
  private Long validatorId;

  @ApiModelProperty("服务商服务类型审批明细")
  private List<ProviderServicesConfirmDto> providerServicesConfirmDtoList;
}
