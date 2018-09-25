package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.ProviderUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ProviderUserVo extends ProviderUser {
}
