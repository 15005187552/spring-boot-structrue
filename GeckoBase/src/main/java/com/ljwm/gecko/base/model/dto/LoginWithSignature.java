package com.ljwm.gecko.base.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Janiffy
 * @date 2018/8/29 18:42
 */
@Data
@Accessors(chain = true)
public class LoginWithSignature {

  @NotBlank(message = "CODE 不可以为空")
  private String code;
}
