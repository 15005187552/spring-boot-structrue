package com.ljwm.gecko.admin.model.form;

import com.ljwm.bootbase.dto.CommonQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class DictQueryForm extends CommonQuery {
}
