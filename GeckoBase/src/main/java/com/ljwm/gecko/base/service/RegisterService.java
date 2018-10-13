package com.ljwm.gecko.base.service;

import cn.hutool.core.collection.CollectionUtil;
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
import com.ljwm.gecko.base.mapper.GuestMapper;
import com.ljwm.gecko.base.mapper.MemberAccountMapper;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.mapper.MemberPasswordMapper;
import com.ljwm.gecko.base.model.dto.*;
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
  MemberInfoDao memberInfoDao;

  @Autowired
  MemberPasswordMapper memberPasswordMapper;

  @Autowired
  MemberAccountMapper memberAccountMapper;

  @Autowired
  GuestMapper guestMapper;

  @Autowired
  MemberMapper memberMapper;

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
