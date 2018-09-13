package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author Janiffy
 * @date 2018/9/13 12:43
 */
@Data
public class ModifyPasswordForm {

  @Pattern(regexp="^(1[3-9]\\d{9})$",message="请填入正确的手机号！")
  @ApiModelProperty("手机号")
  private String phoneNum;

  @ApiModelProperty("旧密码")
  private String oldPassword;

  @ApiModelProperty("新密码")
  private String newPassword;

}
