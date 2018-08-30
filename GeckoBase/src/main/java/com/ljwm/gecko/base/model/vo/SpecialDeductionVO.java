package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.SpecialDeduction;
import com.ljwm.gecko.base.serializer.IdToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SpecialDeductionVO extends SpecialDeduction {

  private Boolean deleteAble;

  @JSONField(serializeUsing = IdToStringSerializer.class)
  private Long id;

  public SpecialDeductionVO(SpecialDeduction specialDeduction){
    if (specialDeduction!=null){
      BeanUtil.copyProperties(specialDeduction,this);
    }
  }
}
