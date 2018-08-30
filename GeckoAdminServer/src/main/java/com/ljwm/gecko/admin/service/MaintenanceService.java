package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.form.MemberQuery;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.model.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@SuppressWarnings("all")
public class MaintenanceService {

  @Autowired
  private MemberMapper memberMapper;

  @Autowired
  private CommonService commonService;

  public Page<MemberVo> find(MemberQuery query){
    return commonService.find(query,(p,q)->memberMapper.find(p, BeanUtil.beanToMap(query)));
  }
}
