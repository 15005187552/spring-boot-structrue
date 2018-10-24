package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileTemplateDto {

  @ApiModelProperty(value = "文件id")
  private Integer id;

  @ApiModelProperty(value = "用户名",hidden = true)
  private String creatorId;

  @ApiModelProperty(value = "文件下载所需费用")
  private Integer money;

  @ApiModelProperty(value = "文件名称")
  private String fileName;

  @ApiModelProperty(value = "文件下载路径")
  private String filePath;
}
