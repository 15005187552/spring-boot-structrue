package com.ljwm.gecko.base.model.form;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OtherReduceForm{

  private Long id;

  private String name;

  private String sort;

  private String description;
}
