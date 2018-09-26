package com.ljwm.gecko.base.model.dto;


import com.ljwm.bootbase.dto.CommonQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MemberQueryDto extends CommonQuery {

  @ApiModelProperty("状态 0  初始状态   1 待审核 2 审核通过 3 审核失败")
  private Integer validateState;

  @ApiModelProperty("是否可用")
  private Integer disabled;

  @ApiModelProperty("状态 0 待审核 1 审核通过 2 审核失败")
  private Integer infoValidateState;
}
