package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.SpecialDeduction;
import com.ljwm.gecko.base.mapper.SpecialDeductionMapper;
import com.ljwm.gecko.base.model.dto.SpecialDeductionDto;
import com.ljwm.gecko.base.model.dto.SpecialDeductionQueryDto;
import com.ljwm.gecko.base.model.vo.SpecialDeductionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SpecialDeductionService {

  @Autowired
  private SpecialDeductionMapper specialDeductionMapper;

  @Autowired
  private CommonService commonService;

  @Transactional
  public SpecialDeductionVo save(SpecialDeductionDto specialDeductionDto){
    return Optional
      .ofNullable(specialDeductionDto)
      .map(f -> {
        SpecialDeduction specialDeduction = new SpecialDeduction();
        BeanUtil.copyProperties(f, specialDeduction);
        if (f.getId()!=null){
          specialDeductionMapper.updateById(specialDeduction);
        } else {
          specialDeductionMapper.insert(specialDeduction);
        }
        return new SpecialDeductionVo(specialDeduction);
      }).map(SpecialDeductionVo::new).get();
  }

  public List<SpecialDeductionVo> find(){
    return specialDeductionMapper.find();
  }

  @Transactional
  public void delete(Long id){
    if (!specialDeductionMapper.deleteAble(id)){
      throw new LogicException(ResultEnum.DATA_ERROR,"节点已被使用,无法删除!");
    }
    specialDeductionMapper.deleteById(id);
  }

  public Page<SpecialDeductionVo> findPage(SpecialDeductionQueryDto specialDeductionQueryDto) {
    return commonService.find(specialDeductionQueryDto, (p, q) -> specialDeductionMapper.findPage(p, Kv.by("text", specialDeductionQueryDto.getText())));
  }
}
