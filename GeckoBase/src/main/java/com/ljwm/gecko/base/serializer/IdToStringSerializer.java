package com.ljwm.gecko.base.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

public class IdToStringSerializer implements ObjectSerializer {
  @Override
  public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {

    if(Objects.isNull(object)){
      serializer.write("");
      return;
    }
    if(object instanceof Long){
      serializer.write(String.valueOf(object));
    }
  }
}
