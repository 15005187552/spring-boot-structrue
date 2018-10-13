package com.ljwm.gecko.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

  public static String getCurrentTime(){
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
    return df.format(new Date());
  }

  public static Date parseString(String str) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.parse(str);
  }

  public static String parseDate(String str) throws ParseException {
    Date date = new Date(str);
    String sdf = new SimpleDateFormat("yyyy-MM-dd").format(date);
    return sdf;
  }
}
