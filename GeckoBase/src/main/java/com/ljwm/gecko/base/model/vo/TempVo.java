package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/28 19:45
 */
@Data
public class TempVo {

  @JSONField(serialzeFeatures = {SerializerFeature.SortField})
  private JSONObject jsonObject;
}
