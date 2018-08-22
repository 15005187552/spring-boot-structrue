package com.ljwm.gecko.client.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by yuzhou on 2018/8/22.
 */
@Data
@ApiModel("当前用户信息")
public class ResultMe {
  private Boolean isGuest;

  private Long id;
  private String username;
  private String token;
}
