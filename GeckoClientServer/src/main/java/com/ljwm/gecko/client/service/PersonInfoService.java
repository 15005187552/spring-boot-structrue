package com.ljwm.gecko.client.service;

import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.entity.NaturalPerson;
import com.ljwm.gecko.base.mapper.NaturalPersonMapper;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.client.model.ApplicationInfo;
import com.ljwm.gecko.client.model.dto.PersonInfoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

/**
 * @author Janiffy
 * @date 2018/9/4 20:12
 */
@Service
public class PersonInfoService {

  @Autowired
  ApplicationInfo appInfo;
  @Autowired
  NaturalPersonMapper naturalPersonMapper;

  public Result commit(PersonInfoForm personInfoForm){
    NaturalPerson naturalPerson = new NaturalPerson();
    naturalPerson.setMemberId(personInfoForm.getMemberId())
                  .setCountry(personInfoForm.getCountry())
                  .setName(personInfoForm.getName())
                  .setCertificate(personInfoForm.getCertificate())
                  .setCertNum(personInfoForm.getCertNum())
                  .setSocialSecu(personInfoForm.getSocialSecu())
                  .setDisablityNum(personInfoForm.getDisablityNum())
                  .setMatrtyrNum(personInfoForm.getMatrtyrNum())
                  .setAcademicNum(personInfoForm.getAcademicNum())
                  .setOldNum(personInfoForm.getOldNum())
                  .setProfessorNum(personInfoForm.getProfessorNum());
    NaturalPerson naturalPersonFind = naturalPersonMapper.selectById(personInfoForm.getMemberId());
    if (naturalPersonFind != null) {
    } else {
      naturalPerson.setCreatTime(new Date());
      naturalPersonMapper.insert(naturalPerson);
      File file = new File(appInfo.getPersonFile() + personInfoForm.getMemberId());
      if(!file.exists()){
        file.mkdirs();
      }
    }
    String destDir = appInfo.getPersonFile() + naturalPerson.getMemberId() + "/";
    if (StrUtil.isNotBlank(personInfoForm.getDisablityPath())) {
      String srcPath = appInfo.getCachePath() + personInfoForm.getDisablityPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setDisablityPath(naturalPerson.getMemberId() + "/" + personInfoForm.getDisablityPath());
    }
    if (StrUtil.isNotBlank(personInfoForm.getAcademicPath())) {
      String srcPath = appInfo.getCachePath() + personInfoForm.getAcademicPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setDisablityPath(naturalPerson.getMemberId() + "/" + personInfoForm.getAcademicPath());
    }
    if (StrUtil.isNotBlank(personInfoForm.getOldPath())) {
      String srcPath = appInfo.getCachePath() + personInfoForm.getOldPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setDisablityPath(naturalPerson.getMemberId() + "/" + personInfoForm.getOldPath());
    }
    if (StrUtil.isNotBlank(personInfoForm.getMatrtyrPath())) {
      String srcPath = appInfo.getCachePath() + personInfoForm.getMatrtyrPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setDisablityPath(naturalPerson.getMemberId() + "/" + personInfoForm.getMatrtyrPath());
    }
    if (StrUtil.isNotBlank(personInfoForm.getProfessorPath())) {
      String srcPath = appInfo.getCachePath() + personInfoForm.getProfessorPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setDisablityPath(naturalPerson.getMemberId() + "/" + personInfoForm.getProfessorPath());
    }
    naturalPersonMapper.updateById(naturalPerson);
    return Result.success("成功！");
  }
}
