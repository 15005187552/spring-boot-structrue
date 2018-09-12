package com.ljwm.gecko.base.model.vo.admin;

import cn.hutool.core.bean.BeanUtil;
import com.ljwm.gecko.base.entity.ServiceType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class ServiceTypeTree extends ServiceType {

  private List<ServiceType> children;

  public ServiceTypeTree(ServiceType serviceType) {
    BeanUtil.copyProperties(serviceType, this);
    this.children = new ArrayList<>();
  }

  public static List<ServiceTypeTree> createServiceTree(List<ServiceType> serviceTypes) {
    return serviceTypes.stream().filter(serviceType -> Objects.equals(serviceType.getLevel(), 0))
      .map(ServiceTypeTree::new).map(root -> {
        root.setChildren(fillTree(root, serviceTypes));
        return root;
      }).collect(Collectors.toList());
  }

  private static List<ServiceType> fillTree(ServiceTypeTree root, List<ServiceType> serviceTypes) {
    return serviceTypes.stream().filter(serviceType -> Objects.equals(serviceType.getPid(), root.getId())).collect(Collectors.toList());
  }
}
