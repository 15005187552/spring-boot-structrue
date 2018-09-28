package com.ljwm.gecko.client.model.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/17 14:54
 */
@Data
public class AttendanceForm {

  @ApiModelProperty("公司id")
  private Long companyId;

  @ApiModelProperty("申报时间")
  private String declareTime;

  @ApiModelProperty("申报类型")
  private Integer declareType;

  private List<AttendanceDto> list;

  @Data
  public class AttendanceDto{

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("证件类型")
    private String certificate;

    @ApiModelProperty("证照号码")
    private String idCard;

    @ApiModelProperty("社保基数")
    private String socialBase;

    @ApiModelProperty("公积金基数")
    private String fundBase;

    @ApiModelProperty("公积金比例")
    private String fundPer;

    private List<AttendanceData> dataList;

    @Data
    public class AttendanceData{

      @ApiModelProperty("属性id")
      private Long id;

      @ApiModelProperty("对应值")
      private String value;
    }

  }


}
