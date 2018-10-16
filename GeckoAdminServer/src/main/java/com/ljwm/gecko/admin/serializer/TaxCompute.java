package com.ljwm.gecko.admin.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.ljwm.gecko.admin.model.vo.TaxIncomeVo;
import com.ljwm.gecko.base.entity.TaxIncome;
import com.ljwm.gecko.base.enums.SocialInsuranceType;
import com.ljwm.gecko.base.utils.EnumUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: xixil
 * Date: 2018/10/16 17:47
 * RUA
 */

public abstract class TaxCompute implements ObjectSerializer {

  public abstract String compute(Object obj);

  @Override
  public void write(JSONSerializer serializer,Object object,Object fieldName,Type type,int i) throws IOException {
    if (object == null) {
      serializer.getWriter().writeNull();
      return;
    }
    if (object instanceof List) {
      String statusName = fieldName + "Result";
      serializer.getWriter()
        .append(JSON.toJSONString(object))
        .append(",")
        .append("\"")
        .append(statusName)
        .append("\"")
        .append(":")
        .append("\"")
        .append(compute(object))
        .append("\"")
      ;
      return;
    }
    serializer.write(object);
  }

  public static class IncomeCompute extends TaxCompute {

    @Override
    public String compute(Object object) {
      return ((ArrayList<TaxIncomeVo>) object).stream().map(TaxIncome::getIncome).reduce((l,n) -> l.add(n)).get().setScale(2).toString();
    }
  }
}
