package com.ljwm.gecko.base.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OrderQueryDto extends CommonQuery {

  @ApiModelProperty("订单状态")
  private Integer status;

  @ApiModelProperty(value = "服务商id")
  @NotBlank
  private Long providerId;
}
