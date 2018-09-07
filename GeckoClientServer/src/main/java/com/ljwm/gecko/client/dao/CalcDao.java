package com.ljwm.gecko.client.dao;

import com.ljwm.gecko.base.entity.CityItem;
import com.ljwm.gecko.base.mapper.CityItemMapper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/9/7 9:48
 */
@Repository
public class CalcDao {
  @Autowired
  CityItemMapper cityItemMapper;

  public List<CityItem> calc(Integer code) {
    Map<String, Object> map = new HashedMap();
    map.put("REGION_CODE", code);
    return cityItemMapper.selectByMap(map);
  }
}
