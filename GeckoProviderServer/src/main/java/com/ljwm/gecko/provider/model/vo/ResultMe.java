package com.ljwm.gecko.provider.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.model.vo.ProviderSimpleVo;
import com.ljwm.gecko.base.model.vo.ProviderVo;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by yuzhou on 2018/8/22.
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("当前用户信息")
public class ResultMe {

  private Boolean isProvider;

  private Long id;

  private String username;

  @JSONField(name = "regMobile")
  private String phoneNum;

  private String nickName;

  private String extInfo;

  private String token;

  private String avatarPath;

  private ProviderSimpleVo provider;
}
