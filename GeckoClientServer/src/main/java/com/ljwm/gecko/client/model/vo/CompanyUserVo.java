package com.ljwm.gecko.client.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Janiffy
 * @date 2018/9/25 15:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyUserVo extends CompanyUser {

  private String nickName;

  private String name;

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String avatarPath;
}
