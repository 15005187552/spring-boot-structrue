package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.entity.AddSpecial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AddSpecialVo extends AddSpecial {

  public AddSpecialVo(AddSpecial addSpecial){
    if (addSpecial!=null){
      BeanUtil.copyProperties(addSpecial,this);
    }
  }
}
