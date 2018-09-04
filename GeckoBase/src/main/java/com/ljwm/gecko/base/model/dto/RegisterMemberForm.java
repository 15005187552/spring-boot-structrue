package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * @author Janiffy
 * @date 2018/8/30 13:36
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMemberForm {

  @Pattern(regexp="^(1[3-9]\\d{9})$",message="请填入正确的手机号！")
  @ApiModelProperty("手机号")
  private String phoneNum;

  @ApiModelProperty("验证码")
  @NotBlank(message = "请输入验证码！")
  private String checkCode;

  @NotBlank(message = "userName不能为空")
  @ApiModelProperty("小程序输入我给你的userName，其他客户端输入手机号")
  private String userName;
}
