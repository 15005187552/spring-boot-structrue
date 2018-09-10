package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.ServiceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ServiceVo extends ServiceType {

  private Boolean deleteAble;

  private List<ServiceVo> children;
}
