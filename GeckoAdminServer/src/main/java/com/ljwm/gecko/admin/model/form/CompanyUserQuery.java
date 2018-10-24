package com.ljwm.gecko.admin.model.form;

import com.ljwm.bootbase.dto.CommonQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: xixil
 * Date: 2018/10/23 15:48
 * RUA
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyUserQuery extends CommonQuery {

  private Long companyId;
}
