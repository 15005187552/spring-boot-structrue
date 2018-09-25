package com.ljwm.gecko.provider.model.form;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AdminQuery extends CommonQuery {

  @ApiModelProperty("0 查看启用 1 查看禁用，  不传值全部")
  private Integer disabled;
}
