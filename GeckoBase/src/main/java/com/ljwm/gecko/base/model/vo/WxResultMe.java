package com.ljwm.gecko.base.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/8/29 18:38
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class WxResultMe {

  private String nickName;

  private String userName;

  private String extInfo;

}
