package com.ljwm.gecko.client.constant;

import com.ljwm.gecko.client.model.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Janiffy
 * @date 2018/9/5 15:33
 */
public class Constant {
  @Autowired
  ApplicationInfo appInfo;

  //微信登录code api
  public static final String LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

  //公司存储路径
  public static final String COMPANY = "company/";

  //个人存储路径
  public static final String PERSON = "person/";

  public static final String HTTP = "http";

  public static final String CACHE = "cache/";
}
