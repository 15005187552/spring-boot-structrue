package com.ljwm.gecko.provider.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.entity.SpecServicesPrice;
import com.ljwm.gecko.base.enums.InfoValidateStateEnum;
import com.ljwm.gecko.base.mapper.ProviderMapper;
import com.ljwm.gecko.base.mapper.ProviderServicesMapper;
import com.ljwm.gecko.base.mapper.SpecServicesPriceMapper;
import com.ljwm.gecko.base.model.vo.ProviderServicesVo;
import com.ljwm.gecko.base.model.vo.ProviderSimpleVo;
import com.ljwm.gecko.base.model.vo.SpecServicesPriceSimpleVo;
import com.ljwm.gecko.provider.model.form.SpecServicesPriceCommonQueryForm;
import com.ljwm.gecko.provider.model.form.SpecServicesPriceForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class SpecServicesPriceService {

  @Autowired
  private SpecServicesPriceMapper specServicesPriceMapper;

  @Autowired
  private ProviderServicesMapper providerServicesMapper;

  @Autowired
  private ProviderMapper providerMapper;

  @Transactional
  public void save(SpecServicesPriceForm specServicesPriceForm){
    if (specServicesPriceForm.getId()!=null){
      //更新
      SpecServicesPriceSimpleVo specServicesPriceSimpleVo = specServicesPriceMapper.findByMap(Kv.by("id",specServicesPriceForm.getId()).set("providerId",specServicesPriceForm.getProviderId()).set("key",specServicesPriceForm.getKey()).set("goodId",specServicesPriceForm.getGoodId()));
      if (specServicesPriceSimpleVo!=null){
        log.info("商品规则id:{},服务商id:{},key:{},商品id:{}",specServicesPriceForm.getId(),specServicesPriceForm.getProviderId(),specServicesPriceForm.getKey(),specServicesPriceForm.getGoodId());
        throw new LogicException(ResultEnum.DATA_ERROR,"商品规则已经存在,不可重复添加!");
      }
      SpecServicesPrice specServicesPrice = specServicesPriceMapper.selectById(specServicesPriceForm.getId());
      if (specServicesPrice==null){
        log.info("根据商品规则id:{},查询不到此商品规则信息!",specServicesPriceForm.getId());
        throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此商品规则信息!");
      }
      BeanUtil.copyProperties(specServicesPriceForm,specServicesPrice);
      specServicesPriceMapper.updateById(specServicesPrice);
    }else {
      //新增
      /*
      ProviderSimpleVo providerVo = providerMapper.findProviderSimpleVoByMemberId(SecurityKit.currentUser().getId());
      if (providerVo!=null && Objects.equals(providerVo.getInfoValidateState(),InfoValidateStateEnum.CONFIRM_SUCCESS.getCode())){
        specServicesPriceForm.setProviderId(providerVo.getId());
      }else {
        log.info("此用户{}非服务商管理员用户!",SecurityKit.currentUser().getId().toString());
        throw new LogicException(ResultEnum.DATA_ERROR,"此用户非服务商管理员用户!");
      }
      */
      SpecServicesPrice specServicesPrice = new SpecServicesPrice();
      BeanUtil.copyProperties(specServicesPriceForm,specServicesPrice);
      specServicesPrice.setCreateTime(DateUtil.date());
      specServicesPriceMapper.insert(specServicesPrice);
    }
  }

  public List<SpecServicesPriceSimpleVo> find(SpecServicesPriceCommonQueryForm specServicesPriceCommonQueryForm){
    return specServicesPriceMapper.find(BeanUtil.beanToMap(specServicesPriceCommonQueryForm));
  }

  public List<ProviderServicesVo> findProviderServicesListByProviderId(Long providerId){
    return providerServicesMapper.findProviderServicesDetailVoListByProviderId(providerId);
  }

  @Transactional
  public Boolean disabled(Long id){
    return specServicesPriceMapper.disabledSpecService(id)>=1;
  }
}
