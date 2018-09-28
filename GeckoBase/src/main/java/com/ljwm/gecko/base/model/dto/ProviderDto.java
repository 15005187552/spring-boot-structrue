package com.ljwm.gecko.base.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ProviderDto {


  @ApiModelProperty("服务商id")
  private Long id;

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

  @ApiModelProperty("服务商logo")
  private String logo;

  @ApiModelProperty("服务商简介")
  private String instro;

  @ApiModelProperty("服务类型ids")
  private List<ProviderServiceDto> providerServiceDtoList;

  @ApiModelProperty("认证人ids 如果创建人已经认证,可以不填")
  private List<Long> memberIds;

  @ApiModelProperty("营业执照图片url")
  private String picPath;

  @ApiModelProperty("报税员是否修改")
  private Boolean isChange=false;
}
