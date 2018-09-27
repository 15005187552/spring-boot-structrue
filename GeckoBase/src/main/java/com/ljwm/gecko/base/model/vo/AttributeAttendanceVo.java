package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.AttendanceTemplate;
import com.ljwm.gecko.base.entity.Attribute;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/26 20:26
 */
@Data
public class AttributeAttendanceVo extends Attribute {

  private AttendanceTemplate attendanceTemplate;

  public AttendanceTemplate getAttendanceTemplate() {
    return attendanceTemplate;
  }

  public void setAttendanceTemplate(AttendanceTemplate attendanceTemplate) {
    this.attendanceTemplate = attendanceTemplate;
  }
}
