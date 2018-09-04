package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.entity.CityItem;
import com.ljwm.gecko.base.entity.Location;
import com.ljwm.gecko.base.entity.SpecialDeduction;
import com.ljwm.gecko.base.enums.DirectCityEnum;
import com.ljwm.gecko.base.mapper.CityItemMapper;
import com.ljwm.gecko.base.mapper.LocationMapper;
import com.ljwm.gecko.base.mapper.SpecialDeductionMapper;
import com.ljwm.gecko.base.model.dto.LocationRateDetailDto;
import com.ljwm.gecko.base.model.dto.LocationRateDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@SuppressWarnings("all")
public class CityItemService {

  @Autowired
  private SpecialDeductionMapper specialDeductionMapper;

  @Autowired
  private LocationMapper locationMapper;

  @Autowired
  private CityItemMapper cityItemMapper;


  @Transactional
  public void saveLocationRate(List<LocationRateDto> locationRateDtoList) {
    if (CollectionUtils.isEmpty(locationRateDtoList)) {
      throw new LogicException(ResultEnum.DATA_ERROR, "列表不能为空!");
    }
    //迭代省的数据
    for (LocationRateDto locationRateDto : locationRateDtoList) {
      String provinceName = locationRateDto.getProvinceName();
//      if (!provinceName.endsWith("省")&& !provinceName.endsWith("市")){
//          if (EnumUtils.getEnumList(DirectCityEnum.class).stream().map(DirectCityEnum::getName).collect(Collectors.toList()).contains(provinceName)){
//            provinceName =provinceName+"市";
//          }else {
//            provinceName =provinceName+"省";
//          }
//      }
      Location location = locationMapper.findCodeByName(Kv.by("name", provinceName).set("level", 0));
      String code = "";
      if (location != null) {
        code = location.getCode().toString();
      }
      Location subLocation = locationMapper.findCodeByName(Kv.by("name", locationRateDto.getCityName()).set("id", code.substring(0, 2)));
      for (LocationRateDetailDto locationRateDetailDto : locationRateDto.getLocationRateDetailDtoList()) {
        CityItem cityItem = new CityItem();
        BeanUtil.copyProperties(locationRateDetailDto, cityItem);
        SpecialDeduction specialDeduction = specialDeductionMapper.findIdByName(locationRateDetailDto.getName());
        if (specialDeduction != null) {
          cityItem.setItemType(specialDeduction.getId());
        }
        if (subLocation != null) {
          cityItem.setRegionCode(subLocation.getCode());
        }
        cityItem.setCreateTime(DateUtil.date());
        cityItem.setUpdateTime(DateUtil.date());
        cityItem.setCreator(SecurityKit.currentId());
        cityItem.setUpdaterId(SecurityKit.currentId());
        cityItemMapper.insert(cityItem);
        //log.info("地区利率为: {}",cityItem.toString());
      }

    }
  }
}
