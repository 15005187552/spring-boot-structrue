package com.ljwm.gecko.im.service;

import com.jfinal.wxaapp.WxaConfig;
import com.jfinal.wxaapp.WxaConfigKit;
import com.ljwm.bootbase.dto.Kv;
import com.ljwm.gecko.base.entity.FormId;
import com.ljwm.gecko.base.entity.MemberAccount;
import com.ljwm.gecko.base.enums.FormIdStatusEnum;
import com.ljwm.gecko.base.enums.MPTemplateEnum;
import com.ljwm.gecko.base.mapper.FormIdMapper;
import com.ljwm.gecko.base.mapper.MemberAccountMapper;
import com.ljwm.gecko.base.utils.TemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by user on 2018-3-30.
 */
@Slf4j
@Service
public class MPTemplateService {

  @Autowired
  private FormIdMapper formIdMapper;

  @Autowired
  private MemberAccountMapper memberAccountMapper;

  @Value("${wx.xcx.appId}")
  private String appId;
  @Value("${wx.xcx.appSecret}")
  private String appSecret;

  public Boolean send(Long memberId, MPTemplateEnum mpTemplateEnum, Kv kv) {
    // 1配置(可在启动时配置)
    WxaConfig wxaConfig = new WxaConfig();
    wxaConfig.setAppId(appId);
    wxaConfig.setAppSecret(appSecret);
    WxaConfigKit.setWxaConfig(wxaConfig);
    // 2数据
    // 2.1 模版id
    String formId = checkFormId(memberId);
    if(formId==null){
      return false;
    }
    // 2.2 用户openid
    String openId = getOpenid(memberId);
    if(openId==null){
      return false;
    }
    // 3发送
    return TemplateUtil.doSend(formId,openId,mpTemplateEnum.getTemplateId(),kv);
  }

  public Boolean sendSimple(Long memberId,String mpTemplateEnum,Kv kv) {
    // 1配置(可在启动时配置)
    WxaConfig wxaConfig = new WxaConfig();
    wxaConfig.setAppId(appId);
    wxaConfig.setAppSecret(appSecret);
    WxaConfigKit.setWxaConfig(wxaConfig);
    // 2数据
    // 2.1 模版id
    String formId = checkFormId(memberId);
    if (formId == null) {
      return false;
    }
    // 2.2 用户openid
    String openId = getOpenid(memberId);
    if (openId == null) {
      return false;
    }
    // 3发送
    return TemplateUtil.doSend(formId,openId,mpTemplateEnum,kv);
  }

  /**
   * 用户openid
   * @param memberId
   * @return
   */
  public String getOpenid(Long memberId){
    List<MemberAccount> list = memberAccountMapper.findByMember(memberId);
    if(list==null||list.size()==0){
      log.error("send template message error :不存在openId");
      return null;
    }
    String openId = null;
    for(MemberAccount memberAccount:list){
      if(Objects.equals(memberAccount.getType(),1)){
        openId = memberAccount.getUsername();
        break;
      }
    }
    return openId;
  }

  /**
   * 模版id
   * @param memberId
   * @return
   */
  public String checkFormId(Long memberId){
    List<FormId> formIdList = formIdMapper.selectByMap(Kv.by("MEMBER_ID",memberId).set("STATUS", FormIdStatusEnum.USE_ABLE.getCode()));
    if (formIdList == null || formIdList.size() <= 0) {
      log.error("send template message error :不存在formId");
      return null;
    }
    FormId formId = formIdList.get(0);
    formIdMapper.updateById(formId.setStatus(FormIdStatusEnum.USE_LESS.getCode()));
    return formId.getFormId();
  }

}
