package com.ljwm.gecko.base.model.dto;

import com.ljwm.gecko.base.entity.Function;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class FunctionDto extends Function {

  private Function parent;

  private List<Function> children;
}
