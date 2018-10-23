package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.form.ProtocolForm;
import com.ljwm.gecko.admin.model.form.ProtocolQuery;
import com.ljwm.gecko.base.entity.Protocol;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.ProtocolMapper;
import com.ljwm.gecko.base.model.vo.admin.ProtocolVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Author: xixil
 * Date: 2018/10/19 10:05
 * RUA
 */

@Slf4j
@Service
public class ProtocolService {

  @Autowired
  private CommonService commonService;

  @Autowired
  private ProtocolMapper protocolMapper;

  @Transactional
  public ProtocolVO save(ProtocolForm form) {
    Protocol protocol = null;
    if (form.getId() != null)
      protocol = protocolMapper.selectById(form.getId());
    if (protocol == null)
      protocol = new Protocol();
    BeanUtil.copyProperties(form,protocol);
    commonService.insertOrUpdate(protocol,protocolMapper);
    return new ProtocolVO(protocol);
  }

  public Page<ProtocolVO> find(ProtocolQuery query) {
    return commonService.find(query,(p,q) -> protocolMapper.find(p,BeanUtil.beanToMap(q)));
  }

  @Transactional
  public Boolean disabled(Long id) {
    Protocol protocol = protocolMapper.selectById(id);
    if (protocol == null) throw new LogicException(ResultEnum.DATA_ERROR,"未找到id为:" + id + "的协议");
    protocol.setDisabled(protocol.getDisabled() ? DisabledEnum.ENABLED.getInfo() : DisabledEnum.DISABLED.getInfo());
    protocolMapper.updateById(protocol);
    return protocol.getDisabled();
  }
}
