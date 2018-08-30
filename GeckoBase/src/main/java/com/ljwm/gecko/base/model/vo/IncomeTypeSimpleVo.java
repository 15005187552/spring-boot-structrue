package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.IncomeType;
import com.ljwm.gecko.base.serializer.IdToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IncomeTypeSimpleVo extends IncomeType {

  @JSONField(serializeUsing = IdToStringSerializer.class)
  private Long id;

  public IncomeTypeSimpleVo(IncomeType incomeType){
    if (incomeType!=null){
      BeanUtil.copyProperties(incomeType,this);
    }
  }
}
