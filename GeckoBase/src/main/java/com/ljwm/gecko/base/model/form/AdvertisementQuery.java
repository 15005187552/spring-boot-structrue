package com.ljwm.gecko.base.model.form;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdvertisementQuery extends CommonQuery {

  @ApiModelProperty("0 查看启用 1 查看禁用，  不传值全部")
  private Integer disabled;
}
