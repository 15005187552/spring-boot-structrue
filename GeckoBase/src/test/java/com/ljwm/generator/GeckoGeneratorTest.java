package com.ljwm.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.ljwm.bootbase.ext.GeneratorConfig;
import com.ljwm.bootbase.ext.MyGenerator;
import com.ljwm.bootbase.kit.PathKit;
import org.junit.Test;

/**
 * Created by yuzhou on 2018/8/21.
 */
public class GeckoGeneratorTest {

  @Test
  public void testGen() {

    MyGenerator.build(
      GeneratorConfig
        .builder()
        .authorName("Levis")  // 设置作者
        .basePackage("com.ljwm.gecko.base")
        .path(PathKit.getProjectPath())
        .prefix("t_")             // 前缀t_
        .url("jdbc:mysql://192.168.8.208:3306/geckodb?characterEncoding=UTF-8&autoReconnect=true&useSSL=false&serverTimezone=GMT%2B8")
        .username("root")
        .password("root")
        .created(true)    // 第一次建类用创建
        .idType(IdType.AUTO)
        .tables(new String[]{
          "t_tax_other_reduce","t_tax_special","t_tax_special_add"})
        .build()
    );
  }
}
