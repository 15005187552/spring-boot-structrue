package com.ljwm.gecko.client.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/28 21:04
 */
@Data
public class AttendanceVo{

  @ApiModelProperty(value = "主键ID")
  private Long id;

  @ApiModelProperty(value = "考勤报税数据ID")
  private String taxId;

  @JSONField(serializeUsing = StatusWithNameSerializer.AttendanceSerializer.class)
  @ApiModelProperty(value = "属性ID")
  private Long attributeId;

  @ApiModelProperty(value = "值")
  private String value;

}
