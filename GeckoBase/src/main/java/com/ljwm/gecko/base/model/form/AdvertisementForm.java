package com.ljwm.gecko.base.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@ApiModel("广告为保存表单")
public class AdvertisementForm {

  private Long id;

  @ApiModelProperty("图片路径")
  private String path;

  @ApiModelProperty("跳转路径")
  private String urlPath;

  @ApiModelProperty("设备类型 0 pc ，1 微信端")
  private Integer equipType;

  private Integer sort;
}
