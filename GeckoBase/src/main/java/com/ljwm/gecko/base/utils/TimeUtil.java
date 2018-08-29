package com.ljwm.gecko.base.utils;

import java.text.SimpleDateFormat;

/**
 * @author Janiffy
 * @date 2018/8/29 14:12
 */
public class TimeUtil {

  /**
   * 判断两个时间是否为同一天
   * @param firstTime
   * @param secondTime
   * @return
   */

  public static boolean isSameDay(Long firstTime, Long secondTime){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    return sdf.format(firstTime).equals(sdf.format(secondTime));
  }
}
