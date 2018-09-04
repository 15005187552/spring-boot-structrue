package com.ljwm.gecko.client.service;

import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.security.SecurityKit;
import com.ljwm.gecko.base.entity.Company;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.enums.IdentificationType;
import com.ljwm.gecko.base.mapper.CompanyMapper;
import com.ljwm.gecko.base.model.bean.AppInfo;
import com.ljwm.gecko.base.utils.FileKit;
import com.ljwm.gecko.client.model.dto.CompanyForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import static com.ljwm.bootbase.dto.Result.fail;

/**
 * @author Janiffy
 * @date 2018/9/3 10:55
 */
@Service
@Slf4j
public class CompanyService {

  @Autowired
  AppInfo appInfo;

  @Autowired
  CompanyMapper companyMapper;

  public Object commit(CompanyForm companyForm) {
    String fileName = FileKit.saveUploadFile(companyForm.getFile(), appInfo.getFilePath(), appInfo.getPic());
    if (StrUtil.isEmpty(fileName))
      return fail(ResultEnum.FAIL_TO_SAVE_FILE);
    Company company = new Company(null, companyForm.getName(), companyForm.getType(), new Date(),
      IdentificationType.NO_IDENTI.getCode(), DisabledEnum.ENABLED.getCode(), companyForm.getCode(), SecurityKit.currentId(),
    fileName, null, null, new Date(), null, companyForm.getProvCode(), companyForm.getCityCode(), companyForm.getAreaCode(), companyForm.getAddress());
    companyMapper.insert(company);
    return "提交成功！";
  }


  public Object upload(MultipartFile[] updateFiles) {
    for (int i=0; i<updateFiles.length; i++) {
      String fileName = FileKit.saveUploadFile(updateFiles[i], appInfo.getFilePath(), appInfo.getPic());
      if (StrUtil.isEmpty(fileName))
        return fail(ResultEnum.FAIL_TO_SAVE_FILE);
    }
    return "上传成功！";
  }
}
