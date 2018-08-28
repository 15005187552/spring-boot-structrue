package com.ljwm.gecko.base.service;

import com.ljwm.bootbase.dto.Kv;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.enums.CertificateType;
import com.ljwm.gecko.base.enums.CompanyType;
import com.ljwm.gecko.base.enums.CountryType;
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
    return Result.success(Arrays.stream(CertificateType.values()).map(certificateType -> Kv.by("code", certificateType.getCode()).set("value", certificateType.getName())).toArray());
  }


  public Result declare() {
    return Result.success(Arrays.stream(CertificateType.values()).map(CertificateType -> Kv.by("code",CertificateType.getCode()).set("value",CertificateType.getName())).toArray());
  }
}

