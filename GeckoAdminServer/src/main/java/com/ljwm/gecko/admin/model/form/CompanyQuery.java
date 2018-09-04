package com.ljwm.gecko.admin.model.form;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompanyQuery extends CommonQuery {

  @ApiModelProperty(hidden = true)
  private Integer validateStatus;
}
