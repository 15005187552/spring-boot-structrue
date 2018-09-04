package com.ljwm.gecko.base.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.ljwm.gecko.base.enums.CompanyType;

import java.io.IOException;
import java.lang.reflect.Type;

public class CompanyEnumCodeToName implements ObjectSerializer {


  @Override
  public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
    if (object == null) {
      serializer.getWriter().writeNull();
      return;
    }
    if (object instanceof Integer) {
      serializer.write(CompanyType.getNameByCode((Integer) object));
      return;
    }
  }
}
