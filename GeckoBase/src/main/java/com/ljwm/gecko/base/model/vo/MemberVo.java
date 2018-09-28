package com.ljwm.gecko.base.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ljwm.gecko.base.config.FilePathAppend;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.MemberAccount;
import com.ljwm.gecko.base.entity.NaturalPerson;
import com.ljwm.gecko.base.serializer.PathToUrlSerializer;
import com.ljwm.gecko.base.serializer.StatusWithNameSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by yuzhou on 2018/8/22.
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class MemberVo extends Member {

  private AccountVo account;

  NaturalPersonVo naturalPerson;

  List<MemberAccount> memberAccounts;

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String picFront;

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String picBack;

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String picPassport;

  @JSONField(serializeUsing = PathToUrlSerializer.UrlSeralizer.class)
  private String avatarPath;

  @JSONField(serializeUsing = StatusWithNameSerializer.ValidateStatSerializer.class)
  private Integer validateState;

  private List<MemberPaperVo> memberPaperVoList;
}
