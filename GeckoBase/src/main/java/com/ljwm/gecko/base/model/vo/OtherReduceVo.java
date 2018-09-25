package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.OtherReduce;
import com.ljwm.gecko.base.serializer.IdToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OtherReduceVo extends OtherReduce {

  @JSONField(serializeUsing = IdToStringSerializer.class)
  private Long id;

  private Boolean deleteAble;
}
