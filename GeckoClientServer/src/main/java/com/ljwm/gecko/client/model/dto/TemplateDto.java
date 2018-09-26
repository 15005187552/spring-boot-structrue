package com.ljwm.gecko.client.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/25 14:36
 */
@Data
@Accessors(chain = true)
public class TemplateDto {

  private Long id;

  private int sort;

}
