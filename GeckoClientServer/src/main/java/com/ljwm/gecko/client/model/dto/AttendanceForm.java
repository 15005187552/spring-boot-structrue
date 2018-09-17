package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Janiffy
 * @date 2018/9/17 14:54
 */
@Data
public class AttendanceForm {

  @ApiModelProperty(value = "主键ID")
  private Long id;

  @ApiModelProperty(value = "时间")
  private String date;

  @ApiModelProperty(value = "公司用户关联ID")
  private Long companyUserId;

  @ApiModelProperty(value = "加班")
  private BigDecimal overtime;

  @ApiModelProperty(value = "定期奖金")
  private BigDecimal bonus;

  @ApiModelProperty(value = "考勤绩效")
  private BigDecimal appraisal;

  @ApiModelProperty(value = "其他工资收入")
  private BigDecimal otherIncome;

  @ApiModelProperty(value = "事假")
  private BigDecimal affairDay;

  @ApiModelProperty(value = "病假")
  private BigDecimal sickLeave;

  @ApiModelProperty(value = "迟到早退")
  private BigDecimal tardiness;

  @ApiModelProperty(value = "其他缺勤")
  private BigDecimal otherAbsence;

  @ApiModelProperty(value = "旷工")
  private BigDecimal absenteeism;

  @ApiModelProperty(value = "离岗")
  private BigDecimal dimission;

  @ApiModelProperty(value = "罚款")
  private BigDecimal fine;

  @ApiModelProperty(value = "其他处罚")
  private BigDecimal otherFine;

  @ApiModelProperty(value = "薪资")
  private BigDecimal salary;

  @ApiModelProperty(value = "薪资调整")
  private BigDecimal salaryAdjust;

  @ApiModelProperty(value = "薪资说明")
  private String salaryRemark;

  @ApiModelProperty(value = "全年一次性奖金补贴")
  private BigDecimal bonusYear;

  @ApiModelProperty(value = "解除劳动关系一次性经济补偿金")
  private BigDecimal compensation;

  @ApiModelProperty(value = "病事假说明")
  private String sickRemark;

  @ApiModelProperty(value = "处罚说明")
  private String punishRemark;

  @ApiModelProperty(value = "社保缴费基数")
  private BigDecimal socialBase;

  @ApiModelProperty(value = "社保公司比例")
  private BigDecimal socialComPer;

  @ApiModelProperty(value = "社保个人比例")
  private BigDecimal socialPerPer;

  @ApiModelProperty(value = "社保公司调整")
  private BigDecimal socialComAjust;

  @ApiModelProperty(value = "社保个人调整")
  private BigDecimal socialPerAjust;

  @ApiModelProperty(value = "社保说明")
  private String socialRemark;

  @ApiModelProperty(value = "公积金基数")
  private BigDecimal fundBase;

  @ApiModelProperty(value = "公积金公司缴费比例")
  private BigDecimal fundComPer;

  @ApiModelProperty(value = "公积金个人缴费比例")
  private BigDecimal fundPerPer;

  @ApiModelProperty(value = "公积金公司调整")
  private BigDecimal fundComAjust;

  @ApiModelProperty(value = "公积金个人调整")
  private BigDecimal fundPerAjust;

  @ApiModelProperty(value = "调整说明")
  private String fundRemark;
}
