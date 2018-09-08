package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConfirmProviderDto {

  @ApiModelProperty("服务商id")
  private Long id;

  @ApiModelProperty("是否同意")
  private boolean isAgree=true;

  @ApiModelProperty(value = "认证人",hidden = true)
  private Long validaterId;

  @ApiModelProperty("认证内容")
  private String validateText;
}
