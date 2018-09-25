package com.ljwm.gecko.base.model.form;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AdvertisementQuery extends CommonQuery {

  @ApiModelProperty(value = "0 查看启用 1 查看禁用，  不传值全部",hidden = true)
  private Integer disabled;

  @ApiModelProperty(value = "设备类型")
  private Integer equipType;
}
