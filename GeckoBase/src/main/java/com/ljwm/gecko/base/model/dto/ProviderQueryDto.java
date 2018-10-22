package com.ljwm.gecko.base.model.dto;

import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ProviderQueryDto extends CommonQuery {

  @ApiModelProperty("状态 审核状态 1待审核 2 审核通过 3 审核失败")
  private Integer validateState;

  @ApiModelProperty("是否可用")
  private Integer disabled;

  @ApiModelProperty("服务类型id")
  private Integer serviceId;

  @ApiModelProperty("状态 0 待审核 1 审核通过 2 审核失败")
  private Integer infoValidateState;

  @ApiModelProperty(value = "服务类型id及子节点",hidden = true)
  private List<Integer> serviceIds;
}
