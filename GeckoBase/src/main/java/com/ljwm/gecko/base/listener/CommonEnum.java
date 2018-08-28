package com.ljwm.gecko.base.listener;

/**
 * @author Janiffy
 * @date 2018/8/24 16:25
 * 枚举接口
 */
public interface CommonEnum {
  //此处对应枚举的字段,如状态枚举CompanyType定义了code,name
  //那么这里定义这个两个字段的get方法,可以获取到所有的字段
  Integer getCode();

  String getName();

}
