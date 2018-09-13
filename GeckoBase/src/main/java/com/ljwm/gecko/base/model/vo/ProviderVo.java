package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ljwm.gecko.base.entity.Provider;
import com.ljwm.gecko.base.entity.ServiceType;
import com.ljwm.gecko.base.model.vo.admin.ServiceTypeTree;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProviderVo extends Provider {

  @JSONField(serializeUsing = StatusWithNameSerializer.ProviderValidateStatSerializer.class)
  private Integer validateState;

  private List<ProviderPaperVo> providerPaperVoList;

  private List<ServiceType> serviceTypes;

  private List<ServiceTypeTree> serviceTypeTrees;


}
