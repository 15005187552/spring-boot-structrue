package com.ljwm.gecko.client.model.vo;

import com.ljwm.gecko.base.model.vo.CompanyVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Janiffy
 * @date 2018/9/15 13:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyInfoVo extends CompanyVo {

  private boolean flag;

}
