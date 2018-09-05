package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.config.FilePathAppend;
import com.ljwm.gecko.base.entity.Advertisement;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AdvertisementVo extends Advertisement {


  @JSONField(serializeUsing = FilePathAppend.class)
  private String path;

  private String equipName;
}
