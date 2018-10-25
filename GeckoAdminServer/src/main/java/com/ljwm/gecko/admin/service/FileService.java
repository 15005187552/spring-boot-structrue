package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.form.FileTemplateQuery;
import com.ljwm.gecko.base.entity.FileTemplate;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.FileTemplateMapper;
import com.ljwm.gecko.base.model.dto.FileTemplateDto;

import com.ljwm.gecko.base.model.vo.FileTemplateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
  public Page<FileTemplateVo> find(FileTemplateQuery query) {
    return commonService.find(query, (p, q) -> fileMapper.findByPage(p, BeanUtil.beanToMap(query)));

  }
}
