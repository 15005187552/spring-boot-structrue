package com.ljwm.gecko.admin.model.vo;

import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.entity.NaturalPersonBackup;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: xixil
 * Date: 2018/10/16 15:27
 * RUA
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ReportLogVo extends NaturalPersonBackup{

    private TaxVo tax;

    private Company company;
}
