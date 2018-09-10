package com.ljwm.gecko.base.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.config.CompanyEnumCodeToName;
import com.ljwm.gecko.base.entity.Admin;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.model.vo.admin.CompanyUserVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AdminCompanyDto extends Company {

  private Admin validator;

  private String location;

  @JSONField(serializeUsing = CompanyEnumCodeToName.class)
  private Integer type;

  private List<CompanyUserVo> companyUsers;
}
