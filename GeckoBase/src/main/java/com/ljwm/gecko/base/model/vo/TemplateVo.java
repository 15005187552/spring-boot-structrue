package com.ljwm.gecko.base.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/26 19:52
 */
@Data
@Accessors(chain = true)
public class TemplateVo {

  private Long id;

  private Integer sort;

  private String name;

}
