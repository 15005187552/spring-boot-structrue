package com.ljwm.gecko.admin.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("证件保存表单")
public class PaperSaveForm {

  private Integer id;

  private String name;

  @ApiModelProperty(value = "适用类型 0: 个人 1: 企业", hidden = true)
  private Integer type;
}
