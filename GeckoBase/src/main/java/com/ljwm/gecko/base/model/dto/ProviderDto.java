package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ProviderDto {

  @ApiModelProperty(value = "会员id",hidden = true)
  private Long memberId;

  @ApiModelProperty("服务商类型(个人：0，机构：1)")
  private Integer type;

  @ApiModelProperty("服务商名称")
  private String name;

  @ApiModelProperty("省份code")
  private Integer provCode;

  @ApiModelProperty("市code")
  private Integer cityCode;

  @ApiModelProperty("区code")
  private Integer areaCode;

  @ApiModelProperty("详细地址")
  private String address;

  @ApiModelProperty("服务类型ids")
  private List<Integer> serviceIds;

  @ApiModelProperty("认证人ids 如果创建人已经认证,可以不填")
  private List<Long> memberIds;

  @ApiModelProperty("资质附件")
  private List<FileDto> fileDtoList;
}