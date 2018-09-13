package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class FileDto {

  @ApiModelProperty("会员资质详情id")
  private Long id;

  @ApiModelProperty(value = "会员id",hidden = true)
  private Long memberId;

  @ApiModelProperty("证件类型id")
  private Integer paperId;

  @ApiModelProperty("文件名称")
  private List<String> fileNameList;
}
