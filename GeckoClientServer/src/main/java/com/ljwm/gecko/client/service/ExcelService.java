package com.ljwm.gecko.client.service;

import cn.hutool.core.collection.CollectionUtil;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.entity.CompanyUser;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.enums.ActivateEnum;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.enums.RoleCodeType;
import com.ljwm.gecko.base.mapper.CompanyUserMapper;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.utils.excelutil.ExcelLogs;
import com.ljwm.gecko.base.utils.excelutil.ExcelUtil;
import com.ljwm.gecko.client.dao.CompanyUserDao;
import com.ljwm.gecko.client.model.dto.PersonInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Janiffy
 * @date 2018/9/11 13:10
 */
@Slf4j
@Service
public class ExcelService {

  @Autowired
  MemberMapper memberMapper;

  @Autowired
  CompanyUserMapper companyUserMapper;

  @Autowired
  CompanyUserDao companyUserDao;

  public void improtPersonInfo(MultipartFile file, Long companyId) throws Exception {
    Map<String, Object> findMap = new HashedMap();
    findMap.put("MEMBER_ID", SecurityKit.currentId());
    findMap.put("COMPANY_ID", companyId);
    findMap.put("DISABLED", DisabledEnum.ENABLED.getCode());
    findMap.put("ACTIVATED", ActivateEnum.ENABLED.getCode());
    List<CompanyUser> companyUserList = companyUserMapper.selectByMap(findMap);
    if(CollectionUtil.isEmpty(companyUserList)){
      throw new LogicException("你没有该操作的权限");
    }
    String roleCode = companyUserList.get(0).getRolesCode().toString();
    int m = roleCode.length()- RoleCodeType.ITIN.getDigit();
    String code = roleCode.substring(m, m+1);
    if (!code.equals(RoleCodeType.ITIN.getValue())){
      throw new LogicException("你没有该操作的权限");
    }
    InputStream inputStream = file.getInputStream();
    ExcelLogs logs =new ExcelLogs();
    Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "yyyy/MM/dd HH:mm:ss", logs , 0);
    for(Map m:importExcel){
      PersonInfoDto personInfoDto = new PersonInfoDto();
      Field[] fields = PersonInfoDto.class.getDeclaredFields();
      String[] fieldNames = new String[fields.length];
      int i = 0,index = 0;
      for (Field f : fields) {
        fieldNames[i] = f.getName();
        i++;
      }
      for (Object key:m.keySet()){
        Field temFiels = PersonInfoDto.class.getDeclaredField(fieldNames[index]);
        temFiels.setAccessible(true);
        temFiels.set(personInfoDto, m.get(key));
        index++;
      }
      Map<String, Object> map = new HashedMap();
      map.put("REG_MOBILE", personInfoDto.getRegMobile());
      List<Member> list = memberMapper.selectByMap(map);
      Long memberId;
      if(CollectionUtil.isNotEmpty(list)){
        memberId = list.get(0).getId();
      } else {
        //发送短信邀请码  并且插入数据库
        Member member = new Member();
        member.setRegMobile(personInfoDto.getRegMobile())
              .setCreateTime(new Date())
              .setDisabled(DisabledEnum.DISABLED.getCode());
        memberMapper.insert(member);
        memberId = member.getId();
      }
      String[] str = new String[RoleCodeType.values().length];
      StringBuffer stringBuffer = new StringBuffer();
      for (int j = 0; j < str.length; j++) {
        str[j] = "0";
        stringBuffer.append(str[j]);
      }
      Integer role = Integer.valueOf(stringBuffer.toString());
      companyUserDao.insertOrUpdate(memberId, companyId, role);


      log.debug("{}", personInfoDto);
    }
  }
}
