package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.IncomeType;
import com.ljwm.gecko.base.serializer.IdToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IncomeTypeVo extends IncomeType {

  @JSONField(serializeUsing = IdToStringSerializer.class)
  private Long id;

  @JSONField(serializeUsing = IdToStringSerializer.class)
  private Long pId;


  private Boolean deleteAble;

  private List<IncomeTypeVo> children;

  public IncomeTypeVo(IncomeType incomeType){
    if (incomeType!=null){
      BeanUtil.copyProperties(incomeType,this);
    }
  }
}
