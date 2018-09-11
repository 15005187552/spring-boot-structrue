package com.ljwm.gecko.base.model.vo;

import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/11 11:19
 */
@Data
public class LoginVo {

  private String salt;

  private String password;

  private Long memberId;
}
