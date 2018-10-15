package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.IncomeType;
import com.ljwm.gecko.base.mapper.IncomeTypeMapper;
import com.ljwm.gecko.base.model.dto.IncomeTypeDto;
import com.ljwm.gecko.base.model.dto.IncomeTypeQueryDto;
import com.ljwm.gecko.base.model.form.AttributeForm;
import com.ljwm.gecko.base.model.vo.IncomeTypeSimpleVo;
import com.ljwm.gecko.base.model.vo.IncomeTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@SuppressWarnings("all")
public class IncomeTypeService {

  @Autowired
  private CommonService commonService;

  @Autowired
  private IncomeTypeMapper incomeTypeMapper;

  @Autowired
  private AttributeAdminService attributeAdminService;

  public Page<IncomeTypeVo> findPage(IncomeTypeQueryDto incomeTypeQueryDto) {
    return commonService.find(incomeTypeQueryDto,(p,q) -> incomeTypeMapper.findPage(p,Kv.by("text",incomeTypeQueryDto.getText()).set("pId",incomeTypeQueryDto.getPId())));
  }

  public List<IncomeTypeVo> find() {
    return incomeTypeMapper.find();
  }

  @Transactional
  public void delete(Long id) {
    if (!incomeTypeMapper.deleteAble(id)) {
      throw new LogicException(ResultEnum.DATA_ERROR,"节点已使用,无法删除");
    }
    incomeTypeMapper.deleteById(id);
    attributeAdminService.delete(IncomeType.class,id);
  }

  @Transactional
  public IncomeTypeSimpleVo save(IncomeTypeDto incomeTypeDto) {
    return Optional
      .ofNullable(incomeTypeDto)
      .map(f -> {
        if (f.getPId() != null) {
          IncomeType incomeType = incomeTypeMapper.selectById(f.getPId());
          if (incomeType == null) {
            throw new LogicException(ResultEnum.DATA_ERROR,"找不到要修改的“id为" + f.getId() + "”节点!");
          }
        }
        IncomeType incomeType = new IncomeType();
        BeanUtil.copyProperties(f,incomeType);
        if (f.getId() == null) {
          incomeTypeMapper.insert(incomeType);
        } else {
          incomeTypeMapper.updateById(incomeType);
        }
        return incomeType;
      })
      // TODO 简化添加 重复添加冗余数据 为客服端版服务
      .map(bean -> {
        attributeAdminService.save(bean.getClass(),new AttributeForm().setItemId(bean.getId()).setName(bean.getName()));
        return bean;
      })
      .map(IncomeTypeSimpleVo::new).get();
  }
}
