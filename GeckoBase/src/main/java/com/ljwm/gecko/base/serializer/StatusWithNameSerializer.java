package com.ljwm.gecko.base.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.ljwm.bootbase.kit.SpringKit;
import com.ljwm.gecko.base.enums.*;
import com.ljwm.gecko.base.service.LocationService;
import com.ljwm.gecko.base.utils.EnumUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;


public abstract class StatusWithNameSerializer implements ObjectSerializer {

  public static class SocialInsuranceTypeSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return EnumUtil.getNameBycode(SocialInsuranceType.class, (Integer) code);
    }
  }

  public static class TagEnumSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return EnumUtil.getNameBycode(TagEnum.class, (Integer) code);
    }
  }

  public static class CountryTypeSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return EnumUtil.getNameBycode(CountryType.class, (Integer) code);
    }
  }

  public static class CertificateSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return EnumUtil.getNameBycode(CertificateType.class, (Integer) code);
    }
  }

  public static class ValidateStatSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return ValidateStatEnum.getName((Integer) code);
    }
  }

  public static class ProviderValidateStatSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return ProviderStatEnum.getName((Integer) code);
    }
  }

  public static class CompanyTypeSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return EnumUtil.getNameBycode(CompanyType.class, (Integer) code);
    }
  }

  public static class CompanyValidateSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return EnumUtil.getNameBycode(CompanyValidateEnum.class, (Integer) code);
    }
  }

  public static class DeclaretypeSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return EnumUtil.getNameBycode(DeclareType.class, (Integer) code);
    }
  }

  public static class LocationSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return SpringKit.getBean(LocationService.class).getNameByCode(code.toString());
    }
  }

  public static class OrderStatusSerializer extends StatusWithNameSerializer {
    @Override
    public String getNameByCode(Object code) {
      return OrderStatusEnum.codeOf((Integer) code);
    }
  }

  public abstract String getNameByCode(Object code);


  @Override
  public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
    if (object == null) {
      serializer.getWriter().writeNull();
      return;
    }

    if (object instanceof Integer || object instanceof String || object instanceof Long) {
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
