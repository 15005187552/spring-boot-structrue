package com.ljwm.gecko.base.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.ljwm.bootbase.kit.SpringKit;
import com.ljwm.gecko.base.enums.*;
import com.ljwm.gecko.base.service.LocationService;
import com.ljwm.gecko.base.utils.EnumUtil;

import java.io.IOException;
import java.lang.reflect.Type;


public abstract class StatusWithNameSerializer implements ObjectSerializer {

  public static class ValidateStatSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return ValidateStatEnum.getName((Integer)code);
    }
  }

  public static class ProviderValidateStatSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return ProviderStatEnum.getName((Integer)code);
    }
  }

  public static class CompanyTypeSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return EnumUtil.getNameBycode(CompanyType.class, (Integer)code);
    }
  }

  public static class CompanyValidateSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return EnumUtil.getNameBycode(CompanyValidateEnum.class, (Integer)code);
    }
  }

  public static class DeclaretypeSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return EnumUtil.getNameBycode(DeclareType.class, (Integer)code);
    }
  }

  public static class LocationSerializer extends StatusWithNameSerializer{
    @Override
    public String getNameByCode(Object code) {
      return SpringKit.getBean(LocationService.class).getNameByCode((String)code);
    }
  }

  public abstract String getNameByCode(Object code);


  @Override
  public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
    if (object == null) {
      serializer.getWriter().writeNull();
      return;
    }

    if (object instanceof Integer  || object instanceof String) {
//      Integer code = (Integer) object;
      String statusName = fieldName + "Name";
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
