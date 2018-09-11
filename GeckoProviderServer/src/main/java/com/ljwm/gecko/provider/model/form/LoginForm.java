package com.ljwm.gecko.provider.model.form;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * stoa Created by yunqisong on 2018/7/7/007.
 * FOR: 登录表单
 */
@Data
@Accessors(chain = true)
public class LoginForm {

  private String username;

  private String password;
}
