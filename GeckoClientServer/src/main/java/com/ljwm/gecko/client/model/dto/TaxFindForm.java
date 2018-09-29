package com.ljwm.gecko.client.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Janiffy
 * @date 2018/9/29 9:24
 */
@Data
public class TaxFindForm extends CommonQuery {

  @NotNull
  @ApiModelProperty("公司id")
  private Long companyId;

  @ApiModelProperty("申报时间")
  private String declareTime;

  @ApiModelProperty("姓名")
  private String name;
}
