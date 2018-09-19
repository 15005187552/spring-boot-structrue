package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/12 18:57
 */
@Data
public class InactiveForm {

  @ApiModelProperty("公司id")
  private Long companyId;

  @ApiModelProperty("是否成为该公司纳税人,成为传true,反之")
  private boolean flag;

}
