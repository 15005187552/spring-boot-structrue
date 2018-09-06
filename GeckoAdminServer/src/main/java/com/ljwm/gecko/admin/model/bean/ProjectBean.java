package com.ljwm.gecko.admin.model.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ProjectBean {

  @Excel(name = "项目")
  private String project;
}
