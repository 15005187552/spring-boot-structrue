package com.ljwm.gecko.base.model.vo.admin;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.Paper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class PaperAdminVo extends Paper {

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;

  private Boolean deleteAble;
}
