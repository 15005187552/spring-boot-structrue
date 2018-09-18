package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.config.FilePathAppend;
import com.ljwm.gecko.base.entity.PaperPath;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;

public class PaperPathVo extends PaperPath {

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String picPath;
}
