package com.ljwm.gecko.client.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.ljwm.bootbase.dto.Result;
import com.ljwm.gecko.base.entity.NaturalPerson;
import com.ljwm.gecko.base.mapper.NaturalPersonMapper;
import com.ljwm.gecko.base.utils.Fileutil;
import com.ljwm.gecko.client.constant.Constant;
import com.ljwm.gecko.client.model.ApplicationInfo;
import com.ljwm.gecko.client.model.dto.PersonInfoForm;
import com.ljwm.gecko.client.model.vo.NaturalPersonVo;
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
      File file = new File(appInfo.getFilePath()+ Constant.PERSON + personInfoForm.getMemberId());
      if(!file.exists()){
        file.mkdirs();
      }
    }
    String destDir = appInfo.getFilePath() + Constant.PERSON + naturalPerson.getMemberId() + "/";
    if (StrUtil.isNotBlank(personInfoForm.getDisablityPath())) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getDisablityPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setDisablityPath(Constant.PERSON+ naturalPerson.getMemberId() + "/" + personInfoForm.getDisablityPath());
    }
    if (StrUtil.isNotBlank(personInfoForm.getAcademicPath())) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getAcademicPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setDisablityPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getAcademicPath());
    }
    if (StrUtil.isNotBlank(personInfoForm.getOldPath())) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getOldPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setDisablityPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getOldPath());
    }
    if (StrUtil.isNotBlank(personInfoForm.getMatrtyrPath())) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getMatrtyrPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setDisablityPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getMatrtyrPath());
    }
    if (StrUtil.isNotBlank(personInfoForm.getProfessorPath())) {
      String srcPath = appInfo.getFilePath()+Constant.CACHE + personInfoForm.getProfessorPath();
      Fileutil.cutGeneralFile(srcPath, destDir);
      naturalPerson.setDisablityPath(Constant.PERSON + naturalPerson.getMemberId() + "/" + personInfoForm.getProfessorPath());
    }
    naturalPersonMapper.updateById(naturalPerson);
    return Result.success("成功！");
  }

  public Result findByMemberId(Long memberId) {
    NaturalPerson naturalPerson= naturalPersonMapper.selectById(memberId);
    NaturalPersonVo naturalPersonVo = new NaturalPersonVo();
    BeanUtil.copyProperties(naturalPerson, naturalPersonVo);
    if(!naturalPersonVo.getAcademicPath().contains(Constant.HTTP)){
      naturalPersonVo.setAcademicPath(appInfo.getWebPath()+naturalPersonVo.getAcademicPath());
    }
    if(!naturalPersonVo.getDisablityPath().contains(Constant.HTTP)){
      naturalPersonVo.setDisablityPath(appInfo.getWebPath()+naturalPersonVo.getDisablityPath());
    }
    if(!naturalPersonVo.getMatrtyrPath().contains(Constant.HTTP)){
      naturalPersonVo.setMatrtyrPath(appInfo.getWebPath()+naturalPersonVo.getMatrtyrPath());
    }
    if(!naturalPersonVo.getOldPath().contains(Constant.HTTP)){
      naturalPersonVo.setOldPath(appInfo.getWebPath()+naturalPersonVo.getOldPath());
    }
    if(!naturalPersonVo.getProfessorPath().contains(Constant.HTTP)){
      naturalPersonVo.setProfessorPath(appInfo.getWebPath()+naturalPersonVo.getProfessorPath());
    }
    return Result.success(naturalPersonVo);
  }
}
