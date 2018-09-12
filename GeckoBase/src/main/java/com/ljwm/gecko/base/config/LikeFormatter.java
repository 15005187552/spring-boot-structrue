package com.ljwm.gecko.base.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.ljwm.gecko.base.utils.UtilKit;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * stoa Created by yunqisong on 2018-4-6.
 * FOR: 关键词输出Formatter
 */
public class LikeFormatter implements ObjectSerializer {

  private static final ThreadLocal<String> keyWordHolder = new ThreadLocal<>();

  public static final String PRE = "<font style='color:red;'>";

  public static final String END = "</font>";

  public static void set(String text) {
    LikeFormatter.keyWordHolder.set(text);
  }

  public static String get() {
    return LikeFormatter.keyWordHolder.get();
  }

  @Override
  public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
    if (object == null) {
      serializer.getWriter().writeNull();
      return;
    }
    if (object instanceof String) {
      String str = (String) object;
      if (StrUtil.isNotBlank(get())) {
        serializer.write(UtilKit.replaceIgnoreCase(str, get(), PRE, END));
      } else {
        serializer.write(str);
      }
      return;
    }
    serializer.write(object);
  }

}
