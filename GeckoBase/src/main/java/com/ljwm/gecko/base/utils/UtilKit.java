package com.ljwm.gecko.base.utils;

import cn.hutool.core.bean.BeanPath;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.*;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JKhaled created by yunqisong@foxmail.com 2018-3-19
 * FOR : 常用工具类
 */
@SuppressWarnings({"unchecked"})
@Slf4j
public class UtilKit {

  /**
   * 创建Salt
   *
   * @return
   */
  public static String salt() {
    return RandomUtil.randomInt(1000, 9999) + "";
  }

  /**
   * 创建单号
   *
   * @param pre
   * @return
   */
  public static String createNum(String pre) {
    Date now = DateUtil.date();
    return pre + DateUtil.format(now, "yyyyMMddHHmmss");
  }

  /**
   * 表达式取值
   *
   * @param expression
   * @param source
   * @param <T>
   * @return
   */
  public static <T> T getTargetByExpression(String expression, Object source) {

    return (T) BeanPath.create(expression).get(source);
  }

  /**
   * Json字符串中表达式取值
   *
   * @param expression
   * @param jsonStr
   * @param <T>
   * @return
   */
  public static <T> T getTargetByExpression(String expression, String jsonStr) {

    return (T) BeanPath.create(expression).get(JSONObject.parseObject(jsonStr));
  }


  /**
   * 打印数组
   *
   * @param ts
   * @param <T>
   */
  public static <T> void printArray(T[] ts) {
    if (ts != null && ts.length > 0) {
      for (int i = 0; i < ts.length; i++) {
        log.info("target[" + i + "]: {}", ts[i]);
      }
    }
  }

  /**
   * 构建值数组
   *
   * @param values
   * @return
   */
  public static BigDecimal[] createdByString(String values) {
    if (StrUtil.isNotBlank(values))
      return Arrays.stream(values.split(",")).map(BigDecimal::new).toArray(BigDecimal[]::new);
    return null;
  }

  /**
   * 获取当前IP
   *
   * @return
   */
  public static String currentIp() {
    try {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      return HttpUtil.getClientIP(request);
    } catch (Exception e) {
      return "127.0.0.1";
    }
  }

  /**
   * 替换忽略大小写
   *
   * @param content
   * @param keyWord
   * @param pre
   * @param end
   * @return
   */
  public static String replaceIgnoreCase(String content, String keyWord, String pre, String end) {
    String wordReg = "(?i)" + keyWord;
    StringBuffer sb = new StringBuffer();
    Matcher matcher = Pattern.compile(wordReg).matcher(content);
    while (matcher.find()) {
      matcher.appendReplacement(sb, pre + matcher.group() + end);//这样保证了原文的大小写没有发生变化
    }
    matcher.appendTail(sb);
    return sb.toString();
  }

  /**
   * 名字转Class安全
   *
   * @param name
   * @return
   */
  public static Class<?> classFormName(String name) {
    try {
      return Class.forName(name);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 替换数组分界字符
   *
   * @param str
   * @return
   */
  public static Integer[] getIdsByStr(String str) {
    if (StrUtil.isBlank(str)) return null;
    str = str.replaceAll(ReUtil.escape("["), "").replaceAll(ReUtil.escape("]"), "");
    String[] idStrs = str.split(",");
    return Arrays.stream(idStrs).map(Integer::valueOf).toArray(Integer[]::new);
  }

  public static String py(String chinese) {
    return PinyinUtil.getPinYin(chinese).replaceAll(ReUtil.escape("*"), "").toUpperCase();
  }

  public static String rate100String(Number number) {

    return number == null ? "" : String.format("%.2f", NumberUtil.mul(100, number)) + '%';
  }

  public static String saveToString(Object o) {
    return o == null ? "" : o.toString();
  }

  public static String saveToStringOr2f(Number number) {
    log.info("Data: {} ", number);
    return number == null ? "" : String.format("%.2f", number);
  }

  public static String saveToStringOr2fMul100(Number number) {
    log.info("Data: {} ", number);
    return number == null ? "" : String.format("%.2f", NumberUtil.mul(100, number));
  }

  public static String quarterChinessName(int i) {

    switch (i) {
      case 1:
        return "第一季度";
      case 2:
        return "第二季度";
      case 3:
        return "第三季度";
      default:
        return "第四季度";
    }
  }

}
