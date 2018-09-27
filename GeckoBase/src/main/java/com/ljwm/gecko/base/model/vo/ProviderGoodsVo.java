package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.ProviderGoods;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ProviderGoodsVo extends ProviderGoods {

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String mainImage;

  private List<ProviderGoodsPathVo> providerGoodsPathVoList;
}
