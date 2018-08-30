package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.Advertisement;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.enums.EquipTypeEnum;
import com.ljwm.gecko.base.mapper.AdvertisementMapper;
import com.ljwm.gecko.base.model.dto.ClientAdvertisementDto;
import com.ljwm.gecko.base.model.form.AdvertisementQuery;
import com.ljwm.gecko.base.model.form.AdvertisementForm;
import com.ljwm.gecko.base.model.vo.AdvertisementVo;
import com.ljwm.gecko.base.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@SuppressWarnings("all")
public class AdvertisementService {

  @Autowired
  private CommonService commonService;

  @Autowired
  private AdvertisementMapper advertisementMapper;

  public Page<AdvertisementVo> find(AdvertisementQuery query) {
    EquipTypeEnum equipType = EnumUtil.getEnumByName(EquipTypeEnum.class, query.getText());
    if (equipType != null) query.setText(String.valueOf(equipType.getCode()));
    return commonService.find(query, (p, q) -> {
      List<AdvertisementVo> advertisementVos = advertisementMapper.find(p, BeanUtil.beanToMap(query));
      return advertisementVos.stream().map(ad-> {
        ad.setEquipName(EnumUtil.getEnumBycode(EquipTypeEnum.class, ad.getEquipType()).getName());
        return ad;
      }).collect(Collectors.toList());
    });
  }

  @Transactional
  public Advertisement save(AdvertisementForm form) {
    return Optional.ofNullable(form).map(f -> {
      Advertisement advertisement = null;
      if (f.getId() != null)
        advertisement = advertisementMapper.selectById(f.getId());
      if (advertisement == null) {
        advertisement = new Advertisement();
        advertisement.setCreaterId(SecurityKit.currentId()).setCreateTime(DateUtil.date());
      } else
        advertisement.setUpdaterId(SecurityKit.currentId()).setUpdateTime(DateUtil.date());
      BeanUtil.copyProperties(f, advertisement);
      commonService.insertOrUpdate(advertisement, advertisementMapper);
      return advertisement;
    }).get();
  }

  public void delete(Long id) {
    if (Objects.isNull(advertisementMapper.selectById(id)))
      throw new LogicException(ResultEnum.DATA_ERROR, "未找到id为" + id + "的广告位");
    advertisementMapper.deleteById(id);
  }

  @Transactional
  public void adDisabeld(Long id) {
    Advertisement advertisement = adIsExist(id);
    advertisement.setDisabled(Objects.equals(advertisement.getDisabled(), DisabledEnum.ENABLED.getCode()) ?
      DisabledEnum.DISABLED.getCode() :
      DisabledEnum.ENABLED.getCode());
    commonService.insertOrUpdate(advertisement, advertisementMapper);
  }

  private Advertisement adIsExist(Long id) {
    Advertisement advertisement = advertisementMapper.selectById(id);
    if (advertisement == null) throw new LogicException(ResultEnum.DATA_ERROR, "未找到id为" + id + "的广告位");
    return advertisement;
  }

  public List<AdvertisementVo> findClient(ClientAdvertisementDto clientAdvertisementDto){
    return advertisementMapper.find(BeanUtil.beanToMap(clientAdvertisementDto));
  }
}
