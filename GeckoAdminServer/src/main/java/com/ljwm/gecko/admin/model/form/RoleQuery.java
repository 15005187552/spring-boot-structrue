package com.ljwm.gecko.admin.model.form;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleQuery extends CommonQuery {

  @ApiModelProperty("0 查看启用 1 查看禁用，  不传值全部")
  private Integer disabled;
}
