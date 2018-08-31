package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Janiffy
 * @date 2018/8/29 15:23
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForm {

  @Pattern(regexp="^(1[3-9]\\d{9})$",message="请填入正确的手机号")
  @ApiModelProperty("手机号")
  private String phoneNum;

  @ApiModelProperty("短信模板类型")
  @NotNull(message = "请输入action类型值")
  private int action;
}
