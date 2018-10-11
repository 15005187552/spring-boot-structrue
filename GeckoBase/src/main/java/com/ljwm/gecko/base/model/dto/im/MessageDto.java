package com.ljwm.gecko.base.model.dto.im;

import com.ljwm.gecko.base.enums.LoginType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Author: xixil
 * Date: 2018/10/11 9:26
 * RUA
 */

@Data
@ApiModel("消息体")
public class MessageDto {

  @ApiModelProperty("接收着 ID")
  private Long receiverId;

  @ApiModelProperty("接收者类型 MOBILE 和 WX_APP 即是用户客户端版登陆")
  private List<LoginType> loginType;

  @ApiModelProperty("消息主体")
  private String message;
}
