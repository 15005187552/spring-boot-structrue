package com.ljwm.gecko.base.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/8/30 16:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WechatInfo {

  private String openId;

  private String nickName;

  private String gender;

  private String language;

  private String city;

  private String province;

  private String country;

  private String avatarUrl;

  private String unionId;
}
