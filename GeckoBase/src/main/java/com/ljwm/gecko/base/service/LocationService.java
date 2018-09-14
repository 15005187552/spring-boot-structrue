package com.ljwm.gecko.base.service;

import com.ljwm.gecko.base.dao.LocationDao;
import com.ljwm.gecko.base.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/8/24 11:47
 */
@Service
public class LocationService {
  @Autowired
  LocationDao locationDao;

  @Transactional
  public void insertOrUpdate(Location location) {
    locationDao.insertOrUpdate(location);
  }


  public List<Location> getProvince() {  // 后台地区税率 有使用
    return locationDao.selectProvinceByLevel();
  }

  public List<Location> getCityOrArea(Integer code) {
    return locationDao.selectByCode(code);
  }

  public Integer getProvinceCode(String name){
    return locationDao.getProvinceCode(name);
  }

  public String getNameByCode(String code) {
    return locationDao.getNameByCode(code);
  }
}
