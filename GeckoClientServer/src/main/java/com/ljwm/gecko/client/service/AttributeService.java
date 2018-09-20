package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.entity.Attribute;
import com.ljwm.gecko.base.mapper.AttributeMapper;
import com.ljwm.gecko.client.model.vo.AttributeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/20 18:14
 */
@Service
public class AttributeService {

  @Autowired
  AttributeMapper attributeMapper;

  public Result getAllAttribute() {
    List<Attribute> list = attributeMapper.selectList(null);
    List<AttributeVo> attributeVoList = new ArrayList<>();
    for (Attribute attribute : list) {
      AttributeVo attributeVo = new AttributeVo();
      BeanUtil.copyProperties(attribute, attributeVo);
      attributeVoList.add(attributeVo);
    }
    return Result.success(attributeVoList);
  }
}
