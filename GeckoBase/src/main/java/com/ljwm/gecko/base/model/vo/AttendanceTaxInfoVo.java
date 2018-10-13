package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.Tax;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/10/13 12:04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AttendanceTaxInfoVo extends Tax {

  @ApiModelProperty("姓名")
  private String name;

  @ApiModelProperty("证件类型")
  private Integer certificate;

  @ApiModelProperty("证照号码")
  private String idCard;

  @ApiModelProperty("社保基数")
  private String socialBase;

  @ApiModelProperty("公积金基数")
  private String fundBase;

  @ApiModelProperty("公积金比例")
  private String fundPer;

  private List<AttendanceData> dataList;
}
