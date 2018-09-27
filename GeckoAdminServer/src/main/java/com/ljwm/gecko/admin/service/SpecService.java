package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.Spec;
import com.ljwm.gecko.base.mapper.SpecMapper;
import com.ljwm.gecko.base.model.dto.SpecCommonQueryDto;
import com.ljwm.gecko.base.model.dto.SpecDto;
import com.ljwm.gecko.base.model.dto.SpecQueryDto;
import com.ljwm.gecko.base.model.vo.SpecSimpleVo;
import com.ljwm.gecko.base.model.vo.SpecVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SpecService {

  @Autowired
  private SpecMapper specMapper;

  @Autowired
  private CommonService commonService;

  public List<SpecSimpleVo> find(SpecCommonQueryDto specCommonQueryDto){
    return specMapper.find(BeanUtil.beanToMap(specCommonQueryDto));
  }

  public Page<SpecVo> findByPage(SpecQueryDto specQueryDto){
    return commonService.find(specQueryDto, (p, q) -> specMapper.findByPage(p, BeanUtil.beanToMap(specQueryDto)));
  }

  @Transactional
  public void save(SpecDto specDto){
    if (specDto.getId()!=null){
      //修改
      SpecSimpleVo specVo = specMapper.findByMap(Kv.by("id",specDto.getId()).set("serviceId",specDto.getServiceId()).set("name",specDto.getName()));
      if (specVo!=null){
        log.info("服务规格 服务类型id {},服务名称{},已经存在！",specDto.getServiceId(),specDto.getName());
        throw new LogicException(ResultEnum.DATA_ERROR,"规格名称已经存在!");
      }
      Spec spec = specMapper.selectById(specDto.getId());
      if (spec==null){
        log.info("服务规格 服务规格id{},查询不到此规格信息!",specDto.getId());
        throw new LogicException(ResultEnum.DATA_ERROR,"查询不到此规格信息!");
      }
      BeanUtil.copyProperties(specDto,spec);
      specMapper.updateById(spec);
    }else {
      Spec spec = new Spec();
      BeanUtil.copyProperties(specDto,spec);
      spec.setCreateTime(DateUtil.date());
      specMapper.insert(spec);
    }
  }
}
