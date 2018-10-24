package com.ljwm.gecko.client.model.vo;

import com.ljwm.gecko.base.utils.excelutil.ExcelCell;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Janiffy
 * @date 2018/10/9 11:48
 */
@Data
@Accessors(chain = true)
public class AttendanceExcelVo {

  @ExcelCell(index = 0)
  private String name;

  @ExcelCell(index = 1)
  private String certificate;

  @ExcelCell(index = 2)
  private String certNum;

  @ExcelCell(index = 3)
  private String socialBase;

  @ExcelCell(index = 4)
  private String fundBase;

  @ExcelCell(index = 5)
  private String fundPer;

}
