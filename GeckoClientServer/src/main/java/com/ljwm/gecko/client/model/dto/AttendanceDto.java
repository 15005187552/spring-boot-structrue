package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Janiffy
 * @date 2018/9/12 10:25
 */
@Data
public class AttendanceDto {

  @ApiModelProperty(value = "工号")
  private String jobNum;

  @ApiModelProperty(value = "部门")
  private String department;

  @ApiModelProperty(value = "岗位")
  private String station;

  @ApiModelProperty(value = "姓名")
  private String name;

  @ApiModelProperty(value = "加班")
  private BigDecimal overtime;

  @ApiModelProperty(value = "定期奖金")
  private BigDecimal bonuses;

  @ApiModelProperty(value = "考勤绩效")
  private BigDecimal appraisal;

  @ApiModelProperty(value = "其他工资收入")
  private BigDecimal otherIncome;

  @ApiModelProperty(value = "奖金津贴小计")
  private BigDecimal incomeAll;

  @ApiModelProperty(value = "事假")
  private BigDecimal affairDay;

  @ApiModelProperty(value = "病假")
  private BigDecimal sickLeave;

  @ApiModelProperty(value = "迟到早退")
  private BigDecimal tardiness;

  @ApiModelProperty(value = "其他缺勤")
  private BigDecimal otherAbsence;

  @ApiModelProperty(value = "缺席小计")
  private BigDecimal absenceAll;

  @ApiModelProperty(value = "旷工")
  private BigDecimal absenteeism;

  @ApiModelProperty(value = "离岗")
  private BigDecimal dimission;

  @ApiModelProperty(value = "罚款")
  private BigDecimal fine;

  @ApiModelProperty(value = "其他处罚")
  private BigDecimal otherFine;

  @ApiModelProperty(value = "处罚小计")
  private BigDecimal fineAll;

  @ApiModelProperty(value = "考勤小计")
  private BigDecimal attendenceAll;
}
