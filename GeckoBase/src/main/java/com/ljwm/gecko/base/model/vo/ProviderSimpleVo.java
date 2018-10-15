package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProviderSimpleVo {

  private Long id;

  private String name;

  private Integer type;

  private Integer provCode;

  private Integer cityCode;

  private Integer areaCode;

  private String address;

  private Integer infoValidateState;

  private BigDecimal cashDeposit;

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String logo;

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String picPath;

  private String locationStr;
  private List<ProviderServicesSimpleVo> providerServicesVoList;

}
