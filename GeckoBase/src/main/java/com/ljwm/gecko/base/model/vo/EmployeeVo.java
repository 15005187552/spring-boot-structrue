package com.ljwm.gecko.base.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/13 13:21
 */
@Data
public class EmployeeVo {

  @ApiModelProperty(value = "会员ID")
  private Long memberId;

  @ApiModelProperty(value = "公司员工表id")
  private Long companyUserId;

  @ApiModelProperty(value = "工号")
  private String jobNum;

  @ApiModelProperty(value = "证件类型")
  private String certType;

  @ApiModelProperty("证件号")
  private String certNum;

  @ApiModelProperty("姓名")
  private String name;

}
