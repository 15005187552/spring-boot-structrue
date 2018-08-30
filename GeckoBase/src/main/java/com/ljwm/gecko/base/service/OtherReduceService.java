package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.OtherReduce;
import com.ljwm.gecko.base.mapper.OtherReduceMapper;
import com.ljwm.gecko.base.model.form.OtherReduceForm;
import com.ljwm.gecko.base.model.form.OtherReduceQuery;
import com.ljwm.gecko.base.model.vo.OtherReduceVo;
import com.ljwm.gecko.base.model.vo.SpecialDeductionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@SuppressWarnings("all")
public class OtherReduceService {

  @Autowired
  private CommonService commonService;

  @Autowired
  private OtherReduceMapper otherReduceMapper;

  public Page<OtherReduceVo> find(OtherReduceQuery query) {
    return commonService.find(query, (p, q) -> otherReduceMapper.find(p, BeanUtil.beanToMap(query)));
  }

  @Transactional
  public OtherReduce save(OtherReduceForm form) {
    return Optional.ofNullable(form).map(f -> {
      OtherReduce otherReduce = new OtherReduce();
      BeanUtil.copyProperties(f,otherReduce);
      if (!Objects.isNull(f.getId())) {
        otherReduceMapper.updateById(otherReduce);
      }
      otherReduceMapper.insert(otherReduce);
      return otherReduce;
    }).get();
  }

  @Transactional
  public void delete(Long id){
    if (!otherReduceMapper.deleteAble(id)){
      throw new LogicException(ResultEnum.DATA_ERROR,"节点已被使用,无法删除!");
    }
    otherReduceMapper.deleteById(id);
  }
}
