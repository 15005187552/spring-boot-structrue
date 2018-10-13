package com.ljwm.gecko.base.service;

import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.enums.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Janiffy
 * @date 2018/8/28 9:25
 */
@Service
public class EnumService {

  public Result country() {
    return Result.success(Arrays.stream(CountryType.values()).map(countryType -> Kv.by("code", countryType.getCode()).set("value", countryType.getName())).toArray());
  }

  public Result company() {
    return Result.success(Arrays.stream(CompanyType.values()).map(companyType -> Kv.by("code", companyType.getCode()).set("value", companyType.getName())).toArray());
  }

  public Result certificate() {
    CertificateType[] c = CertificateType.values();
    System.out.println("1");
    return Result.success(Arrays.stream(CertificateType.values()).map(certificateType -> Kv.by("code", certificateType.getCode()).set("value", certificateType.getName())).toArray());
  }

  public Result declare() {
    return Result.success(Arrays.stream(DeclareType.values()).map(declareType -> Kv.by("code",declareType.getCode()).set("value",declareType.getName())).toArray());
  }

  public Result roleType() {
    return Result.success(Arrays.stream(RoleCodeType.values()).map(roleCodeType -> Kv.by("digit",roleCodeType.getDigit()).set("role",roleCodeType.getRole()).set("value",roleCodeType.getValue())).toArray());
  }

  public Result smsmTemplateType() {
    return Result.success(Arrays.stream(SMSTemplateEnum.values()).map(smsTemplateEnum -> Kv.by("code",smsTemplateEnum.getCode()).set("value",smsTemplateEnum.getName())).toArray());
  }

  public Result educatiocnType() {
    return Result.success(Arrays.stream(EducationEnum.values()).map(educationEnum -> Kv.by("code", educationEnum.getCode()).set("value", educationEnum.getName())).toArray());
  }
}

