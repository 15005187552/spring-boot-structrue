package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.ServiceType;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.enums.IsTopEnum;
import com.ljwm.gecko.base.mapper.ServiceTypeMapper;
import com.ljwm.gecko.base.model.dto.ServeDto;
import com.ljwm.gecko.base.model.form.ServicePathForm;
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
import java.util.stream.Collectors;

@Service
@Slf4j
@SuppressWarnings("all")
public class ServiceTypeService {

  @Autowired
  private CommonService commonService;

  @Autowired
  private ServiceTypeMapper serviceTypeMapper;

  public List<ServiceVo> find() {
    return serviceTypeMapper.findTree();
  }

  public Page<ServiceVo> findByPage(ServiceTypeQuery query) {
    return commonService.find(query,(p,q) -> serviceTypeMapper.find(p,BeanUtil.beanToMap(query)));
  }

  @Transactional
  public ServeSimpleVo save(ServeDto serveDto) {
    return Optional
      .ofNullable(serveDto)
      .map(f -> {
        ServiceType service = null;
        if (f.getPid() != null)
          service = serviceTypeMapper.selectById(f.getPid());
        if (service == null)
          service = new ServiceType();
        BeanUtil.copyProperties(f,service);
        commonService.insertOrUpdate(service,serviceTypeMapper);
        return new ServeSimpleVo(service);
      })
      .map(ServeSimpleVo::new).get();
  }

  @Transactional
  public Boolean disabled(Integer id) {
    ServiceType serviceType = serviceTypeMapper.selectById(id);
    if (Objects.isNull(serviceType)) throw new LogicException(ResultEnum.DATA_ERROR,"未找到id为" + id + "的服务分类");
    serviceType.setDisabled(
      Objects.equals(serviceType.getDisabled(),DisabledEnum.ENABLED.getInfo()) ? DisabledEnum.DISABLED.getInfo() : DisabledEnum.ENABLED.getInfo()
    );
    serviceTypeMapper.updateById(serviceType);
    return serviceType.getDisabled();
  }

  public List<ServeSimpleVo> findTopList() {
    return serviceTypeMapper.findTopList();
  }

  public List<ServiceType> findTypeByLevel(Integer level) {
    return serviceTypeMapper.selectList(
      new QueryWrapper<ServiceType>()
        .eq("LEVEL",level)
    );
  }

  @Transactional
  public Integer savePath(ServicePathForm form) {
    ServiceType serviceType = serviceTypeMapper.selectById(form.getId());
    if (serviceType == null) throw new LogicException("未找到id为" + form.getId() + "的服务类型");
    serviceType.setAvatarPath(form.getPath());
    return serviceTypeMapper.updateById(serviceType);
  }

  @Transactional
  public Integer saveTop(Integer id) {
    List<ServiceType> serviceTypes = serviceTypeMapper.selectList(new QueryWrapper<ServiceType>().eq("IS_TOP",IsTopEnum.TOP.getCode()));

    ServiceType serviceType = serviceTypeMapper.selectById(id);
    if (serviceType == null) throw new LogicException(ResultEnum.DATA_ERROR,"未找到id为" + id + "的服务类型");

    Integer maxSort = serviceTypes.stream()
      .filter(i -> !Objects.isNull(i.getSort()))
      .mapToInt(ServiceType::getSort).max()
      .orElse(0);

    serviceTypeMapper.updateById(
      serviceType
        .setSort(Objects.equals(serviceType.getIsTop(),IsTopEnum.TOP.getCode()) ? serviceType.getSort() : maxSort + 1)
        .setIsTop(Objects.equals(serviceType.getIsTop(),IsTopEnum.TOP.getCode()) ? IsTopEnum.NOT_TOP.getCode() : IsTopEnum.TOP.getCode())
    );
    return serviceType.getIsTop();
  }
}
