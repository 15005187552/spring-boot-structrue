package com.ljwm.gecko.base.serializer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.ljwm.bootbase.kit.SpringKit;
import com.ljwm.gecko.base.bean.ApplicationInfo;

import java.lang.reflect.Type;

public abstract class PathToUrlSerializer implements ObjectSerializer{

  public static class UrlSeralizer extends PathToUrlSerializer{
    @Override
    public String getUrl(String path) {
      return SpringKit.getBean(ApplicationInfo.class).getWebPath()+path;
    }
  }

  public abstract String getUrl(String path);
  @Override
  public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
    if (object == null) {
      serializer.getWriter().writeNull();
      return;
    }

    if (object instanceof String) {
      if(StrUtil.isEmpty(object.toString())){
        serializer.getWriter().writeNull();
        return;
      }
//      Integer code = (Integer) object;
      String statusName = fieldName + "Url";
      serializer.getWriter()
        .append("\"")
        .append(String.valueOf(object))
        .append("\"")
        .append(",")
        .append("\"")
        .append(statusName)
        .append("\"")
        .append(":")
        .append("\"")
        .append(getUrl(String.valueOf(object)))
        .append("\"")
      ;
      return;
    }
    serializer.write(object);
  }
}
