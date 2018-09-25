package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author Janiffy
 * @date 2018/9/25 17:09
 */
@Data
public class MemberIdDto {

  @ApiModelProperty("会员ID")
  private Long memberId;
}
