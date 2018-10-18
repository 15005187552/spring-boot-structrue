package com.ljwm.gecko.admin.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.entity.NaturalPersonBackup;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Author: xixil
 * Date: 2018/10/16 15:27
 * RUA
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ReportLogVo extends NaturalPersonBackup {

  private TaxVo tax;

  private Company company;

  @JSONField(serializeUsing = StatusWithNameSerializer.CertificateSerializer.class)
  private Integer certificate;
}
