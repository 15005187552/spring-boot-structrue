package com.ljwm.gecko.client.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/10/12 14:37
 */
@Data
@Accessors(chain = true)
public class AttendanceData {

  @ApiModelProperty("属性id")
  private Long id;

  @ApiModelProperty("对应值")
  private String value;
}
