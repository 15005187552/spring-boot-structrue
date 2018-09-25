package com.ljwm.gecko.admin.model.form;

import com.ljwm.bootbase.dto.CommonQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LocationRateQuery extends CommonQuery {

  private Integer code;
}
