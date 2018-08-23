package com.ljwm.gecko.admin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Gecko 后台启动类
 */
@SpringBootApplication(scanBasePackages = {
  "com.ljwm.bootbase.*",
  "com.ljwm.gecko.base.*",
  "com.ljwm.gecko.admin.*"
})
@MapperScan(value = {
  "com.ljwm.gecko.base.mapper*",
  "com.ljwm.bootbase.mapper*"
})
@EnableAspectJAutoProxy(proxyTargetClass = true)                            // 允许使用自定义JDK代理切面
@EnableTransactionManagement                                                // 开启事务
@ServletComponentScan
@EnableScheduling
@Slf4j
public class GeckoAdminServerApp {
  public static void main(String[] args) {
    SpringApplication.run(GeckoAdminServerApp.class, args);
  }
}
