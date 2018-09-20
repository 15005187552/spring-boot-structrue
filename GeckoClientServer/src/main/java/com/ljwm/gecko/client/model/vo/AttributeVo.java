package com.ljwm.gecko.client.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/20 18:18
 */
@Data
@Accessors(chain = true)
public class AttributeVo {

  private Long id;

  @ApiModelProperty(value = "名称")
  private String name;
}
