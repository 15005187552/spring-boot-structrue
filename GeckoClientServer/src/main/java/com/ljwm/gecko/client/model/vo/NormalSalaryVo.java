package com.ljwm.gecko.client.model.vo;

import com.ljwm.gecko.base.utils.excelutil.ExcelCell;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/9/18 15:25
 */
@Data
@Accessors(chain = true)
public class NormalSalaryVo {

  @ExcelCell(index = 0)
  @ApiModelProperty(value = "工号")
  private String jobNum;

  @ExcelCell(index = 1)
  @ApiModelProperty(value = "*姓名")
  private String name;

  @ExcelCell(index = 2)
  @ApiModelProperty(value = "*证照类型")
  private String certificate;

  @ExcelCell(index = 3)
  @ApiModelProperty(value = "*证照号码")
  private String certNum;

  @ExcelCell(index = 4)
  @ApiModelProperty(value = "税款负担方式")
  private String taxBurden;

  @ExcelCell(index = 5)
  @ApiModelProperty(value = "*收入额")
  private String income;

  @ExcelCell(index = 6)
  @ApiModelProperty(value = "免税所得")
  private String taxEmpty;

  @ExcelCell(index = 7)
  @ApiModelProperty("基本养老保险费")
  private String entireInsurance;

  @ExcelCell(index = 8)
  @ApiModelProperty("基本医疗保险费")
  private String medicalInsurance;

  @ExcelCell(index = 9)
  @ApiModelProperty("失业保险费")
  private String unemployeeInsurance;

  @ExcelCell(index = 10)
  @ApiModelProperty("住房公积金")
  private String fund;

  @ExcelCell(index = 11)
  @ApiModelProperty("允许扣除的税费")
  private String taxfee;

  @ExcelCell(index = 12)
  @ApiModelProperty("年金")
  private String annuity;

  @ExcelCell(index = 13)
  @ApiModelProperty("商业健康保险费")
  private String commercialInsurance;

  @ExcelCell(index = 14)
  @ApiModelProperty("税延养老保险费")
  private String otherEntireInsurance;

  @ExcelCell(index = 15)
  @ApiModelProperty("其他扣除")
  private String otherReduce;

  @ExcelCell(index = 16)
  @ApiModelProperty("减除费用")
  private String reduceFee;

  @ExcelCell(index = 17)
  @ApiModelProperty("实际捐赠额")
  private String donate;

  @ExcelCell(index = 18)
  @ApiModelProperty("允许列支的捐赠比例")
  private String donatePercent;

  @ExcelCell(index = 19)
  @ApiModelProperty("准予扣除的捐赠额")
  private String reduceDonate;

  @ExcelCell(index = 20)
  @ApiModelProperty("减免税额")
  private String reduceMoney;

  @ExcelCell(index = 21)
  @ApiModelProperty("已扣缴税额")
  private String deductMoney;

  @ExcelCell(index = 22)
  @ApiModelProperty("备注")
  private String remark;
}

