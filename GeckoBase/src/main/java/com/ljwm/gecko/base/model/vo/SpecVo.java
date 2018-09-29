package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.Spec;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SpecVo extends Spec {
  private ServeSimpleVo serveSimpleVo;

  private List<SpecItemSimpleVo> specItemSimpleVoList;
}
