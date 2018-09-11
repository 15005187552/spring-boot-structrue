package com.ljwm.gecko.provider.model.form;

import lombok.Data;

@Data
public class FunctionSaveForm {

  private Long id;

  private String parentId;

  private String name;

  private String title;

  private String description;

  private String icon;

  private String url;

  private String sort;

  private Integer isShow;
}
