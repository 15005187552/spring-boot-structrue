package com.ljwm.gecko.base.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.ljwm.bootbase.kit.SpringKit;
import com.ljwm.gecko.base.dao.*;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author Janiffy
 * @date 2018/9/29 10:09
 */
public abstract class IdToNameSerializer implements ObjectSerializer {

  public abstract String getNameByCode(Object code);

  public static class SpecialDeductionSerializer extends IdToNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return SpringKit.getBean(SpecialDuctionDao.class).idToName((Long) code);
    }
  }

  public static class SpecialAddSerializer extends IdToNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return SpringKit.getBean(SpecialAddDao.class).idToName((Long) code);
    }
  }

  public static class IncomeTypeSerializer extends IdToNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return SpringKit.getBean(IncomeTypeDao.class).idToName((Long) code);
    }
  }

  public static class AttendanceSerializer extends IdToNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return SpringKit.getBean(AttendanceAttributeDao.class).idToName((Long) code);
    }
  }

  public static class OtherReduceSerializer extends IdToNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return SpringKit.getBean(OtherReduceDao.class).idToName((Long) code);
    }
  }

  @Override
  public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
    if (object == null) {
      serializer.getWriter().writeNull();
      return;
    }

    if (object instanceof Integer || object instanceof String || object instanceof Long) {
//      Integer code = (Integer) object;
      String statusName = "name";
      serializer.getWriter()
        .append(String.valueOf(object))
        .append(",")
        .append("\"")
        .append(statusName)
        .append("\"")
        .append(":")
        .append("\"")
        .append(getNameByCode(object))
        .append("\"")
      ;
      return;
    }
    serializer.write(object);
  }
}
