package com.ljwm.gecko.client.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/14 13:09
 */
@Data
public class RecordForm extends CommonQuery {

  @ApiModelProperty("申报类型")
  private Integer declareType;

}
