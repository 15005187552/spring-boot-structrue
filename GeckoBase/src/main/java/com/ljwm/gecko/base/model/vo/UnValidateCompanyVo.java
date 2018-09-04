package com.ljwm.gecko.base.model.vo;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.model.dto.AdminCompanyDto;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UnValidateCompanyVo extends Company {

//  @JSONField(serializeUsing = )
//  private Integer type;

  @JSONField(serialize = false)
  private Integer validateState;

  @JSONField(serialize = false)
  private Integer validaterId;

  @JSONField(serialize = false)
  private Date validateTime;

  @JSONField(serialize = false)
  private String validateText;

//  public UnValidateCompanyVo(AdminCompanyDto adminCompanyDto) {
//    if (adminCompanyDto != null)
//      BeanUtil.copyProperties(adminCompanyDto, this);
//  }
}
