package com.ljwm.gecko.admin.model.form;

import com.ljwm.bootbase.dto.CommonQuery;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MemberQuery extends CommonQuery {

  private Integer companyId;
}
