package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MemberInfoConfirmDto {

  @ApiModelProperty("会员id")
  private Long id;

  @ApiModelProperty(value = "认证人",hidden = true)
  private Long validatorId;

  @ApiModelProperty("是否同意")
  private boolean isAgree=true;

  @ApiModelProperty("会员基本信息认证拒绝理由")
  private String validateText;
}
