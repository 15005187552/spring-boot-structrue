package com.ljwm.gecko.base.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * JKhaled created by yunqisong@foxmail.com 2018-3-18
 * FOR : 钱转化工具
 */
@SuppressWarnings("unchecked")
public class MoneyKit {

  public static String money(Number number) {

    return new BigDecimal((number).doubleValue()).setScale(2, RoundingMode.HALF_UP).toString();
  }

  public static String getFen(Object price) {
    if (price == null) return "";
    String amount = "";
    if (price instanceof Number) amount = money((Number) price);
    if (price instanceof String) amount = (String) price;
    // 金额转化为分为单位
    String currency = amount.replaceAll("[$￥,]", "");  //处理包含, ￥ 或者$的金额
    int index = currency.indexOf(".");
    int length = currency.length();
    Long amLong;
    if (index == -1) {
      amLong = Long.valueOf(currency + "00");
    } else if (length - index >= 3) {
      amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
    } else if (length - index == 2) {
      amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
    } else {
      amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
    }
    return amLong.toString();
  }


}
