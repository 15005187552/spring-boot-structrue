package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.Function;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * stoa Created by yunqisong on 2018/7/8/008.
 * FOR:
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("ALL")
@EqualsAndHashCode(callSuper = false)
public class FunctionTree extends Function {

  private List<FunctionTree> children;
}
