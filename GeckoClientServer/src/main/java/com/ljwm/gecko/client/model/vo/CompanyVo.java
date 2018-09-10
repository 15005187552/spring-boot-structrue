package com.ljwm.gecko.client.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/5 15:13
 */
@Data
public class CompanyVo {
  @ApiModelProperty(value = "企业名称")
  private String name;

  @ApiModelProperty(value = "企业类型")
  private Integer type;

  @ApiModelProperty(value = "企业纳税代码")
  private String code;

  @ApiModelProperty(value = "手机号码")
  private String phoneNum;

  @ApiModelProperty(value = "认证状态：0：未认证，1：认证通过，2:认证失败")
  private Integer validateState;

  @ApiModelProperty(value = "证照图片文件")
  private String filePath;

  @ApiModelProperty(value = "省级代码")
  private Integer provCode;

  @ApiModelProperty(value = "市级代码")
  private Integer cityCode;

  @ApiModelProperty(value = "区级代码")
  private Integer areaCode;

  @ApiModelProperty(value = "详细地址")
  private String address;
}
