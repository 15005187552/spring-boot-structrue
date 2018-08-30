package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.AddSpecial;
import com.ljwm.gecko.base.serializer.IdToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AddSpecialVo extends AddSpecial {

  @JSONField(serializeUsing = IdToStringSerializer.class)
  private Long id;

  private Boolean deleteAble;

  public AddSpecialVo(AddSpecial addSpecial){
    if (addSpecial!=null){
      BeanUtil.copyProperties(addSpecial,this);
    }
  }
}
