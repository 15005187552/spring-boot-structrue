package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.bean.ApplicationInfo;
import com.ljwm.gecko.base.bean.Constant;
import com.ljwm.gecko.base.entity.NaturalPerson;
import com.ljwm.gecko.base.mapper.NaturalPersonMapper;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.client.model.dto.PersonInfoForm;
import com.ljwm.gecko.client.model.vo.NaturalPersonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
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
      File file = new File(appInfo.getFilePath()+ Constant.PERSON + personInfoForm.getMemberId());
      if(!file.exists()){
        file.mkdirs();
      }
    }
    String destDir = appInfo.getFilePath() + Constant.PERSON + naturalPerson.getMemberId() + "/";
    if (StrUtil.isNotBlank(personInfoForm.getDisablityPath()) && personInfoForm.getDisablityPath().indexOf(Constant.HTTP) == -1) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getDisablityPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setDisablityPath(Constant.PERSON+ naturalPerson.getMemberId() + "/" + personInfoForm.getDisablityPath());
    }
    if (StrUtil.isNotBlank(personInfoForm.getAcademicPath()) && personInfoForm.getAcademicPath().indexOf(Constant.HTTP) == -1) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getAcademicPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setAcademicPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getAcademicPath());
    }
    if (StrUtil.isNotBlank(personInfoForm.getOldPath()) && personInfoForm.getOldPath().indexOf(Constant.HTTP) == -1) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getOldPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setOldPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getOldPath());
    }
    if (StrUtil.isNotBlank(personInfoForm.getMatrtyrPath()) && personInfoForm.getMatrtyrPath().indexOf(Constant.HTTP) == -1) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getMatrtyrPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setMatrtyrPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getMatrtyrPath());
    }
    if (StrUtil.isNotBlank(personInfoForm.getProfessorPath()) && personInfoForm.getProfessorPath().indexOf(Constant.HTTP) == -1) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getProfessorPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setProfessorPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getProfessorPath());
    }
    naturalPersonMapper.updateById(naturalPerson);
    return Result.success("成功！");
  }

  public Result findByMemberId(Long memberId) {
    NaturalPerson naturalPerson= naturalPersonMapper.selectById(memberId);
    if (naturalPerson != null) {
      NaturalPersonVo naturalPersonVo = new NaturalPersonVo();
      BeanUtil.copyProperties(naturalPerson, naturalPersonVo);
      if (StrUtil.isNotBlank(naturalPersonVo.getAcademicPath())) {
        naturalPersonVo.setAcademicPath(appInfo.getWebPath() + naturalPersonVo.getAcademicPath());
      }
      if (StrUtil.isNotBlank(naturalPersonVo.getDisablityPath())) {
        naturalPersonVo.setDisablityPath(appInfo.getWebPath() + naturalPersonVo.getDisablityPath());
      }
      if (StrUtil.isNotBlank(naturalPersonVo.getMatrtyrPath())) {
        naturalPersonVo.setMatrtyrPath(appInfo.getWebPath() + naturalPersonVo.getMatrtyrPath());
      }
      if (StrUtil.isNotBlank(naturalPersonVo.getOldPath())) {
        naturalPersonVo.setOldPath(appInfo.getWebPath() + naturalPersonVo.getOldPath());
      }
      if (StrUtil.isNotBlank(naturalPersonVo.getProfessorPath())) {
        naturalPersonVo.setProfessorPath(appInfo.getWebPath() + naturalPersonVo.getProfessorPath());
      }
      return Result.success(naturalPersonVo);
    }
    return Result.success(null);
  }
}
