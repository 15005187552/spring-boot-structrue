package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.form.FileTemplateQuery;
import com.ljwm.gecko.base.entity.Advertisement;
import com.ljwm.gecko.base.entity.FileTemplate;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.FileTemplateMapper;
import com.ljwm.gecko.base.model.dto.FileTemplateDto;

import com.ljwm.gecko.base.model.vo.FileTemplateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * 文件操作服务
 * Created by yuzhou on 2018/10/22.
 */
@Slf4j
@Service
public class FileService {

  @Autowired
  private FileTemplateMapper fileMapper;

  @Autowired
  private CommonService commonService;

public void saveTemplateFile(FileTemplateDto fileTemplateDto){
  FileTemplate fileTemplate = new FileTemplate();
  BeanUtil.copyProperties(fileTemplateDto,fileTemplate);
  fileTemplate.setCreateTime(DateUtil.date());
  fileTemplate.setDisable(DisabledEnum.ENABLED.getCode());
  fileMapper.insert(fileTemplate);
}

  /**
   * 分页显示上传文件
   *
   * @param query
   * @return
   */
  @Transactional
  public Page<FileTemplateVo> find(FileTemplateQuery query) {
    return commonService.find(query, (p, q) -> fileMapper.findByPage(p, BeanUtil.beanToMap(query)));

  }
/**
* 文件模板启用和禁用
* @param id    文件模板id
*/
  @Transactional
  public void disableTemplate(Integer id) {
    FileTemplate fileTemplate = temIsExist(id);
    fileTemplate.setDisable(Objects.equals(fileTemplate.getDisable(),DisabledEnum.ENABLED.getCode())?
      DisabledEnum.DISABLED.getCode() :
      DisabledEnum.ENABLED.getCode());
    commonService.insertOrUpdate(fileTemplate, fileMapper);
  }

  private FileTemplate temIsExist(Integer id) {
    FileTemplate fileTemplate = fileMapper.selectById(id);
    if (fileTemplate == null) throw new LogicException(ResultEnum.DATA_ERROR, "未找到id为" + id + "的文件模板");
    return fileTemplate;
  }
}
