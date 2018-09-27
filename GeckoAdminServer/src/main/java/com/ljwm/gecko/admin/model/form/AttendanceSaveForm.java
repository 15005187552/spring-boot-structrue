package com.ljwm.gecko.admin.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("考情字段保存表单")
public class AttendanceSaveForm {

  private Long id;

  private String name;

  private Integer sort;

  @ApiModelProperty("是否需要输出到前台")
  private Integer isNeedEnter;
}
