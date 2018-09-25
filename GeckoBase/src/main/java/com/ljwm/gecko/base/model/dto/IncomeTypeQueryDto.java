package com.ljwm.gecko.base.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class IncomeTypeQueryDto extends CommonQuery {

  private Long pId;
}
