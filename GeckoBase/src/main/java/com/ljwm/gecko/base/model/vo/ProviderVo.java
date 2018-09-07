package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.Provider;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProviderVo extends Provider {

  private  List<ProviderPaperVo> providerPaperVoList;

}
