package com.ljwm.gecko.base.utils;

import cn.hutool.core.util.RandomUtil;

/**
 * @author Janiffy
 * @date 2018/8/30 14:23
 */
public class StringUtil {

  /**
   * 创建Salt
   *
   * @return
   */
  public static String salt() {
    return RandomUtil.randomInt(1000, 9999) + "";
  }
}
