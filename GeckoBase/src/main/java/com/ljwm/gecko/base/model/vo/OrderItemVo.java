package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.OrderItem;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OrderItemVo extends OrderItem {

  @JSONField(serializeUsing = StatusWithNameSerializer.OrderStatusSerializer.class)
  private Integer orderItemStatus;

  private ProviderGoodsVo providerGoodsVo;

  private SpecServicesPriceSimpleVo specServicesPriceSimpleVo;
}
