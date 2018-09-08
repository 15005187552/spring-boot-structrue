package com.ljwm.gecko.admin.model.vo;

import com.ljwm.gecko.base.entity.Company;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimpleCompany {

  private Long id;

  private String name;

  public SimpleCompany(Company company) {
    this.id = company.getId();
    this.name = company.getName();
  }
}
