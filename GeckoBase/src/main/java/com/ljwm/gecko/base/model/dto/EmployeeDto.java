package com.ljwm.gecko.base.model.dto;

import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.entity.CompanyUserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Janiffy
 * @date 2018/9/17 19:11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeDto extends CompanyUser {

  CompanyUserInfo companyUserInfo;
}
