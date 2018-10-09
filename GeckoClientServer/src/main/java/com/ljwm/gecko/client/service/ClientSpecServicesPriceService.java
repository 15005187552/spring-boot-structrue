package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.SpecServicesPrice;
import com.ljwm.gecko.base.mapper.SpecServicesPriceMapper;
import com.ljwm.gecko.base.model.vo.SpecServicesPriceSimpleVo;
import com.ljwm.gecko.client.model.dto.SpecServicePriceQueryForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ClientSpecServicesPriceService {

  @Autowired
  private SpecServicesPriceMapper specServicesPriceMapper;


  public SpecServicesPriceSimpleVo findSpecServicesPrice(SpecServicePriceQueryForm specServicePriceQueryForm){
    return specServicesPriceMapper.findByMap(BeanUtil.beanToMap(specServicePriceQueryForm));
  }

  public List<String> findProviderKeys(Integer serviceId,Long providerId){
    List keysList = Lists.newArrayList();
    List<SpecServicesPrice> specServicesPriceList = specServicesPriceMapper.selectByMap(Kv.by("SERVICE_ID",serviceId).set("PROVIDER_ID",providerId));
    if (CollectionUtils.isNotEmpty(specServicesPriceList)){
      for (SpecServicesPrice specServicesPrice: specServicesPriceList){
        List list = Arrays.asList(specServicesPrice.getKey().split("\\_"));
        if (CollectionUtils.isNotEmpty(list)){
          keysList.removeAll(list);
          keysList.addAll(list);
        }
      }
    }
    Collections.sort(keysList, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        if(Integer.valueOf(o1).intValue() > Integer.valueOf(o2).intValue()){
          return 1;
        }
        if(Objects.equals(Integer.valueOf(o1),Integer.valueOf(o2))){
          return 0;
        }
        if(Integer.valueOf(o1).intValue() < Integer.valueOf(o2).intValue()){
          return -1;
        }
        return 0;
      }
    });
    return keysList;
  }
}
