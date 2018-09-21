package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.NaturalPerson;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class NaturalPersonVo extends NaturalPerson {

  @JSONField(serializeUsing = StatusWithNameSerializer.CertificateSerializer.class)
  private Integer certificate;

  @JSONField(serializeUsing = StatusWithNameSerializer.CountryTypeSerializer.class)
  private Integer country;

  @JSONField(serializeUsing = StatusWithNameSerializer.SocialInsuranceTypeSerializer.class)
  private Integer socialSecu;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date creatTime;
}
