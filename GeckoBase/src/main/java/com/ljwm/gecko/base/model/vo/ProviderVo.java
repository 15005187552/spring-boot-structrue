package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ljwm.bootbase.kit.SpringKit;
import com.ljwm.gecko.base.entity.Provider;
import com.ljwm.gecko.base.entity.ProviderUser;
import com.ljwm.gecko.base.entity.ServiceType;
import com.ljwm.gecko.base.model.vo.admin.ServiceTypeTree;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import com.ljwm.gecko.base.service.ProviderService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProviderVo extends Provider {

  @JSONField(serializeUsing = StatusWithNameSerializer.ProviderValidateStatSerializer.class)
  private Integer validateState;

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String picPath;

  private String locationStr;

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String logo;

  private List<ProviderServicesVo> providerServicesVoList;

  private List<PaperVo> paperVoList;

  private List<MemberVo> memberVoList;

  private Integer orderCount;

  private Integer starAvg;

  public Integer getStarAvg(){
    if (getId()!=null){
      ProviderService providerService =  SpringKit.getBean(ProviderService.class);
      Integer star = providerService.starCount(getId());
      return Objects.equals(0,star)?5:star;
    }
    return 5;
  }


}
