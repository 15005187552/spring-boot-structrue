package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.SpecItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SpecItemVo extends SpecItem {

  private SpecSimpleVo specSimpleVo;
}
