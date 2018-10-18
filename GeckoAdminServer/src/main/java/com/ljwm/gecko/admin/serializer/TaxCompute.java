package com.ljwm.gecko.admin.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.ljwm.gecko.admin.model.vo.TaxIncomeVo;
import com.ljwm.gecko.admin.model.vo.TaxOtherVo;
import com.ljwm.gecko.admin.model.vo.TaxSpecialAddVo;
import com.ljwm.gecko.admin.model.vo.TaxSpecialVo;
import com.ljwm.gecko.base.entity.TaxIncome;
import com.ljwm.gecko.base.entity.TaxOtherReduce;
import com.ljwm.gecko.base.entity.TaxSpecial;
import com.ljwm.gecko.base.entity.TaxSpecialAdd;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
      String key = fieldName + "Result";
      serializer.getWriter()
        .append(JSON.toJSONString(object))
        .append(",")
        .append("\"")
        .append(key)
        .append("\"")
        .append(":")
        .append("\"")
        .append(((ArrayList) object).size() > 0 ? compute(object) : "")
        .append("\"")
      ;
      return;
    }

    if (object instanceof Integer || object instanceof String || object instanceof Long) {
      String key = fieldName + "Value";
      serializer.getWriter()
        .append(",")
        .append("\"")
        .append(key)
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
    public String compute(Object obj) {
      return ((ArrayList<TaxIncomeVo>) obj).stream().map(TaxIncome::getIncome).reduce((l,n) -> l.add(n)).get().setScale(2).toString();
    }
  }

  public static class SpecialCompute extends TaxCompute {

    @Override
    public String compute(Object obj) {
      return ((ArrayList<TaxSpecialVo>) obj).stream().map(TaxSpecial::getPersonalMoney).reduce((l,n) -> l.add(n)).get().setScale(2).toString();
    }
  }

  public static class SpecialAddCompute extends TaxCompute {

    @Override
    public String compute(Object obj) {
      return ((ArrayList<TaxSpecialAddVo>) obj).stream().map(TaxSpecialAdd::getTaxMoney).reduce((l,n) -> l.add(n)).get().setScale(2).toString();
    }
  }

  public static class OtherCompute extends TaxCompute {

    @Override
    public String compute(Object obj) {
      return ((ArrayList<TaxOtherVo>) obj).stream().map(TaxOtherReduce::getTaxMoney).reduce((l,n) -> l.add(n)).get().setScale(2).toString();
    }
  }
}
