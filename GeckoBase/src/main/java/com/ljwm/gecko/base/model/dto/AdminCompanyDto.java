package com.ljwm.gecko.base.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.config.CompanyEnumCodeToName;
import com.ljwm.gecko.base.entity.Company;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AdminCompanyDto extends Company {

  @JSONField(serializeUsing = CompanyEnumCodeToName.class)
  private Integer type;
}
