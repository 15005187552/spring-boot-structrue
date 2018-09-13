package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.MemberPaper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class MemberPaperVo extends MemberPaper {
  private PaperVo paperVo;

  private List<PaperPathVo> paperPathVoList;
}
