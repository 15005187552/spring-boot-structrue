package com.ljwm.gecko.provider.model.form;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProviderGoodsQueryForm extends CommonQuery {

  @ApiModelProperty(value = "服务商id",hidden = true)
  private Long providerId;

  @ApiModelProperty("服务类型id")
  private Integer serviceId;

  @ApiModelProperty("状态  1-在售 2-下架 3-删除")
  private Integer status;
}
