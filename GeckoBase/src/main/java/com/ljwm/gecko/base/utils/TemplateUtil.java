package com.ljwm.gecko.base.utils;

import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.wxaapp.api.WxaTemplate;
import com.jfinal.wxaapp.api.WxaTemplateApi;
import com.ljwm.bootbase.dto.Kv;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by user on 2018-10-10.
 */
@Slf4j
public class TemplateUtil {

  /**
   * 发送
   * @param formId
   * @param openId
   * @param templateId
   * @param kv
   * @return
   */
  public static Boolean doSend(String formId, String openId, String templateId, Kv kv) {
    WxaTemplate wxaTemplate = new WxaTemplate()
      .setTemplate_id(templateId)
      .setForm_id(formId)
      .setTouser(openId);
    if(kv.containsKey("page")) {
      wxaTemplate.setPage(kv.getStr("page"));
      kv.delete("page");
    }
    for(Object key:kv.keySet().toArray()){
      wxaTemplate.add(key.toString(),kv.getStr(key));
    }
    log.info("send template message {}", wxaTemplate);
    WxaTemplateApi wxaTemplateApi = new WxaTemplateApi();
    return FunctionUtil.retryOnException(3, () -> {
      ApiResult result = wxaTemplateApi.send(wxaTemplate.build());
      log.info("send template message response {}",result);
      return result.isSucceed();
    });
  }
}
