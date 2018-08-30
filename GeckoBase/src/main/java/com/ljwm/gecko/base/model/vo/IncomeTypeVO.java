package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ljwm.gecko.base.entity.IncomeType;
import com.ljwm.gecko.base.serializer.IdToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IncomeTypeVO extends IncomeType {

  @JSONField(serializeUsing = IdToStringSerializer.class)
  private Long id;

  private Boolean deleteAble;

  private List<IncomeTypeVO> children;

  public IncomeTypeVO(IncomeType incomeType){
    if (incomeType!=null){
      BeanUtil.copyProperties(incomeType,this);
    }
  }
}
