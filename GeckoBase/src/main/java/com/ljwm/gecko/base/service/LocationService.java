package com.ljwm.gecko.base.service;

import com.ljwm.gecko.base.entity.Location;
import com.ljwm.gecko.base.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/8/24 11:47
 */
@Service
public class LocationService {
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

  public List<Location> getProvince() {
    return locationMapper.selectProvinceByLevel();
  }

  public List<Location> getCityOrArea(Integer code) {
    return locationMapper.selectByCode(code);
  }
}
