package com.ljwm.gecko.client.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/28 20:40
 */
@Data
public class AttendanceTaxVo {

  private List<AttendanceVo> AttendanceList;

  private List<TaxIncomeVo> incomeList;

  private List<TaxOtherReduceVo> otherReduceList;

  private List<TaxSpecialVo> specialList;

  private List<TaxSpecialAddVo> specialAddList;
}
