package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.FileTemplate;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileTemplateVo extends FileTemplate {

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String filePath;
}
