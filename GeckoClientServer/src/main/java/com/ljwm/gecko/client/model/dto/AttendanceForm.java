package com.ljwm.gecko.client.model.dto;


import lombok.Data;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/17 14:54
 */
@Data
public class AttendanceForm {

  private String declareTime;

  private Integer declareType;

  private List<AttendanceDto> list;

  @Data
  public class AttendanceDto{

    private String name;

    private String certificate;

    private String idCard;

    private List<AttendanceData> dataList;

    @Data
    public class AttendanceData{

      private Long id;

      private String value;
    }

  }


}
