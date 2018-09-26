package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.Spec;
import com.ljwm.gecko.base.entity.SpecItem;
import com.ljwm.gecko.base.mapper.SpecItemMapper;
import com.ljwm.gecko.base.model.dto.*;
import com.ljwm.gecko.base.model.vo.SpecItemSimpleVo;
import com.ljwm.gecko.base.model.vo.SpecSimpleVo;
import com.ljwm.gecko.base.model.vo.SpecVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SpecItemService {

  @Autowired
  private SpecItemMapper specItemMapper;

  @Autowired
  private CommonService commonService;

  public List<SpecItemSimpleVo> find(SpecItemCommonQueryDto specItemCommonQueryDto){
    return specItemMapper.find(BeanUtil.beanToMap(specItemCommonQueryDto));
  }

  public Page<SpecVo> findByPage(SpecItemQueryDto specItemQueryDto){
    return commonService.find(specItemQueryDto, (p, q) -> specItemMapper.findByPage(p, BeanUtil.beanToMap(specItemQueryDto)));
  }

  @Transactional
  public void save(SpecItemDto specItemDto){
    if (specItemDto.getId()!=null){
      //修改
      SpecItemSimpleVo specItemSimpleVo = specItemMapper.findByMap(Kv.by("id",specItemDto.getId()).set("specId",specItemDto.getSpecId()).set("item",specItemDto.getItem()));
      if (specItemSimpleVo!=null){
        log.info("服务规格项 服务规格id {},服务项名称{},已经存在！",specItemDto.getSpecId(),specItemDto.getItem());
        throw new LogicException(ResultEnum.DATA_ERROR,"规格项名称已经存在!");
      }
      SpecItem specItem = specItemMapper.selectById(specItemDto.getId());
      if (specItem==null){
        log.info("服务规格 服务规格项id{},查询不到此规格信息!",specItemDto.getId());
        throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此规格项信息!");
      }
      BeanUtil.copyProperties(specItemDto,specItem);
      specItemMapper.updateById(specItem);
    }else {
      SpecItem specItem = new SpecItem();
      BeanUtil.copyProperties(specItemDto,specItem);
      specItem.setCreateTime(DateUtil.date());
      specItemMapper.insert(specItem);
    }
  }
}
