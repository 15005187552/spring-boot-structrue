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

  @ApiModelProperty(value = "创建人ID")
  private Long createrId;

  @ApiModelProperty(value = "证照图片文件")
  private String filePath;

  @ApiModelProperty(value = "省")
  private String province;

  @ApiModelProperty(value = "市")
  private String city;

  @ApiModelProperty(value = "区")
  private String area;

  @ApiModelProperty(value = "详细地址")
  private String address;
}
