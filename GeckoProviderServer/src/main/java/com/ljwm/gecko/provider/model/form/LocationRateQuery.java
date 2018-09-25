package com.ljwm.gecko.provider.model.form;

import com.ljwm.bootbase.dto.CommonQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LocationRateQuery extends CommonQuery {

  private Integer code;
}
