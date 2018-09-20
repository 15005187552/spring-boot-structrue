package com.ljwm.gecko.client.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/20 18:44
 */
@Data
@Accessors(chain = true)
public class TemplateForm {

  private Long companyId;

  private String[] name;
}
