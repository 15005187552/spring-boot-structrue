package com.ljwm.gecko.admin.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: xixil
 * Date: 2018/10/10 10:43
 * RUA
 */

@Data
@ApiModel("公告保存表单")
public class NoticeSaveForm {

  private Long id;

  private String title;

  private String content;

  @ApiModelProperty("标签Id")
  private Integer tagId;

  private Integer sort;

}
