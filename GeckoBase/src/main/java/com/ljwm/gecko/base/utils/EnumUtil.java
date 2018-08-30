package com.ljwm.gecko.base.utils;

import com.ljwm.gecko.base.listener.CommonEnum;

import java.util.LinkedHashMap;

/**
 * @author Janiffy
 * @date 2018/8/24 16:21
 */
public class EnumUtil {
  /**
   * 返回指定编码的'枚举'
   * @param code
   * @return SharedObjTypeEnum
   * @throws
   */
  public static <T extends CommonEnum> T getEnumBycode(Class<T> clazz, int code) {
    for(T _enum : clazz.getEnumConstants())
      if(code == _enum.getCode())
        return _enum;
    return null;
  }

  /**
   * 返回指定编码的'枚举'
   * @param code
   * @return SharedObjTypeEnum
   * @throws
   */
  public static <T extends CommonEnum> T getEnumBycode(Class<T> clazz, String code) {
    return getEnumBycode(clazz, Integer.valueOf(code));
  }

  /**
   * 返回指定名称的'枚举'
   * @param name
   * @return SharedObjTypeEnum
   * @throws
   */
  public static <T extends CommonEnum> T getEnumByName(Class<T> clazz, String name) {
    for(T _enum : clazz.getEnumConstants())
      if(_enum.getName().equals(name))
        return _enum;
    return null;
  }

  public static <E extends CommonEnum> LinkedHashMap<Integer, String> getEnumMap(final Class<E> enumClass) {
    final LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
    for (final E e : enumClass.getEnumConstants()) {
      map.put(e.getCode(), e.getName());
    }
    return map;
  }

  public static <E extends CommonEnum> LinkedHashMap<String, String> getStringEnumMap(final Class<E> enumClass) {
    final LinkedHashMap<String, String> map = new LinkedHashMap<>();
    for (final E e : enumClass.getEnumConstants()) {
      map.put(""+ e.getCode(), e.getName());
    }
    return map;
  }

}
