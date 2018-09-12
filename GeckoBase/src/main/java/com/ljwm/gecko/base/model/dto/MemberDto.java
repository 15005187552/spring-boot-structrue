package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class MemberDto {

  @ApiModelProperty(value = "会员id",hidden = true)
  private Long id;

  @ApiModelProperty("会员身份证号")
  private String memberIdcard;

  private List<FileDto> fileDtoList;
}
