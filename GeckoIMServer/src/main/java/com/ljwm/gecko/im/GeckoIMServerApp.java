package com.ljwm.gecko.im;

import cn.hutool.core.date.DateTime;
import com.ljwm.gecko.im.ws.ShowcaseServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Gecko 后台启动类
 */
@SpringBootApplication(scanBasePackages = "com.ljwm")
@MapperScan(value = {
  "com.ljwm.gecko.*.mapper*",
  "com.ljwm.bootbase.mapper*"
})
@EnableAspectJAutoProxy(proxyTargetClass = true)                            // 允许使用自定义JDK代理切面
@EnableTransactionManagement                                                // 开启事务
@ServletComponentScan
@EnableScheduling
@Slf4j
public class GeckoIMServerApp {

  public static void main(String[] args) {

    SpringApplication.run(GeckoIMServerApp.class, args);
    log.info("BootGeckoIMServerApp Run Finished: {}", DateTime.now());

  }

  @Bean
  @ConfigurationProperties(prefix = "com.example.demo")
  public ShowcaseServerConfig showcaseServerConfig() {
    return new ShowcaseServerConfig();
  }



}
