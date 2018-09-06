package com.ljwm.gecko.base.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/6 14:41
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TaxOtherReduceVo {

  private Long otherReduceId;

  private String name;

  private String taxMoney;

  private String taxDocPath;
}
