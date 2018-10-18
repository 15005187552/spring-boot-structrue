package com.ljwm.gecko.base.service;

import cn.hutool.core.collection.CollectionUtil;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljwm.aliyun.springboot.service.SmsService;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.dao.MemberInfoDao;
import com.ljwm.gecko.base.dao.MobileCodeDao;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.entity.MemberAccount;
import com.ljwm.gecko.base.entity.MemberPassword;
import com.ljwm.gecko.base.entity.MobileCode;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.enums.LoginType;
import com.ljwm.gecko.base.enums.SMSTemplateEnum;
import com.ljwm.gecko.base.mapper.*;
import com.ljwm.gecko.base.model.dto.*;
import com.ljwm.gecko.base.utils.EnumUtil;
import com.ljwm.gecko.base.utils.IpUtil;
import com.ljwm.gecko.base.utils.StringUtil;
import com.ljwm.gecko.base.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  MobileCodeMapper mobileCodeMapper;

  @Autowired
  MemberInfoDao memberInfoDao;

  @Autowired
  MemberPasswordMapper memberPasswordMapper;

  @Autowired
  MemberAccountMapper memberAccountMapper;

  @Autowired
  GuestMapper guestMapper;

  @Autowired
  MemberMapper memberMapper;

  @Autowired
  SmsService smsService;

  @Transactional
  public Result getSMS(RegisterForm registerForm, HttpServletRequest request) {
    if(registerForm.getAction() == 1) {
      Member member = memberInfoDao.select(registerForm.getPhoneNum());
      if (member != null && member.getDisabled()== DisabledEnum.ENABLED.getInfo()) {
        return fail(ResultEnum.DATA_ERROR.getCode(), "该用户已注册！");
      }
    }
    Long currentTime = System.currentTimeMillis();//获取当前时间
    String phoneNum = registerForm.getPhoneNum();
    MobileCode mobileCode = mobileCodeDao.find(phoneNum, registerForm.getAction());
    if(mobileCode != null){
      Long lastTime = mobileCode.getUpdateTime();
      Long time = currentTime - lastTime;
      if(time < 60000){
        return fail(ResultEnum.DATA_ERROR.getCode(),"60秒内请不要重复发送");
      }
      List<MobileCode> list = mobileCodeMapper.selectList(new QueryWrapper<MobileCode>().eq(MobileCode.MOBILE, phoneNum));
      int index = 0;
      for (MobileCode mobileCode1 : list){
        Long lastTime1 = mobileCode1.getUpdateTime();
        if(TimeUtil.isSameDay(lastTime1, currentTime)){
          index = index + mobileCode1.getDayIndex();
        }
      }
      //如果同一天，看是否超过了5条短信
      if(index >= 5){
        return fail(ResultEnum.DATA_ERROR.getCode(),"你今天发送的验证码已经超过限制次数！");
      }
      //判断是否为同一天,
      if(TimeUtil.isSameDay(lastTime, currentTime)){
        mobileCode.setDayIndex(Integer.valueOf(mobileCode.getDayIndex()+1));
      } else {
        mobileCode.setDayIndex(1);
      }
      mobileCode.setUpdateTime(currentTime)
        .setCode(sendSMSCode(phoneNum, registerForm.getAction())).setFromIp(IpUtil.getIPAddr(request));
      mobileCodeDao.update(mobileCode);
    } else {
      mobileCode = new MobileCode().setCode(sendSMSCode(phoneNum, registerForm.getAction())).setCode(phoneNum)
        .setFromIp(IpUtil.getIPAddr(request)).setCreateTime(currentTime).setDayIndex(1).setUpdateTime(currentTime)
        .setType(registerForm.getAction());
      mobileCodeDao.insert(mobileCode);
    }
    return success("成功");
  }

  public String sendSMSCode(String phoneNum, Integer action) {
    String s = "";
    while (s.length() < 6)
      s += (int) (Math.random() * 10);
    Map params = new HashMap();
    params.put("code", s);
    String a =  EnumUtil.getEnumBycode(SMSTemplateEnum.class, action).getTemplateCode();
    SendSmsResponse response = smsService.send(phoneNum, EnumUtil.getEnumBycode(SMSTemplateEnum.class, action).getTemplateCode(), params);
    return s;
  }

  @Transactional
  public Result register(RegisterMemberForm registerMemberForm) {
    //根据手机号跟验证码查询是否输入正确，正确即注册成为会员
    String code = registerMemberForm.getCheckCode();
    String phoneNum = registerMemberForm.getPhoneNum();
    String userName = registerMemberForm.getUserName();
    MobileCode mobileCode = mobileCodeDao.select(code, phoneNum);
    //小程序只是绑定手机号的操作，其账号密码无实际意义
    if(mobileCode != null){
      Member member = memberInfoDao.select(phoneNum);
      Long memberId = null;
      if (member != null){
        memberId = member.getId();
        List<MemberAccount> list = memberInfoDao.selectAccount(userName, memberId);
        if(CollectionUtil.isNotEmpty(list)) {
          return fail(ResultEnum.DATA_ERROR.getCode(), "该用户已注册！");
        }
        member.setDisabled(DisabledEnum.ENABLED.getInfo());
        memberMapper.updateById(member);
      } else {
        member = memberInfoDao.insert(phoneNum);
        memberId = member.getId();
      }
      log.debug("Saved member :{}", member);
      guestMapper.updateByGuestId(registerMemberForm.getUserName(), memberId);
     /* String salt = StringUtil.salt();
      password = SecurityKit.passwordMD5(password, salt);
      MemberPassword memberPassword = memberInfoDao.insertPassword(salt, password, new Date());
      log.debug("Saved password: {}", memberPassword);
      MemberAccount memberAccount;
      if(StrUtil.isBlank(password)) {*/
      MemberAccount memberAccount = memberInfoDao.insertAccount(userName, LoginType.WX_APP.getCode(), memberId, null);
      memberInfoDao.insertAccount(phoneNum, LoginType.MOBILE.getCode(), memberId, null);
     /* } else {
        memberAccount = memberInfoDao.insertAccount(userName, LoginType.MOBILE.getCode(), memberId, memberPassword.getId());
      }*/
      log.debug("Saved account: {}", memberAccount);
      Map<String, Object> map = new HashedMap();
      map.put("phoneNum", phoneNum);
      return success(map);
    }

    return fail(ResultEnum.DATA_ERROR.getCode(), "验证码错误！");
  }

  @Transactional
  public Result registerPC(RegisterPCForm registerPCForm) {
    //根据手机号跟验证码查询是否输入正确，正确即注册成为会员
    String code = registerPCForm.getCheckCode();
    String phoneNum = registerPCForm.getPhoneNum();
    String password = registerPCForm.getPassword();
    MobileCode mobileCode = mobileCodeDao.select(code, phoneNum);
    if(mobileCode != null){
      Member member = memberInfoDao.select(phoneNum);
      Long memberId =null;
      if (member != null && member.getDisabled()!=DisabledEnum.ENABLED.getInfo()){
        List<MemberAccount> list = memberInfoDao.selectAccount(phoneNum, memberId);
        if(CollectionUtil.isNotEmpty(list)) {
          return fail(ResultEnum.DATA_ERROR.getCode(), "该用户已注册！");
        }
      } else {
        member = memberInfoDao.insert(phoneNum);
        memberId = member.getId();
      }
      log.debug("Saved member :{}", member);
      guestMapper.updateByGuestId(registerPCForm.getGuestId(), memberId);
      String salt = StringUtil.salt();
      password = SecurityKit.passwordMD5(password, salt);
      MemberPassword memberPassword = memberInfoDao.insertPassword(salt, password, new Date());
      log.debug("Saved password: {}", memberPassword);
      MemberAccount memberAccount;
      memberAccount = memberInfoDao.insertAccount(phoneNum, LoginType.MOBILE.getCode(), memberId, memberPassword.getId());
      log.debug("Saved account: {}", memberAccount);
      return success("注册成功！");
    }
    return fail(ResultEnum.DATA_ERROR.getCode(), "验证码错误！");
  }

  @Transactional
  public Result setPasswordWX(PasswordForm passwordForm) {
    String code = passwordForm.getCheckCode();
    String phoneNum = passwordForm.getPhoneNum();
    String password = passwordForm.getPassword();
    MobileCode mobileCode = mobileCodeDao.select(code, phoneNum);
    if(mobileCode != null){
      Member member = memberInfoDao.select(phoneNum);
      String salt = StringUtil.salt();
      password = SecurityKit.passwordMD5(password, salt);
      if(member != null){
        Long memberId = member.getId();
        List<MemberAccount> list = memberInfoDao.selectAccount(phoneNum, memberId);
        MemberPassword memberPassword;
        if (CollectionUtil.isNotEmpty(list)){
          MemberAccount memberAccount = list.get(0);
          if (memberAccount.getPasswordId() != null) {
            memberPassword = memberPasswordMapper.selectById(memberAccount.getPasswordId());
            memberPassword.setSalt(salt).setPassword(password).setLastModifyTime(new Date());
            memberPasswordMapper.updateById(memberPassword);
          }else {
            memberPassword = new MemberPassword();
            memberPassword.setSalt(salt).setPassword(password).setLastModifyTime(new Date());
            memberPasswordMapper.insert(memberPassword);
          }
          memberAccount.setPasswordId(memberPassword.getId());
          memberAccountMapper.updateById(memberAccount);
        }
        /*List<MemberAccount> accountList = memberAccountMapper.selectList(new QueryWrapper<MemberAccount>().eq(MemberAccount.MEMBER_ID, memberId));
        if(CollectionUtil.isEmpty(accountList)){
          throw new LogicException("该用户不是会员");
        }
        for (MemberAccount memberAccount :accountList){
          memberAccount.setPasswordId(memberPassword.getId());
          memberAccountMapper.updateById(memberAccount);
        }*/
      }
      return success("成功！");
    }
    return fail(ResultEnum.DATA_ERROR.getCode(), "验证码错误！");
  }

  @Transactional
  public Result modifyPassword(ModifyPasswordForm modifyPasswordForm) {
    String phoneNum = modifyPasswordForm.getPhoneNum();
    String oldPassword = modifyPasswordForm.getOldPassword();
    String newPassword = modifyPasswordForm.getNewPassword();
    Member member = memberInfoDao.select(phoneNum);
    if(member == null){
      return fail(ResultEnum.DATA_ERROR.getCode(), "该手机号未注册！");
    }
    List<MemberAccount> list = memberInfoDao.selectAccount(phoneNum, member.getId());
    if (CollectionUtil.isNotEmpty(list)){
      Long passwordId = list.get(0).getPasswordId();
      MemberPassword memberPassword = memberPasswordMapper.selectById(passwordId);
      String password = SecurityKit.passwordMD5(oldPassword, memberPassword.getSalt());
      if (!password.equals(memberPassword.getPassword())){
        return fail(ResultEnum.DATA_ERROR.getCode(), "旧密码不正确！");
      }
      String salt = StringUtil.salt();
      memberPassword.setSalt(salt).setPassword(SecurityKit.passwordMD5(newPassword, salt)).setLastModifyTime(new Date());
      memberPasswordMapper.updateById(memberPassword);
      return success("成功！");
    }
    return fail(ResultEnum.DATA_ERROR.getCode(), "该手机号未注册！");
  }
}
