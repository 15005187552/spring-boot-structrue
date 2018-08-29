package com.ljwm.gecko.base.service;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.gecko.base.dao.MobileCodeDao;
import com.ljwm.gecko.base.entity.MobileCode;
import com.ljwm.gecko.base.model.dto.RegisterForm;
import com.ljwm.gecko.base.utils.IpUtil;
import com.ljwm.gecko.base.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.ljwm.bootbase.dto.Result.fail;
import static com.ljwm.bootbase.dto.Result.success;

/**
 * @author Janiffy
 * @date 2018/8/28 19:02
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class RegisterService {

  @Autowired
  MobileCodeDao mobileCodeDao;

  public Result getSMS(RegisterForm registerForm, HttpServletRequest request) {
    Long currentTime = System.currentTimeMillis();//获取当前时间
    String phoneNum = registerForm.getPhoneNum();
    MobileCode mobileCode = mobileCodeDao.find(phoneNum);
    if(mobileCode != null){
      Long lastTime = mobileCode.getCreateTime();
      Long time = currentTime - lastTime;
      if(time < 60000){
        return fail(ResultEnum.DATA_ERROR.getCode(),"60秒内请不要重复发送");
      }
      MobileCode updateMobile = null;
      //判断是否为同一天,
      if(TimeUtil.isSameDay(lastTime, currentTime)){
        //如果同一天，看是否超过了5条短信
        if(mobileCode.getDayIndex() >= 5){
          return fail(ResultEnum.DATA_ERROR.getCode(),"你今天发送的验证码已经超过限制次数！");
        }
        updateMobile = new MobileCode(mobileCode.getId(), sendSMSCode(phoneNum), phoneNum,
          IpUtil.getIPAddr(request), currentTime, Integer.valueOf(mobileCode.getDayIndex()+1));

      } else {
        updateMobile = new MobileCode(mobileCode.getId(), sendSMSCode(phoneNum), phoneNum,
          IpUtil.getIPAddr(request), currentTime, 1);
      }
      mobileCodeDao.update(updateMobile);
    } else {
      MobileCode insertMobile = new MobileCode(null, sendSMSCode(phoneNum), phoneNum,
        IpUtil.getIPAddr(request), currentTime, 1);
      mobileCodeDao.insert(insertMobile);
    }
    return success("成功");
  }

  private String sendSMSCode(String phoneNum) {
    return "123346";
  }
}
