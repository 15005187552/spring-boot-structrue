package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.SpecItem;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpecItemVo extends SpecItem {

  private SpecSimpleVo specSimpleVo;
}
