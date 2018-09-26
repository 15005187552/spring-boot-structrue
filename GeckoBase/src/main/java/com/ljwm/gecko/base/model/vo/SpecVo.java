package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.Spec;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SpecVo extends Spec {
  private ServeSimpleVo serveSimpleVo;
}
