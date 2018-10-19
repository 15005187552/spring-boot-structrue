package com.ljwm.gecko.admin.model.form;

import lombok.Data;

/**
 * Author: xixil
 * Date: 2018/10/19 10:08
 * RUA
 */

@Data
public class ProtocolForm {

  private Long id;

  private Integer code;

  private String name;

  private String description;

  private String content;

  private String picPath;
}
