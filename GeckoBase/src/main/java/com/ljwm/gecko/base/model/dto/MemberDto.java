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

  @ApiModelProperty("身份证正面")
  private String picFront;

  @ApiModelProperty("身份证反面")
  private String picBack;

  @ApiModelProperty("个人证件照")
  private String picPassport;

  @ApiModelProperty("资质文件上传")
  private List<FileDto> fileDtoList;
}
