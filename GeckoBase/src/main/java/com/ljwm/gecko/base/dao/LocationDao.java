package com.ljwm.gecko.base.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.ljwm.gecko.base.entity.Location;
import com.ljwm.gecko.base.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/8/29 16:29
 */
@Repository
public class LocationDao {

  @Autowired
  LocationMapper locationMapper;

  public void insertOrUpdate(Location location) {
    Location findLocation = locationMapper.findByCode(location.getCode());
    if(findLocation != null){
      locationMapper.updateById(location);
    }else {
      locationMapper.insert(location);
    }
  }

  public List<Location> selectProvinceByLevel() {
    return locationMapper.selectProvinceByLevel();
  }

  public List<Location> selectByCode(Integer code) {
    return locationMapper.selectByCode(code);
  }

  public Integer getProvinceCode(String name) {
    Map<String, Object> map = new HashMap<>();
    map.put(Location.NAME, name);
    map.put(Location.LEVEL, "0");
    List<Location> list = locationMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      return list.get(0).getCode();
    }
    return null;
  }

  public Integer getCityCode(String city, Integer provinceCode) {
    Map<String, Object> map = new HashMap<>();
    map.put("NAME", city);
    map.put("LEVEL", "1");
    map.put("PCODE", provinceCode);
    List<Location> list = locationMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      return list.get(0).getCode();
    }
    return null;
  }

  public Integer getAreaCode(String area, Integer cityCode) {
    Map<String, Object> map = new HashMap<>();
    map.put("NAME", area);
    map.put("LEVEL", "2");
    map.put("PCODE", cityCode);
    List<Location> list = locationMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      return list.get(0).getCode();
    }
    return null;
  }

  public String getNameByCode(String code) {
    Map<String ,Object> map = new HashMap<>();
    map.put("CODE", code);
    List<Location> list = locationMapper.selectByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      return list.get(0).getName();
    }
    return null;
  }
}
