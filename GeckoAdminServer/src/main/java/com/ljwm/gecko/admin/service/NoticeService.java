package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.form.NoticeQuery;
import com.ljwm.gecko.admin.model.form.NoticeSaveForm;
import com.ljwm.gecko.base.entity.Notice;
import com.ljwm.gecko.base.enums.DisabledEnum;
import com.ljwm.gecko.base.mapper.NoticeMapper;
import com.ljwm.gecko.base.model.vo.admin.NoticeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Author: xixil
 * Date: 2018/10/10 10:39
 * RUA
 */

@Slf4j
@Service
@SuppressWarnings("all")
public class NoticeService {

  @Autowired
  private CommonService commonService;

  @Autowired
  private NoticeMapper noticeMapper;

  @Transactional
  public NoticeVo save(NoticeSaveForm form) {
    Notice notice = null;
    if (form.getId() != null)
      notice = objNoTNull(form.getId());
    if (notice == null)
      notice = new Notice().setCreateTime(DateTime.now());
    BeanUtil.copyProperties(form,notice);
    commonService.insertOrUpdate(notice,noticeMapper);
    return new NoticeVo(notice);
  }

  public Page<NoticeVo> find(NoticeQuery query) {
    return commonService.find(query,(p,q) -> noticeMapper.find(p,BeanUtil.beanToMap(q)));
  }

  @Transactional
  public Boolean disabled(Long id) {
    Notice notice = objNoTNull(id);
    notice.setDisabled(
      Objects.equals(notice.getDisabled(),DisabledEnum.ENABLED.getInfo()) ?
        DisabledEnum.DISABLED.getInfo() : DisabledEnum.ENABLED.getInfo());
    noticeMapper.updateById(notice);
    return notice.getDisabled();
  }

  @Transactional
  public void delete(Long id) {
    Notice notice = objNoTNull(id);
    noticeMapper.deleteById(notice);
  }

  @Transactional
  public Notice objNoTNull(Long id) {
    Notice notice = noticeMapper.selectById(id);
    if (notice == null) throw new LogicException("不存在id为" + id + "的公告");
    return notice;
  }
}
