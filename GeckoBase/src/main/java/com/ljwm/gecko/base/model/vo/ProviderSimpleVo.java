package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.bootbase.kit.SpringKit;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;
import com.ljwm.gecko.base.service.ProviderService;
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

  private Integer orderCount;

  private Integer starAvg;

  public Integer getStarAvg(){
    if (getId()!=null){
      ProviderService providerService =  SpringKit.getBean(ProviderService.class);
      return providerService.starCount(getId());
    }
    return 0;
  }

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String logo;

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String picPath;

  private String locationStr;
  private List<ProviderServicesSimpleVo> providerServicesVoList;

}
