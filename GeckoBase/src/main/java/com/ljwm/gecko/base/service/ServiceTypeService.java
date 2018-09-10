package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.gecko.base.entity.ServiceType;
import com.ljwm.gecko.base.mapper.ServiceTypeMapper;
import com.ljwm.gecko.base.model.dto.ServeDto;
import com.ljwm.gecko.base.model.vo.ServeSimpleVo;
import com.ljwm.gecko.base.model.vo.ServiceVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ServiceTypeService {

  @Autowired
  private ServiceTypeMapper serviceTypeMapper;

  public List<ServiceVo> find(){
    return serviceTypeMapper.find();
  }

  @Transactional
  public ServeSimpleVo save(ServeDto serveDto){
    return Optional
      .ofNullable(serveDto)
      .map(f -> {
        if (f.getPid()!=null){
          ServiceType service = serviceTypeMapper.selectById(f.getPid());
          if (service==null){
            throw new LogicException(ResultEnum.DATA_ERROR, "找不到要修改的“id为" + f.getId() + "”节点!");
          }
        }
        ServiceType service = new ServiceType();
        BeanUtil.copyProperties(f,service);
        if (f.getId()==null){
          serviceTypeMapper.insert(service);
        }else {
          serviceTypeMapper.updateById(service);
        }
        return new ServeSimpleVo(service);
      })
      .map(ServeSimpleVo::new).get();
  }
}
