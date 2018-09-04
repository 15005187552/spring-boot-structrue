package com.ljwm.gecko.base.service;

import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.dao.MemberInfoDao;
import com.ljwm.gecko.base.dao.MobileCodeDao;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.MemberAccount;
import com.ljwm.gecko.base.entity.MemberPassword;
import com.ljwm.gecko.base.entity.MobileCode;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.mapper.GuestMapper;
import com.ljwm.gecko.base.model.dto.RegisterForm;
import com.ljwm.gecko.base.model.dto.RegisterMemberForm;
import com.ljwm.gecko.base.utils.IpUtil;
import com.ljwm.gecko.base.utils.StringUtil;
import com.ljwm.gecko.base.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

  @Autowired
  MemberInfoDao memberInfoDao;

  @Autowired
  GuestMapper guestMapper;

  public Result getSMS(RegisterForm registerForm, HttpServletRequest request) {
    Long memberId = memberInfoDao.select(registerForm.getPhoneNum());
    if (memberId != null){
      return fail(ResultEnum.DATA_ERROR.getCode(),"该用户已注册！");
    }
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
    return "123456";
  }

  public Result register(RegisterMemberForm registerMemberForm) {
    //根据手机号跟验证码查询是否输入正确，正确即注册成为会员
    String code = registerMemberForm.getCheckCode();
    String phoneNum = registerMemberForm.getPhoneNum();
    String userName = registerMemberForm.getUserName();
    MobileCode mobileCode = mobileCodeDao.select(code, phoneNum);
    if(mobileCode != null){
      Long memberId = memberInfoDao.select(phoneNum);
      if (memberId != null){
        return fail(ResultEnum.DATA_ERROR.getCode(),"该用户已注册！");
      }
      Member member = memberInfoDao.insert(phoneNum);
      memberId = member.getId();
      log.debug("Saved member :{}", member);
      if(memberId != null) {
        guestMapper.updateByGuestId(registerMemberForm.getUserName(), memberId);
      }
      String salt = StringUtil.salt();
      String password = SecurityKit.passwordMD5(userName, salt);
      MemberPassword memberPassword = memberInfoDao.insertPassword(salt, password, new Date());
      log.debug("Saved password: {}", memberPassword);

      MemberAccount memberAccount = memberInfoDao.insertAccount(userName, LoginType.WX_APP.getCode(), memberId, memberPassword.getId());

      log.debug("Saved account: {}", memberAccount);
      return success("注册成功！");
    }

    return fail(ResultEnum.DATA_ERROR.getCode(), "验证码错误！");
  }
}
