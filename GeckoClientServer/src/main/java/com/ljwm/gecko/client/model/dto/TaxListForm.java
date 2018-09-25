package com.ljwm.gecko.client.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/22 13:21
 */
@Data
public class TaxListForm extends CommonQuery {

  @ApiModelProperty("公司ID")
  private Long companyId;

  @ApiModelProperty("名字")
  private String name;

  @ApiModelProperty("申报时间")
  private String declareTime;
}
