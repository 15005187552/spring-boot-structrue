package com.ljwm.gecko.base.model.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AttributeForm {

  private Integer tableName;

  private Long itemId;

  private String name;
}
