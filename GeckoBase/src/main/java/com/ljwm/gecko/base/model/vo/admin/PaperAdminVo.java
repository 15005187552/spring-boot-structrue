package com.ljwm.gecko.base.model.vo.admin;

import com.ljwm.gecko.base.entity.Paper;
import lombok.Data;

@Data
public class PaperAdminVo extends Paper {

  private Boolean deleteAble;
}
