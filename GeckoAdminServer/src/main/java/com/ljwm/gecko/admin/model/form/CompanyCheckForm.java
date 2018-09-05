package com.ljwm.gecko.admin.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("企业审核表单")
public class CompanyCheckForm {

  private Long id;

  @ApiModelProperty("是否通过 1 通过 2 未通过")
  private Integer isPass;

  private String text;
}
