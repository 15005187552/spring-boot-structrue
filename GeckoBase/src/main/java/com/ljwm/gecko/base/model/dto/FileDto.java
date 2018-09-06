package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileDto {

  @ApiModelProperty("文件名称")
  private String fileName;

  @ApiModelProperty("证件类型id")
  private Integer paperId;
}
