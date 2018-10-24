package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.bootbase.kit.SpringKit;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;
import com.ljwm.gecko.base.service.ProviderService;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

  private Integer orderCount;

  private Integer starAvg;

  public Integer getStarAvg() {
    if (getId()!=null){
      ProviderService providerService =  SpringKit.getBean(ProviderService.class);
      Integer star = providerService.starCount(getId());
      return Objects.equals(0,star)?5:star;
    }
    return 5;
  }

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String logo;

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String picPath;

  private String locationStr;

  private List<ProviderServicesSimpleVo> providerServicesVoList;

}
