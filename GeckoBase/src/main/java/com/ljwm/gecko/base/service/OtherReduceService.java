package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

  public OtherReduce save(OtherReduceForm form) {
    return Optional.ofNullable(form).map(f -> {
      OtherReduce otherReduce = null;
      if (Objects.isNull(f.getId())) {
        otherReduceMapper.updateById(otherReduce);
      }
      otherReduceMapper.insert(otherReduce);
      return otherReduce;
    }).get();
  }
}
