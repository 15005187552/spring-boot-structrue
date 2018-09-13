package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.form.PaperQuery;
import com.ljwm.gecko.admin.model.form.PaperSaveForm;
import com.ljwm.gecko.base.entity.Paper;
import com.ljwm.gecko.base.mapper.PaperMapper;
import com.ljwm.gecko.base.model.vo.admin.PaperAdminVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@SuppressWarnings("ALL")
public class PaperService {
  @Autowired
  private CommonService commonService;

  @Autowired
  private PaperMapper paperMapper;

  @Transactional
  public Paper save(PaperSaveForm paperSaveForm) {
    return Optional
      .ofNullable(paperSaveForm)
      .map(form -> {
        Paper paper = null;
        if (form.getId() != null)
          paper = paperMapper.selectById(form.getId()).setUpdateTime(DateTime.now());
        if (paper == null)
          paper = new Paper().setCreateTime(DateTime.now());
        paper.setName(form.getName()).setType(form.getType());
        commonService.insertOrUpdate(paper, paperMapper);
        return paper;
      }).get();
  }

  public Page<PaperAdminVo> find(PaperQuery query) {
    return commonService.find(query, (p, q) -> paperMapper.find(p, BeanUtil.beanToMap(query)));
  }

  @Transactional
  public void delete(Integer id) {
    Paper paper = Optional.of(id).map(i -> paperMapper.selectById(i)).get();
    if (paper == null) throw new LogicException(ResultEnum.DATA_ERROR, "未知道id为" + id + "的证件类型");
    if (!paperMapper.deleteAble(id)) throw new LogicException(ResultEnum.DATA_ERROR, "id为" + id + "的证件类型正在使用");
    paperMapper.deleteById(id);
  }
}
