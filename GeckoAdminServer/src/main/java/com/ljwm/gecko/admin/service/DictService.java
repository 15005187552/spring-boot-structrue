package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.form.DictQueryForm;
import com.ljwm.gecko.base.mapper.DictMapper;
import com.ljwm.gecko.base.model.vo.DictVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DictService {

  @Autowired
  private DictMapper dictMapper;

  @Autowired
  private CommonService commonService;

  public Page<DictVo> findByPage(DictQueryForm dictQueryForm){
   return commonService.find(dictQueryForm,(p,q) -> dictMapper.findByPage(p,BeanUtil.beanToMap(q)));
  }
}
