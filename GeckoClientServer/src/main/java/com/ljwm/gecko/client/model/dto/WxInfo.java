package com.ljwm.gecko.client.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by yunqisong on 2018/3/21/021.
 */
@Data
@Accessors(chain = true)
public class WxInfo {

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
