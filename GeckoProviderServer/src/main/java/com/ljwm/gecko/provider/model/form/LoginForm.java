package com.ljwm.gecko.provider.model.form;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Levis
 * FOR: 登录表单
 */
@Data
@Accessors(chain = true)
public class LoginForm {

  private String phoneNum;

  private String password;
}
