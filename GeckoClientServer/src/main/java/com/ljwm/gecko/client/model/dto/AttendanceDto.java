package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Janiffy
 * @date 2018/10/8 10:06
 */
public class AttendanceDto {

  @ApiModelProperty("公司ID")
  private Long companyId;

  @ApiModelProperty("时间")
  private String declareTime;
}
