package com.ljwm.gecko.base.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljwm.bootbase.enums.ResultEnum;
import com.ljwm.bootbase.exception.LogicException;
import com.ljwm.bootbase.service.CommonService;
import com.ljwm.gecko.base.entity.OtherReduce;
import com.ljwm.gecko.base.mapper.OtherReduceMapper;
import com.ljwm.gecko.base.model.form.AttributeForm;
import com.ljwm.gecko.base.model.form.OtherReduceForm;
import com.ljwm.gecko.base.model.form.OtherReduceQuery;
import com.ljwm.gecko.base.model.vo.OtherReduceVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@SuppressWarnings("all")
public class OtherReduceService {

  @Autowired
  private CommonService commonService;

  @Autowired
  private OtherReduceMapper otherReduceMapper;

  @Autowired
  private AttributeAdminService attributeAdminService;

  /**
   * 分页查询
   *
   * @param query
   * @return
   */
  public Page<OtherReduceVo> findByPage(OtherReduceQuery query) {
    return commonService.find(query, (p, q) -> otherReduceMapper.findByPage(p, BeanUtil.beanToMap(query)));
  }

  public List<OtherReduceVo> find() {
    return otherReduceMapper.find();
  }

  /**
   * 非分页查询
   *
   * @return
   */
  public List<OtherReduce> getOtherReduce() {
    return otherReduceMapper.selectList(null);
  }

  @Transactional
  public OtherReduce save(OtherReduceForm form) {
    return
      Optional
        .ofNullable(form)
        .map(f -> {
          OtherReduce otherReduce = null;
          if (!Objects.isNull(f.getId()))
            otherReduce = otherReduceMapper.selectById(f.getId());
          if (otherReduce == null)
            otherReduce = new OtherReduce();
          BeanUtil.copyProperties(f, otherReduce);
          commonService.insertOrUpdate(otherReduce, otherReduceMapper);
          return otherReduce;
        }).map(bean -> {
          attributeAdminService.save(bean.getClass(), new AttributeForm().setItemId(bean.getId()).setName(bean.getName()));
          return bean;
        }).get();
  }

  @Transactional
  public void delete(Long id) {
    if (!otherReduceMapper.deleteAble(id)) {
      throw new LogicException(ResultEnum.DATA_ERROR, "节点已被使用,无法删除!");
    }
    otherReduceMapper.deleteById(id);
  }
}
