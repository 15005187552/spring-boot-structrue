package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.ServiceType;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.ServiceTypeMapper;
import com.ljwm.gecko.base.model.dto.ServeDto;
import com.ljwm.gecko.base.model.form.ServiceTypeQuery;
import com.ljwm.gecko.base.model.vo.ServeSimpleVo;
import com.ljwm.gecko.base.model.vo.ServiceVo;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@SuppressWarnings("all")
public class ServiceTypeService {

  @Autowired
  private CommonService commonService;

  @Autowired
  private ServiceTypeMapper serviceTypeMapper;

  public List<ServiceVo> find() {
    return serviceTypeMapper.find();
  }

  public Page<ServiceVo> findByPage(ServiceTypeQuery query) {
    return commonService.find(query, (p, q) -> serviceTypeMapper.find(p, BeanUtil.beanToMap(query)));
  }

  @Transactional
  public ServeSimpleVo save(ServeDto serveDto) {
    return Optional
      .ofNullable(serveDto)
      .map(f -> {
        if (f.getPid() != null) {
          ServiceType service = serviceTypeMapper.selectById(f.getPid());
          if (service == null) {
            throw new LogicException(ResultEnum.DATA_ERROR, "找不到要修改的“id为" + f.getId() + "”节点!");
          }
        }
        ServiceType service = new ServiceType();
        BeanUtil.copyProperties(f, service);
        if (f.getId() == null) {
          serviceTypeMapper.insert(service);
        } else {
          serviceTypeMapper.updateById(service);
        }
        return new ServeSimpleVo(service);
      })
      .map(ServeSimpleVo::new).get();
  }

  @Transactional
  public Boolean disabled(Integer id) {
    ServiceType serviceType = serviceTypeMapper.selectById(id);
    if (Objects.isNull(serviceType)) throw new LogicException(ResultEnum.DATA_ERROR, "未找到id为" + id + "的服务分类");
    serviceType.setDisabled(
      Objects.equals(serviceType.getDisabled(), DisabledEnum.ENABLED.getInfo()) ? DisabledEnum.DISABLED.getInfo() : DisabledEnum.ENABLED.getInfo()
    );
    serviceTypeMapper.updateById(serviceType);
    return serviceType.getDisabled();
  }
}
