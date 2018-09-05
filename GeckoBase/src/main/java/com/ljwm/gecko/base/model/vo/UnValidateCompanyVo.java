package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.config.CompanyEnumCodeToName;
import com.ljwm.gecko.base.entity.Company;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UnValidateCompanyVo extends Company {

  private String location;

  @JSONField(serializeUsing = CompanyEnumCodeToName.class)
  private Integer type;

  @JSONField(serialize = false)
  private Integer validateState;

  @JSONField(serialize = false)
  private Integer validaterId;

  @JSONField(serialize = false)
  private Date validateTime;

  @JSONField(serialize = false)
  private String validateText;

//  public UnValidateCompanyVo(AdminCompanyDto adminCompanyDto) {
//    if (adminCompanyDto != null)
//      BeanUtil.copyProperties(adminCompanyDto, this);
//  }
}
