package com.ljwm.gecko.admin.runner;

import com.ljwm.gecko.admin.service.AuthService;
import com.ljwm.gecko.admin.service.FunctionService;
import com.ljwm.gecko.admin.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SystemRunner implements ApplicationRunner {

  @Autowired
  private AdminService adminService;

  @Autowired
  private AuthService authService;

  @Autowired
  private FunctionService functionService;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    log.info("初始化菜单{}");
    functionService.init();

    log.info("初始化角色{}");
    authService.init();

    log.info("初始化用户{}");
    adminService.init();
  }
}
