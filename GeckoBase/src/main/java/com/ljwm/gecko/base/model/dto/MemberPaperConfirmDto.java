package com.ljwm.gecko.base.model.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MemberPaperConfirmDto {

  @ApiModelProperty("会员证件类型id")
  private Long id;

  @ApiModelProperty("是否同意")
  private boolean isAgree=true;

  @ApiModelProperty("认证内容")
  private String validateText;
}
