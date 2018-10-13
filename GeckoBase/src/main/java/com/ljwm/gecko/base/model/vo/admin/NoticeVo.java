package com.ljwm.gecko.base.model.vo.admin;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.Notice;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Author: xixil
 * Date: 2018/10/10 10:47
 * RUA
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class NoticeVo extends Notice {

  @JSONField(serializeUsing = StatusWithNameSerializer.TagEnumSerializer.class)
  private Integer tagId;

}
