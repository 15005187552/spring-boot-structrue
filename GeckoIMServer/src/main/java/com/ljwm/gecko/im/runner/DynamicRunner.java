package com.ljwm.gecko.im.runner;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSONObject;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.im.annotations.DynamicAnnotation;
import com.ljwm.gecko.im.controller.ChatController;
import com.ljwm.gecko.im.service.IDynamicAble;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@Component
@SuppressWarnings("ALL")
public class DynamicRunner implements ApplicationRunner {

  @Autowired
  private ApplicationContext applicationContext;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Map<String, IDynamicAble> maps = applicationContext.getBeansOfType(IDynamicAble.class);
    maps.forEach((k, v) -> {
      Arrays
        .stream(ReflectUtil.getMethods(v.getClass(), (method) -> method.isAnnotationPresent(DynamicAnnotation.class)))
        .forEach(m -> {
          ChatController.invokeMethod.add(
            Kv.by("code", m.getAnnotation(DynamicAnnotation.class).code())
              .set("method", v.getClass().getSuperclass().getName() + "#" + m.getName())
              .set("desc",m.getAnnotation(DynamicAnnotation.class).desc())
          );
        });
    });
  }
}
