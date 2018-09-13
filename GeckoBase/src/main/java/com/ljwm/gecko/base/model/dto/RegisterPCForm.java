package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * @author Janiffy
 * @date 2018/9/13 10:44
 */
@Data
public class RegisterPCForm {

  @Pattern(regexp="^(1[3-9]\\d{9})$",message="请填入正确的手机号！")
  @ApiModelProperty("手机号")
  private String phoneNum;

  @ApiModelProperty("验证码")
  @NotBlank(message = "请输入验证码！")
  private String checkCode;

  @ApiModelProperty("密码")
  private String password;

  @ApiModelProperty("游客ID")
  private String guestId;

}
