package com.ljwm.gecko.base.model.dto;

import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.entity.CompanyUserInfo;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/17 19:11
 */
@Data
public class EmployeeDto extends CompanyUser {

  CompanyUserInfo companyUserInfo;
}
