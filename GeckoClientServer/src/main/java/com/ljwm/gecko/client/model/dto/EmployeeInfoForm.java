package com.ljwm.gecko.client.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Janiffy
 * @date 2018/9/27 14:34
 */
@Data
public class EmployeeInfoForm {

  private Long companyId;

  private List<PersonInfoDto> list;
}
