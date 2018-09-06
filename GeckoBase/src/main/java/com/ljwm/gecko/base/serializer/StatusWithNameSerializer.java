package com.ljwm.gecko.base.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.ljwm.gecko.base.enums.ValidateStatEnum;

import java.io.IOException;
import java.lang.reflect.Type;

public abstract class StatusWithNameSerializer implements ObjectSerializer {


  public static class ValidateStatSerializer extends StatusWithNameSerializer {

    @Override
    public String getNameByCode(Integer code) {
      return ValidateStatEnum.getName(code);
    }

  }

  public abstract String getNameByCode(Integer code);


  @Override
  public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
    if (object == null) {
      serializer.getWriter().writeNull();
      return;
    }

    if (object instanceof Integer) {
      Integer code = (Integer) object;
      String statusName = fieldName + "Name";
      serializer.getWriter()
        .append(String.valueOf(code))
        .append(",")
        .append("\"")
        .append(statusName)
        .append("\"")
        .append(":")
        .append("\"")
        .append(getNameByCode(code))
        .append("\"")
      ;
      return;
    }
    serializer.write(object);
  }
}
