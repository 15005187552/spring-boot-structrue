package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.Attribute;
import lombok.Data;

/**
 * @author Janiffy
 * @date 2018/9/26 19:14
 */
@Data
public class AttributeCompanyVo extends Attribute {

  @JSONField(name = "template")
  private TemplateVo templateVo;

  public TemplateVo getTemplateVo() {
    return templateVo;
  }

  public void setTemplateVo(TemplateVo templateVo) {
    this.templateVo = templateVo;
  }
}
