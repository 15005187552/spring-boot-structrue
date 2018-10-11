package com.ljwm.gecko.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.admin.model.form.AttendanceQuery;
import com.ljwm.gecko.admin.model.form.AttendanceSaveForm;
import com.ljwm.gecko.admin.model.form.MemberQuery;
import com.ljwm.gecko.base.entity.AttendanceAttribute;
import com.ljwm.gecko.base.entity.Member;
import com.ljwm.gecko.base.mapper.AttendanceAttributeMapper;
import com.ljwm.gecko.base.mapper.MemberMapper;
import com.ljwm.gecko.base.model.form.AttributeForm;
import com.ljwm.gecko.base.model.vo.MemberVo;
import com.ljwm.gecko.base.model.vo.admin.AttendanceAtrVo;
import com.ljwm.gecko.base.service.AttributeAdminService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@SuppressWarnings("all")
public class MaintenanceService {

  @Autowired
  private MemberMapper memberMapper;

  @Autowired
  private CommonService commonService;

  @Autowired
  private AttendanceAttributeMapper attendanceAttributeMapper;

  @Autowired
  private AttributeAdminService attributeAdminService;

  public Page<MemberVo> find(MemberQuery query) {
    return commonService.find(query,(p,q) -> memberMapper.find(p,BeanUtil.beanToMap(query)));
  }

  @Transactional
  public AttendanceAttribute save(AttendanceSaveForm form) {
    return Optional.of(form)
      .map(f -> {
        AttendanceAttribute attribute = null;
        if (!Objects.isNull(f.getId()))
          attribute = attendanceAttributeMapper.selectById(f.getId()).setUpdateTime(DateTime.now());
        if (Objects.isNull(attribute))
          attribute = new AttendanceAttribute().setCreateTime(DateTime.now());
        BeanUtil.copyProperties(form,attribute);
        commonService.insertOrUpdate(attribute,attendanceAttributeMapper);
        return attribute;
      })
      // TODO 简化添加 重复添加冗余数据 为客服端版服务
      .map(bean -> {
        attributeAdminService.save(bean.getClass(),new AttributeForm().setItemId(bean.getId()).setName(bean.getName()));
        return bean;
      })
      .get();
  }

  public Page<AttendanceAtrVo> find(AttendanceQuery query) {
    return commonService.find(query,(p,q) -> attendanceAttributeMapper.find(p,BeanUtil.beanToMap(query)));
  }

  @Transactional
  public void delete(Long id) {
    AttendanceAttribute attribute = attendanceAttributeMapper.selectById(id);
    if (attribute == null) throw new LogicException(ResultEnum.DATA_ERROR,"未找到id为" + id + "的考情字段");
    attendanceAttributeMapper.deleteById(attribute);
  }
}
