package com.ljwm.gecko.base.dao;

import com.ljwm.gecko.base.entity.Location;
import com.ljwm.gecko.base.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
