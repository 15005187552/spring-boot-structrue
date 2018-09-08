package com.ljwm.gecko.admin.model.vo;

import com.ljwm.gecko.base.entity.Company;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimpleCompany {

  private Integer id;

  private String name;

  public SimpleCompany(Company company) {
    this.id = company.getId();
    this.name = company.getName();
  }
}
