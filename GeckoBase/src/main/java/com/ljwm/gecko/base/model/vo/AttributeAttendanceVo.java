package com.ljwm.gecko.base.model.vo;

import com.ljwm.gecko.base.entity.AttendanceTemplate;
import com.ljwm.gecko.base.entity.Attribute;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Janiffy
 * @date 2018/9/26 20:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AttributeAttendanceVo extends Attribute {

  private AttendanceTemplate attendanceTemplate;

}
