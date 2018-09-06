package com.ljwm.gecko.client.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/5 16:28
 */
@Data
@Accessors(chain = true)
@ApiModel("个税申报表单")
public class TaxForm {

  @ApiModelProperty(value = "会员ID")
  private Long memberId;

  @ApiModelProperty(value = "申报类型 0-月报 1-年报")
  private String declareType;

  @ApiModelProperty(value = "申报时段")
  private String declareTime;

  List<TaxIncomeForm> taxIncomeFormList;

  List<TaxOtherReduceForm> taxOtherReduceFormList;

  List<TaxSpecialForm> taxSpecialFormList;

  List<TaxSpecialAddForm> taxSpecialAddFormList;

}
