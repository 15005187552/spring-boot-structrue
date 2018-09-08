package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/8 12:01
 */
@Data
public class MemberComForm {

  @ApiModelProperty("会员id")
  private Long memberId;

  @ApiModelProperty("公司id")
  private Long companyId;

  @ApiModelProperty("角色编码,以二进制数传")
  private Integer roleCode;

}
