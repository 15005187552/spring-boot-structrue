package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * @author Janiffy
 * @date 2018/9/13 11:51
 */
@Data
public class PasswordForm {

  @Pattern(regexp="^(1[3-9]\\d{9})$",message="请填入正确的手机号！")
  @ApiModelProperty("手机号")
  private String phoneNum;

  @ApiModelProperty("验证码")
  @NotBlank(message = "请输入验证码！")
  private String checkCode;

  @Pattern(regexp="^.{6,}$",message="请输入6位以上！")
  @ApiModelProperty("密码")
  private String password;
}
