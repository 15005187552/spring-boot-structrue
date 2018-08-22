package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by yuzhou on 2018/8/21.
 */
@Data
@Accessors(chain = true)
@ApiModel("游客信息表单")
public class GuestForm {
  private Integer source;
  private String guestId;
}
