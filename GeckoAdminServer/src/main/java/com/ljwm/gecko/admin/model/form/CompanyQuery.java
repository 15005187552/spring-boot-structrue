package com.ljwm.gecko.admin.model.form;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyQuery extends CommonQuery {

  private Integer validateStatus;
}
