package com.ljwm.gecko.client.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuzhou on 2018/8/22.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("当前用户信息")
public class ResultMe {
  private Boolean isGuest;

  private Long id;
  private String username;
  private String phoneNum;
  private String nickName;
  private String extInfo;
  private String token;
}
