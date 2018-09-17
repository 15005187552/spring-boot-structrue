package com.ljwm.gecko.base.model.dto;

import com.ljwm.gecko.base.entity.NaturalPerson;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/17 18:18
 */
@Data
public class NaturalPersonDto extends NaturalPerson {

  private String regMobile;
}
