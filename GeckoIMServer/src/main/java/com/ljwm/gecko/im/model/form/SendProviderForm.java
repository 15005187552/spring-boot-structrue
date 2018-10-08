package com.ljwm.gecko.im.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: xixil
 * Date: 2018/9/30 14:10
 * RUA
 */

@Data
@ApiModel("会话发送来自服务商")
public class SendProviderForm {

  @ApiModelProperty("会员ID")
  private Long memberId;

  @ApiModelProperty("服务商ID")
  private Long providerId;

  @ApiModelProperty("自定义消息体  JSONString")
  private String message;
}
