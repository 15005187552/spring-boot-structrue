package com.ljwm.gecko.base.model.dto;

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
  private String phoneNum;

  @NotBlank(message = "请输入验证码！")
  private String checkCode;

  @NotBlank
  private String userName;
}
