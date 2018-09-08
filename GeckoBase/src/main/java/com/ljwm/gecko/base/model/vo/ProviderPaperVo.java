package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.ProviderPaper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProviderPaperVo extends ProviderPaper {
  private PaperVo paperVo;
}
