package com.ljwm.gecko.client.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Janiffy
 * @date 2018/9/11 10:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {


  private String phoneNum;

  private String password;

}
