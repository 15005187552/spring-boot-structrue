package com.ljwm.gecko.base.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Janiffy
 * @date 2018/8/28 18:44
 */
public class IpUtil {

  /*
   * 获取用户的真实ip
   */
  public static String getIPAddr(HttpServletRequest request){
    if (request == null)
      return null;
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("Proxy-Client-IP");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("WL-Proxy-Client-IP");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("HTTP_CLIENT_IP");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
      ip = request.getRemoteAddr();
    if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip))
      try {
        ip = InetAddress.getLocalHost().getHostAddress();
      }
      catch (UnknownHostException unknownhostexception) {
      }
    return ip;
  }


}
