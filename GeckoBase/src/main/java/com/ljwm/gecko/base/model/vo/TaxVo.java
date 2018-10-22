package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.Tax;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/25 10:23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class TaxVo extends Tax {

  @JSONField(serializeUsing = StatusWithNameSerializer.DeclaretypeSerializer.class)
  @ApiModelProperty(value = "申报类型 0-月度申报 1-年度汇缴申报")
  private Integer declareType;

  List<TaxIncomeVo> incomeVoList;

  List<TaxOtherReduceVo> otherReduceVoList;

  List<TaxSpecialVo> specialVoList;

  List<TaxSpecialAddVo> specialAddVoList;
}
