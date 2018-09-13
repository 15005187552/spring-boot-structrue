package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.config.FilePathAppend;
import com.ljwm.gecko.base.entity.PaperPath;

public class PaperPathVo extends PaperPath {

  @JSONField(serializeUsing = FilePathAppend.class)
  private String picPath;
}
